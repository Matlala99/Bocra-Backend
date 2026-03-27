package bw.org.bocra.backend.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class EncryptionUtils {

    private static String secretKey;

    @Value("${bocra.security.encryption.key}")
    public void setSecretKey(String key) {
        secretKey = key;
    }

    public static String encrypt(String value) {
        if (value == null || value.isEmpty()) return value;
        if (secretKey == null || secretKey.length() < 16) {
            System.err.println("CRITICAL: Encryption Key not properly configured! Length must be >= 16 chars.");
            throw new RuntimeException("Encryption key missing or weak.");
        }
        try {
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting value", e);
        }
    }

    public static String decrypt(String encryptedValue) {
        if (encryptedValue == null || encryptedValue.isEmpty()) return encryptedValue;
        if (secretKey == null || secretKey.length() < 16) {
            throw new RuntimeException("Encryption key missing or weak.");
        }
        try {
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
            return new String(original);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting value: invalid format or key mismatch", e);
        }
    }
}
