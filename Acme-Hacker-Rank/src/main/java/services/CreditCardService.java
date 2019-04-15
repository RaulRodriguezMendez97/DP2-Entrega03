
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CreditCardRepository;
import domain.Administrator;
import domain.Company;
import domain.CreditCard;
import domain.Hacker;
import forms.RegistrationForm;
import forms.RegistrationFormCompanyAndCreditCard;
import forms.RegistrationFormHacker;

@Service
@Transactional
public class CreditCardService {

	@Autowired
	private CreditCardRepository	creditCardRepository;
	@Autowired
	private CompanyService			companyService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private Validator				validator;


	public Collection<CreditCard> findAll() {
		return this.creditCardRepository.findAll();
	}
	public CreditCard findOne(final Integer creditCardId) {
		return this.creditCardRepository.findOne(creditCardId);
	}
	//Metodo create
	public CreditCard create() {
		final CreditCard cc = new CreditCard();
		cc.setBrandName("");
		cc.setHolderName("");
		cc.setNumber(0);
		cc.setExpirationMonth(0);
		cc.setExpirationYear(0);
		cc.setCW(0);
		return cc;
	}
	public CreditCard save(final CreditCard cc) {
		if (cc.getId() == 0) {
			final Collection<Integer> creditCardsNumbers = this.getAllNumbers();
			Assert.isTrue(!creditCardsNumbers.contains(cc.getNumber()));
		}

		Assert.isTrue(cc != null && cc.getBrandName() != null && cc.getHolderName() != null && cc.getBrandName() != "" && cc.getHolderName() != "");
		return this.creditCardRepository.save(cc);

	}

	public void delete(final CreditCard creditCard) {
		this.creditCardRepository.delete(creditCard);
	}

	public Collection<Integer> getAllNumbers() {
		return this.creditCardRepository.getAllNumbercreditCards();
	}

	public CreditCard getCreditCardByNumber(final Integer number) {
		return this.creditCardRepository.CreditCardByNumber(number);
	}

	public CreditCard reconstruct(final RegistrationFormCompanyAndCreditCard registrationForm, final BindingResult binding) {
		CreditCard res = new CreditCard();

		if (registrationForm.getId() == 0) {
			res.setId(registrationForm.getId());
			res.setVersion(registrationForm.getVersion());
			res.setBrandName(registrationForm.getBrandName());
			res.setHolderName(registrationForm.getHolderName());
			res.setNumber(registrationForm.getNumber());
			res.setExpirationMonth(registrationForm.getExpirationMonth());
			res.setExpirationYear(registrationForm.getExpirationYear());
			res.setCW(registrationForm.getCW());

			this.validator.validate(res, binding);

		} else {
			Company company;
			company = this.companyService.findOne(registrationForm.getId());
			res = company.getCreditCard();
			final CreditCard p = new CreditCard();
			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setBrandName(registrationForm.getBrandName());
			p.setHolderName(registrationForm.getHolderName());
			p.setNumber(registrationForm.getNumber());
			p.setExpirationMonth(registrationForm.getExpirationMonth());
			p.setExpirationYear(registrationForm.getExpirationYear());
			p.setCW(registrationForm.getCW());

			this.validator.validate(p, binding);
			res = p;

		}
		return res;
	}

	public CreditCard reconstruct(final RegistrationForm registrationForm, final BindingResult binding) {
		CreditCard res = new CreditCard();

		if (registrationForm.getId() == 0) {
			res.setId(registrationForm.getId());
			res.setVersion(registrationForm.getVersion());
			res.setBrandName(registrationForm.getBrandName());
			res.setHolderName(registrationForm.getHolderName());
			res.setNumber(registrationForm.getNumber());
			res.setExpirationMonth(registrationForm.getExpirationMonth());
			res.setExpirationYear(registrationForm.getExpirationYear());
			res.setCW(registrationForm.getCW());

			this.validator.validate(res, binding);

		} else {
			Administrator admin;
			admin = this.administratorService.findOne(registrationForm.getId());
			res = admin.getCreditCard();
			final CreditCard p = new CreditCard();
			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setBrandName(registrationForm.getBrandName());
			p.setHolderName(registrationForm.getHolderName());
			p.setNumber(registrationForm.getNumber());
			p.setExpirationMonth(registrationForm.getExpirationMonth());
			p.setExpirationYear(registrationForm.getExpirationYear());
			p.setCW(registrationForm.getCW());

			this.validator.validate(p, binding);
			res = p;

		}
		return res;
	}

	public CreditCard reconstruct(final RegistrationFormHacker registrationForm, final BindingResult binding) {
		CreditCard res = new CreditCard();

		if (registrationForm.getId() == 0) {
			res.setId(registrationForm.getId());
			res.setVersion(registrationForm.getVersion());
			res.setBrandName(registrationForm.getBrandName());
			res.setHolderName(registrationForm.getHolderName());
			res.setNumber(registrationForm.getNumber());
			res.setExpirationMonth(registrationForm.getExpirationMonth());
			res.setExpirationYear(registrationForm.getExpirationYear());
			res.setCW(registrationForm.getCW());

			this.validator.validate(res, binding);

		} else {
			Hacker hacker;
			hacker = this.hackerService.findOne(registrationForm.getId());
			res = hacker.getCreditCard();
			final CreditCard p = new CreditCard();
			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setBrandName(registrationForm.getBrandName());
			p.setHolderName(registrationForm.getHolderName());
			p.setNumber(registrationForm.getNumber());
			p.setExpirationMonth(registrationForm.getExpirationMonth());
			p.setExpirationYear(registrationForm.getExpirationYear());
			p.setCW(registrationForm.getCW());

			this.validator.validate(p, binding);
			res = p;

		}
		return res;
	}
}
