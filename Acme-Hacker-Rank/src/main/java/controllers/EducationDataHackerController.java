
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.EducationDataService;
import domain.Curricula;
import domain.EducationData;

@Controller
@RequestMapping("/educationData/hacker")
public class EducationDataHackerController extends AbstractController {

	@Autowired
	private CurriculaService		curriculaService;
	@Autowired
	private EducationDataService	educationDataService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int curriculaId) {
		ModelAndView result;
		try {
			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			final Collection<EducationData> educationData = curricula.getEducationData();
			result = new ModelAndView("educationData/list");
			result.addObject("educationsData", educationData);
			result.addObject("curricula", curricula);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../curricula/hacker/list.do");
		}
		return result;
	}
}