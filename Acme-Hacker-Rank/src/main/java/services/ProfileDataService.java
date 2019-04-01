
package services;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfileDataRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Curricula;
import domain.ProfileData;

@Service
@Transactional
public class ProfileDataService {

	@Autowired
	private CustomizableSystemService	customizableService;
	@Autowired
	private ProfileDataRepository		profileDataRepository;
	@Autowired
	private ActorService				actorS;
	@Autowired
	private CurriculaService			curriculaService;


	public ProfileData create() {
		final ProfileData res = new ProfileData();
		final String telephoneCode = this.customizableService.getTelephoneCode();
		res.setFullName("");
		res.setStatement("");
		res.setPhoneNumber(telephoneCode + " ");
		res.setGithubProfile("");
		res.setLinkedlnProfile("");
		return res;
	}

	public Collection<ProfileData> findAll() {
		return this.profileDataRepository.findAll();
	}

	public ProfileData findOne(final Integer profileDataId) {
		final ProfileData profileData = this.profileDataRepository.findOne(profileDataId);
		final Curricula curricula = this.curriculaService.getCurriculaByProfileData(profileData.getId());
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("HACKER"));
		Assert.isTrue(curricula.getHacker() == a);
		return profileData;
	}

	public ProfileData save(final ProfileData profileData) {
		final Curricula curricula = this.curriculaService.getCurriculaByProfileData(profileData.getId());
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		if (profileData.getId() != 0)
			Assert.isTrue(curricula.getHacker() == a);

		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("HACKER"));
		Assert.isTrue(profileData != null && profileData.getFullName() != null && profileData.getFullName() != "" && profileData.getStatement() != null && profileData.getGithubProfile() != null && profileData.getLinkedlnProfile() != null);
		if (a.getPhone() != "" || a.getPhone() != null) {
			final String regexTelefono = "^\\+[1-9][0-9]{0,2}\\ \\([1-9][0-9]{0,2}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}$";
			final Pattern patternTelefono = Pattern.compile(regexTelefono);
			final Matcher matcherTelefono = patternTelefono.matcher(a.getPhone());
			Assert.isTrue(matcherTelefono.find() == true, "ProfileDataService.save -> Telefono no valido");
		}
		return this.profileDataRepository.save(profileData);
	}

	public void delete(final ProfileData profileData, final int curriculaId) {
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
