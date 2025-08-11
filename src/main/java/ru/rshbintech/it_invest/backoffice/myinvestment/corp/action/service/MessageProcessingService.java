package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.dto.*;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.entity.*;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.entity.message.BaseMessage;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageProcessingService {

    private final MessageRepository messageRepository;
    private final CorpActionsRepository corpActionsRepository;
    private final SecurityRepository securityRepository;
    private final ClientAccRepository clientAccRepository;
    private final ResultRepository resultRepository;
    private final LinkRepository linkRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void processMessage(BaseMessage message) {
        try {
            CorporateActionNotificationDTO dto =
                    objectMapper.readValue(message.getJson(), CorporateActionNotificationDTO.class);

            CorporateActionNotificationDTO.CorporateActionNotification notification =
                    dto.getCorporateActionNotification();

            // 1. Сохранить CorpActions
            CorpActionsEntity corpAction = saveCorpAction(notification);

            // 2. Сохранить Security
            SecurityEntity security = saveSecurity(notification.getFinInstrmId());

            // 3. Сохранить ClientAcc и Result для каждого владельца
            for (CorporateActionNotificationDTO.BnfclOwnrDtls owner : notification.getBnfclOwnrDtls()) {
                ClientAccEntity clientAcc = saveClientAcc(owner);
                ResultEntity result = saveResult(owner, notification);
                saveLink(corpAction.getCaid(), clientAcc.getAccDepo(), result.getClientDiaId());
            }

            // Обновить статус сообщения
            message.setStatus(1L); // PARSED_SUCCESS
            message.setCaid(corpAction.getCaid());
        } catch (Exception e) {
            e.printStackTrace();
            message.setStatus(-1L); // FLK_ERROR
        }
        messageRepository.save(message);
    }

    private CorpActionsEntity saveCorpAction(
            CorporateActionNotificationDTO.CorporateActionNotification notification
    ) {
        CorpActionsEntity entity = new CorpActionsEntity();
        entity.setCaid(Long.parseLong(notification.getCorporateActionIssuerID()));
        entity.setCaType(notification.getCorporateActionType());
        entity.setReference(notification.getCorpActnEvtId());
        entity.setSwiftType(notification.getEvtTp());
        entity.setMaVoCode(notification.getMndtryVlntryEvtTp());
        if (!StringUtils.isEmpty(notification.getRcrdDt())) {
            entity.setDateRegOwners(LocalDate.parse(notification.getRcrdDt()));
        }
        entity.setIsin(notification.getFinInstrmId().getIsin());

        // Для дат используем первый вариант (если есть)
        if (!notification.getCorpActnOptnDtls().isEmpty()) {
            CorporateActionNotificationDTO.CorpActnOptnDtls firstOption =
                    notification.getCorpActnOptnDtls().get(0);

            if (firstOption.getActnPrd() != null) {
                if (!StringUtils.isEmpty(firstOption.getActnPrd().getStartDt())) {
                    entity.setDateStart(LocalDate.parse(firstOption.getActnPrd().getStartDt()));
                }
                if (!StringUtils.isEmpty(firstOption.getActnPrd().getEndDt())) {
                    entity.setDateEnd(LocalDate.parse(firstOption.getActnPrd().getEndDt()));
                }
            }
        }

        return corpActionsRepository.save(entity);
    }

    private SecurityEntity saveSecurity(
            CorporateActionNotificationDTO.FinInstrmId finInstrmId
    ) {
        // Пытаемся найти существующую запись
        Optional<SecurityEntity> existing = securityRepository.findByIsin(finInstrmId.getIsin());
        SecurityEntity entity = existing.orElseGet(SecurityEntity::new);

        entity.setIsin(finInstrmId.getIsin());
        entity.setRegNumber(finInstrmId.getRegNumber());
        entity.setNsdr(finInstrmId.getNsdr());

        return securityRepository.save(entity);
    }

    private ClientAccEntity saveClientAcc(
            CorporateActionNotificationDTO.BnfclOwnrDtls owner
    ) {
        // Используем accDepo как первичный ключ
        ClientAccEntity entity = clientAccRepository.findById(owner.getAcct())
                .orElseGet(ClientAccEntity::new);

        entity.setAccDepo(owner.getAcct());
        entity.setCftid(Long.parseLong(owner.getCftid()));
        entity.setSubAccDepo(owner.getSubAcct());

        return clientAccRepository.save(entity);
    }

    private ResultEntity saveResult(
            CorporateActionNotificationDTO.BnfclOwnrDtls owner,
            CorporateActionNotificationDTO.CorporateActionNotification notification
    ) {
        // Используем первый вариант (если есть)
        CorporateActionNotificationDTO.CorpActnOptnDtls firstOption =
                !notification.getCorpActnOptnDtls().isEmpty()
                        ? notification.getCorpActnOptnDtls().get(0)
                        : null;

        ResultEntity entity = new ResultEntity();
        entity.setClientDiaId(Long.parseLong(owner.getOwnerSecurityID()));
        entity.setSecQtyMess(Float.parseFloat(owner.getBal()));
        entity.setSecQtyClient(0.0f); // По умолчанию

        if (firstOption != null) {
            entity.setDefaultOptions(
                    "1".equals(firstOption.getDfltOptnInd()) ||
                            Boolean.parseBoolean(firstOption.getDfltOptnInd())
            );
            entity.setRedemptionPrice(new BigDecimal(firstOption.getPricVal()));
            entity.setRedemptionCurrency(firstOption.getPricValCcy());
            entity.setOptNumber(Integer.parseInt(firstOption.getOptnNb()));
            entity.setOptType(firstOption.getOptnTp());
        }

        entity.setStatus(0L); // NEW
        entity.setCreateDateTime(ZonedDateTime.now());
        entity.setUpdateDateTime(ZonedDateTime.now());

        return resultRepository.save(entity);
    }

    private void saveLink(Long caid, String accDepo, Long clientDiaId) {
        LinkEntity entity = new LinkEntity();
        entity.setCaid(caid);
        entity.setAccDepo(accDepo);
        entity.setClientDiaId(clientDiaId);
        linkRepository.save(entity);
    }
}