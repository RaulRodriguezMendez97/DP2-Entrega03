
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
import services.BrotherhoodService;
import services.HistoryService;
import services.MiscellaneousRecordService;
import domain.Actor;
import domain.Brotherhood;
import domain.History;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("/miscellaneousRecord/brotherhood")
public class MiscellaneousRecordBrotherhoodController extends AbstractController {

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;
	@Autowired
	private HistoryService				historyService;
	@Autowired
	private ActorService				actorService;
	@Autowired
	private BrotherhoodService			brotherhoodService;
	@Autowired
	private ActorService				actorS;


	//Listado de Floats(Pasos)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Integer historyId) {
		ModelAndView result;
		try {
			final Collection<MiscellaneousRecord> miscellaneousRecords;
			final History history;
			history = this.historyService.findOne(historyId);
			Assert.notNull(history);
			miscellaneousRecords = history.getMiscellaneousRecords();

			result = new ModelAndView("miscellaneousRecord/list");
			result.addObject("miscellaneousRecords", miscellaneousRecords);
			result.addObject("history", history);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createMiscellaneousRecord(@RequestParam final Integer historyId) {

		ModelAndView result;
		try {
			final MiscellaneousRecord miscellaneousRecord;
			final History history;
			history = this.historyService.findOne(historyId);
			Assert.notNull(history);
			miscellaneousRecord = this.miscellaneousRecordService.create();

			result = new ModelAndView("miscellaneousRecord/edit");
			result.addObject("miscellaneousRecord", miscellaneousRecord);
			result.addObject("history", history);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editMiscellaneousRecord(@RequestParam final Integer historyId, @RequestParam final Integer miscellaneousRecordId) {
		ModelAndView result;
		try {
			MiscellaneousRecord miscellaneousRecord;
			History history;

			miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
			history = this.historyService.findOne(historyId);
			Assert.notNull(history);
			Assert.notNull(miscellaneousRecord);
			result = new ModelAndView("miscellaneousRecord/edit");
			result.addObject("miscellaneousRecord", miscellaneousRecord);
			result.addObject("history", history);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveMiscellaneousRecordHistory(@Valid final MiscellaneousRecord miscellaneousRecord, final BindingResult binding, @RequestParam final Integer historyId) {
		ModelAndView result;
		try {
			if (!binding.hasErrors()) {
				final MiscellaneousRecord miscellaneousRecordSave = this.miscellaneousRecordService.save(miscellaneousRecord);

				final History history = this.historyService.findOne(historyId);
				Assert.notNull(history);
				if (history.getMiscellaneousRecords().contains(miscellaneousRecordSave)) {
					history.getMiscellaneousRecords().remove(miscellaneousRecordSave);
					history.getMiscellaneousRecords().add(miscellaneousRecordSave);
				} else
					history.getMiscellaneousRecords().add(miscellaneousRecordSave);
				this.historyService.save(history);
				result = new ModelAndView("redirect:list.do?historyId=" + historyId);
			} else {
				final History history = this.historyService.findOne(historyId);
				Assert.notNull(history);
				result = new ModelAndView("miscellaneousRecord/edit");
				result.addObject("miscellaneousRecord", miscellaneousRecord);
				result.addObject("history", history);
			}
		} catch (final Exception e) {
			final UserAccount user = this.actorS.getActorLogged().getUserAccount();
			final Brotherhood br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
			final History history = this.historyService.findOne(historyId);
			if (br.equals(history.getBrotherhood())) {
				result = new ModelAndView("miscellaneousRecord/edit");
				result.addObject("miscellaneousRecord", miscellaneousRecord);
				result.addObject("history", history);
			} else
				result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteMiscellaneousRecordHistory(final MiscellaneousRecord miscellaneousRecord, final BindingResult binding, @RequestParam final Integer historyId) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				final History history = this.historyService.findOne(historyId);
				Assert.notNull(history);
				final UserAccount user = LoginService.getPrincipal();
				final Actor a = this.actorService.getActorByUserAccount(user.getId());
				Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
				Assert.isTrue(history.getBrotherhood() == a);

				//history.getMiscellaneousRecords().remove(miscellaneousRecord);
				this.miscellaneousRecordService.delete(miscellaneousRecord, historyId);
				//this.historyService.save(history);
				result = new ModelAndView("redirect:list.do?historyId=" + historyId);
			} else {
				final History history = this.historyService.findOne(historyId);
				Assert.notNull(history);
				result = new ModelAndView("miscellaneousRecord/edit");
				result.addObject("miscellaneousRecord", miscellaneousRecord);
				result.addObject("history", history);
			}
		} catch (final Exception e) {
			final History history = this.historyService.findOne(historyId);
			final UserAccount user = this.actorS.getActorLogged().getUserAccount();
			final Brotherhood br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
			if (br.equals(history.getBrotherhood())) {
				result = new ModelAndView("miscellaneousRecord/edit");
				result.addObject("miscellaneousRecord", miscellaneousRecord);
				result.addObject("history", history);
			} else
				result = new ModelAndView("redirect:../../");
		}
		return result;
	}

}
