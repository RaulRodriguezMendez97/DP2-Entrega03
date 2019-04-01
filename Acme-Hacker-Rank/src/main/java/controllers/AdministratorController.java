/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.PositionService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private PositionService		positionService;

	@Autowired
	private ApplicationService	applicationService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		final ModelAndView result;

		final List<Object[]> getAvgMinMaxDesvPositionByCompany = this.positionService.getAvgMinMaxDesvPositionByCompany();
		final Double getAvgPositionByCompany = (Double) getAvgMinMaxDesvPositionByCompany.get(0)[0];
		final Double getMinPositionByCompany = (Double) getAvgMinMaxDesvPositionByCompany.get(0)[1];
		final Double getMaxPositionByCompany = (Double) getAvgMinMaxDesvPositionByCompany.get(0)[2];
		final Double getDesvPositionByCompany = (Double) getAvgMinMaxDesvPositionByCompany.get(0)[3];

		final List<Object[]> getAvgMinMaxDesvAppByHackers = this.applicationService.getAvgMinMaxDesvAppByHackers();
		final Double getAvgAppByHackers = (Double) getAvgMinMaxDesvAppByHackers.get(0)[0];
		final Double getMinAppByHackers = (Double) getAvgMinMaxDesvAppByHackers.get(0)[1];
		final Double getMaxAppByHackers = (Double) getAvgMinMaxDesvAppByHackers.get(0)[2];
		final Double getDesvAppByHackers = (Double) getAvgMinMaxDesvAppByHackers.get(0)[3];

		result = new ModelAndView("administrator/dashboard");

		result.addObject("getAvgPositionByCompany", getAvgPositionByCompany);
		result.addObject("getMinPositionByCompany", getMinPositionByCompany);
		result.addObject("getMaxPositionByCompany", getMaxPositionByCompany);
		result.addObject("getDesvPositionByCompany", getDesvPositionByCompany);

		result.addObject("getAvgAppByHackers", getAvgAppByHackers);
		result.addObject("getMinAppByHackers", getMinAppByHackers);
		result.addObject("getMaxAppByHackers", getMaxAppByHackers);
		result.addObject("getDesvAppByHackers", getDesvAppByHackers);

		return result;
	}

}