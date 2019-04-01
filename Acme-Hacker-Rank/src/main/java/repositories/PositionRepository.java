
package repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select p from Position p where p.company.id=?1")
	public Collection<Position> getPositionsByCompany(Integer id);

	@Query("select p from Position p where p.draftMode=0")
	public Collection<Position> getPositionsOutDraftMode();

	@Query("select p from Position p where p.company.id=?1 and p.draftMode=0")
	public Collection<Position> getPositionsByCompanyOutDraftMode(Integer id);

	@Query(value = "select date_add(CURRENT_DATE, interval 1 day)", nativeQuery = true)
	public Date getTomorrow();

	//DASHBOARD
	@Query("select avg(1.0*(select count(p.company) from Position p where p.company.id = b.id)), min(1.0*(select count(p.company) from Position p where p.company.id = b.id)), max(1.0*(select count(p.company) from Position p where p.company.id = b.id)), sqrt(1.0*sum(1.0*(select count (p.company) from Position p where p.company.id = b.id) * (select count(p.company) from Position p where p.company.id = b.id)) / count(b) - avg(1.0*(select count(p.company) from Position p where p.company.id = b.id)) * avg(1.0*(select count(p.company) from Position p where p.company.id = b.id))) from Company b")
	public List<Object[]> getAvgMinMaxDesvPositionByCompany();
}
