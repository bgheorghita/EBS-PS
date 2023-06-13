package uaic.fii.client.configs.kafka.topics;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaOutputTopicConfig {
    @Value("${subscriptions.simple.output-topic}")
    private String simpleSubscriptionsOutputTopic;

    @Value("${subscriptions.complex.output-topic}")
    private String complexSubscriptionsOutputTopic;

    @Bean
    public NewTopic outputTopicSimpleSubscriptions(){
        return TopicBuilder.name(simpleSubscriptionsOutputTopic)
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic outputTopicComplexSubscriptions(){
        return TopicBuilder.name(complexSubscriptionsOutputTopic)
                .partitions(3)
                .replicas(3)
                .build();
    }

    public String getSimpleSubscriptionsOutputTopic() {
        return simpleSubscriptionsOutputTopic;
    }

    public String getComplexSubscriptionsOutputTopic() {
        return complexSubscriptionsOutputTopic;
    }
}
