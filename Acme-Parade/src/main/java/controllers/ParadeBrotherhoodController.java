
package controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BrotherhoodService;
import services.ParadeService;
import domain.Parade;

@Controller
@RequestMapping("/parade/brotherhood")
public class ParadeBrotherhoodController extends AbstractController {

	@Autowired
	private ParadeService		paradeService;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Parade> allMyParades = this.paradeService.getAllProcessionsByBrotherhood(this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()).getId());
		result = new ModelAndView("parade/list");
		result.addObject("processions", allMyParades);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Parade parade;
		parade = this.paradeService.create();

		result = new ModelAndView("parade/edit");
		result.addObject("procession", parade);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int paradeId) {
		ModelAndView result;
		Parade parade;
		try {
			parade = this.paradeService.findOne(paradeId);
			Assert.notNull(parade);
			Assert.isTrue(this.paradeService.findOne(paradeId).getDraftMode() == 1);
			final Collection<Parade> allMyProcession = this.paradeService.getAllProcessionsByBrotherhood(this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()).getId());
			Assert.isTrue(allMyProcession.contains(parade));
			result = new ModelAndView("parade/edit");
			result.addObject("procession", parade);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int paradeId) {
		ModelAndView result;
		Parade parade;
		try {
			parade = this.paradeService.findOne(paradeId);
			Assert.notNull(parade);
			final Collection<Parade> allMyParades = this.paradeService.getAllProcessionsByBrotherhood(this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()).getId());
			Assert.isTrue(allMyParades.contains(parade));
			result = new ModelAndView("parade/show");
			result.addObject("procession", parade);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(Parade newParade, final BindingResult binding) {
		ModelAndView result;
		try {
			newParade = this.paradeService.reconstruct(newParade, binding);
			Assert.isTrue(newParade.getMoment().after(new Date()));
			if (!binding.hasErrors()) {
				this.paradeService.save(newParade);
				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("parade/edit");
				result.addObject("procession", newParade);
			}
		} catch (final Exception e) {
			result = new ModelAndView("parade/edit");
			result.addObject("procession", newParade);
			result.addObject("fechaPasada", "fechaPasada");
		}

		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int paradeId) {
		ModelAndView result;
		try {
			final Parade p = this.paradeService.findOne(paradeId);
			final Collection<Parade> allMyParades = this.paradeService.getAllProcessionsByBrotherhood(this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()).getId());
			Assert.isTrue(allMyParades.contains(p));
			this.paradeService.delete(p);
			result = new ModelAndView("redirect:list.do");
			return result;
		} catch (final Exception e) {
			result = new ModelAndView("parade/list");
			result.addObject("exception", e);
			final Collection<Parade> allMyParades = this.paradeService.getAllProcessionsByBrotherhood(this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()).getId());
			result.addObject("processions", allMyParades);
			return result;
		}
	}

}
