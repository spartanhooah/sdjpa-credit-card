package net.frey.creditcard.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import lombok.RequiredArgsConstructor;
import net.frey.creditcard.services.EncryptionService;
import org.springframework.stereotype.Component;

@Convert
@Component
@RequiredArgsConstructor
public class CreditCardConverter implements AttributeConverter<String, String> {
    private final EncryptionService encryptionService;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return encryptionService.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String attribute) {
        return encryptionService.decrypt(attribute);
    }
}
