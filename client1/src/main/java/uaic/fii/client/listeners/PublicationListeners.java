package uaic.fii.client.listeners;

import com.github.mervick.aes_everywhere.Aes256;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import uaic.fii.client.configs.security.PublicationEncryptionConfig;
import uaic.fii.converters.PublicationConverter;
import uaic.fii.models.Publication;
import uaic.fii.protobuf.PublicationOuterClass;

import java.nio.charset.StandardCharsets;


@Component
public class PublicationListeners {

    private final PublicationEncryptionConfig publicationEncryptionConfig;

    public PublicationListeners(final PublicationEncryptionConfig publicationEncryptionConfig){
        this.publicationEncryptionConfig = publicationEncryptionConfig;
    }

    @KafkaListener(topics = "${subscriptions.simple.output-topic}", groupId = "${kafka.listener.simple-output-topic.group-id}")
    public void listener1(String data) throws Exception {
        byte[] decryptedData = Aes256.decrypt(data.getBytes(StandardCharsets.UTF_8), publicationEncryptionConfig.getUTF8EncryptionKeyBytes());
        PublicationOuterClass.Publication protoBufPublication = PublicationOuterClass.Publication.parseFrom(decryptedData);
        Publication publication = PublicationConverter.fromProtoBuf(protoBufPublication);
        System.out.println("Simple subscription matched with " + publication);
    }

    @KafkaListener(topics = "${subscriptions.simple.input-topic}", groupId = "${kafka.listener.simple-input-topic.group-id}")
    public void listener2(String data) throws Exception {
        byte[] decryptedData = Aes256.decrypt(data.getBytes(StandardCharsets.UTF_8), publicationEncryptionConfig.getUTF8EncryptionKeyBytes());
        PublicationOuterClass.Publication protoBufPublication = PublicationOuterClass.Publication.parseFrom(decryptedData);
        Publication publication = PublicationConverter.fromProtoBuf(protoBufPublication);
        System.out.println("Input topic-ebs: " + publication);
    }

    @KafkaListener(topics = "${subscriptions.complex.output-topic}", groupId = "${kafka.listener.complex-output-topic.group-id}")
    public void listener3(String data) {
        System.out.println(data);
    }
}
