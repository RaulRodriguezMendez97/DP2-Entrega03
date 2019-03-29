
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.InceptionRecordRepository;
import domain.InceptionRecord;

@Component
@Transactional
public class StringToInceptionRecordConverter implements Converter<String, InceptionRecord> {

	@Autowired
	private InceptionRecordRepository	inceptionRecordRepository;


	@Override
	public InceptionRecord convert(final String source) {

		InceptionRecord inceptionRecord;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				inceptionRecord = null;
			else {
				id = Integer.valueOf(source);
				inceptionRecord = this.inceptionRecordRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return inceptionRecord;
	}
}
