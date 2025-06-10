package net.frey.creditcard.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;
import net.frey.creditcard.interceptors.EncryptedString;

/**
 * Created by jt on 6/27/22.
 */
@Setter
@Getter
@Entity
@EntityListeners(CreditCardJPACallback.class)
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EncryptedString
    private String creditCardNumber;

    private String cvv;

    private String expirationDate;

    @PrePersist
    public void prePersistCallback() {
        System.out.println("JPA pre-persist callback");
    }
}
