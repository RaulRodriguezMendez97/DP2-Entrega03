
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LinkRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Companie;
import domain.History;
import domain.LinkRecord;

@Service
@Transactional
public class LinkRecordService {

	@Autowired
	private LinkRecordRepository	linkRecordRepository;
	@Autowired
	private ActorService			actorS;
	@Autowired
	private HistoryService			historyService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private BrotherhoodService		brotherhoodService;
	@Autowired
	private Validator				validator;


	public LinkRecord create() {
		final LinkRecord linkRecord = new LinkRecord();
		linkRecord.setTitle("");
		linkRecord.setDescription("");
		linkRecord.setBrotherhood(new Companie());
		return linkRecord;
	}

	//listing
	public Collection<LinkRecord> findAll() {
		return this.linkRecordRepository.findAll();
	}
	public LinkRecord findOne(final int linkRecordId) {
		final LinkRecord linkRecord = this.linkRecordRepository.findOne(linkRecordId);

		final History h = this.historyService.getHistotyByLinkRecord(linkRecord.getId());
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(h.getBrotherhood() == a);
		return linkRecord;
	}

	//updating
	public LinkRecord save(final LinkRecord linkRecord) {
		final History h = this.historyService.getHistotyByLinkRecord(linkRecord.getId());
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		final Companie br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		if (linkRecord.getId() != 0)
			Assert.isTrue(h.getBrotherhood() == br);

		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(linkRecord != null && linkRecord.getTitle() != null && linkRecord.getTitle() != "" && linkRecord.getDescription() != null && linkRecord.getDescription() != "" && linkRecord.getBrotherhood() != null
			&& linkRecord.getBrotherhood() != br);

		return this.linkRecordRepository.save(linkRecord);
	}

	//deleting
	public void delete(final LinkRecord linkRecord, final int historyId) {
		final History history = this.historyService.findOne(historyId);
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		final Companie br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(history.getBrotherhood() == br);
		Assert.isTrue(history.getLinkRecords().contains(linkRecord));
		history.getLinkRecords().remove(linkRecord);
		this.linkRecordRepository.delete(linkRecord);
		this.historyService.save(history);
	}

	//RECONSTRUCT
	public LinkRecord reconstruct(final LinkRecord linkRecord, final BindingResult binding) {
		LinkRecord res;

		if (linkRecord.getId() == 0) {
			res = linkRecord;
			this.validator.validate(res, binding);
		} else {
			res = this.linkRecordRepository.findOne(linkRecord.getId());
			final LinkRecord i = new LinkRecord();
			i.setId(res.getId());
			i.setVersion(res.getVersion());
			i.setTitle(linkRecord.getTitle());
			i.setDescription(linkRecord.getDescription());
			i.setBrotherhood(linkRecord.getBrotherhood());
			this.validator.validate(i, binding);
			res = i;
		}
		return res;
	}

}
