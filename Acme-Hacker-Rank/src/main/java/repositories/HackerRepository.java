
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Company;
import domain.Hacker;

@Repository
public interface HackerRepository extends JpaRepository<Company, Integer> {

	@Query("select h from Hacker h where h.userAccount.id = ?1")
	public Hacker hackerUserAccount(Integer id);
}
