
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Paso extends DomainEntity {

	private String				title;
	private String				description;
	private Collection<Picture>	pictures;
	private Parade			procession;
	private Brotherhood			brotherhood;


	@NotBlank
	@NotNull
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotNull
	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@OneToMany
	@Valid
	public Collection<Picture> getPictures() {
		return this.pictures;
	}
	public void setPictures(final Collection<Picture> pictures) {
		this.pictures = pictures;
	}

	@ManyToOne(optional = true)
	@Valid
	public Parade getProcession() {
		return this.procession;
	}
	public void setProcession(final Parade procession) {
		this.procession = procession;
	}

	@ManyToOne(optional = false)
	@Valid
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}
	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}
}
