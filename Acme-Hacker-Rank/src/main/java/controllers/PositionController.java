
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PositionService;
import domain.Position;

@Controller
@RequestMapping("/position")
public class PositionController extends AbstractController {

	@Autowired
	private PositionService	positionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int companyId) {
		final ModelAndView result;
		final Collection<Position> positions;

		positions = this.positionService.getPositionsByCompanyOutDraftMode(companyId);
		Assert.notNull(positions);

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		return result;

	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int positionId) {
		ModelAndView result;
		try {
			final Position position;

			position = this.positionService.findOne(positionId);
			Assert.notNull(position);
			Assert.isTrue(position.getDraftMode() == 0);

			result = new ModelAndView("position/show");
			result.addObject("position", position);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../");
		}
		return result;

	}

	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Position> positions;

		positions = this.positionService.getPositionsOutDraftMode();
		Assert.notNull(positions);

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		return result;

	}
}
