
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Position;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select h.finder from Hacer h where h.userAccount.id = ?1")
	public Finder getMyFinder(int id);

	@Query("select p from Position p where (locate(?1,p.title) != 0 or locate(?1,p.ticker) != 0 or locate(?1,p.description) != 0 or locate(?1,p.skillsRequired) != 0 or locate(?1,technologiesRequired) or locate(?1,requiredProfile)) and p.deadline between ?4 and ?5 and p.salary between ?2 and ?3")
	public Collection<Position> filterPositions(String keyWord, Double minSalary, Double maxSalary, Date deadline, Date maxDeadline);
}
