
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.InceptionRecord;

@Component
@Transactional
public class InceptionRecordToStringConverter implements Converter<InceptionRecord, String> {

	@Override
	public String convert(final InceptionRecord source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());
		return result;
	}

}
