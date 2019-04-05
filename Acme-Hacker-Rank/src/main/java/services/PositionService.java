
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import repositories.PositionRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Company;
import domain.Finder;
import domain.Position;
import domain.Problem;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepository	positionRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private FinderRepository	finderRepository;


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
		res.setIsCancelled(0);
		res.setProblems(new HashSet<Problem>());

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

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		if (p.getId() != 0) {
			final Position old = this.findOne(p.getId());
			Assert.isTrue(old.getDraftMode() == 1);
		}

		if (p.getId() != 0)
			if (p.getDraftMode() == 0)
				Assert.isTrue(p.getProblems().size() >= 2);

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
		Assert.isTrue(p.getIsCancelled() == 0);

		final Position saved = this.positionRepository.save(p);

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
			position.setIsCancelled(0);
			position.setProblems(new HashSet<Problem>());
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
			copy.setProblems(res.getProblems());
			copy.setIsCancelled(res.getIsCancelled());

			this.validator.validate(copy, binding);
			//			if (binding.hasErrors())
			//				throw new ValidationException();
			return copy;

		}

	}

	public void delete(final Position p) {
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		Assert.isTrue(p.getCompany().equals(a));
		Assert.isTrue(p.getDraftMode() == 1);

		final List<Finder> finders = this.finderService.getFinderByPosition(p.getId());
		for (int i = 0; i < finders.size(); i++) {
			finders.get(i).getPositions().remove(p);
			this.finderRepository.save(finders.get(i));
		}

		this.positionRepository.delete(p);
	}

	public void cancel(final Position p) {
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		Assert.isTrue(p.getCompany().equals(a));
		Assert.isTrue(p.getDraftMode() == 0);
		p.setIsCancelled(1);
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

	public List<Object[]> getAvgMinMaxDesvPositionByCompany() {
		return this.positionRepository.getAvgMinMaxDesvPositionByCompany();
	}

	public List<Object[]> getAvgMaxMinDesvSalaryOfPositions() {
		return this.positionRepository.getAvgMaxMinDesvSalaryOfPositions();
	}

	public String getPositionWithBestSalary() {
		return this.positionRepository.getPositionWithBestSalary();
	}

	public String getPositionWithWorstSalary() {
		return this.positionRepository.getPositionWithWorstSalary();
	}

	public Collection<Position> getPositionsByFinder(final String word) {
		return this.positionRepository.getPositionByFinder(word);
	}

	public Position getPositionByProblem(final Integer id) {
		return this.positionRepository.getPositionByProblem(id);
	}
}
