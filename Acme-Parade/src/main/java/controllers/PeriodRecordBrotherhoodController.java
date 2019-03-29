
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
import services.PeriodRecordService;
import domain.Actor;
import domain.History;
import domain.PeriodRecord;

@Controller
@RequestMapping("/periodRecord/brotherhood")
public class PeriodRecordBrotherhoodController extends AbstractController {

	@Autowired
	private PeriodRecordService	periodRecordService;
	@Autowired
	private HistoryService		historyService;
	@Autowired
	private ActorService		actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Integer historyId) {
		ModelAndView result;
		try {
			final Collection<PeriodRecord> periodRecords;

			periodRecords = this.periodRecordService.getPeriodRecordsByHistory(historyId);
			Assert.notNull(periodRecords);
			final History h = this.historyService.findOne(historyId);

			result = new ModelAndView("periodRecords/list");
			result.addObject("periodRecords", periodRecords);
			result.addObject("history", h);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;

	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final Integer periodRecordId) {
		ModelAndView result;
		try {
			PeriodRecord periodRecord;

			periodRecord = this.periodRecordService.findOne(periodRecordId);
			Assert.notNull(periodRecord);
			final History h = this.historyService.getHistotyByPeriodRecord(periodRecordId);

			result = new ModelAndView("periodRecords/show");
			result.addObject("periodRecord", periodRecord);
			result.addObject("history", h);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer historyId) {
		ModelAndView result;

		try {
			final PeriodRecord periodRecord;
			final History history;
			history = this.historyService.findOne(historyId);
			Assert.notNull(history);
			periodRecord = this.periodRecordService.create();

			result = new ModelAndView("periodRecord/edit");
			result.addObject("periodRecord", periodRecord);
			result.addObject("history", history);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer historyId, @RequestParam final Integer periodRecordId) {
		ModelAndView result;
		try {
			PeriodRecord periodRecord;
			History history;

			periodRecord = this.periodRecordService.findOne(periodRecordId);
			history = this.historyService.findOne(historyId);
			Assert.notNull(periodRecord);
			Assert.notNull(history);
			result = new ModelAndView("periodRecord/edit");
			result.addObject("periodRecord", periodRecord);
			result.addObject("history", history);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final PeriodRecord periodRecord, final BindingResult binding, @RequestParam final int historyId) {
		ModelAndView result;
		try {
			if (!binding.hasErrors()) {
				final PeriodRecord saved = this.periodRecordService.save(periodRecord);
				final History history = this.historyService.findOne(historyId);
				if (history.getPeriodRecords().contains(saved)) {
					history.getPeriodRecords().remove(saved);
					history.getPeriodRecords().add(saved);
				} else
					history.getPeriodRecords().add(saved);
				this.historyService.save(history);
				result = new ModelAndView("redirect:list.do?historyId=" + historyId);
			} else {
				final History history = this.historyService.findOne(historyId);
				result = new ModelAndView("periodRecord/edit");
				result.addObject("periodRecord", periodRecord);
				result.addObject("history", history);
			}
		} catch (final Exception e) {
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());
			final History history = this.historyService.findOne(historyId);
			if (a.equals(history.getBrotherhood())) {

				result = new ModelAndView("periodRecord/edit");
				result.addObject("periodRecord", periodRecord);
				result.addObject("history", history);
				result.addObject("exception", e);
			} else
				result = new ModelAndView("redirect:../../");

		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final PeriodRecord periodRecord, final BindingResult binding, @RequestParam final int historyId) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				this.periodRecordService.delete(periodRecord, historyId);
				result = new ModelAndView("redirect:list.do?historyId=" + historyId);
			} else {
				result = new ModelAndView("periodRecord/edit");
				result.addObject("periodRecord", periodRecord);
				result.addObject("history", this.historyService.findOne(historyId));
			}
		} catch (final Exception e) {
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());
			final History history = this.historyService.findOne(historyId);
			if (a.equals(history.getBrotherhood())) {
				result = new ModelAndView("periodRecord/edit");
				result.addObject("periodRecord", periodRecord);
				result.addObject("history", this.historyService.findOne(historyId));
				result.addObject("exception", e);
			} else
				result = new ModelAndView("redirect:../../");

		}
		return result;
	}
}
