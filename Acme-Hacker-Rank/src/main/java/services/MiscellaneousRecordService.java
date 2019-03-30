
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Company;
import domain.History;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;
	@Autowired
	private ActorService					actorS;
	@Autowired
	private HistoryService					historyService;
	@Autowired
	private ActorService					actorService;
	@Autowired
	private BrotherhoodService				brotherhoodService;


	public MiscellaneousRecord create() {
		final MiscellaneousRecord miscellaneousRecord = new MiscellaneousRecord();
		miscellaneousRecord.setTitle("");
		miscellaneousRecord.setDescription("");
		return miscellaneousRecord;
	}

	//listing
	public Collection<MiscellaneousRecord> findAll() {
		return this.miscellaneousRecordRepository.findAll();
	}
	public MiscellaneousRecord findOne(final int miscellaneousRecordId) {
		final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);

		final History h = this.historyService.getHistotyByMiscellaneousRecord(miscellaneousRecord.getId());
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(h.getBrotherhood() == a);
		return miscellaneousRecord;
	}

	//updating
	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		final History h = this.historyService.getHistotyByMiscellaneousRecord(miscellaneousRecord.getId());
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		final Company br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		if (miscellaneousRecord.getId() != 0)
			Assert.isTrue(h.getBrotherhood() == br);

		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(miscellaneousRecord != null && miscellaneousRecord.getTitle() != null && miscellaneousRecord.getTitle() != "" && miscellaneousRecord.getDescription() != null && miscellaneousRecord.getDescription() != "");

		return this.miscellaneousRecordRepository.save(miscellaneousRecord);
	}

	//deleting
	public void delete(final MiscellaneousRecord miscellaneousRecord, final int historyId) {
		final History history = this.historyService.findOne(historyId);
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		final Company br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(history.getBrotherhood() == br);
		Assert.isTrue(history.getMiscellaneousRecords().contains(miscellaneousRecord));
		history.getMiscellaneousRecords().remove(miscellaneousRecord);
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
		this.historyService.save(history);
	}

}
