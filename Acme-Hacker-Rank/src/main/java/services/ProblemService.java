
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import repositories.ProblemRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Application;
import domain.Position;
import domain.Problem;

@Transactional
@Service
public class ProblemService {

	@Autowired
	private ProblemRepository	problemRepository;

	@Autowired
	private PositionRepository	positionRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;

	@Autowired
	private PositionService		positionService;


	public Problem create() {
		final Problem p = new Problem();

		p.setTitle("");
		p.setStatement("");
		p.setHint("");
		p.setAttachment(new HashSet<String>());
		p.setDraftMode(1);
		p.setApplications(new HashSet<Application>());

		return p;
	}

	public Collection<Problem> findAll() {
		return this.problemRepository.findAll();
	}

	public Problem findOne(final Integer id) {
		return this.problemRepository.findOne(id);
	}

	public Problem save(final Problem problem) {

		if (problem.getId() != 0) {
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());
			final Position p = this.positionRepository.getPositionByProblem(problem.getId());
			Assert.isTrue(p.getCompany().equals(a));
			//Assert.isTrue(p.getDraftMode() == 1);
			final Problem old = this.findOne(problem.getId());
			Assert.isTrue(old.getDraftMode() == 1);
		}

		final Problem saved = this.problemRepository.save(problem);
		return saved;
	}

	public Problem reconstruct(final Problem problem, final BindingResult binding) {
		final Problem res;

		if (problem.getId() == 0) {

			res = problem;
			problem.setDraftMode(1);
			problem.setApplications(new HashSet<Application>());

			this.validator.validate(res, binding);
			return res;
		} else {
			res = this.problemRepository.findOne(problem.getId());
			final Problem copy = new Problem();

			copy.setId(res.getId());
			copy.setVersion(res.getVersion());
			copy.setApplications(res.getApplications());
			copy.setTitle(problem.getTitle());
			copy.setStatement(problem.getStatement());
			copy.setHint(problem.getHint());
			copy.setAttachment(problem.getAttachment());
			copy.setDraftMode(problem.getDraftMode());

			this.validator.validate(copy, binding);
			return copy;
		}
	}

	public void delete(final Problem problem, final Integer positionId) {
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		final Position p = this.positionRepository.findOne(positionId);
		Assert.isTrue(p.getCompany().equals(a));
		Assert.isTrue(p.getProblems().contains(problem));
		//Assert.isTrue(p.getDraftMode()==1 && p.getIsCancelled()==0);
		Assert.isTrue(problem.getDraftMode() == 1);
		p.getProblems().remove(problem);
		this.problemRepository.delete(problem);
		this.positionService.save(p);

	}

	public Problem getProblemByApplication(final Application a) {
		return this.problemRepository.getProblemByApplication(a);
	}
	public Problem getAleatoryProblem() {
		final Collection<Problem> ps = this.findAll();
		final List<Problem> list = new ArrayList<Problem>(ps);
		final int number = (int) Math.random() % list.size();
		return list.get(number);
	}

	public Problem saveApplication(final Problem p) {
		return this.problemRepository.save(p);
	}

}
