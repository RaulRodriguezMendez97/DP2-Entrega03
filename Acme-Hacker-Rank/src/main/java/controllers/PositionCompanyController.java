
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.PositionService;
import domain.Actor;
import domain.Position;

@Controller
@RequestMapping("/position/company")
public class PositionCompanyController extends AbstractController {

	@Autowired
	private PositionService	positionService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Position> positions;

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		positions = this.positionService.getPositionsByCompany(a.getId());
		Assert.notNull(positions);

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Position position;

		position = this.positionService.create();

		result = new ModelAndView("position/edit");
		result.addObject("position", position);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer positionId) {
		final ModelAndView result;
		final Position position;

		position = this.positionService.findOne(positionId);

		result = new ModelAndView("position/edit");
		result.addObject("position", position);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final Position p, final BindingResult binding) {
		final ModelAndView result;
		final Position position;

		position = this.positionService.reconstruct(p, binding);

		if (!binding.hasErrors()) {
			this.positionService.save(position);
			result = new ModelAndView("list.do");
		} else {
			result = new ModelAndView("position/edit");
			result.addObject("position", position);
		}

		return result;

	}

}
