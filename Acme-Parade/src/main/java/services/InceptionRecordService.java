
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.InceptionRecordRepository;
import security.LoginService;
import domain.Brotherhood;
import domain.History;
import domain.InceptionRecord;
import domain.Picture;

@Service
@Transactional
public class InceptionRecordService {

	@Autowired
	private InceptionRecordRepository	inceptionRecordRepository;
	@Autowired
	private Validator					validator;
	@Autowired
	private HistoryService				historyService;
	@Autowired
	private BrotherhoodService			brotherhoodService;


	public InceptionRecord create() {
		final InceptionRecord inceptionRecord = new InceptionRecord();
		inceptionRecord.setTitle("");
		inceptionRecord.setDescription("");
		inceptionRecord.setPictures(new HashSet<Picture>());
		return inceptionRecord;
	}
	public InceptionRecord findOne(final int inceptionRecordId) {
		return this.inceptionRecordRepository.findOne(inceptionRecordId);
	}

	public InceptionRecord findOneIfItIsMine(final int inceptionRecordId) {
		final InceptionRecord inceptionRecord = this.findOne(inceptionRecordId);
		Assert.isTrue(this.getAllMyInceptionRecords().contains(inceptionRecord));
		return inceptionRecord;
	}

	public InceptionRecord findOneThisBrotherhood(final int inceptionRecordId, final Brotherhood brotherhood) {
		final InceptionRecord inceptionRecord = this.findOne(inceptionRecordId);
		Assert.isTrue(this.getAllInceptionRecordsBrotherhood(brotherhood).contains(inceptionRecord));
		return inceptionRecord;
	}

	public Collection<InceptionRecord> findAll() {
		return this.inceptionRecordRepository.findAll();
	}
	public InceptionRecord save(final InceptionRecord inceptionRecord) {
		final InceptionRecord savedInceptionRecord;
		Assert.isTrue(inceptionRecord.getTitle() != null && inceptionRecord.getTitle() != "");
		Assert.isTrue(inceptionRecord.getDescription() != null && inceptionRecord.getDescription() != "");
		savedInceptionRecord = this.inceptionRecordRepository.save(inceptionRecord);
		if (inceptionRecord.getId() == 0) {
			final History history = this.historyService.create();
			history.setBrotherhood(this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()));
			history.setInceptionRecord(savedInceptionRecord);
			this.historyService.save(history);
		}
		return savedInceptionRecord;
	}
	public void delete(final InceptionRecord inceptionRecord) {
		final History history = this.historyService.getHistoryByInceptionRecord(inceptionRecord.getId());
		this.historyService.delete(history);
		//this.inceptionRecordRepository.delete(inceptionRecord);
	}
	//RECONSTRUCT
	public InceptionRecord reconstruct(final InceptionRecord inceptionRecord, final BindingResult binding) {
		InceptionRecord res;

		if (inceptionRecord.getId() == 0) {
			res = inceptionRecord;
			res.setPictures(new HashSet<Picture>());
			this.validator.validate(res, binding);
		} else {
			res = this.inceptionRecordRepository.findOne(inceptionRecord.getId());
			final InceptionRecord i = new InceptionRecord();
			i.setId(res.getId());
			i.setVersion(res.getVersion());
			i.setTitle(inceptionRecord.getTitle());
			i.setDescription(inceptionRecord.getDescription());
			i.setPictures(res.getPictures());
			this.validator.validate(i, binding);
			res = i;
		}
		return res;
	}
	public Collection<InceptionRecord> getAllMyInceptionRecords() {
		final int userAccountId = LoginService.getPrincipal().getId();
		return this.inceptionRecordRepository.allMyInceptionRecords(userAccountId);
	}
	public Collection<InceptionRecord> getAllInceptionRecordsBrotherhood(final Brotherhood brotherhood) {

		return this.inceptionRecordRepository.allMyInceptionRecords(brotherhood.getUserAccount().getId());
	}
}
