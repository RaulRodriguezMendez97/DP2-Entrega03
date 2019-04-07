
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ApplicationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Curricula;
import domain.Hacker;
import domain.Problem;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository	applicationRepository;
	@Autowired
	private Validator				validator;
	@Autowired
	private HackerService			hackerService;
	@Autowired
	private CurriculaService		curriculaService;
	@Autowired
	private ProblemService			problemService;


	public Application create() {
		final Application a = new Application();
		a.setCurricula(null);
		a.setExplication("");
		a.setHacker(null);
		a.setMoment(new Date());
		a.setStatus(0);
		a.setSubmitMoment(null);
		a.setUrlCode("");
		return a;
	}

	public Application findOne(final Integer id) {
		return this.applicationRepository.findOne(id);
	}

	public Collection<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	public Application save(final Application application) {
		Application savedApplication;

		if (application.getId() != 0) {
			Assert.isTrue(this.getAllMyApplicationsHacker().contains(application));
			Assert.isTrue(this.applicationRepository.findOne(application.getId()).getStatus() == 0);
		} else {
			final Collection<Curricula> c = this.curriculaService.getCurriculasByHacker(this.hackerService.hackerUserAccount(LoginService.getPrincipal().getId()).getId());
			Assert.isTrue(c.contains(application.getCurricula()));
		}
		savedApplication = this.applicationRepository.save(application);
		if (application.getId() == 0) {
			final Problem p = this.problemService.getAleatoryProblem();
			p.getApplications().add(savedApplication);
			this.problemService.saveApplication(p);
		}
		return savedApplication;
	}
	//RECONSTRUCT
	public Application reconstruct(final Application application, final BindingResult binding) {
		Application res;

		if (application.getId() == 0) {
			res = application;

			final UserAccount user = LoginService.getPrincipal();
			final Hacker h = this.hackerService.hackerUserAccount(user.getId());

			res.setHacker(h);
			res.setStatus(0);
			res.setMoment(new Date());
			res.setExplication("");
			res.setUrlCode("");

			this.validator.validate(res, binding);

		} else {
			res = this.applicationRepository.findOne(application.getId());
			final Application a = new Application();
			a.setId(application.getId());
			a.setVersion(application.getVersion());
			a.setCurricula(res.getCurricula());
			a.setExplication(application.getExplication());
			a.setUrlCode(application.getUrlCode());
			a.setStatus(application.getStatus());
			a.setMoment(res.getMoment());
			a.setHacker(res.getHacker());
			a.setSubmitMoment(new Date());

			this.validator.validate(a, binding);

			res = a;

		}
		return res;
	}
	//DASHBOARD
	public List<Object[]> getAvgMinMaxDesvAppByHackers() {
		return this.applicationRepository.getAvgMinMaxDesvAppByHackers();
	}

	public Collection<Application> getAllMyApplicationsHacker() {
		final int hackerId = this.hackerService.hackerUserAccount(LoginService.getPrincipal().getId()).getId();
		return this.applicationRepository.getAllMyApplicationsHacker(hackerId);
	}

	public Collection<Curricula> getCurriculaHacker() {
		final Integer hackerId = this.hackerService.hackerUserAccount(LoginService.getPrincipal().getId()).getId();
		return this.curriculaService.getCurriculasByHacker(hackerId);
	}

	public void delete(final Application application) {
		Assert.isTrue(this.getAllMyApplicationsHacker().contains(application));
		this.applicationRepository.delete(application);
	}
}
