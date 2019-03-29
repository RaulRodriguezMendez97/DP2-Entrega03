
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "row, columna, status, procession")
})
public class Request extends DomainEntity {

	private int			status;
	private Integer		columna;
	private Integer		row;
	private String		description;
	private Member		member;

	private Parade	procession;


	@NotNull
	@Range(min = 0, max = 2)
	public int getStatus() {
		return this.status;
	}
	public void setStatus(final int status) {
		this.status = status;
	}

	@Min(0)
	public Integer getColumna() {
		return this.columna;
	}

	public void setColumna(final Integer column) {
		this.columna = column;
	}

	@Min(0)
	public Integer getRow() {
		return this.row;
	}
	public void setRow(final Integer row) {
		this.row = row;
	}

	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	public Member getMember() {
		return this.member;
	}
	public void setMember(final Member member) {
		this.member = member;
	}

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	public Parade getProcession() {
		return this.procession;
	}

	public void setProcession(final Parade procession) {
		this.procession = procession;
	}

}
