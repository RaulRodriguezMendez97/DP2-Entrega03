
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
import domain.Companie;
import domain.History;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("/miscellaneousRecord")
public class MiscellaneousRecordController extends AbstractController {

	@Autowired
	private HistoryService	historyService;


	//Listado de Floats(Pasos)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int historyId) {

		ModelAndView result;
		try {
			final Collection<MiscellaneousRecord> miscellaneousRecords;
			final History history;
			history = this.historyService.findOneAnomimo(historyId);
			Assert.notNull(history);
			miscellaneousRecords = history.getMiscellaneousRecords();
			final Companie brotherhood = history.getBrotherhood();
			result = new ModelAndView("miscellaneousRecord/list");
			result.addObject("miscellaneousRecords", miscellaneousRecords);
			result.addObject("history", history);
			result.addObject("brotherhood", brotherhood);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../");
		}

		return result;
	}
}
