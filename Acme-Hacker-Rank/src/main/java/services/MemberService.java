
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

import repositories.MemberRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Hacker;
import domain.Request;
import forms.MemberRegistrationForm;

@Service
@Transactional
public class MemberService {

	@Autowired
	private MemberRepository			memberRepo;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private CustomizableSystemService	customizableService;
	@Autowired
	private Validator					validator;


	//Crear nuevo member
	public Hacker create() {
		final Hacker res = new Hacker();
		res.setAddress("");
		res.setEmail("");
		res.setName("");
		res.setMiddleName("");
		final String telephoneCode = this.customizableService.getTelephoneCode();
		res.setPhone(telephoneCode + " ");
		res.setPhoto("");
		res.setSurname("");
		res.setRequests(new HashSet<Request>());

		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.MEMBER);
		user.getAuthorities().add(ad);
		user.setUsername("");
		user.setPassword("");
		res.setUserAccount(user);

		return res;
	}
	//Todos los member
	public Collection<Hacker> findAll() {
		return this.memberRepo.findAll();
	}

	//Un member por el id
	public Hacker findOne(final int memberId) {
		return this.memberRepo.findOne(memberId);
	}

	//Guardar
	public Hacker save(final Hacker r) {
		Hacker res = null;
		Assert.isTrue(r != null && r.getName() != null && r.getSurname() != null && r.getName() != "" && r.getSurname() != "" && r.getUserAccount() != null && r.getEmail() != null && r.getEmail() != "", "MemberService.save -> Name or Surname invalid");

		final String regexEmail1 = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
		final Pattern patternEmail1 = Pattern.compile(regexEmail1);
		final Matcher matcherEmail1 = patternEmail1.matcher(r.getEmail());

		final String regexEmail2 = "^[A-z0-9]+\\s*[A-z0-9\\s]*\\s\\<[A-z0-9]+\\@[A-z0-9]+\\.[A-z0-9.]+\\>";
		final Pattern patternEmail2 = Pattern.compile(regexEmail2);
		final Matcher matcherEmail2 = patternEmail2.matcher(r.getEmail());
		Assert.isTrue(matcherEmail1.find() == true || matcherEmail2.find() == true, "CustomerService.save -> Correo inválido");

		final List<String> emails = this.actorService.getEmails();

		if (r.getId() == 0)
			Assert.isTrue(!emails.contains(r.getEmail()));
		else {
			final Hacker a = this.memberRepo.findOne(r.getId());
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

		//Probar tele

		if (r.getPhone() == "+34 ")
			r.setPhone("");

		res = this.memberRepo.save(r);
		return res;
	}

	public Hacker getMemberByUserAccount(final int userAccountId) {
		return this.memberRepo.getMemberByUserAccount(userAccountId);
	}

	public Collection<Hacker> getMemberByBrotherhood(final Integer id) {
		return this.memberRepo.getMembersOfBrotherhood(id);
	}

	//RECONSTRUCT
	public Hacker reconstruct(final MemberRegistrationForm memberRegistrationForm, final BindingResult binding) {
		Hacker res;

		if (memberRegistrationForm.getId() == 0) {
			res = new Hacker();
			res.setAddress(memberRegistrationForm.getAddress());
			res.setEmail(memberRegistrationForm.getEmail());
			res.setMiddleName(memberRegistrationForm.getMiddleName());
			res.setName(memberRegistrationForm.getName());
			res.setPhone(memberRegistrationForm.getPhone());
			res.setPhoto(memberRegistrationForm.getPhoto());
			res.setSurname(memberRegistrationForm.getSurname());
			final UserAccount user = new UserAccount();
			user.setAuthorities(new HashSet<Authority>());
			final Authority ad = new Authority();
			ad.setAuthority(Authority.MEMBER);
			user.getAuthorities().add(ad);
			user.setUsername(memberRegistrationForm.getUserAccount().getUsername());
			user.setPassword(memberRegistrationForm.getUserAccount().getPassword());
			res.setUserAccount(user);
			res.setRequests(new HashSet<Request>());
			Assert.isTrue(memberRegistrationForm.getPassword2().equals(memberRegistrationForm.getUserAccount().getPassword()));
			Assert.isTrue(memberRegistrationForm.getCheck() == true);
			if (res.getPhone().length() <= 5)
				res.setPhone("");

			if (memberRegistrationForm.getPatternPhone() == false) {
				final String regexTelefono = "^\\+[0-9]{0,3}\\s\\([0-9]{0,3}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}|^\\+[0-9]\\ $|^$|^\\+$";
				final Pattern patternTelefono = Pattern.compile(regexTelefono);
				final Matcher matcherTelefono = patternTelefono.matcher(res.getPhone());
				Assert.isTrue(matcherTelefono.find() == true, "BrotherhoodService.save -> Telefono no valido");
			}
			this.validator.validate(res, binding);
		} else {
			Assert.isTrue(memberRegistrationForm.getPassword2().equals(memberRegistrationForm.getUserAccount().getPassword()));
			res = this.memberRepo.findOne(memberRegistrationForm.getId());
			final Hacker p = new Hacker();
			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setAddress(memberRegistrationForm.getAddress());
			p.setEmail(memberRegistrationForm.getEmail());
			p.setMiddleName(memberRegistrationForm.getMiddleName());
			p.setName(memberRegistrationForm.getName());
			p.setPhone(memberRegistrationForm.getPhone());
			p.setPhoto(memberRegistrationForm.getPhoto());
			p.setRequests(res.getRequests());
			p.setSurname(memberRegistrationForm.getSurname());
			if (p.getPhone().length() <= 5)
				p.setPhone("");

			if (memberRegistrationForm.getPatternPhone() == false) {
				final String regexTelefono = "^\\+[0-9]{0,3}\\s\\([0-9]{0,3}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}|^\\+[0-9]\\ $|^$|^\\+$";
				final Pattern patternTelefono = Pattern.compile(regexTelefono);
				final Matcher matcherTelefono = patternTelefono.matcher(p.getPhone());
				Assert.isTrue(matcherTelefono.find() == true, "BrotherhoodService.save -> Telefono no valido");
			}

			if (memberRegistrationForm.getPassword2().equals("") || memberRegistrationForm.getPassword2() == null)
				p.setUserAccount(res.getUserAccount());
			else {
				final UserAccount user = res.getUserAccount();
				final Md5PasswordEncoder encoder;
				encoder = new Md5PasswordEncoder();
				final String hash = encoder.encodePassword(memberRegistrationForm.getPassword2(), null);
				user.setPassword(hash);
				p.setUserAccount(user);
			}

			this.validator.validate(p, binding);
			res = p;
		}
		return res;
	}

	public Collection<String> members10Percentage() {
		final UserAccount userLoged = LoginService.getPrincipal();
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
		return this.memberRepo.member10Percentage();
	}

}
