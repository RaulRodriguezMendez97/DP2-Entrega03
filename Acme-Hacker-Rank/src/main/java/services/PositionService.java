
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Company;
import domain.Position;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepository	positionRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	public Position create() {
		final Position res = new Position();

		res.setCompany(new Company());
		res.setTitle("");
		res.setDescription("");
		final Date tomorrow = this.positionRepository.getTomorrow();
		res.setDeadLine(tomorrow);
		res.setRequiredProfile("");
		res.setSkillsRequired("");
		res.setTechnologiesRequired("");
		res.setSalary(0.0);
		res.setTicker("");
		res.setDraftMode(1);

		return res;

	}

	public Collection<Position> findAll() {
		return this.positionRepository.findAll();
	}

	public Position findOne(final Integer id) {
		return this.positionRepository.findOne(id);
	}

	public Collection<Position> getPositionsByCompany(final Integer id) {
		return this.positionRepository.getPositionsByCompany(id);
	}

	public Collection<Position> getPositionsOutDraftMode() {
		return this.positionRepository.getPositionsOutDraftMode();
	}

	public Collection<Position> getPositionsByCompanyOutDraftMode(final Integer id) {
		return this.positionRepository.getPositionsByCompanyOutDraftMode(id);
	}

	public Position save(final Position p) {
		final Position saved = this.positionRepository.save(p);

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		final Position old = this.positionRepository.findOne(p.getId());
		Assert.isTrue(old.getDraftMode() == 1);

		Assert.isTrue(p.getCompany().equals(a));
		Assert.isTrue(p.getTitle() != null && p.getTitle() != "");
		Assert.isTrue(p.getDescription() != null && p.getDescription() != "");
		Assert.isTrue(new Date().before(p.getDeadLine()));
		Assert.isTrue(p.getRequiredProfile() != null && p.getRequiredProfile() != "");
		Assert.isTrue(p.getSkillsRequired() != null && p.getSkillsRequired() != "");
		Assert.isTrue(p.getTechnologiesRequired() != null && p.getTechnologiesRequired() != "");
		Assert.isTrue(p.getSalary() >= 0.0);
		Assert.isTrue(p.getTicker() != null && p.getTicker() != "");
		Assert.isTrue(p.getDraftMode() == 0 || p.getDraftMode() == 1);

		return saved;
	}

	//RECONSTRUCT
	public Position reconstruct(final Position position, final BindingResult binding) {
		final Position res;

		if (position.getId() == 0) {
			res = position;

			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());

			position.setCompany((Company) a);
			position.setTicker(PositionService.generarTicker((Company) a));
			position.setDraftMode(1);
			this.validator.validate(res, binding);

			return res;
		} else {
			res = this.positionRepository.findOne(position.getId());
			final Position copy = new Position();
			copy.setCompany(res.getCompany());
			copy.setTicker(res.getTicker());
			copy.setDeadLine(position.getDeadLine());
			copy.setDescription(position.getDescription());
			copy.setDraftMode(position.getDraftMode());
			copy.setRequiredProfile(position.getRequiredProfile());
			copy.setSalary(position.getSalary());
			copy.setSkillsRequired(position.getSkillsRequired());
			copy.setTechnologiesRequired(position.getTechnologiesRequired());
			copy.setTitle(position.getTitle());
			copy.setId(position.getId());
			copy.setVersion(position.getVersion());
			this.validator.validate(copy, binding);
			return copy;

		}

	}

	//TICKER
	public static String generarTicker(final Company company) {
		final int tam = 4;

		final String d = company.getNameCompany().substring(0, 4);

		String ticker = "-";
		final String a = "0123456789";

		for (int i = 0; i < tam; i++) {
			final Integer random = (int) (Math.floor(Math.random() * a.length()) % a.length());
			ticker = ticker + a.charAt(random);
		}

		return d + ticker;

	}
}
