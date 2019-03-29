
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Paso;

@Component
@Transactional
public class FloatToStringConverter implements Converter<Paso, String> {

	@Override
	public String convert(final Paso source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());
		return result;
	}
}
