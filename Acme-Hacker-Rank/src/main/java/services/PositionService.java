
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


	public Position create() {
		final Position res = new Position();

		res.setCompanie(new Company());
		res.setTitle("");
		res.setDescription("");
		res.setDeadLine(new Date());
		res.setRequiredProfile("");
		res.setSkillsRequired("");
		res.setTechnologiesRequired("");
		res.setSalary(0);
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

	public Position save(final Position p) {
		final Position saved = this.positionRepository.save(p);

		if (p.getId() == 0) {
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());
			p.setCompanie((Company) a);
		}

		return saved;
	}
}
