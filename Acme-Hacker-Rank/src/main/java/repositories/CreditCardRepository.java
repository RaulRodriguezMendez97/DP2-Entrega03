
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CreditCard;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

	@Query("select c.number from CreditCard c")
	public Collection<Integer> getAllNumbercreditCards();

	@Query("select c from CreditCard c where c.number=?1 ")
	public CreditCard CreditCardByNumber(Integer number);

}
