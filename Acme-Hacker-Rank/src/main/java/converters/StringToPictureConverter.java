
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PictureRepository;
import domain.Picture;

@Component
@Transactional
public class StringToPictureConverter implements Converter<String, Picture> {

	@Autowired
	private PictureRepository	pictureRepository;


	@Override
	public Picture convert(final String source) {

		Picture picture;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				picture = null;
			else {
				id = Integer.valueOf(source);
				picture = this.pictureRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return picture;
	}
}
