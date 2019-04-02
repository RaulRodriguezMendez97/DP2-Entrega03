
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

import repositories.HackerRepository;
import security.Authority;
import security.UserAccount;
import domain.CredictCard;
import domain.Finder;
import domain.Hacker;
import forms.RegistrationFormHacker;

@Service
@Transactional
public class HackerService {

	@Autowired
	private HackerRepository			hackerRepository;

	@Autowired
	private FinderService				finderService;

	@Autowired
	private CustomizableSystemService	customizableService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private Validator					validator;


	public Hacker create() {
		final Hacker res = new Hacker();
		res.setFinder(new Finder());
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

		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.HACKER);
		user.getAuthorities().add(ad);
		user.setUsername("");
		user.setPassword("");
		res.setUserAccount(user);

		return res;
	}

	public Collection<Hacker> findAll() {
		return this.hackerRepository.findAll();
	}

	public Hacker findOne(final int hackerId) {
		return this.hackerRepository.findOne(hackerId);
	}

	public Hacker save(final Hacker r) {

		//final UserAccount userLoged = LoginService.getPrincipal();
		//Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("HACKER"), "Comprobar que hay Company conectado");
		Hacker res = null;
		Assert.isTrue(r.getFinder() != null, "Hacker.save -> Finder  invalid");
		Assert.isTrue(r != null && r.getName() != null && r.getSurnames() != null && r.getName() != "" && r.getUserAccount() != null && r.getEmail() != null && r.getEmail() != "", "Company.save -> Name, Surname or email invalid");
		Assert.isTrue(r.getVatNumber() != null, "Companny.save -> VatNumber  invalid");
		Assert.isTrue(r.getCredictCard() != null, "Companny.save -> VatNumber  invalid");

		final String regexEmail1 = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
		final Pattern patternEmail1 = Pattern.compile(regexEmail1);
		final Matcher matcherEmail1 = patternEmail1.matcher(r.getEmail());

		final String regexEmail2 = "^[A-z0-9]+\\s*[A-z0-9\\s]*\\s\\<[A-z0-9]+\\@[A-z0-9]+\\.[A-z0-9.]+\\>";
		final Pattern patternEmail2 = Pattern.compile(regexEmail2);
		final Matcher matcherEmail2 = patternEmail2.matcher(r.getEmail());
		Assert.isTrue(matcherEmail1.find() == true || matcherEmail2.find() == true, "CustomerService.save -> Correo inválido");

		final List<String> emails = this.actorService.getEmails();

		if (r.getId() == 0)
			Assert.isTrue(!emails.contains(r.getEmail()), "Company.Email -> The email you entered is already being used");
		else {
			final Hacker a = this.hackerRepository.findOne(r.getId());
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

			final Finder finder = this.finderService.create();
			final Finder savedFinder = this.finderService.save(finder);
			r.setFinder(savedFinder);
		}

		res = this.hackerRepository.save(r);
		return res;
	}

	public Hacker hackerUserAccount(final Integer id) {
		return this.hackerRepository.hackerUserAccount(id);
	}

	public Hacker reconstruct(final RegistrationFormHacker registrationForm, final BindingResult binding) {
		Hacker res = new Hacker();

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
			ad.setAuthority(Authority.HACKER);
			user.getAuthorities().add(ad);
			res.setUserAccount(user);
			user.setUsername(registrationForm.getUserAccount().getUsername());
			user.setPassword(registrationForm.getUserAccount().getPassword());
			res.setFinder(registrationForm.getFinder());

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
			res = this.hackerRepository.findOne(registrationForm.getId());
			final Hacker p = new Hacker();
			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setAddress(registrationForm.getAddress());
			p.setEmail(registrationForm.getEmail());
			p.setVatNumber(registrationForm.getVatNumber());
			p.setName(registrationForm.getName());
			p.setPhone(registrationForm.getPhone());
			p.setPhoto(registrationForm.getPhoto());
			p.setSurnames(registrationForm.getSurnames());
			p.setFinder(registrationForm.getFinder());

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

	//dashboard
	public List<String> getHackersWithMoreApplications() {
		return this.hackerRepository.getHackersWithMoreApplications();
	}
}
