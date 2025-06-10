package net.frey.creditcard.domain;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import net.frey.creditcard.services.EncryptionService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditCardJPACallback {
    private final EncryptionService encryptionService;

    @PrePersist
    @PreUpdate
    public void beforeInsertOrUpdate(CreditCard creditCard) {
        System.out.println("before update or insert");

        creditCard.setCreditCardNumber(encryptionService.encrypt(creditCard.getCreditCardNumber()));
    }

    @PostPersist
    @PostLoad
    @PostUpdate
    public void postLoad(CreditCard creditCard) {
        System.out.println("post load");

        creditCard.setCreditCardNumber(encryptionService.decrypt(creditCard.getCreditCardNumber()));
    }
}
