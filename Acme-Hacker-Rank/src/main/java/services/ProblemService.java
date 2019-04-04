
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ProblemRepository;
import domain.Application;
import domain.Problem;

@Transactional
@Service
public class ProblemService {

	@Autowired
	private ProblemRepository	problemRepository;


	public Problem create() {
		final Problem p = new Problem();

		p.setTitle("");
		p.setStatement("");
		p.setHint("");
		p.setAttachment(new HashSet<String>());
		p.setDraftMode(1);
		p.setApplications(new HashSet<Application>());

		return p;
	}

	public Collection<Problem> findAll() {
		return this.problemRepository.findAll();
	}

	public Problem findOne(final Integer id) {
		return this.problemRepository.findOne(id);
	}

}
