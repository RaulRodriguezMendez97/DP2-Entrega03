
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {

	//Devolver todas las imaganes de todos los pasos de una hermandad
	@Query("select p.pictures from Paso p where p.brotherhood.id=?1")
	public Collection<Picture> getPicturesFloatByBrotherhood(Integer id);

	@Query("select h.inceptionRecord.pictures from History h where h.brotherhood.id=?1")
	public Collection<Picture> getOnlyMyPicturesInceptionRecord(int id);
}
