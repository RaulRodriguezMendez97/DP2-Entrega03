
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Parade;

@Repository
public interface ParadeRepository extends JpaRepository<Parade, Integer> {

	//Sepodria mejorar poniendo where p.ticker=?1 y el parametro String ticker
	@Query("select p.ticker from Parade p")
	public Collection<String> getAllTickers();

	@Query("select p.ticker from Parade p")
	public Collection<Parade> getProcessions();

	@Query("select p from Parade p where p.brotherhood.id = ?1")
	public Collection<Parade> getAllProcessionsByBrotherhood(int brotherhoodId);

	@Query("select p from Parade p where p.brotherhood.id = ?1 and p.draftMode = 0")
	public Collection<Parade> getAllProcessionsByBrotherhoodFinalMode(int brotherhoodId);

	//DASHBOARD
	@Query(value = "select Parade.title from Parade where date_add(CURRENT_DATE, interval 30 day) >= Parade.moment", nativeQuery = true)
	public Collection<String> processions();
}
