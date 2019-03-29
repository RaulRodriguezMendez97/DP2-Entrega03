
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import services.ParadeService;
import domain.Parade;

@Controller
@RequestMapping("/parade/member")
public class ParadeMemberController extends AbstractController {

	@Autowired
	private ParadeService	processionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<String> list(@RequestParam final int brotherhoodId) {
		try {
			final Collection<Parade> p = this.processionService.getAllProcessionsByBrotherhoodFinalMode(brotherhoodId);
			final List<Parade> a = new ArrayList<Parade>(p);
			String processions = "";
			for (int x = 0; x < a.size(); x++)
				if (x == 0)
					processions += a.get(x).getTitle() + ":" + a.get(x).getId();
				else
					processions += ";" + a.get(x).getTitle() + ":" + a.get(x).getId();
			return new ResponseEntity<String>(processions, HttpStatus.OK);
		} catch (final Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
