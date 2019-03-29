
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.InceptionRecord;

@Repository
public interface InceptionRecordRepository extends JpaRepository<InceptionRecord, Integer> {

	@Query("select h.inceptionRecord from History h where h.brotherhood.userAccount.id = ?1")
	Collection<InceptionRecord> allMyInceptionRecords(int userAccountId);
}
