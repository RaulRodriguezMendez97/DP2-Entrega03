
package controllers;

import java.util.Collection;

import javax.validation.Valid;

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
import services.HistoryService;
import services.LegalRecordService;
import domain.Actor;
import domain.Companie;
import domain.History;
import domain.LegalRecord;

@Controller
@RequestMapping("/legalRecord/brotherhood")
public class LegalRecordBrotherhoodController extends AbstractController {

	@Autowired
	private LegalRecordService	legalRecordService;
	@Autowired
	private HistoryService		historyService;

	@Autowired
	private ActorService		actorService;


	//Listado de Floats(Pasos)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Integer historyId) {
		ModelAndView result;
		try {
			final Collection<LegalRecord> legalRecords;
			final History history;
			final Companie brotherhood;
			history = this.historyService.findOne(historyId);
			legalRecords = history.getLegalRecords();
			brotherhood = history.getBrotherhood();

			result = new ModelAndView("legalRecord/list");
			result.addObject("legalRecords", legalRecords);
			result.addObject("history", history);
			result.addObject("brotherhood", brotherhood);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createLegalRecord(@RequestParam final int historyId) {
		final ModelAndView result;
		final LegalRecord legalRecord;
		final History history;
		history = this.historyService.findOne(historyId);
		legalRecord = this.legalRecordService.create();

		result = new ModelAndView("legalRecord/edit");
		result.addObject("legalRecord", legalRecord);
		result.addObject("history", history);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editLegalRecord(@RequestParam final Integer historyId, @RequestParam final Integer legalRecordId) {
		ModelAndView result;
		try {
			LegalRecord legalRecord;
			History history;

			legalRecord = this.legalRecordService.findOne(legalRecordId);
			history = this.historyService.findOne(historyId);
			Assert.notNull(history);
			Assert.notNull(legalRecord);
			result = new ModelAndView("legalRecord/edit");
			result.addObject("legalRecord", legalRecord);
			result.addObject("history", history);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView savelegalRecord(@Valid final LegalRecord legalRecord, final BindingResult binding, @RequestParam final Integer historyId) {
		ModelAndView result;
		try {
			if (!binding.hasErrors()) {
				final LegalRecord legalRecordSave = this.legalRecordService.save(legalRecord);
				final History history = this.historyService.findOne(historyId);
				Assert.notNull(history);
				if (history.getLegalRecords().contains(legalRecordSave)) {
					history.getLegalRecords().remove(legalRecordSave);
					history.getLegalRecords().add(legalRecordSave);
				} else
					history.getLegalRecords().add(legalRecordSave);
				this.historyService.save(history);
				result = new ModelAndView("redirect:list.do?historyId=" + historyId);
			} else {
				final History history = this.historyService.findOne(historyId);
				Assert.notNull(history);
				result = new ModelAndView("legalRecord/edit");
				result.addObject("legalRecord", legalRecord);
				result.addObject("history", history);
			}
		} catch (final Exception e) {
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());
			final History history = this.historyService.findOne(historyId);
			if (a.equals(history.getBrotherhood())) {

				result = new ModelAndView("legalRecord/edit");
				result.addObject("legalRecord", legalRecord);
				result.addObject("history", history);
				result.addObject("exception", e);
			} else
				result = new ModelAndView("redirect:../../");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteLegalRecordHistory(final LegalRecord legalRecord, final BindingResult binding, @RequestParam final Integer historyId) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				this.legalRecordService.delete(legalRecord, historyId);
				result = new ModelAndView("redirect:list.do?historyId=" + historyId);
			} else {
				final History history = this.historyService.findOne(historyId);
				Assert.notNull(history);
				result = new ModelAndView("legalRecord/edit");
				result.addObject("legalRecord", legalRecord);
				result.addObject("history", history);
			}
		} catch (final Exception e) {

			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());
			final History history = this.historyService.findOne(historyId);
			if (a.equals(history.getBrotherhood())) {
				result = new ModelAndView("legalRecord/edit");
				result.addObject("legalRecord", legalRecord);
				result.addObject("history", this.historyService.findOne(historyId));
				result.addObject("exception", e);
			} else
				result = new ModelAndView("redirect:../../");

		}
		return result;
	}

}
