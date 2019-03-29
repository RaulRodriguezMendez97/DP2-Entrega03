
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.RequestRepository;
import domain.Request;

@Component
@Transactional
public class StringToRequestConverter implements Converter<String, Request> {

	@Autowired
	private RequestRepository	requestRepository;


	@Override
	public Request convert(final String source) {

		Request request;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				request = null;
			else {
				id = Integer.valueOf(source);
				request = this.requestRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return request;
	}

}
