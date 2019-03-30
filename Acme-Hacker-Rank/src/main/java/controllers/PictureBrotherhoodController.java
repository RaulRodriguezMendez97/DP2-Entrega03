
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.BrotherhoodService;
import services.FloatService;
import services.HistoryService;
import services.InceptionRecordService;
import services.PeriodRecordService;
import services.PictureService;
import domain.Actor;
import domain.Company;
import domain.History;
import domain.InceptionRecord;
import domain.Paso;
import domain.PeriodRecord;
import domain.Picture;

@Controller
@RequestMapping("/picture/brotherhood")
public class PictureBrotherhoodController extends AbstractController {

	@Autowired
	private PictureService			pictureService;
	@Autowired
	private FloatService			floatService;
	@Autowired
	private BrotherhoodService		brotherhoodService;
	@Autowired
	private ActorService			actorS;
	@Autowired
	private PeriodRecordService		periodRecordService;
	@Autowired
	private InceptionRecordService	inceptionRecordService;
	@Autowired
	private HistoryService			historyService;


	//Imagenes de una Brotherhood(Hermandad)
	@RequestMapping(value = "/picturesBrotherhood", method = RequestMethod.GET)
	public ModelAndView picturesBrotherhood() {
		final ModelAndView result;
		final Collection<Picture> pictures;
		final UserAccount user = LoginService.getPrincipal();
		final Company br = this.brotherhoodService.brotherhoodUserAccount(user.getId());

		pictures = br.getPictures();

		result = new ModelAndView("picture/picturesBrotherhood");
		result.addObject("pictures", pictures);

		return result;
	}

	//Imagenes de un Float(Paso)
	@RequestMapping(value = "/picturesFloat", method = RequestMethod.GET)
	public ModelAndView picturesFloat(@RequestParam final int floatId) {
		ModelAndView result;
		try {
			final Collection<Picture> pictures;

			final Paso fl = this.floatService.findOne(floatId);
			pictures = fl.getPictures();

			result = new ModelAndView("picture/picturesFloat");
			result.addObject("pictures", pictures);
			result.addObject("paso", fl);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../float/brotherhood/list.do");
		}
		return result;
	}

	//Creacion de una imagen para la hermandad
	@RequestMapping(value = "/createPictureBrotherhood", method = RequestMethod.GET)
	public ModelAndView createPictureBrotherhood() {
		ModelAndView result;
		Picture picture;

		picture = this.pictureService.create();
		result = new ModelAndView("picture/editPictureBrotherhood");
		result.addObject("picture", picture);

		return result;
	}

	//Edicion de una imagne de una brotherhood(hermandad)
	@RequestMapping(value = "/editPictureBrotherhood", method = RequestMethod.GET)
	public ModelAndView editPictureBrotherhood(@RequestParam final int pictureId) {
		ModelAndView result;
		try {
			Picture picture;

			picture = this.pictureService.findOne(pictureId);
			Assert.notNull(picture);
			result = new ModelAndView("picture/editPictureBrotherhood");
			result.addObject("picture", picture);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:picturesBrotherhood.do");
		}
		return result;
	}

	@RequestMapping(value = "/editPictureBrotherhood", method = RequestMethod.POST, params = "save")
	public ModelAndView savePictureBrotherhood(@Valid final Picture picture, final BindingResult binding) {
		ModelAndView result;
		final UserAccount user = LoginService.getPrincipal();
		final Company br = this.brotherhoodService.brotherhoodUserAccount(user.getId());
		if (!binding.hasErrors()) {
			final Picture picSave = this.pictureService.save(picture);
			if (br.getPictures().contains(picture)) {
				br.getPictures().remove(picture);
				br.getPictures().add(picSave);
			} else
				br.getPictures().add(picSave);
			this.brotherhoodService.save(br);
			result = new ModelAndView("redirect:picturesBrotherhood.do");
		} else {
			result = new ModelAndView("picture/editPictureBrotherhood");
			result.addObject("picture", picture);
		}
		return result;
	}

	@RequestMapping(value = "/editPictureBrotherhood", method = RequestMethod.POST, params = "delete")
	public ModelAndView deletePictureBrotherhood(final Picture picture, final BindingResult binding) {
		ModelAndView result;
		final UserAccount user = LoginService.getPrincipal();
		final Company br = this.brotherhoodService.brotherhoodUserAccount(user.getId());

		if (!binding.hasErrors()) {
			br.getPictures().remove(picture);
			this.pictureService.delete(picture);
			result = new ModelAndView("redirect:picturesBrotherhood.do");
		} else {
			result = new ModelAndView("picture/editPictureBrotherhood");
			result.addObject("picture", picture);
		}

		return result;

	}

	//Creacion de una imagen para la hermandad
	@RequestMapping(value = "/createPictureFloat", method = RequestMethod.GET)
	public ModelAndView createPictureFloat(@RequestParam final int floatId) {
		ModelAndView result;
		try {
			Picture picture;
			final Paso fl = this.floatService.findOne(floatId);

			picture = this.pictureService.create();
			result = new ModelAndView("picture/editPictureFloat");
			result.addObject("picture", picture);
			result.addObject("paso", fl);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../float/brotherhood/list.do");
		}
		return result;
	}

	//Edicion de una imagne de un float(paso)
	@RequestMapping(value = "/editPictureFloat", method = RequestMethod.GET)
	public ModelAndView editPictureFloat(@RequestParam final int pictureId, @RequestParam final int floatId) {
		ModelAndView result;
		try {
			Picture picture;
			Paso paso;

			picture = this.pictureService.findOne(pictureId);
			paso = this.floatService.findOne(floatId);
			Assert.notNull(picture);
			result = new ModelAndView("picture/editPictureFloat");
			result.addObject("picture", picture);
			result.addObject("paso", paso);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../float/brotherhood/list.do");
		}

		return result;
	}

	@RequestMapping(value = "/editPictureFloat", method = RequestMethod.POST, params = "save")
	public ModelAndView savePictureFloat(@Valid final Picture picture, final BindingResult binding, @RequestParam final int floatId) {
		ModelAndView result;
		if (!binding.hasErrors()) {
			final Picture picSave = this.pictureService.save(picture);
			final Paso fl = this.floatService.findOne(floatId);
			if (fl.getPictures().contains(picSave)) {
				fl.getPictures().remove(picSave);
				fl.getPictures().add(picSave);
			} else
				fl.getPictures().add(picSave);
			this.floatService.save(fl);
			result = new ModelAndView("redirect:picturesFloat.do?floatId=" + floatId);
		} else {
			final Paso fl = this.floatService.findOne(floatId);
			result = new ModelAndView("picture/editPictureFloat");
			result.addObject("picture", picture);
			result.addObject("paso", fl);
		}
		return result;
	}

	@RequestMapping(value = "/editPictureFloat", method = RequestMethod.POST, params = "delete")
	public ModelAndView deletePictureFloat(final Picture picture, final BindingResult binding, @RequestParam final int floatId) {
		ModelAndView result;

		if (!binding.hasErrors()) {
			final Paso p = this.floatService.findOne(floatId);
			p.getPictures().remove(picture);
			this.pictureService.delete(picture);
			this.floatService.save(p);
			result = new ModelAndView("redirect:picturesFloat.do?floatId=" + floatId);
		} else {
			result = new ModelAndView("picture/editPictureFloat");
			result.addObject("picture", picture);
			result.addObject("paso", this.floatService.findOne(floatId));
		}
		return result;
	}

	//PERIOD RECORD
	@RequestMapping(value = "/picturesPeriodRecord", method = RequestMethod.GET)
	public ModelAndView picturesPeriodRecord(@RequestParam final Integer periodRecordId) {
		ModelAndView result;
		try {
			final Collection<Picture> pictures;

			final PeriodRecord p = this.periodRecordService.findOne(periodRecordId);
			Assert.notNull(p);
			pictures = p.getPictures();

			result = new ModelAndView("picture/picturesPeriodRecord");
			result.addObject("pictures", pictures);
			result.addObject("periodRecord", p);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/createPicturePeriodRecord", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer periodRecordId) {
		ModelAndView result;
		try {
			Picture picture;
			final PeriodRecord p = this.periodRecordService.findOne(periodRecordId);
			Assert.notNull(p);

			picture = this.pictureService.create();
			result = new ModelAndView("picture/editPicturePeriodRecord");
			result.addObject("picture", picture);
			result.addObject("periodRecord", p);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/editPicturePeriodRecord", method = RequestMethod.GET)
	public ModelAndView editPicturePeriodRecord(@RequestParam final Integer pictureId, @RequestParam final Integer periodRecordId) {
		ModelAndView result;
		try {
			Picture picture;
			PeriodRecord periodRecord;

			picture = this.pictureService.findOne(pictureId);
			periodRecord = this.periodRecordService.findOne(periodRecordId);
			Assert.notNull(picture);
			Assert.notNull(periodRecord);

			result = new ModelAndView("picture/editPicturePeriodRecord");
			result.addObject("picture", picture);
			result.addObject("periodRecord", periodRecord);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;
	}

	@RequestMapping(value = "/editPicturePeriodRecord", method = RequestMethod.POST, params = "save")
	public ModelAndView editPicturePeriodRecord(@Valid final Picture picture, final BindingResult binding, @RequestParam final int periodRecordId) {
		ModelAndView result;
		try {
			if (!binding.hasErrors()) {
				final Picture picSave = this.pictureService.save(picture);
				final PeriodRecord periodRecord = this.periodRecordService.findOne(periodRecordId);
				if (periodRecord.getPictures().contains(picSave)) {
					periodRecord.getPictures().remove(picSave);
					periodRecord.getPictures().add(picSave);
				} else
					periodRecord.getPictures().add(picSave);
				this.periodRecordService.save(periodRecord);
				result = new ModelAndView("redirect:picturesPeriodRecord.do?periodRecordId=" + periodRecordId);
			} else {
				final PeriodRecord periodRecord = this.periodRecordService.findOne(periodRecordId);
				result = new ModelAndView("picture/editPicturePeriodRecord");
				result.addObject("picture", picture);
				result.addObject("periodRecord", periodRecord);
			}
		} catch (final Exception e) {
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorS.getActorByUserAccount(user.getId());
			final History history = this.historyService.getHistotyByPeriodRecord(periodRecordId);
			if (a.equals(history.getBrotherhood())) {

				final PeriodRecord periodRecord = this.periodRecordService.findOne(periodRecordId);
				result = new ModelAndView("picture/editPicturePeriodRecord");
				result.addObject("picture", picture);
				result.addObject("periodRecord", periodRecord);
				result.addObject("exception", e);
			} else
				result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/editPicturePeriodRecord", method = RequestMethod.POST, params = "delete")
	public ModelAndView deletePicturePeriodRecord(final Picture picture, final BindingResult binding, @RequestParam final int periodRecordId) {
		ModelAndView result;
		try {
			if (!binding.hasErrors()) {
				final PeriodRecord p = this.periodRecordService.findOne(periodRecordId);
				p.getPictures().remove(picture);
				this.periodRecordService.save(p);
				this.pictureService.delete(picture);

				result = new ModelAndView("redirect:picturesPeriodRecord.do?periodRecordId=" + periodRecordId);
			} else {
				result = new ModelAndView("picture/editPicturePeriodRecord");
				result.addObject("picture", picture);
				result.addObject("periodRecord", this.periodRecordService.findOne(periodRecordId));
			}
		} catch (final Exception e) {
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorS.getActorByUserAccount(user.getId());
			final History history = this.historyService.getHistotyByPeriodRecord(periodRecordId);
			if (a.equals(history.getBrotherhood())) {

				final PeriodRecord periodRecord = this.periodRecordService.findOne(periodRecordId);
				result = new ModelAndView("picture/editPicturePeriodRecord");
				result.addObject("picture", picture);
				result.addObject("periodRecord", periodRecord);
				result.addObject("exception", e);
			} else
				result = new ModelAndView("redirect:../../");
		}
		return result;
	}
	//------------------------------- InceptionRecord Pictures -----------------------

	//Imagenes de un InceptionRecord
	@RequestMapping(value = "/picturesInceptionRecord", method = RequestMethod.GET)
	public ModelAndView picturesInceptionRecord(@RequestParam final int inceptionRecordId, @RequestParam final int historyId) {
		ModelAndView result;
		try {
			final Collection<Picture> pictures;

			final InceptionRecord inceptionRecord = this.inceptionRecordService.findOneIfItIsMine(inceptionRecordId);
			pictures = inceptionRecord.getPictures();

			result = new ModelAndView("picture/inceptionRecord");
			result.addObject("pictures", pictures);
			result.addObject("inceptionRecord", inceptionRecord);
			result.addObject("historyId", historyId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../inception-record/brotherhood/show.do?historyId=" + historyId);
		}
		return result;
	}

	//Creacion de una imagen para InceptionRecord
	@RequestMapping(value = "/createPictureInceptionRecord", method = RequestMethod.GET)
	public ModelAndView createPictureInceptionRecord(@RequestParam final int inceptionRecordId, @RequestParam final int historyId) {
		ModelAndView result;
		try {
			Picture picture;
			final InceptionRecord inceptionRecord = this.inceptionRecordService.findOneIfItIsMine(inceptionRecordId);

			picture = this.pictureService.create();
			result = new ModelAndView("picture/editPictureInceptionRecord");
			result.addObject("picture", picture);
			result.addObject("inceptionRecord", inceptionRecord);
			result.addObject("historyId", historyId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../inception-record/brotherhood/show.do?historyId=" + historyId);
		}
		return result;
	}

	@RequestMapping(value = "/editPictureInceptionRecord", method = RequestMethod.GET)
	public ModelAndView editPictureInceptionRecord(@RequestParam final int pictureId, @RequestParam final int inceptionRecordId, @RequestParam final int historyId) {
		ModelAndView result;
		try {
			Picture picture;
			InceptionRecord inceptionRecord;

			picture = this.pictureService.findMeAPicture(pictureId);
			inceptionRecord = this.inceptionRecordService.findOneIfItIsMine(inceptionRecordId);
			Assert.notNull(picture);
			result = new ModelAndView("picture/editPictureInceptionRecord");
			result.addObject("picture", picture);
			result.addObject("inceptionRecord", inceptionRecord);
			result.addObject("historyId", historyId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../inception-record/brotherhood/show.do?historyId=" + historyId);
		}

		return result;
	}

	@RequestMapping(value = "/editPictureInceptionRecord", method = RequestMethod.POST, params = "save")
	public ModelAndView savePictureInceptionRecord(@Valid final Picture picture, final BindingResult binding, @RequestParam final int inceptionRecordId, @RequestParam final int historyId) {
		ModelAndView result;
		try {
			if (!binding.hasErrors()) {
				final Picture picSave = this.pictureService.save(picture);
				final InceptionRecord inceptionRecord = this.inceptionRecordService.findOne(inceptionRecordId);
				if (inceptionRecord.getPictures().contains(picSave)) {
					inceptionRecord.getPictures().remove(picSave);
					inceptionRecord.getPictures().add(picSave);
				} else
					inceptionRecord.getPictures().add(picSave);
				this.inceptionRecordService.save(inceptionRecord);
				result = new ModelAndView("redirect:picturesInceptionRecord.do?inceptionRecordId=" + inceptionRecordId + "&historyId=" + historyId);
			} else {
				final InceptionRecord inceptionRecord = this.inceptionRecordService.findOne(inceptionRecordId);
				result = new ModelAndView("picture/editPictureInceptionRecord");
				result.addObject("picture", picture);
				result.addObject("inceptionRecord", inceptionRecord);
				result.addObject("historyId", historyId);
			}
		} catch (final Exception e) {
			final History history = this.historyService.findOne(historyId);
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorS.getActorByUserAccount(user.getId());
			if (a.equals(history.getBrotherhood()))
				result = new ModelAndView("redirect:../../inception-record/brotherhood/show.do?historyId=" + historyId);
			else
				result = new ModelAndView("redirect:../../");
		}
		return result;
	}
	@RequestMapping(value = "/editPictureInceptionRecord", method = RequestMethod.POST, params = "delete")
	public ModelAndView deletePictureInceptionRecord(final Picture picture, final BindingResult binding, @RequestParam final int inceptionRecordId, @RequestParam final int historyId) {
		ModelAndView result;
		try {
			if (!binding.hasErrors()) {
				final InceptionRecord inceptionRecord = this.inceptionRecordService.findOneIfItIsMine(inceptionRecordId);
				inceptionRecord.getPictures().remove(picture);
				this.inceptionRecordService.save(inceptionRecord);
				this.pictureService.delete(picture);
				result = new ModelAndView("redirect:picturesInceptionRecord.do?inceptionRecordId=" + inceptionRecordId + "&historyId=" + historyId);
			} else {
				result = new ModelAndView("picture/editPictureFloat");
				result.addObject("picture", picture);
				result.addObject("historyId", historyId);
				result.addObject("inceptionRecord", this.inceptionRecordService.findOne(inceptionRecordId));
			}
		} catch (final Exception e) {
			final History history = this.historyService.findOne(historyId);
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorS.getActorByUserAccount(user.getId());
			if (a.equals(history.getBrotherhood()))
				result = new ModelAndView("redirect:../../inception-record/brotherhood/show.do?historyId=" + historyId);
			else
				result = new ModelAndView("redirect:../../");
		}
		return result;
	}
}
