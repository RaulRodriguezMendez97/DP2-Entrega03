
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FloatRepository;
import security.LoginService;
import security.UserAccount;
import domain.Companie;
import domain.Paso;
import domain.Picture;
import domain.Parade;

@Service
@Transactional
public class FloatService {

	@Autowired
	private FloatRepository		FRepo;
	@Autowired
	private ActorService		actorS;
	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private Validator			validator;


	//Metodo create
	public Paso create() {
		final Paso paso = new Paso();
		final UserAccount user = LoginService.getPrincipal();
		final Companie br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		paso.setTitle("");
		paso.setDescription("");
		paso.setPictures(new HashSet<Picture>());
		paso.setBrotherhood(br);
		paso.setProcession(new Parade());
		return paso;
	}
	//Metodo findAll
	public Collection<Paso> finaAll() {
		return this.FRepo.findAll();
	}
	//Metodo findOne
	public Paso findOne(final int floatId) {
		final UserAccount user = LoginService.getPrincipal();
		final Companie br = this.brotherhoodService.brotherhoodUserAccount(user.getId());

		final Paso res = this.FRepo.findOne(floatId);
		final Companie brFloat = res.getBrotherhood();
		Assert.isTrue(this.getFloatsByBrotherhood(br.getId()).contains(res));
		Assert.isTrue(br.equals(brFloat));
		return res;
	}
	//Metodo save
	public Paso save(final Paso paso) {
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		final Companie br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		Assert.isTrue(br.equals(paso.getBrotherhood()));
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(paso.getTitle() != null);
		Assert.isTrue(paso != null);
		Assert.isTrue(paso.getTitle() != "");
		Assert.isTrue(paso.getDescription() != null);
		Assert.isTrue(paso.getDescription() != "");
		Assert.isTrue(paso.getBrotherhood() != null);
		return this.FRepo.save(paso);
	}
	//Metodo delete
	public void delete(final Paso paso) {
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		final Companie br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(this.getFloatsByBrotherhood(br.getId()).contains(paso));
		this.FRepo.delete(paso);
	}

	//Metodo que devuelve las floats(pasos) de una brotherhood
	public Collection<Paso> getFloatsByBrotherhood(final Integer brotherhoodId) {
		return this.FRepo.getFlotasByBrotherhood(brotherhoodId);
	}

	//RECONSTRUCT
	public Paso reconstruct(final Paso paso, final BindingResult binding) {
		Paso res;

		if (paso.getId() == 0) {
			res = paso;
			res.setPictures(new HashSet<Picture>());
			res.setBrotherhood(this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()));
			this.validator.validate(res, binding);
			return res;
		} else {
			res = this.FRepo.findOne(paso.getId());
			final Paso p = new Paso();
			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setTitle(paso.getTitle());
			p.setDescription(paso.getDescription());
			p.setProcession(paso.getProcession());
			p.setPictures(res.getPictures());
			p.setBrotherhood(res.getBrotherhood());

			this.validator.validate(p, binding);
			return p;
		}

	}

}
