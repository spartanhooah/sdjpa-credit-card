package net.frey.creditcard.repositories

import net.frey.creditcard.domain.CreditCard
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditCardRepositoryTest extends Specification {
    static final private def CREDIT_CARD_NUMBER = "12345678900000"

    @Autowired
    CreditCardRepository repository

    def "save and store credit card"() {
        given:
        def card = new CreditCard(
            creditCardNumber: CREDIT_CARD_NUMBER,
            cvv: "123",
            expirationDate: "12/2028"
        )

        def savedCard = repository.saveAndFlush(card)

        when:
        def fetchedCard = repository.findById(savedCard.id).get()

        then:
        fetchedCard.creditCardNumber == CREDIT_CARD_NUMBER
    }
}
