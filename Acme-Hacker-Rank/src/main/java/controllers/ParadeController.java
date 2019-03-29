
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ParadeService;
import domain.Parade;

@Controller
@RequestMapping("/parade")
public class ParadeController extends AbstractController {

	@Autowired
	private ParadeService	processionService;


	@RequestMapping(value = "/list-All", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int idBrotherhood) {
		final ModelAndView result;
		final Collection<Parade> processions;

		processions = this.processionService.getAllProcessionsByBrotherhoodFinalMode(idBrotherhood);

		result = new ModelAndView("parade/list-All");
		result.addObject("processions", processions);
		result.addObject("idBrotherhood", idBrotherhood);

		return result;

	}
}
