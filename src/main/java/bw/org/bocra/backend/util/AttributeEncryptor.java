package bw.org.bocra.backend.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Component
@Converter
public class AttributeEncryptor implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        return EncryptionUtils.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) return dbData;
        try {
            return EncryptionUtils.decrypt(dbData);
        } catch (Exception e) {
            // Log warning: falling back to raw data (likely legacy plain-text)
            return dbData;
        }
    }
}
