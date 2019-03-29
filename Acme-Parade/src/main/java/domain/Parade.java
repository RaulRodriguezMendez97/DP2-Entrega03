
package domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "brotherhood, draftMode")
})
public class Parade extends DomainEntity {

	private List<Integer>		positionsRow;
	private List<Integer>		positionsColumn;
	private Integer				maxRows;
	private Integer				maxColumns;
	private String				ticker;
	private Date				moment;
	private String				description;
	private String				title;
	private int					draftMode;
	private Collection<Request>	requests;
	private Brotherhood			brotherhood;


	@Pattern(regexp = "^[0-9]{6}\\-[A-Z]{5}$")
	@Column(unique = true)
	@NotNull
	@NotBlank
	public String getTicker() {
		return this.ticker;
	}
	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	//@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@NotNull
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@Range(min = 0, max = 1)
	public int getDraftMode() {
		return this.draftMode;
	}
	public void setDraftMode(final int draftMode) {
		this.draftMode = draftMode;
	}

	@OneToMany(mappedBy = "procession")
	@Valid
	public Collection<Request> getRequests() {
		return this.requests;
	}

	public void setRequests(final Collection<Request> requests) {
		this.requests = requests;
	}

	@ManyToOne(optional = false)
	@Valid
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}
	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}
	@ElementCollection
	public List<Integer> getPositionsRow() {
		return this.positionsRow;
	}

	public void setPositionsRow(final List<Integer> positionsRow) {
		this.positionsRow = positionsRow;
	}
	@ElementCollection
	public List<Integer> getPositionsColumn() {
		return this.positionsColumn;
	}

	public void setPositionsColumn(final List<Integer> positionsColumn) {
		this.positionsColumn = positionsColumn;
	}

	@NotNull
	@Range(min = 1, max = 10)
	public Integer getMaxRows() {
		return this.maxRows;
	}

	public void setMaxRows(final Integer maxRows) {
		this.maxRows = maxRows;
	}

	@NotNull
	@Range(min = 1, max = 5000)
	public Integer getMaxColumns() {
		return this.maxColumns;
	}

	public void setMaxColumns(final Integer maxColumns) {
		this.maxColumns = maxColumns;
	}

}
