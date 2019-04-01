
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ApplicationRepository;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository	applicationRepository;


	//DASHBOARD
	public List<Object[]> getAvgMinMaxDesvAppByHackers() {
		return this.applicationRepository.getAvgMinMaxDesvAppByHackers();
	}

}
