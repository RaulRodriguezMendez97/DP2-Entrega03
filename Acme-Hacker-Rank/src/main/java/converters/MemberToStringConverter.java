
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Hacker;

@Component
@Transactional
public class MemberToStringConverter implements Converter<Hacker, String> {

	@Override
	public String convert(final Hacker member) {
		String result;

		if (member == null)
			result = null;
		else
			result = String.valueOf(member.getId());
		return result;
	}
}
