
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Posicion;

@Repository
public interface PosicionRepository extends JpaRepository<Posicion, Integer> {

	//PARA DELETE
	@Query("select p.position.name from Enrolment p")
	public Collection<String> getUsedNames();

	//HISTOGRAMA
	@Query("select count(e) from Enrolment e where e.status=1")
	public Double countTotal();

	@Query("select count(e) from Enrolment e where e.status=1 and e.position.name='President'")
	public Double countPresident();

	@Query("select count(e) from Enrolment e where e.status=1 and e.position.name='Vice-President'")
	public Double countVicePresident();

	@Query("select count(e) from Enrolment e where e.status=1 and e.position.name='Secretary'")
	public Double countSecretaty();

	@Query("select count(e) from Enrolment e where e.status=1 and e.position.name='Treasurer'")
	public Double countTreasurer();

	@Query("select count(e) from Enrolment e where e.status=1 and e.position.name='Historian'")
	public Double countHistorian();

	@Query("select count(e) from Enrolment e where e.status=1 and e.position.name='Fundraiser'")
	public Double countFundraiser();

	@Query("select count(e) from Enrolment e where e.status=1 and e.position.name='Officer'")
	public Double countOfficer();

}
