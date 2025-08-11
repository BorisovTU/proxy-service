package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.dto.*;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.entity.projection.ICorporateActionProjection;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CorporateActionMapper {

    public CorporateActionResponse toResponse(
            List<ICorporateActionProjection> projections,
            String nextCursor,
            boolean hasNext,
            int limit
    ) {
        List<CorporateActionNotificationDTO> data = projections.stream()
                .limit(limit) // На случай если взяли на 1 больше
                .map(this::toDTO)
                .collect(Collectors.toList());

        PaginationResponse pagination = new PaginationResponse();
        pagination.setNext_cursor(nextCursor);
        pagination.setHas_next(hasNext);
        pagination.setPer_page(limit);

        CorporateActionResponse response = new CorporateActionResponse();
        response.setData(data);
        response.setPagination(pagination);
        return response;
    }

    public CorporateActionNotificationDTO toDTO(ICorporateActionProjection projection) {
        CorporateActionNotificationDTO dto = new CorporateActionNotificationDTO();
        CorporateActionNotificationDTO.CorporateActionNotification notification =
                new CorporateActionNotificationDTO.CorporateActionNotification();

        // Маппинг основных полей
        notification.setCorporateActionIssuerID(String.valueOf(projection.getCaid()));
        notification.setCorporateActionType(projection.getCatype());
        notification.setCorpActnEvtId(projection.getReference());
        notification.setEvtTp(projection.getSwiftType());
        notification.setMndtryVlntryEvtTp(projection.getMaVoCode());
        if (projection.getDateRegOwners() != null) {
            notification.setRcrdDt(projection.getDateRegOwners().toString());
            //  notification.setIsin(projection.getIsin());
        }
        // Маппинг финансового инструмента
        CorporateActionNotificationDTO.FinInstrmId finInstrmId =
                new CorporateActionNotificationDTO.FinInstrmId();
        finInstrmId.setIsin(projection.getIsin());
        notification.setFinInstrmId(finInstrmId);

        // Маппинг периода действия
        if (projection.getDateStart() != null && projection.getDateEnd() != null) {
            CorporateActionNotificationDTO.ActnPrd actnPrd =
                    new CorporateActionNotificationDTO.ActnPrd();
            actnPrd.setStartDt(projection.getDateStart().toString());
            actnPrd.setEndDt(projection.getDateEnd().toString());

            CorporateActionNotificationDTO.CorpActnOptnDtls option =
                    new CorporateActionNotificationDTO.CorpActnOptnDtls();
            option.setActnPrd(actnPrd);

            notification.setCorpActnOptnDtls(List.of(option));
        }

        dto.setCorporateActionNotification(notification);
        return dto;
    }
}
