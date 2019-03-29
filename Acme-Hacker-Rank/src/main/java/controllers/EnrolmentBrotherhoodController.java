
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
import services.EnrolmentService;
import services.PositionService;
import domain.Actor;
import domain.Enrolment;
import domain.Posicion;

@Controller
@RequestMapping("/enrolment/brotherhood")
public class EnrolmentBrotherhoodController {

	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private PositionService		positionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Enrolment> enrolments;

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		enrolments = this.enrolmentService.enrolmentByBrotherhood(a.getId());
		Assert.notNull(enrolments);

		result = new ModelAndView("enrolment/list");
		result.addObject("enrolments", enrolments);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer idEnrolment) {
		ModelAndView result;

		try {

			final Enrolment enrolment;
			Collection<Posicion> positions;
			String language;

			language = LocaleContextHolder.getLocale().getLanguage();

			enrolment = this.enrolmentService.findOne(idEnrolment);
			positions = this.positionService.findAll();

			Assert.notNull(enrolment);

			result = new ModelAndView("enrolment/edit");
			result.addObject("enrolment", enrolment);
			result.addObject("positions", positions);
			result.addObject("language", language);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final Enrolment enrolmentBrotherhood, final BindingResult binding) {
		ModelAndView result;

		Enrolment enrolment;
		enrolment = this.enrolmentService.reconstruct(enrolmentBrotherhood, binding);

		try {
			if (!binding.hasErrors()) {
				this.enrolmentService.save(enrolment);
				result = new ModelAndView("redirect:list.do");
			} else {

				Collection<Posicion> positions;
				String language;

				language = LocaleContextHolder.getLocale().getLanguage();

				positions = this.positionService.findAll();
				result = new ModelAndView("enrolment/edit");
				result.addObject("enrolment", enrolment);
				result.addObject("positions", positions);
				result.addObject("language", language);

			}

		} catch (final Exception e) {
			Collection<Posicion> positions;
			String language;

			language = LocaleContextHolder.getLocale().getLanguage();

			positions = this.positionService.findAll();
			result = new ModelAndView("enrolment/edit");
			result.addObject("enrolment", enrolment);
			result.addObject("exception", e);
			result.addObject("positions", positions);
			result.addObject("language", language);

		}

		return result;
	}

	@RequestMapping(value = "/listAccepted", method = RequestMethod.GET)
	public ModelAndView list2(@RequestParam final Integer idMember) {
		final ModelAndView result;
		final Collection<Enrolment> enrolments;

		enrolments = this.enrolmentService.enrolmentAcceptedByBrotherhood(idMember);
		Assert.notNull(enrolments);

		result = new ModelAndView("enrolment/list");
		result.addObject("enrolments", enrolments);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idEnrolment) {
		ModelAndView result;
		try {
			final Enrolment e = this.enrolmentService.findOne(idEnrolment);
			final Collection<Enrolment> enrolments = this.enrolmentService.enrolmentAcceptedByBrotherhood(e.getMember().getId());

			Assert.isTrue(enrolments.contains(e));
			this.enrolmentService.cancelEnrolment(e);
			result = new ModelAndView("redirect:listAccepted.do?idMember=" + e.getMember().getId());
			return result;
		} catch (final Exception e2) {
			result = new ModelAndView("redirect:list.do");

		}
		return result;
	}

}
