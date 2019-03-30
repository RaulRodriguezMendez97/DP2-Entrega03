
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
import services.MemberService;
import domain.Actor;
import domain.Hacker;

@Controller
@RequestMapping("/member/brotherhood")
public class MemberBrotherhoodController {

	@Autowired
	private ActorService	actorService;

	@Autowired
	private MemberService	memberService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Hacker> members;

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		members = this.memberService.getMemberByBrotherhood(a.getId());
		Assert.notNull(members);

		result = new ModelAndView("member/list");
		result.addObject("members", members);
		return result;

	}

}
