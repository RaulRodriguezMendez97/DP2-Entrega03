
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class CustomizableSystem extends DomainEntity {

	private String	nameSystem;
	private String	banner;
	private String	messageWelcomePage;
	private String	spanishMessageWelcomePage;
	private String	telephoneCode;
	private Integer	timeCache;
	private Integer	maxResults;


	@NotBlank
	@NotNull
	@Range(min = 1, max = 24)
	public Integer getTimeCache() {
		return this.timeCache;
	}

	public void setTimeCache(final Integer timeCache) {
		this.timeCache = timeCache;
	}

	@NotBlank
	@NotNull
	@Range(min = 1, max = 100)
	public Integer getMaxResults() {
		return this.maxResults;
	}

	public void setMaxResults(final Integer maxResults) {
		this.maxResults = maxResults;
	}

	@NotNull
	@NotBlank
	public String getNameSystem() {
		return this.nameSystem;
	}

	public void setNameSystem(final String nameSystem) {
		this.nameSystem = nameSystem;
	}

	@NotBlank
	@NotNull
	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	@NotNull
	public String getMessageWelcomePage() {
		return this.messageWelcomePage;
	}

	public void setMessageWelcomePage(final String messageWelcomePage) {
		this.messageWelcomePage = messageWelcomePage;
	}

	@NotBlank
	@NotNull
	public String getSpanishMessageWelcomePage() {
		return this.spanishMessageWelcomePage;
	}

	public void setSpanishMessageWelcomePage(final String spanishMessageWelcomePage) {
		this.spanishMessageWelcomePage = spanishMessageWelcomePage;
	}

	@NotBlank
	@NotNull
	public String getTelephoneCode() {
		return this.telephoneCode;
	}

	public void setTelephoneCode(final String telephoneCode) {
		this.telephoneCode = telephoneCode;
	}

}
