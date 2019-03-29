
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.LegalRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.History;
import domain.LegalRecord;

@Service
@Transactional
public class LegalRecordService {

	@Autowired
	private LegalRecordRepository	legalRecordRepository;
	@Autowired
	private HistoryService			historyService;

	@Autowired
	private ActorService			actorService;


	public LegalRecord create() {
		final LegalRecord legalRecord = new LegalRecord();
		legalRecord.setTitle("");
		legalRecord.setDescription("");
		legalRecord.setLegalName("");
		legalRecord.setVATNumber("");
		legalRecord.setLaws(new HashSet<String>());
		return legalRecord;
	}

	//listing
	public Collection<LegalRecord> findAll() {
		return this.legalRecordRepository.findAll();
	}
	public LegalRecord findOne(final int legalRecordId) {
		final LegalRecord l = this.legalRecordRepository.findOne(legalRecordId);
		final History h = this.historyService.getHistotyByLegalRecord(l.getId());
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(h.getBrotherhood() == a);
		return l;
	}

	//updating
	public LegalRecord save(final LegalRecord legalRecord) {
		Assert.isTrue(legalRecord != null && legalRecord.getTitle() != null && legalRecord.getTitle() != "" && legalRecord.getDescription() != null && legalRecord.getDescription() != "" && legalRecord.getLegalName() != null
			&& legalRecord.getLegalName() != "" && legalRecord.getLaws() != null && legalRecord.getVATNumber() != null && legalRecord.getVATNumber() != "");

		return this.legalRecordRepository.save(legalRecord);
	}

	//deleting
	public void delete(final LegalRecord legalRecord, final Integer historyId) {
		final History history = this.historyService.findOne(historyId);
		Assert.notNull(history);
		final UserAccount user = LoginService.getPrincipal();

		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(history.getBrotherhood() == a);

		history.getLegalRecords().remove(legalRecord);

		this.legalRecordRepository.delete(legalRecord);

		this.historyService.save(history);
	}
}
