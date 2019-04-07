
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

	@Query("SELECT p FROM Problem p WHERE ?1 MEMBER OF p.applications")
	public Problem getProblemByApplication(Application a);

}
