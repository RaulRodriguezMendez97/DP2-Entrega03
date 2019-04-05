
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.PositionService;
import services.ProblemService;
import domain.Actor;
import domain.Position;
import domain.Problem;

@Controller
@RequestMapping("/problem/company")
public class ProblemCompanyController extends AbstractController {

	@Autowired
	private PositionService	positionService;

	@Autowired
	private ProblemService	problemService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Integer positionId) {
		ModelAndView result;
		try {
			final Position position = this.positionService.findOne(positionId);
			final Collection<Problem> problems = position.getProblems();

			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());

			Assert.isTrue(position.getCompany().equals(a));

			result = new ModelAndView("problem/list");
			result.addObject("problems", problems);
			result.addObject("position", position);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer positionId) {
		ModelAndView result;

		try {
			final Problem problem;
			final Position position;
			position = this.positionService.findOne(positionId);

			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());

			Assert.isTrue(position.getCompany().equals(a));

			problem = this.problemService.create();

			result = new ModelAndView("problem/edit");
			result.addObject("problem", problem);
			result.addObject("position", position);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

}
