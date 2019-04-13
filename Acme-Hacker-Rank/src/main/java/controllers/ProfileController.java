/*
 * ProfileController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.AdministratorService;
import services.CompanyService;
import services.CreditCardService;
import domain.Actor;
import domain.Company;
import domain.CreditCard;
import forms.RegistrationFormCompanyAndCreditCard;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private CreditCardService		creditCardService;


	// Action-2 ---------------------------------------------------------------		

	//VER SUS DATOS PERSONALES
	@RequestMapping(value = "/personal-datas", method = RequestMethod.GET)
	public ModelAndView action2() {
		ModelAndView result;
		Actor a;
		CreditCard creditCard;

		final UserAccount user = LoginService.getPrincipal();
		a = this.actorService.getActorByUserAccount(user.getId());
		creditCard = a.getCreditCard();

		result = new ModelAndView("profile/action-1");
		result.addObject("actor", a);
		result.addObject("creditCard", creditCard);

		return result;
	}

	@RequestMapping(value = "/edit-company", method = RequestMethod.GET)
	public ModelAndView editCompany() {
		ModelAndView result;
		final RegistrationFormCompanyAndCreditCard registrationForm = new RegistrationFormCompanyAndCreditCard();
		Company company;
		CreditCard creditCard;
		try {

			company = this.companyService.findOne(this.companyService.companyUserAccount(LoginService.getPrincipal().getId()).getId());
			creditCard = company.getCreditCard();
			Assert.notNull(company);
			registrationForm.setId(company.getId());
			registrationForm.setVersion(company.getVersion());
			registrationForm.setName(company.getName());
			registrationForm.setVatNumber(company.getVatNumber());
			registrationForm.setSurnames(company.getSurnames());
			registrationForm.setPhoto(company.getPhoto());
			registrationForm.setEmail(company.getEmail());
			registrationForm.setPhone(company.getPhone());
			registrationForm.setCreditCard(company.getCreditCard());
			registrationForm.setAddress(company.getAddress());
			registrationForm.setPassword(company.getUserAccount().getPassword());
			registrationForm.setCheck(true);
			registrationForm.setPatternPhone(false);
			registrationForm.setNameCompany(company.getNameCompany());
			final UserAccount userAccount = new UserAccount();
			userAccount.setUsername(company.getUserAccount().getUsername());
			userAccount.setPassword(company.getUserAccount().getPassword());
			registrationForm.setUserAccount(userAccount);
			registrationForm.setBrandName(creditCard.getBrandName());
			registrationForm.setHolderName(creditCard.getHolderName());
			registrationForm.setNumber(creditCard.getNumber());
			registrationForm.setExpirationMonth(creditCard.getExpirationMonth());
			registrationForm.setExpirationYear(creditCard.getExpirationYear());
			registrationForm.setCW(creditCard.getCW());

			result = new ModelAndView("profile/editCompany");
			result.addObject("actor", registrationForm);
			result.addObject("action", "profile/edit-company.do");

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;

	}

	@RequestMapping(value = "/edit-company", method = RequestMethod.POST, params = "save")
	public ModelAndView editCompany(final RegistrationFormCompanyAndCreditCard registrationForm, final BindingResult binding) {
		ModelAndView result;

		try {
			final CreditCard creditCard = this.creditCardService.reconstruct(registrationForm, binding);
			registrationForm.setCreditCard(creditCard);
			final Company company = this.companyService.reconstruct(registrationForm, binding);
			if (!binding.hasErrors()) {
				final CreditCard creditCardSave = this.creditCardService.save(creditCard);
				company.setCreditCard(creditCardSave);
				this.companyService.save(company);

				result = new ModelAndView("redirect:personal-datas.do");
			} else {
				result = new ModelAndView("profile/editCompany");
				result.addObject("actor", registrationForm);

			}
		} catch (final Exception e) {

			result = new ModelAndView("profile/editCompany");
			result.addObject("actor", registrationForm);
			result.addObject("exception", e);

		}
		return result;

	}

}
