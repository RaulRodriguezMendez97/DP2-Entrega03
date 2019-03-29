
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PositionService;
import domain.Posicion;

@Controller
@RequestMapping("/position/administrator")
public class PosicionAdministratorController extends AbstractController {

	@Autowired
	private PositionService	posicionService;


	public PosicionAdministratorController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Posicion> positions;

		positions = this.posicionService.findAll();
		Assert.notNull(positions);

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Posicion position;

		position = this.posicionService.create();
		Assert.notNull(position);

		result = new ModelAndView("position/edit");
		result.addObject("position", position);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer positionId) {
		ModelAndView result;
		try {
			final Posicion position;

			position = this.posicionService.findOne(positionId);
			Assert.notNull(position);

			result = new ModelAndView("position/edit");
			result.addObject("position", position);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("position") @Valid final Posicion position, final BindingResult binding) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				this.posicionService.save(position);
				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("position/edit");
				result.addObject("position", position);
			}
		} catch (final Exception e) {
			result = new ModelAndView("position/edit");
			result.addObject("exception", e);
			result.addObject("position", position);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Posicion position, final BindingResult binding) {
		ModelAndView result;

		try {
			this.posicionService.delete(position);
			result = new ModelAndView("redirect:list.do");

		} catch (final Exception e) {
			result = new ModelAndView("position/edit");
			result.addObject("position", position);
			result.addObject("exception", e);

		}
		return result;
	}

}
