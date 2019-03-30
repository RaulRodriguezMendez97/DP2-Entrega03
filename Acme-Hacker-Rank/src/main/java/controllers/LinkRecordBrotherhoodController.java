
package controllers;

import java.util.Collection;

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
import services.LinkRecordService;
import domain.Actor;
import domain.Companie;
import domain.History;
import domain.LinkRecord;

@Controller
@RequestMapping("/linkRecord/brotherhood")
public class LinkRecordBrotherhoodController extends AbstractController {

	@Autowired
	private LinkRecordService	linkRecordService;
	@Autowired
	private HistoryService		historyService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private ActorService		actorS;


	//Listado de Floats(Pasos)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Integer historyId) {
		ModelAndView result;
		try {
			final Collection<LinkRecord> linkRecords;
			final History history;
			history = this.historyService.findOne(historyId);
			Assert.notNull(history);
			linkRecords = history.getLinkRecords();

			result = new ModelAndView("linkRecord/list");
			result.addObject("linkRecords", linkRecords);
			result.addObject("history", history);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createLinkRecord(@RequestParam final Integer historyId) {

		ModelAndView result;
		try {
			final UserAccount user = this.actorS.getActorLogged().getUserAccount();
			final Companie br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
			final LinkRecord linkRecord;
			final History history;
			final Collection<Companie> brotherhoods;
			brotherhoods = this.brotherhoodService.findAll();
			brotherhoods.remove(br);
			history = this.historyService.findOne(historyId);
			Assert.notNull(history);
			linkRecord = this.linkRecordService.create();

			result = new ModelAndView("linkRecord/edit");
			result.addObject("linkRecord", linkRecord);
			result.addObject("history", history);
			result.addObject("brotherhoods", brotherhoods);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;
	}

	//Edicion de una imagne de un float(paso)
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editPictureFloat(@RequestParam final Integer historyId, @RequestParam final Integer linkRecordId) {
		ModelAndView result;
		try {
			final UserAccount user = this.actorS.getActorLogged().getUserAccount();
			final Companie br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
			LinkRecord linkRecord;
			History history;
			final Collection<Companie> brotherhoods;
			brotherhoods = this.brotherhoodService.findAll();
			brotherhoods.remove(br);

			linkRecord = this.linkRecordService.findOne(linkRecordId);
			history = this.historyService.findOne(historyId);
			Assert.notNull(history);
			Assert.notNull(linkRecord);
			result = new ModelAndView("linkRecord/edit");
			result.addObject("linkRecord", linkRecord);
			result.addObject("history", history);
			result.addObject("brotherhoods", brotherhoods);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveLinkRecordHistory(LinkRecord linkRecord, final BindingResult binding, @RequestParam final Integer historyId) {
		ModelAndView result;
		try {
			linkRecord = this.linkRecordService.reconstruct(linkRecord, binding);
			if (!binding.hasErrors()) {
				final LinkRecord linkRecordSave = this.linkRecordService.save(linkRecord);

				final History history = this.historyService.findOne(historyId);
				Assert.notNull(history);
				if (history.getLinkRecords().contains(linkRecordSave)) {
					history.getLinkRecords().remove(linkRecordSave);
					history.getLinkRecords().add(linkRecordSave);
				} else
					history.getLinkRecords().add(linkRecordSave);
				this.historyService.save(history);
				result = new ModelAndView("redirect:list.do?historyId=" + historyId);
			} else {
				final UserAccount user = this.actorS.getActorLogged().getUserAccount();
				final Companie br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
				final History history = this.historyService.findOne(historyId);
				final Collection<Companie> brotherhoods;
				brotherhoods = this.brotherhoodService.findAll();
				brotherhoods.remove(br);
				Assert.notNull(history);
				result = new ModelAndView("linkRecord/edit");
				result.addObject("linkRecord", linkRecord);
				result.addObject("history", history);
				result.addObject("brotherhoods", brotherhoods);
			}
		} catch (final Exception e) {
			final UserAccount user = this.actorS.getActorLogged().getUserAccount();
			final Companie br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
			final History history = this.historyService.findOne(historyId);
			final Collection<Companie> brotherhoods;
			brotherhoods = this.brotherhoodService.findAll();
			brotherhoods.remove(br);
			if (br.equals(history.getBrotherhood())) {
				result = new ModelAndView("linkRecord/edit");
				result.addObject("linkRecord", linkRecord);
				result.addObject("history", history);
				result.addObject("brotherhoods", brotherhoods);
			} else
				result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteLinkRecordHistory(final LinkRecord linkRecord, final BindingResult binding, @RequestParam final Integer historyId) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				final History history = this.historyService.findOne(historyId);
				Assert.notNull(history);
				final UserAccount user = LoginService.getPrincipal();
				final Actor a = this.actorService.getActorByUserAccount(user.getId());
				Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
				Assert.isTrue(history.getBrotherhood() == a);

				//history.getLinkRecords().remove(linkRecord);
				this.linkRecordService.delete(linkRecord, historyId);
				//this.historyService.save(history);
				result = new ModelAndView("redirect:list.do?historyId=" + historyId);
			} else {
				final UserAccount user = this.actorS.getActorLogged().getUserAccount();
				final Companie br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
				final History history = this.historyService.findOne(historyId);
				final Collection<Companie> brotherhoods;
				brotherhoods = this.brotherhoodService.findAll();
				brotherhoods.remove(br);
				Assert.notNull(history);
				result = new ModelAndView("linkRecord/edit");
				result.addObject("linkRecord", linkRecord);
				result.addObject("history", history);
				result.addObject("brotherhoods", brotherhoods);
			}
		} catch (final Exception e) {
			final UserAccount user = this.actorS.getActorLogged().getUserAccount();
			final Companie br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
			final History history = this.historyService.findOne(historyId);
			final Collection<Companie> brotherhoods;
			brotherhoods = this.brotherhoodService.findAll();
			brotherhoods.remove(br);
			if (br.equals(history.getBrotherhood())) {
				result = new ModelAndView("linkRecord/edit");
				result.addObject("linkRecord", linkRecord);
				result.addObject("history", history);
				result.addObject("brotherhoods", brotherhoods);
			} else
				result = new ModelAndView("redirect:../../");
		}
		return result;
	}

}
