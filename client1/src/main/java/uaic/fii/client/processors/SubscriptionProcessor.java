package uaic.fii.client.processors;

import org.apache.kafka.streams.StreamsBuilder;
import org.springframework.boot.ApplicationRunner;
import uaic.fii.client.configs.kafka.streams.KafkaStreamsConfig;
import uaic.fii.client.configs.kafka.topics.KafkaInputTopicConfig;
import uaic.fii.client.configs.kafka.topics.KafkaOutputTopicConfig;
import uaic.fii.client.configs.security.PublicationEncryptionConfig;
import uaic.fii.client.configs.subscriptions.SubscriptionProcessorConfig;


public abstract class SubscriptionProcessor implements ApplicationRunner {
    protected final StreamsBuilder streamsBuilder;
    protected final KafkaStreamsConfig kafkaStreamsConfig;
    protected final KafkaInputTopicConfig kafkaInputTopicConfig;
    protected final KafkaOutputTopicConfig kafkaOutputTopicConfig;
    protected final SubscriptionProcessorConfig subscriptionProcessorConfig;
    protected final PublicationEncryptionConfig publicationEncryptionConfig;
    public SubscriptionProcessor(final StreamsBuilder streamsBuilder,
                                 final KafkaStreamsConfig kafkaStreamsConfig,
                                 final SubscriptionProcessorConfig subscriptionProcessorConfig,
                                 final KafkaInputTopicConfig kafkaInputTopicConfig,
                                 final KafkaOutputTopicConfig kafkaOutputTopicConfig,
                                 final PublicationEncryptionConfig publicationEncryptionConfig){
        this.streamsBuilder = streamsBuilder;
        this.kafkaStreamsConfig = kafkaStreamsConfig;
        this.subscriptionProcessorConfig = subscriptionProcessorConfig;
        this.kafkaInputTopicConfig = kafkaInputTopicConfig;
        this.kafkaOutputTopicConfig = kafkaOutputTopicConfig;
        this.publicationEncryptionConfig = publicationEncryptionConfig;
    }
}
