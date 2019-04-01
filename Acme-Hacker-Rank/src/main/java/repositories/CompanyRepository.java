
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	@Query("select c from Company c where c.userAccount.id = ?1")
	public Company companyUserAccount(Integer id);

	//DASHBOARD
	@Query("select c.nameCompany from Company c where (select max(1.0*(select count(p.company) from Position p where p.company.id =x.id)) from Company x)=(1.0*(select count(p.company) from Position p where p.company.id=c.id))")
	public List<String> getCompaniesWithMorePositions();
}
