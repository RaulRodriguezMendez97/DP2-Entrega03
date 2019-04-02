
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}
