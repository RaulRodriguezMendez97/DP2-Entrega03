
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ProfileDataRepository;
import domain.ProfileData;

@Component
@Transactional
public class StringToProfileDataConverter implements Converter<String, ProfileData> {

	@Autowired
	ProfileDataRepository	profileDataRepository;


	@Override
	public ProfileData convert(final String text) {
		ProfileData result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.profileDataRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
