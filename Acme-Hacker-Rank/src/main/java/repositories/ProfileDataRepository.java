
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ProfileData;

@Repository
public interface ProfileDataRepository extends JpaRepository<ProfileData, Integer> {

	@Query("select c.profileData from Curricula c where c.id=?1")
	public Collection<ProfileData> getProfileDatasByCurricula(Integer curriculaId);
}
