
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.PositionDataService;
import domain.Curricula;
import domain.PositionData;

@Controller
@RequestMapping("/positionData/hacker")
public class PositionDataHackerController extends AbstractController {

	@Autowired
	private CurriculaService	curriculaService;
	@Autowired
	private PositionDataService	positionDataService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int curriculaId) {
		ModelAndView result;
		try {
			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			final Collection<PositionData> positionData = curricula.getPositionData();
			result = new ModelAndView("positionData/list");
			result.addObject("positionsData", positionData);
			result.addObject("curricula", curricula);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../curricula/hacker/list.do");
		}
		return result;
	}
}
