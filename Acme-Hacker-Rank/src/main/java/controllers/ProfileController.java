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
import services.HackerService;
import domain.Actor;
import domain.Administrator;
import domain.Company;
import domain.CreditCard;
import domain.Hacker;
import forms.RegistrationForm;
import forms.RegistrationFormCompanyAndCreditCard;
import forms.RegistrationFormHacker;

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
	private HackerService			hackerService;

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

	@RequestMapping(value = "/edit-administrator", method = RequestMethod.GET)
	public ModelAndView editAdmin() {
		ModelAndView result;
		final RegistrationForm registrationForm = new RegistrationForm();
		Administrator admin;
		CreditCard creditCard;
		try {

			admin = this.adminService.findOne(this.adminService.getAdministratorByUserAccount(LoginService.getPrincipal().getId()).getId());
			creditCard = admin.getCreditCard();
			Assert.notNull(admin);
			registrationForm.setId(admin.getId());
			registrationForm.setVersion(admin.getVersion());
			registrationForm.setName(admin.getName());
			registrationForm.setVatNumber(admin.getVatNumber());
			registrationForm.setSurnames(admin.getSurnames());
			registrationForm.setPhoto(admin.getPhoto());
			registrationForm.setEmail(admin.getEmail());
			registrationForm.setPhone(admin.getPhone());
			registrationForm.setCreditCard(admin.getCreditCard());
			registrationForm.setAddress(admin.getAddress());
			registrationForm.setPassword(admin.getUserAccount().getPassword());
			registrationForm.setPatternPhone(false);
			final UserAccount userAccount = new UserAccount();
			userAccount.setUsername(admin.getUserAccount().getUsername());
			userAccount.setPassword(admin.getUserAccount().getPassword());
			registrationForm.setUserAccount(userAccount);
			registrationForm.setBrandName(creditCard.getBrandName());
			registrationForm.setHolderName(creditCard.getHolderName());
			registrationForm.setNumber(creditCard.getNumber());
			registrationForm.setExpirationMonth(creditCard.getExpirationMonth());
			registrationForm.setExpirationYear(creditCard.getExpirationYear());
			registrationForm.setCW(creditCard.getCW());

			result = new ModelAndView("profile/editAdmin");
			result.addObject("actor", registrationForm);
			result.addObject("action", "profile/edit-administrator.do");

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;

	}

	@RequestMapping(value = "/edit-administrator", method = RequestMethod.POST, params = "save")
	public ModelAndView editAdmin(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;

		try {
			final CreditCard creditCard = this.creditCardService.reconstruct(registrationForm, binding);
			registrationForm.setCreditCard(creditCard);
			final Administrator admin = this.adminService.reconstruct(registrationForm, binding);
			if (!binding.hasErrors()) {
				final CreditCard creditCardSave = this.creditCardService.save(creditCard);
				admin.setCreditCard(creditCardSave);
				this.adminService.save(admin);

				result = new ModelAndView("redirect:personal-datas.do");
			} else {
				result = new ModelAndView("profile/editAdmin");
				result.addObject("actor", registrationForm);

			}
		} catch (final Exception e) {

			result = new ModelAndView("profile/editAdmin");
			result.addObject("actor", registrationForm);
			result.addObject("exception", e);

		}
		return result;

	}

	@RequestMapping(value = "/edit-hacker", method = RequestMethod.GET)
	public ModelAndView editHacker() {
		ModelAndView result;
		final RegistrationFormHacker registrationForm = new RegistrationFormHacker();
		Hacker hacker;
		CreditCard creditCard;
		try {

			hacker = this.hackerService.findOne(this.hackerService.hackerUserAccount(LoginService.getPrincipal().getId()).getId());
			creditCard = hacker.getCreditCard();
			Assert.notNull(hacker);
			registrationForm.setId(hacker.getId());
			registrationForm.setVersion(hacker.getVersion());
			registrationForm.setName(hacker.getName());
			registrationForm.setVatNumber(hacker.getVatNumber());
			registrationForm.setSurnames(hacker.getSurnames());
			registrationForm.setPhoto(hacker.getPhoto());
			registrationForm.setEmail(hacker.getEmail());
			registrationForm.setPhone(hacker.getPhone());
			registrationForm.setCreditCard(hacker.getCreditCard());
			registrationForm.setAddress(hacker.getAddress());
			registrationForm.setPassword(hacker.getUserAccount().getPassword());
			registrationForm.setCheck(true);
			registrationForm.setPatternPhone(false);
			final UserAccount userAccount = new UserAccount();
			userAccount.setUsername(hacker.getUserAccount().getUsername());
			userAccount.setPassword(hacker.getUserAccount().getPassword());
			registrationForm.setUserAccount(userAccount);
			registrationForm.setBrandName(creditCard.getBrandName());
			registrationForm.setHolderName(creditCard.getHolderName());
			registrationForm.setNumber(creditCard.getNumber());
			registrationForm.setExpirationMonth(creditCard.getExpirationMonth());
			registrationForm.setExpirationYear(creditCard.getExpirationYear());
			registrationForm.setCW(creditCard.getCW());

			result = new ModelAndView("profile/editHacker");
			result.addObject("actor", registrationForm);
			result.addObject("action", "profile/edit-hacker.do");

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;

	}

	@RequestMapping(value = "/edit-hacker", method = RequestMethod.POST, params = "save")
	public ModelAndView editHacker(final RegistrationFormHacker registrationForm, final BindingResult binding) {
		ModelAndView result;

		try {
			final CreditCard creditCard = this.creditCardService.reconstruct(registrationForm, binding);
			registrationForm.setCreditCard(creditCard);
			final Hacker hacker = this.hackerService.reconstruct(registrationForm, binding);
			if (!binding.hasErrors()) {
				final CreditCard creditCardSave = this.creditCardService.save(creditCard);
				hacker.setCreditCard(creditCardSave);
				this.hackerService.save(hacker);

				result = new ModelAndView("redirect:personal-datas.do");
			} else {
				result = new ModelAndView("profile/editHacker");
				result.addObject("actor", registrationForm);

			}
		} catch (final Exception e) {

			result = new ModelAndView("profile/editHacker");
			result.addObject("actor", registrationForm);
			result.addObject("exception", e);

		}
		return result;

	}

}
