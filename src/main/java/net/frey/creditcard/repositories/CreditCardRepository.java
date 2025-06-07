package net.frey.creditcard.repositories;

import net.frey.creditcard.domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jt on 6/27/22.
 */
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {}
