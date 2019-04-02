
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
import domain.PersonalData;

@Service
@Transactional
public class PersonalDataService {

	@Autowired
	private CustomizableSystemService	customizableService;
	@Autowired
	private PersonalDataRepository		profileDataRepository;
	@Autowired
	private ActorService				actorS;
	@Autowired
	private CurriculaService			curriculaService;


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
		return this.profileDataRepository.findAll();
	}

	public PersonalData findOne(final Integer profileDataId) {
		final PersonalData profileData = this.profileDataRepository.findOne(profileDataId);
		final Curricula curricula = this.curriculaService.getCurriculaByProfileData(profileData.getId());
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("HACKER"));
		Assert.isTrue(curricula.getHacker() == a);
		return profileData;
	}

	public PersonalData save(final PersonalData profileData) {
		final Curricula curricula = this.curriculaService.getCurriculaByProfileData(profileData.getId());
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		if (profileData.getId() != 0)
			Assert.isTrue(curricula.getHacker() == a);

		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("HACKER"));
		Assert.isTrue(profileData != null && profileData.getFullName() != null && profileData.getFullName() != "" && profileData.getStatement() != null && profileData.getStatement() != "" && profileData.getGithubProfile() != null
			&& profileData.getGithubProfile() != "" && profileData.getLinkedlnProfile() != null && profileData.getLinkedlnProfile() != "");
		if (a.getPhone() != "" || a.getPhone() != null) {
			final String regexTelefono = "^\\+[1-9][0-9]{0,2}\\ \\([1-9][0-9]{0,2}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}$";
			final Pattern patternTelefono = Pattern.compile(regexTelefono);
			final Matcher matcherTelefono = patternTelefono.matcher(a.getPhone());
			Assert.isTrue(matcherTelefono.find() == true, "ProfileDataService.save -> Telefono no valido");
		}
		return this.profileDataRepository.save(profileData);
	}

	public void delete(final PersonalData profileData, final int curriculaId) {
		final Curricula curricula = this.curriculaService.findOne(curriculaId);
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("HACKER"));
		Assert.isTrue(curricula.getHacker() == a);
		//Assert.isTrue(history.getLinkRecords().contains(linkRecord));
		//history.getLinkRecords().remove(linkRecord);
		this.profileDataRepository.delete(profileData);
		//this.historyService.save(history);
	}

}
