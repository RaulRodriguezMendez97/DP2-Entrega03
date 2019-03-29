
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.HistoryRepository;
import domain.History;

@Component
@Transactional
public class StringToHistoryConverter implements Converter<String, History> {

	@Autowired
	private HistoryRepository	historyRepository;


	@Override
	public History convert(final String source) {

		History history;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				history = null;
			else {
				id = Integer.valueOf(source);
				history = this.historyRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return history;
	}
}
