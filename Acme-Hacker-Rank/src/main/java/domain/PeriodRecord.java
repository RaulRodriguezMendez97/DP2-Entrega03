
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class PeriodRecord extends DomainEntity {

	private String				title;
	private String				description;
	private int					starYear;
	private int					endYear;
	private Collection<Picture>	pictures;


	@NotBlank
	@NotNull
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@NotNull
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Min(value = 2019)
	public int getStarYear() {
		return this.starYear;
	}

	public void setStarYear(final int starYear) {
		this.starYear = starYear;
	}

	@Min(value = 2019)
	public int getEndYear() {
		return this.endYear;
	}

	public void setEndYear(final int endYear) {
		this.endYear = endYear;
	}

	@OneToMany
	@Valid
	public Collection<Picture> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<Picture> pictures) {
		this.pictures = pictures;
	}

}
