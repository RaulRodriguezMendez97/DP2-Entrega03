
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Companie;

@Component
@Transactional
public class BrotherhoodToStringConverter implements Converter<Companie, String> {

	@Override
	public String convert(final Companie source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());
		return result;
	}
}
