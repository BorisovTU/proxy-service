package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.dao.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.dao.MessageDao;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.entity.message.BaseMessage;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.entity.message.IncomeMessage;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.repository.MessageRepository;

@Component
@RequiredArgsConstructor
public class MessageDaoImpl implements MessageDao {
    private final MessageRepository messageRepository;

    @Override
    @Transactional
    public void saveIncomeDiasoftMessage(String payload) {
        BaseMessage messageEntity = new IncomeMessage();
        messageEntity.setJson(payload);
        messageRepository.save(messageEntity);
    }
}
