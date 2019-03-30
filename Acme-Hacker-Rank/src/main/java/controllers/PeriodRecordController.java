
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
import services.PeriodRecordService;
import domain.Company;
import domain.History;
import domain.PeriodRecord;

@Controller
@RequestMapping("/periodRecord")
public class PeriodRecordController extends AbstractController {

	@Autowired
	private PeriodRecordService	periodRecordService;
	@Autowired
	private HistoryService		historyService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Integer historyId) {
		ModelAndView result;
		try {

			final History h = this.historyService.findOneAnomimo(historyId);
			Assert.notNull(h);
			final Company brotherhood = h.getBrotherhood();
			final Collection<PeriodRecord> periodRecords = h.getPeriodRecords();

			result = new ModelAndView("periodRecords/list");
			result.addObject("periodRecords", periodRecords);
			result.addObject("history", h);
			result.addObject("brotherhood", brotherhood);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../");
		}
		return result;

	}

}
