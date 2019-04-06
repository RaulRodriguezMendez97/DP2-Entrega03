
package services;

import java.util.Collection;
import java.util.HashSet;

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

		res = problem;
		res.setDraftMode(1);
		res.setApplications(new HashSet<Application>());

		this.validator.validate(res, binding);
		return res;
	}

}
