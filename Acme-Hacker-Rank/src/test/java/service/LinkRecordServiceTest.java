
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.BrotherhoodService;
import services.HistoryService;
import services.LinkRecordService;
import utilities.AbstractTest;
import domain.Companie;
import domain.History;
import domain.LinkRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class LinkRecordServiceTest extends AbstractTest {

	@Autowired
	private LinkRecordService	linkRecordService;
	@Autowired
	private HistoryService		historyService;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	/*
	 * a) Brotherhoods can manage their histories.
	 * A history is composed of one inception record,
	 * ze-ro or more period records, zero or more legal records
	 * , zero or more link records, and zero or more miscellaneous records.
	 * For every record, the system must store its title and
	 * a piece of text that describes it.
	 * For every inception record, it must also store some photos;
	 * for every period record, it must also store a start year, an end year
	 * , and some photos; for every legal record, it must also store a legal name,
	 * a VAT number, and the applicable laws; for every link record,
	 * it must also store a link to another brotherhood with which the original brotherhood is linked.
	 * 
	 * b) Broken bussines rule:
	 * Una hermandad intenta modificar un linkRecord que no es suyo.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo finOne -> 7
	 * Sentencias metodo save -> 8
	 * Sentencias totales -> 15 (100%)
	 * Sentence covegare positive test -> 15 (100%)
	 * Sentence coverage negative test -> 6 (40%)
	 * 
	 * d) Data coverage:-
	 */

	@Test
	public void linkRecordServiceEdit() {
		final Object testingData[][] = {
			{//Positive test
				"brotherhood", super.getEntityId("linkRecord1"), "Nuevo title", "Nueva description", null
			}, {//Negative test
				"brotherhood1", super.getEntityId("linkRecord1"), "Nuevo title", "Nueva description", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.linkRecordServiceTemplateEdit((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void linkRecordServiceTemplateEdit(final String authority, final int linkRecordId, final String title, final String description, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final LinkRecord linkRecord = this.linkRecordService.findOne(linkRecordId);
			linkRecord.setTitle(title);
			linkRecord.setDescription(description);
			this.linkRecordService.save(linkRecord);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Brotherhoods can manage their histories.
	 * A history is composed of one inception record,
	 * ze-ro or more period records, zero or more legal records
	 * , zero or more link records, and zero or more miscellaneous records.
	 * For every record, the system must store its title and
	 * a piece of text that describes it.
	 * For every inception record, it must also store some photos;
	 * for every period record, it must also store a start year, an end year
	 * , and some photos; for every legal record, it must also store a legal name,
	 * a VAT number, and the applicable laws; for every link record,
	 * it must also store a link to another brotherhood with which the original brotherhood is linked.
	 * 
	 * b) Broken bussines rule:
	 * Una hermandad intenta mostrar un linkRecord que no pertenece a dicha hermandad.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo finOne -> 7
	 * Sentencias totales -> 7 (100%)
	 * Sentence covegare positive test -> 7 (100%)
	 * Sentence coverage negative test -> 6 (85,7%)
	 * 
	 * d) Data coverage:-
	 */

	@Test
	public void linkRecordServiceShow() {
		final Object testingData[][] = {
			{//Positive test
				"brotherhood", super.getEntityId("linkRecord1"), null
			}, {//Negative test
				"brotherhood1", super.getEntityId("linkRecord1"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.linkRecordServiceTemplateShow((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void linkRecordServiceTemplateShow(final String authority, final int linkRecordId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final LinkRecord linkRecord = this.linkRecordService.findOne(linkRecordId);
			Assert.notNull(linkRecord);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Brotherhoods can manage their histories.
	 * A history is composed of one inception record,
	 * ze-ro or more period records, zero or more legal records
	 * , zero or more link records, and zero or more miscellaneous records.
	 * For every record, the system must store its title and
	 * a piece of text that describes it.
	 * For every inception record, it must also store some photos;
	 * for every period record, it must also store a start year, an end year
	 * , and some photos; for every legal record, it must also store a legal name,
	 * a VAT number, and the applicable laws; for every link record,
	 * it must also store a link to another brotherhood with which the original brotherhood is linked.
	 * 
	 * b) Broken bussines rule:
	 * Una hermandad intenta eliminar un linkRecord que no pertenece a dicha hermandad.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo finOne -> 7
	 * Sentecias metodo getHistoryByLinkRecord -> 1
	 * Sentencias metodo delete -> 10
	 * Sentencias totales -> 18 (100%)
	 * Sentence covegare positive test -> 18 (100%)
	 * Sentence coverage negative test -> 6 (33,3%)
	 * 
	 * d) Data coverage:-
	 */

	@Test
	public void linkRecordServiceDelete() {
		final Object testingData[][] = {
			{//Positive test
				"brotherhood", super.getEntityId("linkRecord1"), null
			}, {//Negative test
				"brotherhood1", super.getEntityId("linkRecord1"), NullPointerException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.linkRecordServiceTemplateDelete((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void linkRecordServiceTemplateDelete(final String authority, final int linkRecordId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final LinkRecord linkRecord = this.linkRecordService.findOne(linkRecordId);
			Assert.notNull(linkRecord);
			final History h = this.historyService.getHistotyByLinkRecord(linkRecord.getId());
			this.linkRecordService.delete(linkRecord, h.getId());

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Brotherhoods can manage their histories.
	 * A history is composed of one inception record,
	 * ze-ro or more period records, zero or more legal records
	 * , zero or more link records, and zero or more miscellaneous records.
	 * For every record, the system must store its title and
	 * a piece of text that describes it.
	 * For every inception record, it must also store some photos;
	 * for every period record, it must also store a start year, an end year
	 * , and some photos; for every legal record, it must also store a legal name,
	 * a VAT number, and the applicable laws; for every link record,
	 * it must also store a link to another brotherhood with which the original brotherhood is linked.
	 * 
	 * b) Broken bussines rule:
	 * Una hermandad intenta crear un linkRecord con el atributo "title" vacio.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo findOne(Brotherhood) -> 1
	 * Sentencias metodo create -> 5
	 * Sentecias metodo save -> 8
	 * Sentencias totales -> 14 (100%)
	 * Sentence covegare positive test -> 13 (92,8%)
	 * Sentence coverage negative test -> 12 (85,7%)
	 * 
	 * d) Data coverage:33,33 (1 atributo incorrecto/3Atributos)
	 */

	@Test
	public void linkRecordServiceCreate() {
		final Object testingData[][] = {
			{//Positive test
				"Nuevo title", "Nueva description", super.getEntityId("brotherhood2"), null
			}, {//Negative test
				"", "Nueva description", super.getEntityId("brotherhood2"), IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.linkRecordServiceTemplateCreate((String) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void linkRecordServiceTemplateCreate(final String title, final String description, final int brotherhoodId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("brotherhood");

			final LinkRecord linkRecord = this.linkRecordService.create();
			final Companie brotherhood = this.brotherhoodService.findOne(brotherhoodId);
			linkRecord.setTitle(title);
			linkRecord.setDescription(description);
			linkRecord.setBrotherhood(brotherhood);
			this.linkRecordService.save(linkRecord);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
