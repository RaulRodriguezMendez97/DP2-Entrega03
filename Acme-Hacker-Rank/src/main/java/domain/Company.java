
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Company extends Actor {

	private String	name;


	@Override
	@NotNull
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}
}
