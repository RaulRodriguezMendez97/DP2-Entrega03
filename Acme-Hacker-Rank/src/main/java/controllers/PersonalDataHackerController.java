
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.PersonalDataService;
import domain.Curricula;
import domain.PersonalData;

@Controller
@RequestMapping("/personalData/hacker")
public class PersonalDataHackerController extends AbstractController {

	@Autowired
	private CurriculaService	curriculaService;
	@Autowired
	private PersonalDataService	personalData;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int curriculaId) {
		ModelAndView result;
		try {
			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			//final PersonalData personalData = this.personalData.findOne(curricula.getPersonalData().getId());
			final PersonalData personalData = curricula.getPersonalData();
			result = new ModelAndView("personalData/show");
			result.addObject("personalData", personalData);
			//result.addObject("curriculaId", curriculaId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../curricula/hacker/list.do");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final PersonalData personalData = this.personalData.create();
		result = new ModelAndView("personalData/edit");
		result.addObject("personalData", personalData);
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalDataId) {
		ModelAndView result;
		try {
			final PersonalData personalData = this.personalData.findOne(personalDataId);
			result = new ModelAndView("personalData/edit");
			result.addObject("personalData", personalData);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../curricula/hacker/list.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PersonalData personalData, final BindingResult binding) {
		ModelAndView result;
		try {
			if (binding.hasErrors()) {
				result = new ModelAndView("personalData/edit");
				result.addObject("personalData", personalData);
			} else {
				this.personalData.save(personalData);
				result = new ModelAndView("redirect:../../curricula/hacker/list.do");
			}
		} catch (final Exception e) {
			result = new ModelAndView("personalData/edit");
			result.addObject("personalData", personalData);
			result.addObject("exception", "e");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int personalDataId) {
		ModelAndView result;
		try {
			final PersonalData personalData = this.personalData.findOne(personalDataId);
			this.personalData.delete(personalData);
			result = new ModelAndView("redirect:../../curricula/hacker/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../curricula/hacker/list.do");
			result.addObject("exception", e);
		}
		return result;
	}

}
