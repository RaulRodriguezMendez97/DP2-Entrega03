
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PeriodRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.History;
import domain.PeriodRecord;
import domain.Picture;

@Service
@Transactional
public class PeriodRecordService {

	@Autowired
	private PeriodRecordRepository	periodRecordRepository;

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private ActorService			actorService;


	public PeriodRecord create() {
		final PeriodRecord p = new PeriodRecord();

		p.setTitle("");
		p.setDescription("");
		p.setStarYear(0);
		p.setEndYear(0);
		p.setPictures(new ArrayList<Picture>());

		return p;
	}

	public Collection<PeriodRecord> findAll() {
		return this.periodRecordRepository.findAll();
	}

	public PeriodRecord findOne(final int periodRecord) {
		final PeriodRecord p = this.periodRecordRepository.findOne(periodRecord);

		final History h = this.historyService.getHistotyByPeriodRecord(p.getId());
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(h.getBrotherhood() == a);

		return p;
	}

	public PeriodRecord findOneWithoutCredentials(final int PeriodRecordId) {
		return this.periodRecordRepository.findOne(PeriodRecordId);
	}

	public PeriodRecord save(final PeriodRecord p) {
		PeriodRecord saved;

		//ACTOR LOGEADO SEA BROTHERHOOD Y 
		//LA HISTORY A LA QUE PERTENECE SEA DEL MISMO BROTHERHOOD LOGEADO
		final History h = this.historyService.getHistotyByPeriodRecord(p.getId());
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		if (p.getId() != 0)
			Assert.isTrue(h.getBrotherhood() == a);

		//COMPROBRACIONES DE ATRIBUTOS
		Assert.isTrue(p.getTitle() != "" && p.getTitle() != null, "Title invalido");
		Assert.isTrue(p.getDescription() != "" && p.getDescription() != null, "Description invalida");
		final Integer ano = new Date().getYear() + 1900;
		Assert.isTrue(p.getStarYear() >= ano, "Año de inicio inválido");
		Assert.isTrue(p.getEndYear() >= ano, "Año de fin inválido");
		Assert.isTrue(p.getEndYear() >= p.getStarYear(), "Año de fin menor que año de inicio");

		saved = this.periodRecordRepository.save(p);
		return saved;

	}

	public void delete(final PeriodRecord p, final Integer historyId) {
		final History history = this.historyService.findOne(historyId);

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(history.getBrotherhood() == a);
		Assert.isTrue(history.getPeriodRecords().contains(p));
		history.getPeriodRecords().remove(p);
		this.periodRecordRepository.delete(p);
		this.historyService.save(history);
	}

	public Collection<PeriodRecord> getPeriodRecordsByHistory(final Integer id) {
		Collection<PeriodRecord> res;
		final History h = this.historyService.findOne(id);
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(h.getBrotherhood() == a);
		res = this.periodRecordRepository.getPeriodRecordsByHistory(id);
		return res;
	}

}
