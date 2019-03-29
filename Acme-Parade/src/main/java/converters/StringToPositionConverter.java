
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PosicionRepository;
import domain.Posicion;

@Component
@Transactional
public class StringToPositionConverter implements Converter<String, Posicion> {

	@Autowired
	private PosicionRepository	positionRepository;


	@Override
	public Posicion convert(final String source) {

		Posicion position;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				position = null;
			else {
				id = Integer.valueOf(source);
				position = this.positionRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return position;
	}
}
