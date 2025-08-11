package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.dto.CorporateActionRequestDTO;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.dto.CorporateActionResponse;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.entity.projection.ICorporateActionProjection;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.mapper.CorporateActionMapper;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.repository.CorpActionsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CorporateActionService {

    private final CorpActionsRepository corporateActionRepository;
    private final CorporateActionMapper corporateActionMapper;

    public CorporateActionResponse findCorporateActions(CorporateActionRequestDTO requestDTO) {
        // Валидация лимита
        int limit = Math.min(requestDTO.getLimit(), 1000);
        if (limit < 1) limit = 50;
        requestDTO.setLimit(limit);

        List<ICorporateActionProjection> results = corporateActionRepository
                .findCorporateActionsNative(requestDTO);

        // Обработка пагинации
        boolean hasNext = results.size() > limit;
        List<ICorporateActionProjection> data = hasNext ?
                results.subList(0, limit) :
                results;

        // Получаем курсор для следующей страницы
        String nextCursor = null;
        if (!data.isEmpty() && hasNext) {
            nextCursor = String.valueOf(data.get(data.size() - 1).getCaid());
        }

        return corporateActionMapper.toResponse(data, nextCursor, hasNext, limit);
    }
}
