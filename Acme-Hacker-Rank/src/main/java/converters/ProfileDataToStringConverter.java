
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ProfileData;

@Component
@Transactional
public class ProfileDataToStringConverter implements Converter<ProfileData, String> {

	@Override
	public String convert(final ProfileData profileData) {
		String result;

		if (profileData == null)
			result = null;
		else
			result = String.valueOf(profileData.getId());

		return result;
	}

}
