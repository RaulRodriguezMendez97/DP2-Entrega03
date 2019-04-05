
package services;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalDataRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Curricula;
import domain.Hacker;
import domain.PersonalData;

@Service
@Transactional
public class PersonalDataService {

	@Autowired
	private CustomizableSystemService	customizableService;
	@Autowired
	private PersonalDataRepository		personalDataRepository;
	@Autowired
	private ActorService				actorS;
	@Autowired
	private CurriculaService			curriculaService;
	@Autowired
	HackerService						hackerService;


	public PersonalData create() {
		final PersonalData res = new PersonalData();
		final String telephoneCode = this.customizableService.getTelephoneCode();
		res.setFullName("");
		res.setStatement("");
		res.setPhoneNumber(telephoneCode + " ");
		res.setGithubProfile("");
		res.setLinkedlnProfile("");
		return res;
	}

	public Collection<PersonalData> findAll() {
		return this.personalDataRepository.findAll();
	}

	public PersonalData findOne(final Integer personalDataId) {
		final PersonalData profileData = this.personalDataRepository.findOne(personalDataId);
		final Curricula curricula = this.curriculaService.getCurriculaByProfileData(profileData.getId());
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("HACKER"));
		Assert.isTrue(curricula.getHacker() == a);
		return profileData;
	}

	public PersonalData save(final PersonalData personalData) {
		final PersonalData personalDataSave;
		final Hacker h = this.hackerService.hackerUserAccount(LoginService.getPrincipal().getId());
		if (h.getPhone() != "" || h.getPhone() != null) {
			final String regexTelefono = "^\\+[1-9][0-9]{0,2}\\ \\([1-9][0-9]{0,2}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}$";
			final Pattern patternTelefono = Pattern.compile(regexTelefono);
			final Matcher matcherTelefono = patternTelefono.matcher(h.getPhone());
			Assert.isTrue(matcherTelefono.find() == true, "ProfileDataService.save -> Telefono no valido");
		}
		personalDataSave = this.personalDataRepository.save(personalData);
		if (personalData.getId() == 0) {
			final Curricula curricula = this.curriculaService.create();
			curricula.setHacker(h);
			curricula.setPersonalData(personalDataSave);
			this.curriculaService.save(curricula);
		}
		return personalDataSave;
	}
	public void delete(final PersonalData personalData) {
		final Curricula curricula = this.curriculaService.getCurriculaByProfileData(personalData.getId());
		this.curriculaService.delete(curricula);

	}

}
