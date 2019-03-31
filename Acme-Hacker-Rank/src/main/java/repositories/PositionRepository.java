
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Position;

public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select p from Position p where p.companie.id=?1")
	public Collection<Position> getPositionsByCompany(Integer id);

}
