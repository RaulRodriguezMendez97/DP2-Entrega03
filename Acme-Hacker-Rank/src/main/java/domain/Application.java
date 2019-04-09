
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	private Date		moment;
	private String		explication;
	private String		urlCode;
	private Date		submitMoment;
	private int			status;
	private Hacker		hacker;
	private Curricula	curricula;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Curricula getCurricula() {
		return this.curricula;
	}

	public void setCurricula(final Curricula curricula) {
		this.curricula = curricula;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Hacker getHacker() {
		return this.hacker;
	}

	public void setHacker(final Hacker hacker) {
		this.hacker = hacker;
	}

	@Past
	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotNull
	public String getExplication() {
		return this.explication;
	}

	public void setExplication(final String explication) {
		this.explication = explication;
	}

	@NotNull
	@URL
	public String getUrlCode() {
		return this.urlCode;
	}

	public void setUrlCode(final String urlCode) {
		this.urlCode = urlCode;
	}

	public Date getSubmitMoment() {
		return this.submitMoment;
	}

	public void setSubmitMoment(final Date submitMoment) {
		this.submitMoment = submitMoment;
	}

	@Range(min = 0, max = 3)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(final int status) {
		this.status = status;
	}
}
