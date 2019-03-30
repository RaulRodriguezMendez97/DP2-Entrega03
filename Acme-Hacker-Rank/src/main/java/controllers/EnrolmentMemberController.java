
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
import services.BrotherhoodService;
import services.EnrolmentService;
import services.PositionService;
import domain.Actor;
import domain.Company;
import domain.Enrolment;
import domain.Posicion;

@Controller
@RequestMapping("/enrolment/member")
public class EnrolmentMemberController {

	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Enrolment> enrolments;

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		enrolments = this.enrolmentService.enrolmentByMember(a.getId());
		Assert.notNull(enrolments);

		result = new ModelAndView("enrolment/list");
		result.addObject("enrolments", enrolments);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Collection<Posicion> positions;
		Collection<Company> brotherhoods;
		final Enrolment enrolment;
		final String language;

		language = LocaleContextHolder.getLocale().getLanguage();
		enrolment = this.enrolmentService.create();
		positions = this.positionService.findAll();
		brotherhoods = this.brotherhoodService.findAll();
		Assert.notNull(enrolment);

		result = new ModelAndView("enrolment/edit");
		result.addObject("enrolment", enrolment);
		result.addObject("positions", positions);
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("language", language);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer idEnrolment) {
		ModelAndView result;

		try {

			final Enrolment enrolment;

			enrolment = this.enrolmentService.findOne(idEnrolment);
			Assert.notNull(enrolment);

			result = new ModelAndView("enrolment/edit");
			result.addObject("enrolment", enrolment);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final Enrolment enrolmentMember, final BindingResult binding) {
		ModelAndView result;
		Enrolment enrolment;

		enrolment = this.enrolmentService.reconstruct(enrolmentMember, binding);

		try {
			if (!binding.hasErrors()) {
				this.enrolmentService.save(enrolment);
				result = new ModelAndView("redirect:list.do");
			} else {
				final String language = LocaleContextHolder.getLocale().getLanguage();

				final Collection<Posicion> positions = this.positionService.findAll();

				final Collection<Company> brotherhoods = this.brotherhoodService.findAll();
				result = new ModelAndView("enrolment/edit");
				result.addObject("enrolment", enrolment);
				result.addObject("positions", positions);
				result.addObject("brotherhoods", brotherhoods);
				result.addObject("language", language);

			}
		} catch (final Exception e) {
			final String language = LocaleContextHolder.getLocale().getLanguage();

			final Collection<Posicion> positions = this.positionService.findAll();
			final Collection<Company> brotherhoods = this.brotherhoodService.findAll();
			result = new ModelAndView("enrolment/edit");
			result.addObject("enrolment", enrolment);
			result.addObject("positions", positions);
			result.addObject("brotherhoods", brotherhoods);
			result.addObject("exception", e);
			result.addObject("language", language);

		}

		return result;
	}

}
