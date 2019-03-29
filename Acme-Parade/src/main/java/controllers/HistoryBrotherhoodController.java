
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
import services.HistoryService;
import domain.Actor;
import domain.History;

@Controller
@RequestMapping("/history/brotherhood")
public class HistoryBrotherhoodController extends AbstractController {

	@Autowired
	private HistoryService	historyService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<History> histories;

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		histories = this.historyService.getHistoryByBrotherhood(a.getId());
		Assert.notNull(histories);

		result = new ModelAndView("history/list");
		result.addObject("histories", histories);

		return result;

	}

}
