
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Posicion;

@Component
@Transactional
public class PositionToStringConverter implements Converter<Posicion, String> {

	@Override
	public String convert(final Posicion source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());
		return result;
	}
}
