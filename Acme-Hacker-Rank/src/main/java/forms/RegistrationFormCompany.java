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

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.CreditCard;

public class RegistrationFormCompany extends Actor {

	// Constructors -----------------------------------------------------------

	public RegistrationFormCompany() {
		super();
	}


	// Properties -------------------------------------------------------------

	private String	password;

	private Boolean	check;

	private Boolean	patternPhone;

	private String	nameCompany;


	@NotBlank
	public String getNameCompany() {
		return this.nameCompany;
	}

	public void setNameCompany(final String nameCompany) {
		this.nameCompany = nameCompany;
	}

	public Boolean getPatternPhone() {
		return this.patternPhone;
	}

	public void setPatternPhone(final Boolean patternPhone) {
		this.patternPhone = patternPhone;
	}

	public Boolean getCheck() {
		return this.check;
	}

	public void setCheck(final Boolean check) {
		this.check = check;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	// Business methods -------------------------------------------------------

	public RegistrationFormCompany createToCompany() {

		final RegistrationFormCompany registrationForm = new RegistrationFormCompany();
		registrationForm.setNameCompany("");
		registrationForm.setCheck(false);
		registrationForm.setPatternPhone(false);
		registrationForm.setAddress("");
		registrationForm.setEmail("");
		registrationForm.setName("");
		registrationForm.setVatNumber("");
		registrationForm.setPhoto("");
		registrationForm.setSurnames(new HashSet<String>());
		registrationForm.setAddress("");
		registrationForm.setCredictCard(new CreditCard());
		registrationForm.setPassword("");

		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.COMPANY);
		user.getAuthorities().add(ad);

		//NUEVO
		user.setUsername("");
		user.setPassword("");

		registrationForm.setUserAccount(user);

		return registrationForm;
	}

}
