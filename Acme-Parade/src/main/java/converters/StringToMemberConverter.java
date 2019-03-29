
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MemberRepository;
import domain.Member;

@Component
@Transactional
public class StringToMemberConverter implements Converter<String, Member> {

	@Autowired
	private MemberRepository	memberRepository;


	@Override
	public Member convert(final String source) {

		Member member;
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
