
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.BrotherhoodService;
import domain.Actor;
import domain.Companie;

@Controller
@RequestMapping("/brotherhood/member")
public class BrotherhoodMemberController {

	@Autowired
	private ActorService		actorService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/list-member-belongs", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Companie> brotherhoods;

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		brotherhoods = this.brotherhoodService.getBrotherhoodsBelongsByMember(a.getId());
		Assert.notNull(brotherhoods);

		final Boolean belong = true;

		result = new ModelAndView("brotherhood/list-member");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("belong", belong);

		return result;

	}

	@RequestMapping(value = "/list-member-belonged", method = RequestMethod.GET)
	public ModelAndView listbelonged() {
		final ModelAndView result;
		final Collection<Companie> brotherhoods;

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		brotherhoods = this.brotherhoodService.getBrotherhoodsBelongedByMember(a.getId());
		Assert.notNull(brotherhoods);

		final Boolean belong = false;
		result = new ModelAndView("brotherhood/list-member");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("belong", belong);

		return result;

	}

}
