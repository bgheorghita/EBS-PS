package uaic.fii.client.processors;

import com.github.mervick.aes_everywhere.Aes256;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import uaic.fii.client.configs.kafka.streams.KafkaStreamsConfig;
import uaic.fii.client.configs.kafka.topics.KafkaInputTopicConfig;
import uaic.fii.client.configs.kafka.topics.KafkaOutputTopicConfig;
import uaic.fii.client.configs.security.PublicationEncryptionConfig;
import uaic.fii.client.utils.SubscriptionMatcher;
import uaic.fii.client.configs.subscriptions.SubscriptionProcessorConfig;
import uaic.fii.client.utils.SubscriptionReader;
import uaic.fii.converters.PublicationConverter;
import uaic.fii.models.Publication;
import uaic.fii.models.Subscription;
import uaic.fii.protobuf.PublicationOuterClass;
import uaic.fii.utils.PublicationProtoDeserializer;

import java.util.List;

@Component
public class SimpleSubscriptionProcessor extends SubscriptionProcessor{
    public SimpleSubscriptionProcessor(final StreamsBuilder streamsBuilder,
                                       final KafkaStreamsConfig kafkaStreamsConfig,
                                       final SubscriptionProcessorConfig subscriptionProcessorConfig,
                                       final KafkaInputTopicConfig kafkaInputTopicConfig,
                                       final KafkaOutputTopicConfig kafkaOutputTopicConfig,
                                       final PublicationEncryptionConfig publicationEncryptionConfig) {
        super(streamsBuilder, kafkaStreamsConfig, subscriptionProcessorConfig,
                kafkaInputTopicConfig,kafkaOutputTopicConfig, publicationEncryptionConfig);
    }

    @Override
    public void run(ApplicationArguments args) {
        if(subscriptionProcessorConfig.processSimpleSubscriptionsIsSet()){
            processSimpleSubscriptions();
        }
    }


    public void processSimpleSubscriptions(){
        KStream<String, byte[]> inputStream = streamsBuilder
                .stream(kafkaInputTopicConfig.getSimpleSubscriptionsInputTopic(),
                        Consumed.with(Serdes.String(), Serdes.ByteArray()));

        KStream<String, byte[]> decryptedStream = inputStream
                .mapValues(encryptedPub -> {
                    try {
                        return Aes256.decrypt(encryptedPub, publicationEncryptionConfig.getUTF8EncryptionKeyBytes());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        KStream<String, Publication> deserializedStream = decryptedStream
                .mapValues(PublicationProtoDeserializer::deserialize);

        List<Subscription> subscriptionList = new SubscriptionReader()
                .readSubscriptionsFromFile("subscriptions");

        KStream<String, Publication> filteredStream = deserializedStream
                .filter((key, publication) ->
                {
                    for (Subscription subscription : subscriptionList) {
                        if (SubscriptionMatcher.match(subscription, publication)) {
                            return true;
                        }
                    }
                    return false;
                });

        KStream<String, byte[]> serializedStream = filteredStream
                .mapValues(PublicationConverter::toProtoBuf)
                .mapValues(PublicationOuterClass.Publication::toByteArray)
                .mapValues(serializedBytes -> {
                    try {
                        return Aes256.encrypt(serializedBytes, publicationEncryptionConfig.getUTF8EncryptionKeyBytes());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        serializedStream.to(kafkaOutputTopicConfig.getSimpleSubscriptionsOutputTopic(),
                Produced.with(Serdes.String(), Serdes.ByteArray()));


        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(),
                kafkaStreamsConfig.getKafkaStreamsConfigSimpleSubscriptions());

        kafkaStreams.cleanUp();
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }
}
