
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import services.PeriodRecordService;
import domain.PeriodRecord;

@Component
@Transactional
public class StringToPeriodRecordConverter implements Converter<String, PeriodRecord> {

	@Autowired
	private PeriodRecordService	periodRecordService;


	@Override
	public PeriodRecord convert(final String source) {

		PeriodRecord periodRecord;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				periodRecord = null;
			else {
				id = Integer.valueOf(source);
				periodRecord = this.periodRecordService.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return periodRecord;
	}
}
