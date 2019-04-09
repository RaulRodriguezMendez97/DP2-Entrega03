
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

	@Query("SELECT p FROM Problem p WHERE ?1 MEMBER OF p.applications and p.draftMode = 1")
	public Problem getProblemByApplication(Application a);

	@Query("select p from Problem p where p.draftMode = 1")
	public Collection<Problem> getProblemDraftModeOut();

}
