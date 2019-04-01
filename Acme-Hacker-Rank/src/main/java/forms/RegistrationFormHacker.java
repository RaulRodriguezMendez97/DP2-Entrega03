/*
 * DomainEntity.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package forms;

import java.util.HashSet;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.CredictCard;
import domain.Finder;

public class RegistrationFormHacker extends Actor {

	public RegistrationFormHacker() {
		super();
	}


	private String	password;
	private Boolean	check;
	private Boolean	patternPhone;
	private Finder	finder;


	@Valid
	@NotNull
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	public Boolean getPatternPhone() {
		return this.patternPhone;
	}

	public void setPatternPhone(final Boolean patternPhone) {
		this.patternPhone = patternPhone;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public Boolean getCheck() {
		return this.check;
	}

	public void setCheck(final Boolean check) {
		this.check = check;
	}

	public RegistrationFormHacker createToHacker() {

		final RegistrationFormHacker registrationForm = new RegistrationFormHacker();
		registrationForm.setFinder(new Finder());
		registrationForm.setCheck(false);
		registrationForm.setPatternPhone(false);
		registrationForm.setAddress("");
		registrationForm.setEmail("");
		registrationForm.setName("");
		registrationForm.setVatNumber("");
		registrationForm.setPhoto("");
		registrationForm.setSurnames(new HashSet<String>());
		registrationForm.setAddress("");
		registrationForm.setCredictCard(new CredictCard());
		registrationForm.setPassword("");

		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.HACKER);
		user.getAuthorities().add(ad);

		//NUEVO
		user.setUsername("");
		user.setPassword("");

		registrationForm.setUserAccount(user);

		return registrationForm;
	}

}
