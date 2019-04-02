
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class PersonalData extends DomainEntity {

	private String	fullName;
	private String	statement;
	private String	phoneNumber;
	private String	githubProfile;
	private String	linkedlnProfile;


	@NotNull
	@NotBlank
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	@NotNull
	@NotBlank
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	@NotNull
	@NotBlank
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@NotNull
	@URL
	@NotBlank
	public String getGithubProfile() {
		return this.githubProfile;
	}

	public void setGithubProfile(final String githubProfile) {
		this.githubProfile = githubProfile;
	}

	@NotNull
	@URL
	@NotBlank
	public String getLinkedlnProfile() {
		return this.linkedlnProfile;
	}

	public void setLinkedlnProfile(final String linkedlnProfile) {
		this.linkedlnProfile = linkedlnProfile;
	}
}
