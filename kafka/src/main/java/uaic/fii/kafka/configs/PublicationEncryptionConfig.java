package uaic.fii.kafka.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
public class PublicationEncryptionConfig {

    @Value("${publication.encrypt.secret-key}")
    private String encryptionKey;

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public byte[] getUTF8EncryptionKeyBytes(){
        return encryptionKey.getBytes(StandardCharsets.UTF_8);
    }
}
