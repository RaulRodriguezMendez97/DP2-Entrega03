
package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PosicionRepository;
import security.LoginService;
import security.UserAccount;
import domain.Posicion;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PosicionRepository	positionRepository;


	public Posicion create() {

		final Posicion posicion = new Posicion();
		posicion.setSpanishName("");
		posicion.setName("");

		return posicion;

	}

	public Collection<Posicion> findAll() {
		return this.positionRepository.findAll();
	}

	public Posicion findOne(final Integer id) {
		final Posicion res = this.positionRepository.findOne(id);
		return res;
	}

	public Posicion save(final Posicion p) {

		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Posicion res;

		Assert.notNull(p.getSpanishName() != null && p.getSpanishName() != "", "No debe tener un idioma vacio");
		Assert.notNull(p.getName() != null && p.getName() != "", "No debe tener un nombre vacio");

		res = this.positionRepository.save(p);

		return res;

	}

	public Collection<String> getUsedNames() {
		return this.positionRepository.getUsedNames();
	}

	public void delete(final Posicion p) {

		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		final Collection<String> usedNames = this.getUsedNames();
		Assert.isTrue(!usedNames.contains(p.getName()));
		this.positionRepository.delete(p);

	}

	public Map<String, Double> computeStatistics() {
		final UserAccount userLoged = LoginService.getPrincipal();
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
		Map<String, Double> map;
		Double total, countPresident, countVicePresident, countSecretaty, countTreasurer, countHistorian, countFundraiser, countOfficer;

		total = this.positionRepository.countTotal();
		countPresident = this.positionRepository.countPresident();
		countVicePresident = this.positionRepository.countVicePresident();
		countSecretaty = this.positionRepository.countSecretaty();
		countTreasurer = this.positionRepository.countTreasurer();
		countHistorian = this.positionRepository.countHistorian();
		countFundraiser = this.positionRepository.countFundraiser();
		countOfficer = this.positionRepository.countOfficer();

		map = new HashMap<String, Double>();

		map.put("total", total);
		map.put("countPresident", countPresident);
		map.put("countVicePresident", countVicePresident);
		map.put("countSecretaty", countSecretaty);
		map.put("countTreasurer", countTreasurer);
		map.put("countHistorian", countHistorian);
		map.put("countFundraiser", countFundraiser);
		map.put("countOfficer", countOfficer);

		return map;
	}
}
