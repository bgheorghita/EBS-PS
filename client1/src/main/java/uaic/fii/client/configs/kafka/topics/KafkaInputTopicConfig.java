package uaic.fii.client.configs.kafka.topics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaInputTopicConfig {

    @Value("${subscriptions.simple.input-topic}")
    private String simpleSubscriptionsInputTopic;

    @Value("${subscriptions.complex.input-topic}")
    private String complexSubscriptionsInputTopic;


    public String getSimpleSubscriptionsInputTopic() {
        return simpleSubscriptionsInputTopic;
    }

    public String getComplexSubscriptionsInputTopic() {
        return complexSubscriptionsInputTopic;
    }
}
