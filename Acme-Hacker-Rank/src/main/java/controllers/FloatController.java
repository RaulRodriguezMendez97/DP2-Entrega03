
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FloatService;
import domain.Paso;

@Controller
@RequestMapping("/float")
public class FloatController extends AbstractController {

	@Autowired
	private FloatService	floatService;


	//Listado de Floats(Pasos)
	@RequestMapping(value = "/list-All", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int idBrotherhood) {
		final ModelAndView result;
		final Collection<Paso> floats;
		floats = this.floatService.getFloatsByBrotherhood(idBrotherhood);

		result = new ModelAndView("float/list-All");
		result.addObject("floats", floats);
		result.addObject("idBrotherhood", idBrotherhood);

		return result;
	}

}
