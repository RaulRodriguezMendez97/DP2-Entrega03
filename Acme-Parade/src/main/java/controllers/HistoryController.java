
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HistoryService;
import domain.History;

@Controller
@RequestMapping("/history")
public class HistoryController extends AbstractController {

	@Autowired
	private HistoryService	historyService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Integer idBrotherhood) {
		ModelAndView result;
		try {

			final Collection<History> histories;

			histories = this.historyService.getHistoryByBrotherhood(idBrotherhood);
			Assert.notNull(histories);

			result = new ModelAndView("history/list");
			result.addObject("histories", histories);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../");
		}

		return result;

	}

}
