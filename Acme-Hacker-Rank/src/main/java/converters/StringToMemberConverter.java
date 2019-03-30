
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MemberRepository;
import domain.Hacker;

@Component
@Transactional
public class StringToMemberConverter implements Converter<String, Hacker> {

	@Autowired
	private MemberRepository	memberRepository;


	@Override
	public Hacker convert(final String source) {

		Hacker member;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				member = null;
			else {
				id = Integer.valueOf(source);
				member = this.memberRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return member;
	}

}
