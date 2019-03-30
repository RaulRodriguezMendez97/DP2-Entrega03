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

import java.util.Collection;

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
import services.BrotherhoodService;
import services.MemberService;
import services.PictureService;
import domain.Actor;
import domain.Administrator;
import domain.Company;
import domain.Hacker;
import domain.Picture;
import forms.MemberRegistrationForm;
import forms.RegistrationForm;
import forms.RegistrationFormBrotherhood;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private PictureService			pictureService;

	@Autowired
	private MemberService			memberService;


	// Action-2 ---------------------------------------------------------------		

	//VER SUS DATOS PERSONALES
	@RequestMapping(value = "/personal-datas", method = RequestMethod.GET)
	public ModelAndView action2() {
		ModelAndView result;
		Actor a;

		final UserAccount user = LoginService.getPrincipal();
		a = this.actorService.getActorByUserAccount(user.getId());

		result = new ModelAndView("profile/action-1");
		result.addObject("actor", a);

		if (user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD")) {
			final Company b = this.brotherhoodService.brotherhoodUserAccount(user.getId());
			final Collection<Picture> pictures = b.getPictures();
			result.addObject("pictures", pictures);
		}

		return result;
	}

	//---------- ADMIN

	@RequestMapping(value = "/edit-administrator", method = RequestMethod.GET)
	public ModelAndView editAdministrator() {
		ModelAndView result;
		final RegistrationForm registrationForm = new RegistrationForm();
		Administrator admin;

		try {

			admin = this.adminService.findOne(this.adminService.getAdministratorByUserAccount(LoginService.getPrincipal().getId()).getId());
			Assert.notNull(admin);
			registrationForm.setId(admin.getId());
			registrationForm.setVersion(admin.getVersion());
			registrationForm.setName(admin.getName());
			registrationForm.setMiddleName(admin.getMiddleName());
			registrationForm.setSurname(admin.getSurname());
			registrationForm.setPhoto(admin.getPhoto());
			registrationForm.setEmail(admin.getEmail());
			registrationForm.setPhone(admin.getPhone());
			registrationForm.setPatternPhone(false);
			registrationForm.setAddress(admin.getAddress());
			registrationForm.setPassword(admin.getUserAccount().getPassword());
			final UserAccount userAccount = new UserAccount();
			userAccount.setPassword(admin.getUserAccount().getPassword());
			admin.setUserAccount(userAccount);

			result = new ModelAndView("profile/editAdmin");
			result.addObject("actor", registrationForm);
			result.addObject("action", "profile/edit-administrator.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;

	}

	@RequestMapping(value = "/edit-administrator", method = RequestMethod.POST, params = "save")
	public ModelAndView editAdministrator(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;

		try {
			final Administrator admin = this.adminService.reconstruct(registrationForm, binding);
			if (!binding.hasErrors()) {
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

	@RequestMapping(value = "/edit-brotherhood", method = RequestMethod.GET)
	public ModelAndView editBrotherhood() {
		ModelAndView result;
		final RegistrationFormBrotherhood registrationForm = new RegistrationFormBrotherhood();
		Company brotherhood;

		try {

			brotherhood = this.brotherhoodService.findOne(this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()).getId());
			Assert.notNull(brotherhood);
			registrationForm.setId(brotherhood.getId());
			registrationForm.setVersion(brotherhood.getVersion());
			registrationForm.setName(brotherhood.getName());
			registrationForm.setMiddleName(brotherhood.getMiddleName());
			registrationForm.setSurname(brotherhood.getSurname());
			registrationForm.setPhoto(brotherhood.getPhoto());
			registrationForm.setEmail(brotherhood.getEmail());
			registrationForm.setPhone(brotherhood.getPhone());
			registrationForm.setAddress(brotherhood.getAddress());
			registrationForm.setPassword(brotherhood.getUserAccount().getPassword());
			registrationForm.setCheck(true);
			registrationForm.setPatternPhone(false);
			registrationForm.setEstablishmentDate(brotherhood.getEstablishmentDate());
			registrationForm.setTitle(brotherhood.getTitle());
			final UserAccount userAccount = new UserAccount();
			userAccount.setPassword(brotherhood.getUserAccount().getPassword());
			brotherhood.setUserAccount(userAccount);

			result = new ModelAndView("profile/editBrotherhood");
			result.addObject("actor", registrationForm);
			result.addObject("action", "profile/edit-brotherhood.do");

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;

	}

	@RequestMapping(value = "/edit-brotherhood", method = RequestMethod.POST, params = "save")
	public ModelAndView editBrotherhood(final RegistrationFormBrotherhood registrationForm, final BindingResult binding) {
		ModelAndView result;

		try {
			final Company brother = this.brotherhoodService.reconstruct(registrationForm, binding);
			if (!binding.hasErrors()) {
				this.brotherhoodService.save(brother);

				result = new ModelAndView("redirect:personal-datas.do");
			} else {
				result = new ModelAndView("profile/editBrotherhood");
				result.addObject("actor", registrationForm);

			}
		} catch (final Exception e) {

			result = new ModelAndView("profile/editBrotherhood");
			result.addObject("actor", registrationForm);
			result.addObject("exception", e);

		}
		return result;

	}

	@RequestMapping(value = "/edit-member", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Hacker member;
		final MemberRegistrationForm formulario = new MemberRegistrationForm();
		try {
			member = this.memberService.findOne(this.memberService.getMemberByUserAccount(LoginService.getPrincipal().getId()).getId());
			Assert.notNull(member);
			formulario.setId(member.getId());
			formulario.setVersion(member.getVersion());
			formulario.setAddress(member.getAddress());
			formulario.setEmail(member.getEmail());
			formulario.setMiddleName(member.getMiddleName());
			formulario.setName(member.getName());
			formulario.setPhoto(member.getPhoto());
			formulario.setPhone(member.getPhone());
			formulario.setCheck(true);
			formulario.setPatternPhone(false);
			formulario.setSurname(member.getSurname());
			formulario.setPassword2(member.getUserAccount().getPassword());
			final UserAccount userAccount = new UserAccount();
			userAccount.setPassword(member.getUserAccount().getPassword());
			member.setUserAccount(userAccount);

			result = new ModelAndView("profile/editMember");
			result.addObject("actor", formulario);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;
	}

	@RequestMapping(value = "/edit-member", method = RequestMethod.POST, params = "save")
	public ModelAndView editMember(final MemberRegistrationForm memberRegistrationForm, final BindingResult binding) {
		ModelAndView result;
		Hacker member;
		try {
			member = this.memberService.reconstruct(memberRegistrationForm, binding);
			if (!binding.hasErrors()) {
				this.memberService.save(member);
				result = new ModelAndView("redirect:personal-datas.do");
			} else {
				result = new ModelAndView("profile/editMember");
				result.addObject("actor", memberRegistrationForm);
			}
		} catch (final Exception e) {
			result = new ModelAndView("profile/editMember");
			result.addObject("actor", memberRegistrationForm);
			result.addObject("exception", e);

		}
		return result;

	}
}
