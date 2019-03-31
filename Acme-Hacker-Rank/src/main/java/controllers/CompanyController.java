
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import domain.Company;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

	@Autowired
	private CompanyService	companyService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView company() {
		final ModelAndView result;
		final Collection<Company> companies;

		companies = this.companyService.findAll();
		Assert.notNull(companies);

		result = new ModelAndView("company/list");
		result.addObject("companies", companies);
		return result;

	}

}
