
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.LegalRecordRepository;
import domain.LegalRecord;

@Component
@Transactional
public class StringToLegalRecordConverter implements Converter<String, LegalRecord> {

	@Autowired
	private LegalRecordRepository	legalRecordRepository;


	@Override
	public LegalRecord convert(final String source) {

		LegalRecord legalRecord;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				legalRecord = null;
			else {
				id = Integer.valueOf(source);
				legalRecord = this.legalRecordRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return legalRecord;
	}
}
