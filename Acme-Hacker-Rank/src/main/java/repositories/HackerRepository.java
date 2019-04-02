
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Hacker;

@Repository
public interface HackerRepository extends JpaRepository<Hacker, Integer> {

	@Query("select h from Hacker h where h.userAccount.id = ?1")
	public Hacker hackerUserAccount(Integer id);

	//DASHBOARD
	@Query("select h.name from Hacker h where (select max(1.0*( select count(a.hacker) from Application a where a.hacker.id=x.id)) from Hacker x)=(1.0*(select count(b.hacker) from Application b where b.hacker.id=h.id))")
	public List<String> getHackersWithMoreApplications();
}
