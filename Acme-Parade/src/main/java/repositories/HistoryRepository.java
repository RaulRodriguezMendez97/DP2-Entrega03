
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {

	@Query("select avg(1.0*(select count(c.inceptionRecord)+ c.periodRecords.size+c.legalRecords.size+c.linkRecords.size+ c.miscellaneousRecords.size from History c where c.id=h.id)), min(1.0*(select count(c.inceptionRecord)+ c.periodRecords.size+c.legalRecords.size+c.linkRecords.size+ c.miscellaneousRecords.size from History c where c.id=h.id)), max(1.0*(select count(c.inceptionRecord)+ c.periodRecords.size+c.legalRecords.size+c.linkRecords.size+ c.miscellaneousRecords.size from History c where c.id=h.id)), sqrt(1.0*sum(1.0*(select count(c.inceptionRecord)+  c.periodRecords.size+c.legalRecords.size+c.linkRecords.size+  c.miscellaneousRecords.size from History c where c.id=h.id) *  (select count(c.inceptionRecord)+  c.periodRecords.size+c.legalRecords.size+c.linkRecords.size+  c.miscellaneousRecords.size from History c where c.id=h.id)) /  count(h) - avg(1.0*(select count(c.inceptionRecord)+ c.periodRecords.size+c.legalRecords.size+c.linkRecords.size+ c.miscellaneousRecords.size from History c where c.id=h.id)) *  avg(1.0*(select count(c.inceptionRecord)+ c.periodRecords.size+c.legalRecords.size+c.linkRecords.size+ c.miscellaneousRecords.size from History c where c.id=h.id))) from History h")
	public List<Object[]> AvgMinMaxDesvRecordByHistory();

	@Query("select h from History h where h.brotherhood.id = ?1")
	public Collection<History> getHistoryBrotherhood(Integer id);

	@Query("select h from History h where ?1 member of h.periodRecords")
	public History getHistoryByPeriodRecord(Integer id);

	@Query("select h from History h where ?1 member of h.legalRecords")
	public History getHistoryByLegalRecord(Integer id);

	@Query("select h from History h where ?1 member of h.linkRecords")
	public History getHistoryByLinkRecord(Integer id);

	@Query("select h from History h where ?1 member of h.miscellaneousRecords")
	public History getHistoryByMiscellaneousRecord(Integer id);

	@Query("select h from History h where h.inceptionRecord.id = ?1")
	public History getHistoryByInceptionRecord(int id);

}
