package uaic.fii.kafka.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    public static final String TOPIC_NAME = "topic-ebs";

    @Bean
    public NewTopic topic(){
        return TopicBuilder.name(TOPIC_NAME)
                .partitions(3)
                .replicas(3) // three replicas for each partition
                .build();
    }

}
