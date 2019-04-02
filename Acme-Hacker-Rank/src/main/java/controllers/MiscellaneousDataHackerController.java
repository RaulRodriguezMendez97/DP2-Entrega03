
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.MiscellaneousDataService;
import domain.Curricula;
import domain.MiscellaneousData;

@Controller
@RequestMapping("/miscellaneousData/hacker")
public class MiscellaneousDataHackerController {

	@Autowired
	private CurriculaService			curriculaService;
	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int curriculaId) {
		ModelAndView result;
		try {
			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			//final PersonalData personalData = this.personalData.findOne(curricula.getPersonalData().getId());
			final MiscellaneousData miscellaneousData = curricula.getMiscellaneousData();
			result = new ModelAndView("miscellaneousData/show");
			result.addObject("miscellaneousData", miscellaneousData);
			//result.addObject("curriculaId", curriculaId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../curricula/hacker/list.do");
		}
		return result;
	}
}
