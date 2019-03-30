
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Position extends DomainEntity {

	private String		title;
	private String		description;
	private Date		deadLine;
	private String		requiredProfile;
	private String		skillsRequired;
	private String		technologiesRequired;
	private int			salaty;
	private String		ticker;
	private int			draftMode;
	private Company	companie;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Company getCompanie() {
		return this.companie;
	}

	public void setCompanie(final Company companie) {
		this.companie = companie;
	}

	@NotNull
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotNull
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDeadLine() {
		return this.deadLine;
	}

	public void setDeadLine(final Date deadLine) {
		this.deadLine = deadLine;
	}

	@NotNull
	public String getRequiredProfile() {
		return this.requiredProfile;
	}

	public void setRequiredProfile(final String requiredProfile) {
		this.requiredProfile = requiredProfile;
	}

	@NotNull
	public String getSkillsRequired() {
		return this.skillsRequired;
	}

	public void setSkillsRequired(final String skillsRequired) {
		this.skillsRequired = skillsRequired;
	}

	@NotNull
	public String getTechnologiesRequired() {
		return this.technologiesRequired;
	}

	public void setTechnologiesRequired(final String technologiesRequired) {
		this.technologiesRequired = technologiesRequired;
	}

	public int getSalaty() {
		return this.salaty;
	}

	public void setSalaty(final int salaty) {
		this.salaty = salaty;
	}

	@Pattern(regexp = "^[A-z]{4}\\-[0-9]{4}$")
	@Column(unique = true)
	@NotNull
	@NotBlank
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@Range(min = 0, max = 1)
	public int getDraftMode() {
		return this.draftMode;
	}

	public void setDraftMode(final int draftMode) {
		this.draftMode = draftMode;
	}
}
