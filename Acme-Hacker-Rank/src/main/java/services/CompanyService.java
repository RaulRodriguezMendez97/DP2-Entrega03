
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CompanyRepository;
import domain.Company;

@Service
@Transactional
public class CompanyService {

	@Autowired
	private CompanyRepository	companyRepository;


	public Collection<Company> findAll() {
		return this.companyRepository.findAll();
	}

}
