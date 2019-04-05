
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import security.LoginService;
import domain.Company;
import domain.Finder;
import domain.Position;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository	finderRepository;

	@Autowired
	private Validator			validator;


	public Finder create() {
		final Finder res = new Finder();
		res.setDeadLine(new Date());
		res.setMaxSalary(0.);
		res.setKeyWord("");
		res.setMinSalary(0.);
		res.setPositions(new HashSet<Position>());
		return res;

	}

	public Collection<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder findOne() {
		return this.finderRepository.getMyFinder(this.hackerUserAccountId());
	}

	public Finder save(final Finder f) {
		Finder saved;
		if (f.getId() == 0) {
			Assert.isTrue(f.getKeyWord() == "" && f.getDeadLine() != null && f.getMaxSalary() >= 0. && f.getMinSalary() >= 0. && f.getPositions().isEmpty());
			saved = this.finderRepository.save(f);
		} else {
			final Finder savedFinder = this.findOne();
			savedFinder.setDeadLine(f.getDeadLine());
			savedFinder.setMaxSalary(f.getMaxSalary());
			savedFinder.setKeyWord(f.getKeyWord());
			savedFinder.setMinSalary(f.getMinSalary());
			savedFinder.setPositions(f.getPositions());
			saved = this.finderRepository.save(savedFinder);
		}
		return saved;
	}

	//RECONSTRUCT
	public Finder reconstruct(final Finder finder, final BindingResult binding) {
		final Finder res;

		res = this.findOne();
		final Finder copy = res;
		copy.setKeyWord(finder.getKeyWord());
		copy.setDeadLine(finder.getDeadLine());
		copy.setMaxSalary(finder.getMaxSalary());
		copy.setMinSalary(finder.getMinSalary());
		if (finder.getMinSalary() == null)
			finder.setMinSalary(0.);
		if (finder.getMaxSalary() == null)
			finder.setMaxSalary(Double.MAX_VALUE);
		String fecha;
		if (finder.getDeadLine() == null)
			fecha = "";
		else
			fecha = this.getStringToDate(finder.getDeadLine());
		final Collection<Position> c = this.finderRepository.filterPositions2(finder.getKeyWord(), finder.getMinSalary(), finder.getMaxSalary(), fecha);
		copy.setPositions(c);
		this.validator.validate(copy, binding);
		return copy;

	}
	//TICKER
	public static String generarTicker(final Company company) {
		final int tam = 4;

		final String d = company.getNameCompany().substring(0, 3);

		String ticker = "-";
		final String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

		for (int i = 0; i < tam; i++) {
			final Integer random = (int) (Math.floor(Math.random() * a.length()) % a.length());
			ticker = ticker + a.charAt(random);
		}

		return d + ticker;

	}

	private int hackerUserAccountId() {
		return LoginService.getPrincipal().getId();
	}

	private String getStringToDate(final Date date) {
		String cadena = "";
		cadena += date.toString().substring(24, 28);

		final Integer month = date.getMonth() + 1;
		if (month < 10)
			cadena += "-" + "0" + month;
		else
			cadena += "-" + month;

		if (date.getDate() < 10)
			cadena += "-" + "0" + date.getDate();
		else
			cadena += "-" + date.getDate();

		return cadena;
	}

	public List<Finder> getFinderByPosition(final Integer id) {
		return this.finderRepository.getFinderByPosition(id);
	}

	public void clearResults() {
		final Finder finder = this.findOne();
		String fecha;
		final Date a = finder.getDeadLine();
		if (finder.getDeadLine() == null)
			fecha = "";
		else
			fecha = this.getStringToDate(new Date(a.getYear(), a.getMonth(), a.getDate()));
		final Collection<Position> c = this.finderRepository.filterPositions2(finder.getKeyWord(), finder.getMinSalary(), finder.getMaxSalary(), fecha);
		finder.setPositions(c);
		this.save(finder);
	}
}
