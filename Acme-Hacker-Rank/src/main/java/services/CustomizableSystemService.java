
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomizableSystemRepository;
import security.UserAccount;
import domain.CustomizableSystem;

@Service
@Transactional
public class CustomizableSystemService {

	@Autowired
	private CustomizableSystemRepository	customizableSystemRepository;

	@Autowired
	private ActorService					actorService;


	public CustomizableSystem create() {
		final CustomizableSystem res = new CustomizableSystem();
		res.setNameSystem("");
		res.setBanner("");
		res.setMessageWelcomePage("");
		res.setSpanishMessageWelcomePage("");
		res.setTelephoneCode("");
		res.setTimeCache(1);
		res.setMaxResults(0);

		return res;
	}

	public Collection<CustomizableSystem> findAll() {
		return this.customizableSystemRepository.findAll();
	}

	//		public CustomizableSystem findOne(final int customizableSystemId) {
	//			return this.customizableSystemRepository.findOne(customizableSystemId);
	//		}

	//updating
	public CustomizableSystem save(final CustomizableSystem customizableSystem) {
		final UserAccount user = this.actorService.getActorLogged().getUserAccount();
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
		Assert.isTrue(customizableSystem.getBanner() != null && customizableSystem.getBanner() != "" && customizableSystem.getMessageWelcomePage() != null && customizableSystem.getMessageWelcomePage() != "" && customizableSystem.getNameSystem() != null
			&& customizableSystem.getNameSystem() != "" && customizableSystem.getTelephoneCode() != null & customizableSystem.getTelephoneCode() != "" && customizableSystem.getMessageWelcomePage() != null
			&& customizableSystem.getSpanishMessageWelcomePage() != "" && customizableSystem.getSpanishMessageWelcomePage() != null && (customizableSystem.getMaxResults() >= 1 && customizableSystem.getMaxResults() <= 100)
			&& (customizableSystem.getTimeCache() >= 1 && customizableSystem.getTimeCache() <= 24));
		return this.customizableSystemRepository.save(customizableSystem);
	}

	public String getWelcomeMessage() {
		return this.customizableSystemRepository.getWelcomeMessage();
	}

	public String getSpanishWelcomeMessage() {
		return this.customizableSystemRepository.getSpanishWelcomeMessage();
	}

	public String getTelephoneCode() {
		return this.customizableSystemRepository.getTelephoneCode();
	}

	public String getUrlBanner() {
		return this.customizableSystemRepository.getUrlBanner();
	}

	public String getNameApp() {
		return this.customizableSystemRepository.getNameApp();
	}

	public int getTimeCache() {
		return this.customizableSystemRepository.getTimeCache();
	}

	public int getMaxResults() {
		return this.customizableSystemRepository.getMaxResults();
	}
}
