
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HistoryService;
import services.InceptionRecordService;
import domain.Companie;
import domain.History;
import domain.InceptionRecord;

@Controller
@RequestMapping("/inception-record")
public class InceptionRecordController extends AbstractController {

	@Autowired
	private InceptionRecordService	inceptionRecordService;
	@Autowired
	private HistoryService			historyService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int historyId) {
		ModelAndView result;
		try {
			final History history = this.historyService.findOneAnomimo(historyId);
			final Companie brotherhood = history.getBrotherhood();
			final InceptionRecord inceptionRecord = this.inceptionRecordService.findOneThisBrotherhood(history.getInceptionRecord().getId(), brotherhood);
			result = new ModelAndView("inceptionRecord/show");
			result.addObject("inceptionRecord", inceptionRecord);
			result.addObject("brotherhood", brotherhood);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../");
		}
		return result;

	}
}
