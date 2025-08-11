package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.entity.message.BaseMessage;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.repository.MessageRepository;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.service.MessageProcessingService;

import java.util.List;

@EnableScheduling
@Component
@Slf4j
public class MessageProcessingJob {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageProcessingService processingService;

    @Scheduled(initialDelay = 1000)
    public void execute() throws JobExecutionException {
        // Получение сообщений со статусом 0 (NEW)
        log.info("Executing job");
        List<BaseMessage> messages = messageRepository.findByStatus(0L);
    log.info("Found {} messages", messages.size());
        // Обработка каждого сообщения
        for (BaseMessage message : messages) {
            processingService.processMessage(message);
        }
    }
}
