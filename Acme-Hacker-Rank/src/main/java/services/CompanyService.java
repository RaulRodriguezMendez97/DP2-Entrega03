
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CompanyRepository;
import security.Authority;
import security.UserAccount;
import domain.Company;
import domain.CredictCard;
import forms.RegistrationFormCompany;

@Service
@Transactional
public class CompanyService {

	@Autowired
	private CompanyRepository			companyRepository;

	@Autowired
	private CustomizableSystemService	customizableService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private Validator					validator;


	public Company create() {
		final Company res = new Company();

		res.setAddress("");
		res.setEmail("");
		res.setName("");
		res.setVatNumber("");
		final String telephoneCode = this.customizableService.getTelephoneCode();
		res.setPhone(telephoneCode + " ");
		res.setPhoto("");
		res.setSurnames(new HashSet<String>());
		res.setAddress("");
		res.setCredictCard(new CredictCard());
		res.setNameCompany("");

		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.COMPANY);
		user.getAuthorities().add(ad);
		user.setUsername("");
		user.setPassword("");
		res.setUserAccount(user);

		return res;
	}

	public Collection<Company> findAll() {
		return this.companyRepository.findAll();
	}

	public Company findOne(final int companyId) {
		return this.companyRepository.findOne(companyId);
	}

	public Company save(final Company r) {

		//final UserAccount userLoged = LoginService.getPrincipal();
		//Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("COMPANY"), "Comprobar que hay Company conectado");
		Company res = null;
		Assert.isTrue(r.getNameCompany() != null && r.getNameCompany() != "", "Companny.save -> NameCompany  invalid");
		Assert.isTrue(r != null && r.getName() != null && r.getSurnames() != null && r.getName() != "" && r.getUserAccount() != null && r.getEmail() != null && r.getEmail() != "", "Company.save -> Name, Surname or email invalid");
		Assert.isTrue(r.getVatNumber() != null, "Companny.save -> VatNumber  invalid");
		Assert.isTrue(r.getCredictCard() != null, "Companny.save -> VatNumber  invalid");

		final String regexEmail1 = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
		final Pattern patternEmail1 = Pattern.compile(regexEmail1);
		final Matcher matcherEmail1 = patternEmail1.matcher(r.getEmail());

		final String regexEmail2 = "^[A-z0-9]+\\s*[A-z0-9\\s]*\\s\\<[A-z0-9]+\\@[A-z0-9]+\\.[A-z0-9.]+\\>";
		final Pattern patternEmail2 = Pattern.compile(regexEmail2);
		final Matcher matcherEmail2 = patternEmail2.matcher(r.getEmail());
		Assert.isTrue(matcherEmail1.find() == true || matcherEmail2.find() == true, "CustomerService.save -> Correo inv�lido");

		final List<String> emails = this.actorService.getEmails();

		if (r.getId() == 0)
			Assert.isTrue(!emails.contains(r.getEmail()), "Company.Email -> The email you entered is already being used");
		else {
			final Company a = this.companyRepository.findOne(r.getId());
			Assert.isTrue(a.getEmail().equals(r.getEmail()));
		}

		//NUEVO
		Assert.isTrue(r.getUserAccount().getUsername() != null && r.getUserAccount().getUsername() != "");
		Assert.isTrue(r.getUserAccount().getPassword() != null && r.getUserAccount().getPassword() != "");

		if (r.getId() == 0) {

			final Md5PasswordEncoder encoder;
			encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(r.getUserAccount().getPassword(), null);
			final UserAccount user = r.getUserAccount();
			user.setPassword(hash);
		}

		res = this.companyRepository.save(r);
		return res;
	}

	public Company companyUserAccount(final Integer id) {
		return this.companyRepository.companyUserAccount(id);
	}

	public Company reconstruct(final RegistrationFormCompany registrationForm, final BindingResult binding) {
		Company res = new Company();

		if (registrationForm.getId() == 0) {
			res.setId(registrationForm.getUserAccount().getId());
			res.setVersion(registrationForm.getVersion());
			res.setAddress(registrationForm.getAddress());
			res.setEmail(registrationForm.getEmail());
			res.setVatNumber(registrationForm.getVatNumber());
			res.setName(registrationForm.getName());
			res.setPhone(registrationForm.getPhone());
			res.setPhoto(registrationForm.getPhoto());
			res.setSurnames(registrationForm.getSurnames());
			final Authority ad = new Authority();
			final UserAccount user = new UserAccount();
			user.setAuthorities(new HashSet<Authority>());
			ad.setAuthority(Authority.COMPANY);
			user.getAuthorities().add(ad);
			res.setUserAccount(user);
			user.setUsername(registrationForm.getUserAccount().getUsername());
			user.setPassword(registrationForm.getUserAccount().getPassword());
			res.setNameCompany(registrationForm.getNameCompany());

			Assert.isTrue(registrationForm.getPassword().equals(registrationForm.getUserAccount().getPassword()));
			Assert.isTrue(registrationForm.getCheck() == true);

			if (res.getPhone().length() <= 5)
				res.setPhone("");

			if (registrationForm.getPatternPhone() == false) {
				final String regexTelefono = "^\\+[0-9]{0,3}\\s\\([0-9]{0,3}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}|^\\+[0-9]\\ $|^$|^\\+$";
				final Pattern patternTelefono = Pattern.compile(regexTelefono);
				final Matcher matcherTelefono = patternTelefono.matcher(res.getPhone());
				Assert.isTrue(matcherTelefono.find() == true, "CompanyService.save -> Telefono no valido");
			}

			this.validator.validate(res, binding);

		} else {
			Assert.isTrue(registrationForm.getPassword().equals(registrationForm.getUserAccount().getPassword()));
			res = this.companyRepository.findOne(registrationForm.getId());
			final Company p = new Company();
			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setAddress(registrationForm.getAddress());
			p.setEmail(registrationForm.getEmail());
			p.setVatNumber(registrationForm.getVatNumber());
			p.setName(registrationForm.getName());
			p.setPhone(registrationForm.getPhone());
			p.setPhoto(registrationForm.getPhoto());
			p.setSurnames(registrationForm.getSurnames());
			p.setNameCompany(registrationForm.getNameCompany());

			if (p.getPhone().length() <= 5)
				p.setPhone("");

			if (registrationForm.getPatternPhone() == false) {
				final String regexTelefono = "^\\+[0-9]{0,3}\\s\\([0-9]{0,3}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}|^\\+[0-9]\\ $|^$|^\\+$";
				final Pattern patternTelefono = Pattern.compile(regexTelefono);
				final Matcher matcherTelefono = patternTelefono.matcher(p.getPhone());
				Assert.isTrue(matcherTelefono.find() == true, "CompanyService.save -> Telefono no valido");
			}

			if (registrationForm.getPassword().equals("") || registrationForm.getPassword() == null)
				p.setUserAccount(res.getUserAccount());
			else {
				final UserAccount user = res.getUserAccount();
				final Md5PasswordEncoder encoder;
				encoder = new Md5PasswordEncoder();
				final String hash = encoder.encodePassword(registrationForm.getPassword(), null);
				user.setPassword(hash);
				p.setUserAccount(user);
			}

			this.validator.validate(p, binding);
			res = p;

		}
		return res;

	}

	//Dashboard
	public List<String> getCompaniesWithMorePositions() {
		return this.companyRepository.getCompaniesWithMorePositions();
	}
}
