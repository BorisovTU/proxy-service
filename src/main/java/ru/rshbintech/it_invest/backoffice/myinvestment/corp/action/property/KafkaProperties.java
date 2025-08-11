package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.property;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.mapping.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@ConfigurationProperties(prefix = "kafka.corp-action")
@Getter
@Setter
public class KafkaProperties {

    private String bootstrapServers;
    private Consumer consumer = new Consumer();

    @Getter
    @Setter
    public static class Consumer {
        private String groupId;
        private int concurrency;
        private String topic;
    }
}
