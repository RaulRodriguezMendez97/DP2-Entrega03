
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.CustomizableSystemService;
import domain.Companie;
import forms.RegistrationFormBrotherhood;

@Controller
@RequestMapping("/brotherhood")
public class BrotherhoodController extends AbstractController {

	@Autowired
	private BrotherhoodService			brotherhoodService;

	@Autowired
	private CustomizableSystemService	customizableService;


	//	@RequestMapping(value = "/create", method = RequestMethod.GET)
	//	public ModelAndView create() {
	//		ModelAndView result;
	//		final Brotherhood brotherhood;
	//		//final Collection<Picture> pictures = this.pictureService.finaAll();
	//		brotherhood = this.brotherhoodService.create();
	//		result = new ModelAndView("brotherhood/create");
	//		result.addObject("brotherhood", brotherhood);
	//		//result.addObject("pictures", pictures);
	//		return result;
	//	}
	//
	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView edit(@Valid final Brotherhood brotherhood, final BindingResult binding) {
	//		ModelAndView result;
	//		try {
	//			if (!binding.hasErrors()) {
	//				this.brotherhoodService.save(brotherhood);
	//				result = new ModelAndView("redirect:https://localhost:8443/Acme-Madruga");
	//			} else {
	//				result = new ModelAndView("brotherhood/create");
	//				result.addObject("brotherhood", brotherhood);
	//			}
	//		} catch (final Exception e) {
	//			result = new ModelAndView("brotherhood/create");
	//			result.addObject("exception", e);
	//			brotherhood.getUserAccount().setPassword("");
	//			result.addObject("brotherhood", brotherhood);
	//		}
	//
	//		return result;
	//	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView result;
		RegistrationFormBrotherhood registrationForm = new RegistrationFormBrotherhood();

		registrationForm = registrationForm.createToBrotherhood();
		final String telephoneCode = this.customizableService.getTelephoneCode();
		registrationForm.setPhone(telephoneCode + " ");

		result = new ModelAndView("brotherhood/create");
		result.addObject("registrationForm", registrationForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("registrationForm") final RegistrationFormBrotherhood registrationForm, final BindingResult binding) {
		ModelAndView result;
		Companie brotherhood = null;

		try {

			brotherhood = this.brotherhoodService.reconstruct(registrationForm, binding);
			if (!binding.hasErrors() && registrationForm.getUserAccount().getPassword().equals(registrationForm.getPassword())) {
				this.brotherhoodService.save(brotherhood);
				result = new ModelAndView("redirect:/");
			} else {

				result = new ModelAndView("brotherhood/create");
				result.addObject("registrationForm", registrationForm);
			}
		} catch (final Exception e) {
			result = new ModelAndView("brotherhood/create");
			result.addObject("exception", e);
			result.addObject("registrationForm", registrationForm);

		}

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Companie> brotherhoods;

		brotherhoods = this.brotherhoodService.findAll();
		Assert.notNull(brotherhoods);

		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhoods", brotherhoods);

		return result;

	}

}
