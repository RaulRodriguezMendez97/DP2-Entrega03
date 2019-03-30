
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EnrolmentRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Company;
import domain.Enrolment;
import domain.Hacker;
import domain.Posicion;

@Service
@Transactional
public class EnrolmentService {

	@Autowired
	private EnrolmentRepository	enrolmentRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	public Enrolment create() {
		final Enrolment enrolment = new Enrolment();

		enrolment.setMoment(new Date());
		enrolment.setBrotherhood(null);
		enrolment.setStatus(0);
		enrolment.setIsOut(0);
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		enrolment.setMember((Hacker) a);
		enrolment.setPosition(new Posicion());
		enrolment.setBrotherhood(new Company());
		return enrolment;
	}

	public Collection<Enrolment> findAll() {
		return this.enrolmentRepository.findAll();
	}

	public Enrolment findOne(final int idEnrolment) {
		final Enrolment res = this.enrolmentRepository.findOne(idEnrolment);

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		if (user.getAuthorities().iterator().next().getAuthority().equals("MEMBER"))
			Assert.isTrue(a.equals(res.getMember()));
		else if (user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"))
			Assert.isTrue(a.equals(res.getBrotherhood()));

		return res;
	}

	public Enrolment save(final Enrolment enrolment) {
		Enrolment res = null;

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		if (enrolment.getId() == 0) {
			enrolment.setMember((Hacker) a);
			enrolment.setMoment(new Date());
		} else {

			if (user.getAuthorities().iterator().next().getAuthority().equals("MEMBER"))
				Assert.isTrue(enrolment.getMember().equals(a));
			else if (user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"))
				Assert.isTrue(enrolment.getBrotherhood().equals(a));

			if (enrolment.getIsOut() == 1 && enrolment.getStatus() == 1) {
				enrolment.setStatus(2);
				enrolment.setEndMoment(new Date());
				Assert.isTrue(enrolment.getIsOut() == 1, "Enrolment.service: Estas fuera de la hermandad");
			} else if (enrolment.getIsOut() == 1) {
				enrolment.setStatus(2);
				Assert.isTrue(enrolment.getIsOut() == 1, "Enrolment.service: Estas fuera de la hermandad");
			}

			if (enrolment.getStatus() == 2)
				enrolment.setIsOut(1);

			Assert.isTrue(enrolment.getBrotherhood() != null, "Enrolment.service: Brotherhood no puede ser null");
			Assert.isTrue(enrolment.getMember() != null, "Enrolment.service: Member no puede ser null");
			Assert.isTrue(enrolment.getPosition() != null, "Enrolment.service: Position no puede ser null");
			Assert.isTrue(enrolment.getStatus() >= 0 && enrolment.getStatus() <= 3, "Enrolment.service: Status debe tener un valor entre 0 y 2");
			Assert.isTrue(enrolment.getIsOut() >= 0 && enrolment.getIsOut() <= 1, "Enrolment.service: Is out debe tener un valor entre 0 y 2");
		}
		res = this.enrolmentRepository.save(enrolment);
		return res;
	}

	//RECONSTRUCT
	public Enrolment reconstruct(final Enrolment enrolment, final BindingResult binding) {
		Enrolment res;

		if (enrolment.getId() == 0) {
			res = enrolment;
			res.setMoment(new Date());
			res.setEndMoment(null);
			res.setStatus(0);
			res.setIsOut(0);
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());
			res.setMember((Hacker) a);
			this.validator.validate(res, binding);

			return res;
		} else {
			final UserAccount user = LoginService.getPrincipal();
			res = this.enrolmentRepository.findOne(enrolment.getId());
			final Enrolment copy = res;
			if (user.getAuthorities().iterator().next().getAuthority().equals("MEMBER")) {

				if (res.getStatus() == 3)
					copy.setStatus(enrolment.getStatus());
				else
					copy.setIsOut(enrolment.getIsOut());
			} else if (user.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD")) {
				copy.setStatus(enrolment.getStatus());
				copy.setPosition(enrolment.getPosition());
			}

			this.validator.validate(copy, binding);
			return copy;

		}

	}
	public Collection<Enrolment> enrolmentByMember(final Integer id) {
		return this.enrolmentRepository.enrolmentByMember(id);
	}

	public Collection<Enrolment> enrolmentByBrotherhood(final Integer id) {
		return this.enrolmentRepository.enrolmentByBrotherhood(id);
	}

	public Collection<Enrolment> enrolmentAcceptedByBrotherhood(final Integer id) {
		return this.enrolmentRepository.enrolmentAcceptedByBrotherhood(id);
	}

	public void cancelEnrolment(final Enrolment enrolment) {
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		Assert.isTrue(a.equals(enrolment.getBrotherhood()));
		enrolment.setStatus(2);
		enrolment.setIsOut(1);
		enrolment.setEndMoment(new Date());

		this.enrolmentRepository.save(enrolment);
	}

}
