package uaic.fii.client.processors;

import com.github.mervick.aes_everywhere.Aes256;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;
import uaic.fii.client.aggregators.Aggregator;
import uaic.fii.client.configs.kafka.streams.KafkaStreamsConfig;
import uaic.fii.client.configs.kafka.topics.KafkaInputTopicConfig;
import uaic.fii.client.configs.kafka.topics.KafkaOutputTopicConfig;
import uaic.fii.client.configs.security.PublicationEncryptionConfig;
import uaic.fii.client.configs.subscriptions.SubscriptionProcessorConfig;
import uaic.fii.client.models.MetaPublication;
import uaic.fii.client.models.MetaPublicationField;
import uaic.fii.client.utils.CriteriaHelper;
import uaic.fii.client.utils.CriteriaMatcher;
import uaic.fii.client.utils.SubscriptionReader;
import uaic.fii.models.Publication;
import uaic.fii.models.Subscription;
import uaic.fii.models.SubscriptionField;
import uaic.fii.utils.PublicationProtoDeserializer;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class ComplexSubscriptionProcessor extends SubscriptionProcessor{

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public ComplexSubscriptionProcessor(final StreamsBuilder streamsBuilder,
                                        final KafkaStreamsConfig kafkaStreamsConfig,
                                        final SubscriptionProcessorConfig subscriptionProcessorConfig,
                                        final KafkaInputTopicConfig kafkaInputTopicConfig,
                                        final KafkaOutputTopicConfig kafkaOutputTopicConfig,
                                        final KafkaTemplate<String, byte[]> kafkaTemplate,
                                        final PublicationEncryptionConfig publicationEncryptionConfig) {
        super(streamsBuilder, kafkaStreamsConfig, subscriptionProcessorConfig,
                kafkaInputTopicConfig, kafkaOutputTopicConfig, publicationEncryptionConfig);
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public void run(ApplicationArguments args) {
        if(subscriptionProcessorConfig.processComplexSubscriptionsIsSet()){
            processComplexSubscriptions();
        }
    }

    public void processComplexSubscriptions(){
        KStream<String, byte[]> inputStream = streamsBuilder
                .stream(kafkaInputTopicConfig.getComplexSubscriptionsInputTopic(), Consumed.with(Serdes.String(), Serdes.ByteArray()));

        KStream<String, byte[]> decryptedStream = inputStream
                .mapValues(encryptedPub -> {
                    try {
                        return Aes256.decrypt(encryptedPub, publicationEncryptionConfig.getUTF8EncryptionKeyBytes());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        // it works for one subscription containing a city field and criteria for average temp
        List<Subscription> subscriptions = new SubscriptionReader().readSubscriptionsFromFile("complex-subscriptions");
        List<SubscriptionField> fields = subscriptions.get(0).getFieldList();
        SubscriptionField cityField = fields.get(0);
        SubscriptionField avgFieldCriteria = fields.get(1);

        if(!cityField.getKey().equalsIgnoreCase("city")){
            throw new RuntimeException(cityField.getKey() + " is not implemented.");
        }

        if(!avgFieldCriteria.getKey().equalsIgnoreCase("avg_temp")){
            throw new RuntimeException(avgFieldCriteria.getKey() + " is not implemented.");
        }

        int windowSizeInSeconds = getWindowSizeInMillis();
        TimeWindows tumblingWindow = TimeWindows.of(Duration.ofMillis(windowSizeInSeconds));

        KTable<Windowed<String>, Aggregator> averageStream =
                decryptedStream
                        .mapValues(PublicationProtoDeserializer::deserialize)
                        .groupBy((key, value) -> "window", Grouped.with(Serdes.String(), new JsonSerde<>(Publication.class)))
                        .windowedBy(tumblingWindow)
                        .aggregate(
                                Aggregator::new,
                                (key, publication, aggregate) -> {
                                    Optional<String> cityOptional = publication.getFieldValue("city");
                                    if(cityOptional.isPresent() && cityOptional.get().equalsIgnoreCase(cityField.getValue())){
                                        String criteria = CriteriaHelper.getCriteria(avgFieldCriteria.getKey());
                                        Optional<String> criteriaValueOptional = publication.getFieldValue(criteria);
                                        if (criteriaValueOptional.isPresent()) {
                                            try{
                                                double temperature = Double.parseDouble(criteriaValueOptional.get());
                                                return aggregate.addToSum(temperature);
                                            } catch (Exception e){
                                                return aggregate;
                                            }
                                        } else {
                                            return aggregate;
                                        }
                                    } else {
                                        return aggregate;
                                    }
                                },
                                Materialized.with(Serdes.String(), new JsonSerde<>(Aggregator.class))
                        );

        //averageStream.toStream().foreach((k, v) -> System.out.println(k + ", " + v));

        averageStream.toStream().foreach((windowKey, aggregator) -> {
            if(aggregator.getCount() > 0 && CriteriaMatcher.match(String.valueOf(aggregator.getAverage()), avgFieldCriteria.getOperator(), avgFieldCriteria.getValue())){
                MetaPublicationField field1 = new MetaPublicationField(cityField.getKey(), cityField.getValue(), cityField.getOperator());
                MetaPublicationField field2 = new MetaPublicationField("conditions", "true", "=");
                MetaPublicationField field3 = new MetaPublicationField("count", String.valueOf(aggregator.getCount()), "=");
                Set<MetaPublicationField> metaPublicationFields = new LinkedHashSet<>();
                metaPublicationFields.add(field1);
                metaPublicationFields.add(field2);
                metaPublicationFields.add(field3);
                MetaPublication metaPublication = new MetaPublication(metaPublicationFields);
                kafkaTemplate.send(kafkaOutputTopicConfig.getComplexSubscriptionsOutputTopic(), metaPublication.toString().getBytes(StandardCharsets.UTF_8));
            }
        });

        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), kafkaStreamsConfig.getKafkaStreamsConfigComplexSubscriptions());
        kafkaStreams.cleanUp();
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }

    private int getWindowSizeInMillis() {
        int defaultWindowSizeInMillis = 5000;
        int readWindowSizeInMillis;
        try{
            readWindowSizeInMillis = Integer.parseInt(subscriptionProcessorConfig.getWindowSizeMillisComplexSubscriptions());
        } catch (NumberFormatException e){
            return defaultWindowSizeInMillis;
        }
        return readWindowSizeInMillis;
    }

}
