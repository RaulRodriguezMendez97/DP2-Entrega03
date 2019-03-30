
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HistoryService;
import domain.Company;
import domain.History;
import domain.LegalRecord;

@Controller
@RequestMapping("/legalRecord")
public class LegalRecordController extends AbstractController {

	@Autowired
	private HistoryService	historyService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int historyId) {
		ModelAndView result;
		try {
			final Collection<LegalRecord> legalRecords;
			final History history;
			final Company brotherhood;

			history = this.historyService.findOneAnomimo(historyId);

			brotherhood = history.getBrotherhood();

			legalRecords = history.getLegalRecords();

			result = new ModelAndView("legalRecord/list");
			result.addObject("legalRecords", legalRecords);
			result.addObject("history", history);
			result.addObject("brotherhood", brotherhood);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../");
		}

		return result;
	}

}
