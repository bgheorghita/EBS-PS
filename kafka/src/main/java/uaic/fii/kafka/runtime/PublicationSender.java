package uaic.fii.kafka.runtime;

import com.github.mervick.aes_everywhere.Aes256;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import uaic.fii.kafka.configs.KafkaTopicConfig;
import uaic.fii.converters.PublicationConverter;
import uaic.fii.kafka.configs.PublicationEncryptionConfig;
import uaic.fii.protobuf.PublicationOuterClass;
import uaic.fii.managers.PublicationManager;
import uaic.fii.models.Publication;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class PublicationSender implements CommandLineRunner {
    public final static long PUBLICATIONS_FEED_MILLIS = 180_000;
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final PublicationEncryptionConfig publicationEncryptionConfig;
    private int successfullySent = 0;
    private int failedToSend = 0;
    private long sentPublications = 0;

    public PublicationSender(final KafkaTemplate<String, byte[]> kafkaTemplate, final PublicationEncryptionConfig publicationEncryptionConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.publicationEncryptionConfig = publicationEncryptionConfig;
    }

    @Override
    public void run(String... args) throws Exception {
        sendPublications();
    }

    public void sendPublications() throws Exception {
        long consumedTime = 0;

        PublicationManager publicationManager = new PublicationManager();
        Set<CompletableFuture<SendResult<String, byte[]>>> futures = new HashSet<>();

        while (consumedTime < PUBLICATIONS_FEED_MILLIS ){
            List<Publication> publications = publicationManager.generatePublicationsWithThreadParallelization(1000, 1, false);

            long startTimeSendingPub = System.currentTimeMillis();
            for (Publication publication : publications) {
                System.out.println("Sending " + publication);
                PublicationOuterClass.Publication serializedPublication = PublicationConverter.toProtoBuf(publication);
                byte[] serializedBytes = serializedPublication.toByteArray();
                byte[] encryptedPublication = Aes256.encrypt(serializedBytes, publicationEncryptionConfig.getUTF8EncryptionKeyBytes());
                CompletableFuture<SendResult<String, byte[]>> future = kafkaTemplate.send(KafkaTopicConfig.TOPIC_NAME, encryptedPublication);
                futures.add(future);
            }
            long timeConsumedForSendingPub = System.currentTimeMillis() - startTimeSendingPub;
            consumedTime += timeConsumedForSendingPub;

            refreshResults(futures);
            futures = new HashSet<>();
        }

        System.out.println("Success: " + successfullySent);
        System.out.println("Failure: " + failedToSend);
        System.out.println("Time (ms): " + consumedTime);
        System.out.println("Publications sent: " + sentPublications);
    }

    private void refreshResults(Set<CompletableFuture<SendResult<String,byte[]>>> futures) {
        for (CompletableFuture<SendResult<String, byte[]>> future : futures) {
            try {
                future.get();
                successfullySent++;
            } catch (InterruptedException | ExecutionException e) {
                failedToSend++;
            }
        }
        sentPublications += futures.size();
    }
}

