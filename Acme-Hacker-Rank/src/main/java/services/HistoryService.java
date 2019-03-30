
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HistoryRepository;
import security.LoginService;
import security.UserAccount;
import domain.Companie;
import domain.History;
import domain.InceptionRecord;
import domain.LegalRecord;
import domain.LinkRecord;
import domain.MiscellaneousRecord;
import domain.PeriodRecord;

@Service
@Transactional
public class HistoryService {

	@Autowired
	private HistoryRepository	historyRepository;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	//Metodo create
	public History create() {
		final History history = new History();
		final UserAccount user = LoginService.getPrincipal();
		final Companie br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		history.setInceptionRecord(new InceptionRecord());
		history.setLinkRecords(new HashSet<LinkRecord>());
		history.setLegalRecords(new HashSet<LegalRecord>());
		history.setMiscellaneousRecords(new HashSet<MiscellaneousRecord>());
		history.setPeriodRecords(new HashSet<PeriodRecord>());
		history.setBrotherhood(br);
		return history;
	}
	//Metodo findAll
	public Collection<History> finaAll() {
		return this.historyRepository.findAll();
	}

	public History save(final History history) {
		final UserAccount user = LoginService.getPrincipal();
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(history.getBrotherhood() != null);
		Assert.isTrue(history.getInceptionRecord() != null && history.getLegalRecords() != null && history.getLinkRecords() != null && history.getMiscellaneousRecords() != null && history.getPeriodRecords() != null);
		Assert.isTrue(history.getBrotherhood().equals(this.brotherhoodService.brotherhoodUserAccount(user.getId())));
		return this.historyRepository.save(history);
	}
	//DASHBOARD
	public List<Object[]> AvgMinMaxDesv() {
		return this.historyRepository.AvgMinMaxDesvRecordByHistory();
	}

	public Collection<History> getHistoryByBrotherhood(final Integer brotherhoodId) {

		Assert.notNull(brotherhoodId);

		return this.historyRepository.getHistoryBrotherhood(brotherhoodId);
	}
	public History findOne2(final int historyId) {
		return this.findOne(historyId);
	}

	public History findOne(final int historyId) {
		final History h = this.historyRepository.findOne(historyId);
		final UserAccount userAccount = LoginService.getPrincipal();
		final Companie b = this.brotherhoodService.brotherhoodUserAccount(userAccount.getId());
		Assert.isTrue(h.getBrotherhood().getId() == b.getId());
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));

		return h;
	}
	public History findOneAnomimo(final int historyId) {
		return this.historyRepository.findOne(historyId);
	}
	public History getHistotyByPeriodRecord(final Integer id) {
		return this.historyRepository.getHistoryByPeriodRecord(id);
	}

	public History getHistotyByLegalRecord(final Integer id) {
		return this.historyRepository.getHistoryByLegalRecord(id);
	}

	public History getHistotyByLinkRecord(final Integer id) {
		return this.historyRepository.getHistoryByLinkRecord(id);
	}

	public History getHistotyByMiscellaneousRecord(final Integer id) {
		return this.historyRepository.getHistoryByMiscellaneousRecord(id);
	}

	public History getHistoryByInceptionRecord(final int id) {
		return this.historyRepository.getHistoryByInceptionRecord(id);
	}

	public void delete(final History history) {
		Assert.isTrue(history.getBrotherhood().equals(this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId())));
		this.historyRepository.delete(history);
	}

}
