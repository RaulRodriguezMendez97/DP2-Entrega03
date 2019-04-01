
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CurriculaRepository;
import security.UserAccount;
import domain.Curricula;
import domain.EducationData;
import domain.MiscellaneousData;
import domain.PositionData;
import domain.ProfileData;

@Service
@Transactional
public class CurriculaService {

	@Autowired
	private CurriculaRepository	curriculaRepository;
	@Autowired
	private HackerService		hackerService;
	@Autowired
	private ActorService		actorS;


	public Curricula create() {
		final Curricula res = new Curricula();
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		res.setHacker(this.hackerService.hackerUserAccount(user.getId()));
		res.setEducationData(new EducationData());
		res.setMiscellaneousData(new MiscellaneousData());
		res.setPositionData(new PositionData());
		res.setProfileData(new ProfileData());
		return res;
	}

	public Collection<Curricula> findAll() {
		return this.curriculaRepository.findAll();
	}
	public Curricula findOne(final Integer curriculaId) {
		return this.curriculaRepository.findOne(curriculaId);
	}

	public Curricula getCurriculaByProfileData(final Integer profileDataId) {
		return this.curriculaRepository.getCurriculaByProfileData(profileDataId);
	}
	public Curricula getCurriculaByPositionData(final Integer positionDataId) {
		return this.curriculaRepository.getCurriculaByPositionData(positionDataId);
	}
	public Curricula getCurriculaByEducationData(final Integer educationDataId) {
		return this.curriculaRepository.getCurriculaByEducationData(educationDataId);
	}
	public Curricula getCurriculaByMiscellaneousData(final Integer miscellaneousDataId) {
		return this.curriculaRepository.getCurriculaByMiscellaneousData(miscellaneousDataId);
	}

}
