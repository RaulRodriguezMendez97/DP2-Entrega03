
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Enrolment;

@Repository
public interface EnrolmentRepository extends JpaRepository<Enrolment, Integer> {

	@Query("select e from Enrolment e where e.member.id = ?1")
	public Collection<Enrolment> enrolmentByMember(Integer id);

	@Query("select e from Enrolment e where e.brotherhood.id = ?1 and (e.status=0) ")
	public Collection<Enrolment> enrolmentByBrotherhood(Integer id);

	@Query("select e from Enrolment e where e.member.id = ?1 and e.status=1")
	public Collection<Enrolment> enrolmentAcceptedByBrotherhood(Integer id);
}
