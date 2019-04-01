
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

	@Query("select h.finder from Hacker h where h.userAccount.id = ?1")
	public Finder getMyFinder(int id);

	@Query("select p from Position p ")
	public Collection<Position> filterPositions(String keyWord, Double minSalary, Double maxSalary, Date deadline, Date maxDeadline);
}
