
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BrotherhoodService;
import services.HistoryService;
import services.InceptionRecordService;
import domain.History;
import domain.InceptionRecord;

@Controller
@RequestMapping("/inception-record/brotherhood")
public class InceptionRecordBrotherhoodController extends AbstractController {

	@Autowired
	private InceptionRecordService	inceptionRecordService;
	@Autowired
	private HistoryService			historyService;
	@Autowired
	private BrotherhoodService		brotherhoodService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int historyId) {
		ModelAndView result;
		try {
			final History history = this.historyService.findOne(historyId);
			final InceptionRecord inceptionRecord = this.inceptionRecordService.findOneIfItIsMine(history.getInceptionRecord().getId());
			result = new ModelAndView("inceptionRecord/show");
			result.addObject("inceptionRecord", inceptionRecord);
			result.addObject("historyId", historyId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../history/brotherhood/list.do");
		}
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final InceptionRecord inceptionRecord = this.inceptionRecordService.create();
		result = new ModelAndView("inceptionRecord/edit");
		result.addObject("inceptionRecord", inceptionRecord);
		result.addObject("pictures", this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()).getPictures());
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int inceptionRecordId) {
		ModelAndView result;
		try {
			final InceptionRecord inceptionRecord = this.inceptionRecordService.findOneIfItIsMine(inceptionRecordId);
			result = new ModelAndView("inceptionRecord/edit");
			result.addObject("inceptionRecord", inceptionRecord);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../history/brotherhood/list.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(InceptionRecord inceptionRecord, final BindingResult binding) {
		ModelAndView result;
		try {
			inceptionRecord = this.inceptionRecordService.reconstruct(inceptionRecord, binding);
			if (binding.hasErrors()) {
				result = new ModelAndView("inceptionRecord/edit");
				result.addObject("inceptionRecord", inceptionRecord);
			} else {
				this.inceptionRecordService.save(inceptionRecord);
				result = new ModelAndView("redirect:../../history/brotherhood/list.do");
			}
		} catch (final Exception e) {
			result = new ModelAndView("inceptionRecord/edit");
			result.addObject("inceptionRecord", inceptionRecord);
			result.addObject("exception", "e");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int inceptionRecordId) {
		ModelAndView result;
		try {
			final InceptionRecord inceptionRecord = this.inceptionRecordService.findOneIfItIsMine(inceptionRecordId);
			this.inceptionRecordService.delete(inceptionRecord);
			result = new ModelAndView("redirect:../../history/brotherhood/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../history/brotherhood/list.do");
			result.addObject("exception", e);
			final Collection<InceptionRecord> allMyInceptionRecord = this.inceptionRecordService.getAllMyInceptionRecords();
			result.addObject("inceptionRecords", allMyInceptionRecord);
		}
		return result;
	}
}
