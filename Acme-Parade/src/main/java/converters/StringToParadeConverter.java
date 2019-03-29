
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ParadeRepository;
import domain.Parade;

@Component
@Transactional
public class StringToParadeConverter implements Converter<String, Parade> {

	@Autowired
	private ParadeRepository	processionRepository;


	@Override
	public Parade convert(final String source) {

		Parade procession;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				procession = null;
			else {
				id = Integer.valueOf(source);
				procession = this.processionRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return procession;
	}
}
