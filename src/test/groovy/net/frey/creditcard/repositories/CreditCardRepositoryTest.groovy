package net.frey.creditcard.repositories

import net.frey.creditcard.domain.CreditCard
import net.frey.creditcard.services.EncryptionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditCardRepositoryTest extends Specification {
    static final private def CREDIT_CARD_NUMBER = "12345678900000"

    @Autowired
    CreditCardRepository repository

    @Autowired
    EncryptionService encryptionService

    @Autowired
    JdbcTemplate jdbcTemplate

    def "save and store credit card"() {
        given:
        def card = new CreditCard(
            creditCardNumber: CREDIT_CARD_NUMBER,
            cvv: "123",
            expirationDate: "12/2028"
        )

        def savedCard = repository.saveAndFlush(card)
        println "CC number from DB: $savedCard.creditCardNumber"
        println "CC at rest: ${encryptionService.encrypt(CREDIT_CARD_NUMBER)}"

        def row = jdbcTemplate.queryForMap("SELECT * FROM credit_card WHERE id = $savedCard.id")

        def dbCardValue = row.credit_card_number
        assert dbCardValue != savedCard.creditCardNumber
        assert dbCardValue == encryptionService.encrypt(CREDIT_CARD_NUMBER)

        when:
        def fetchedCard = repository.findById(savedCard.id).get()

        then:
        fetchedCard.creditCardNumber == CREDIT_CARD_NUMBER
    }
}
