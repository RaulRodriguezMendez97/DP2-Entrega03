
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;

@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer> {

	@Query("select c from Curricula c where c.hacker.id=?1")
	public Collection<Curricula> getCurriculasByHacker(Integer hackerId);

	@Query("select c from Curricula c where c.personalData.id=?1")
	public Curricula getCurriculaByProfileData(Integer id);

	@Query("select c from Curricula c where c.positionData.id=?1")
	public Curricula getCurriculaByPositionData(Integer id);

	@Query("select c from Curricula c where c.educationData.id=?1")
	public Curricula getCurriculaByEducationData(Integer id);

	@Query("select c from Curricula c where c.miscellaneousData.id=?1")
	public Curricula getCurriculaByMiscellaneousData(Integer id);
}
