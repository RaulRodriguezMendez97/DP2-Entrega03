
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.LinkRecordRepository;
import domain.LinkRecord;

@Component
@Transactional
public class StringToLinkRecordConverter implements Converter<String, LinkRecord> {

	@Autowired
	private LinkRecordRepository	linkRecordRepository;


	@Override
	public LinkRecord convert(final String source) {

		LinkRecord linkRecord;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				linkRecord = null;
			else {
				id = Integer.valueOf(source);
				linkRecord = this.linkRecordRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return linkRecord;
	}
}
