
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Paso;

@Repository
public interface FloatRepository extends JpaRepository<Paso, Integer> {

	@Query("select p from Paso p where p.brotherhood.id = ?1")
	public Collection<Paso> getFlotasByBrotherhood(Integer brotherhoodId);
}
