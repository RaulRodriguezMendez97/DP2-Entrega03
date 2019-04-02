
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity {

	private Hacker				hacker;
	private PersonalData		personalData;
	private PositionData		positionData;
	private EducationData		educationData;
	private MiscellaneousData	miscellaneousData;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Hacker getHacker() {
		return this.hacker;
	}

	public void setHacker(final Hacker hacker) {
		this.hacker = hacker;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.REMOVE)
	public PersonalData getPersonalData() {
		return this.personalData;
	}

	public void setPersonalData(final PersonalData personalData) {
		this.personalData = personalData;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.REMOVE)
	public PositionData getPositionData() {
		return this.positionData;
	}

	public void setPositionData(final PositionData positionData) {
		this.positionData = positionData;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.REMOVE)
	public EducationData getEducationData() {
		return this.educationData;
	}

	public void setEducationData(final EducationData educationData) {
		this.educationData = educationData;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.REMOVE)
	public MiscellaneousData getMiscellaneousData() {
		return this.miscellaneousData;
	}

	public void setMiscellaneousData(final MiscellaneousData miscellaneousData) {
		this.miscellaneousData = miscellaneousData;
	}

}
