
package acme.datatypes;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import acme.framework.datatypes.DomainDatatype;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Phone extends DomainDatatype {

	private static final long	serialVersionUID	= 1L;

	//--Attributes-------------------------------------
	@NotNull
	@Range(min = 1, max = 999)
	private Integer				countryCode;

	@Pattern(regexp = "\\d{1,6}", message = "default.error.conversion")
	private String				areaCode;

	@Pattern(regexp = "\\d{1,9}([\\s-]\\d{1,9}){0,5}", message = "default.error.conversion")
	private String				number;


	//--Interface--------------------------

	@Override
	public String toString() {
		StringBuilder result;
		result = new StringBuilder();
		result.append("<<+");
		result.append(this.countryCode);
		if (this.areaCode == null) {
			result.append(" ");
		} else {
			result.append(" (");
			result.append(this.areaCode);
			result.append(") ");
		}
		result.append(this.number);
		result.append(">>");
		return result.toString();
	}
}
