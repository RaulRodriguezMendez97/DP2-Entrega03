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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccount;
import services.AdministratorService;
import services.BrotherhoodService;
import services.CustomizableSystemService;
import services.HistoryService;
import services.MemberService;
import services.ParadeService;
import services.PositionService;
import services.RequestService;
import domain.Administrator;
import forms.RegistrationForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private PositionService				positionService;

	@Autowired
	private ParadeService				processionService;

	@Autowired
	private BrotherhoodService			brotherhoodService;

	@Autowired
	private AdministratorService		administratorService;

	@Autowired
	private RequestService				requestService;

	@Autowired
	private MemberService				memberService;

	@Autowired
	private CustomizableSystemService	customizableService;

	@Autowired
	private HistoryService				historyService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView result;

		final Collection<String> procession = this.processionService.procession();

		final List<Object[]> memberBrotherhood = this.brotherhoodService.getMaxMinAvgDesvMembersBrotherhood();
		final Double memberBrotherhoodAvg = (Double) memberBrotherhood.get(0)[0];
		final Double memberBrotherhoodMin = (Double) memberBrotherhood.get(0)[1];
		final Double memberBrotherhoodMax = (Double) memberBrotherhood.get(0)[2];
		final Double memberBrotherhoodDesv = (Double) memberBrotherhood.get(0)[3];

		final Collection<String> largestBrotherhoods = this.brotherhoodService.getLargestBrotherhoods();
		final Collection<String> smallestBrotherhoods = this.brotherhoodService.getSmallestBrotherhoods();

		final Collection<String> members10Percentage = this.memberService.members10Percentage();

		final List<Object[]> recordByHistory = this.historyService.AvgMinMaxDesv();
		final Double recordByHistoryAvg = (Double) recordByHistory.get(0)[0];
		final Double recordByHistoryMin = (Double) recordByHistory.get(0)[1];
		final Double recordByHistoryMax = (Double) recordByHistory.get(0)[2];
		final Double recordByHistoryDesv = (Double) recordByHistory.get(0)[3];

		final Collection<String> getLargestHistoryBrotherhoods = this.brotherhoodService.getLargestHistoryBrotherhoods();

		final Collection<String> getMoreHistoryBrotherhoodsThanAverage = this.brotherhoodService.getMoreHistoryBrotherhoodsThanAverage();

		result = new ModelAndView("administrator/dashboard");
		result.addObject("procession", procession);

		result.addObject("memberBrotherhoodAvg", memberBrotherhoodAvg);
		result.addObject("memberBrotherhoodMin", memberBrotherhoodMin);
		result.addObject("memberBrotherhoodMax", memberBrotherhoodMax);
		result.addObject("memberBrotherhoodDesv", memberBrotherhoodDesv);

		result.addObject("largestBrotherhoods", largestBrotherhoods);
		result.addObject("smallestBrotherhoods", smallestBrotherhoods);

		result.addObject("ratioPendingRequest", this.requestService.pendingRatio());
		result.addObject("ratioAcceptedRequest", this.requestService.acceptedRatio());
		result.addObject("ratioRejectedRequest", this.requestService.rejectedRatio());

		result.addObject("members10Percentage", members10Percentage);

		result.addObject("recordByHistoryAvg", recordByHistoryAvg);
		result.addObject("recordByHistoryMin", recordByHistoryMin);
		result.addObject("recordByHistoryMax", recordByHistoryMax);
		result.addObject("recordByHistoryDesv", recordByHistoryDesv);

		result.addObject("getLargestHistoryBrotherhoods", getLargestHistoryBrotherhoods);

		result.addObject("getMoreHistoryBrotherhoodsThanAverage", getMoreHistoryBrotherhoodsThanAverage);

		return result;
	}

	@RequestMapping("/graph")
	public ModelAndView graph() {

		ModelAndView result;
		Map<String, Double> statistics;

		statistics = this.positionService.computeStatistics();

		result = new ModelAndView("administrator/bar-chart");
		result.addObject("statistics", statistics);

		return result;

	}

	//	@RequestMapping(value = "/create", method = RequestMethod.GET)
	//	public ModelAndView action1() {
	//		ModelAndView result;
	//		final Administrator administrator;
	//
	//		administrator = this.administratorService.create();
	//
	//		result = new ModelAndView("administrator/create");
	//		result.addObject("administrator", administrator);
	//
	//		return result;
	//	}
	//
	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView edit(@Valid final Administrator administrator, final BindingResult binding) {
	//		ModelAndView result;
	//		try {
	//			if (!binding.hasErrors()) {
	//				this.administratorService.save(administrator);
	//				result = new ModelAndView("redirect:https://localhost:8443/Acme-Madruga");
	//			} else {
	//				result = new ModelAndView("administrator/create");
	//				result.addObject("administrator", administrator);
	//			}
	//		} catch (final Exception e) {
	//			result = new ModelAndView("administrator/create");
	//			result.addObject("exception", e);
	//			result.addObject("administrator", administrator);
	//		}
	//
	//		return result;
	//	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView result;
		final RegistrationForm registrationForm = new RegistrationForm();

		registrationForm.setName("");
		registrationForm.setMiddleName("");
		registrationForm.setSurname("");
		registrationForm.setPhoto("");
		registrationForm.setEmail("");
		final String telephoneCode = this.customizableService.getTelephoneCode();
		registrationForm.setPhone(telephoneCode + " ");
		registrationForm.setPatternPhone(false);
		registrationForm.setAddress("");
		registrationForm.setPassword("");

		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.ADMIN);
		user.getAuthorities().add(ad);

		//NUEVO
		user.setUsername("");
		user.setPassword("");

		registrationForm.setUserAccount(user);

		result = new ModelAndView("administrator/create");
		result.addObject("registrationForm", registrationForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;
		Administrator administrator = null;

		try {

			administrator = this.administratorService.reconstruct(registrationForm, binding);
			if (!binding.hasErrors() && registrationForm.getUserAccount().getPassword().equals(registrationForm.getPassword())) {
				this.administratorService.save(administrator);
				result = new ModelAndView("redirect:/");
			} else {

				result = new ModelAndView("administrator/create");
				result.addObject("registrationForm", registrationForm);
				Assert.isTrue(registrationForm.getPassword().equals(registrationForm.getUserAccount().getPassword()));
			}
		} catch (final Exception e) {
			result = new ModelAndView("administrator/create");
			result.addObject("exception", e);
			result.addObject("registrationForm", registrationForm);

		}

		return result;
	}

}
