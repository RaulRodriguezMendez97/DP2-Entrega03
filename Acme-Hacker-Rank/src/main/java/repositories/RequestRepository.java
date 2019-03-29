
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select r from Request r where r.row = ?1 and r.columna = ?2 and r.status = 0 and r.procession.id = ?3")
	public Request getRequestWithThisRowAndColumn(Integer row, Integer column, int processionId);

	@Query("select r from Request r where r.member.id = ?1 ORDER BY  r.status")
	public Collection<Request> getAllMyRequest(int memberId);

	//DASHBOARD
	@Query("select count(r)*1.0/(select count(a) from Request a) from Request r where r.status=0")
	public Double pendingRatio();

	@Query("select count(r)*1.0/(select count(a) from Request a) from Request r where r.status=1")
	public Double acceptedRatio();

	@Query("select count(r)*1.0/(select count(a) from Request a) from Request r where r.status=2")
	public Double rejectedRatio();
}
