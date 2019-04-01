
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String					keyWord;
	private Date					deadLine;
	private Double					minSalary;
	private Date					maxDeadLine;
	private Collection<Position>	positions;


	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	@Valid
	@ManyToMany
	public Collection<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(final Collection<Position> positions) {
		this.positions = positions;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDeadLine() {
		return this.deadLine;
	}

	public void setDeadLine(final Date deadLine) {
		this.deadLine = deadLine;
	}

	public Double getMinSalary() {
		return this.minSalary;
	}

	public void setMinSalary(final Double minSalary) {
		this.minSalary = minSalary;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMaxDeadLine() {
		return this.maxDeadLine;
	}

	public void setMaxDeadLine(final Date maxDeadLine) {
		this.maxDeadLine = maxDeadLine;
	}

}
