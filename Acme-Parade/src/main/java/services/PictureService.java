
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PictureRepository;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;
import domain.Picture;

@Service
@Transactional
public class PictureService {

	@Autowired
	private PictureRepository	PRepo;
	@Autowired
	private ActorService		actorS;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	//Metodo create
	public Picture create() {
		final Picture pic = new Picture();
		pic.setUrl("");
		return pic;
	}
	//Metodo findAll
	public Collection<Picture> finaAll() {
		return this.PRepo.findAll();
	}
	//Metodo findOne
	public Picture findOne(final int PictureId) {
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		final Brotherhood br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		final Picture res = this.PRepo.findOne(PictureId);
		Assert.isTrue(br.getPictures().contains(res) || this.getPicturesFloatByBrotherhood(br.getId()).contains(res) || this.brotherhoodService.getBrotherhoodByPeriodRecordPicture(PictureId).equals(br));
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));

		return res;
	}
	//Metodo save
	public Picture save(final Picture picture) {
		//Que la iamgen que se va a guardar no se nulla y la url de la imagen no sea nula
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		//final Brotherhood br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(picture != null && picture.getUrl() != null);
		//Assert.isTrue(!this.PRepo.findAll().contains(picture));
		//final Picture picSave = this.PRepo.save(picture);
		//Assert.isTrue((br.getPictures().contains(picture) && !this.getPicturesFloatByBrotherhood(br.getId()).contains(picture)) || (!br.getPictures().contains(picture) && this.getPicturesFloatByBrotherhood(br.getId()).contains(picture)));
		//		final Picture picSave = this.PRepo.save(picture);
		//		final Brotherhood br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		//		br.getPictures().add(picSave);
		return this.PRepo.save(picture);
		//return picSave;
	}
	//Metodo delete
	public void delete(final Picture picture) {
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		//final Brotherhood br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		//Assert.isTrue(br.getPictures().contains(picture));
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));

		this.PRepo.delete(picture);
	}
	public Collection<Picture> getPicturesFloatByBrotherhood(final Integer id) {
		return this.PRepo.getPicturesFloatByBrotherhood(id);
	}

	public Picture findMeAPicture(final int id) {
		Picture res;
		res = this.PRepo.findOne(id);
		final int brotherhoodId = this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()).getId();
		final Collection<Picture> myPictures = this.PRepo.getOnlyMyPicturesInceptionRecord(brotherhoodId);
		Assert.isTrue(myPictures.contains(res));
		return res;
	}

}
