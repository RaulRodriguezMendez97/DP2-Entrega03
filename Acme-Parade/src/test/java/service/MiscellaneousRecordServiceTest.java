
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.HistoryService;
import services.MiscellaneousRecordService;
import utilities.AbstractTest;
import domain.History;
import domain.MiscellaneousRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;
	@Autowired
	private HistoryService				historyService;


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
	 * Una hermandad intenta modificar un miscellaneousRecord que no le pertenece.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo finOne -> 7
	 * Sentencias metodo save -> 8
	 * Sentencias totales -> 15 (100%)
	 * Sentence covegare positive test -> 15 (100%)
	 * Sentence coverage negative test -> 6 (40%)
	 * 
	 * 
	 * d) Data coverage:-
	 */

	@Test
	public void miscellaneousRecordServiceEdit() {
		final Object testingData[][] = {
			{//Positive test
				"brotherhood", super.getEntityId("miscellaneousRecord1"), "Nuevo title", "Nueva description", null
			}, {//Negative test: Case 2
				"brotherhood1", super.getEntityId("miscellaneousRecord1"), "Nuevo title", "Nueva description", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.miscellaneousRecordServiceTemplate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void miscellaneousRecordServiceTemplate(final String authority, final int miscellaneousRecordId, final String title, final String description, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
			miscellaneousRecord.setTitle(title);
			miscellaneousRecord.setDescription(description);
			this.miscellaneousRecordService.save(miscellaneousRecord);

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
	 * Una hermandad crea un miscellaneousRecord con los atributos incorrectos.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo create -> 4
	 * Sentencias metodo save -> 8
	 * Sentencias totales -> 12 (100%)
	 * Sentence covegare positive test -> 11 (91,6%)
	 * Sentence coverage negative test -> 10 (83,3%)
	 * 
	 * d) Data coverage:100% (2 atributos incorrectos/ 2 atributos totales)
	 */

	@Test
	public void CreateMiscellaneousRecordService() {
		final Object testingData[][] = {
			{//Positive test
				"Nuevo title", "Description", null
			}, {//Negative test: los atributos "title" y "description" no son válidos
				"", "", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreatePeriodRecordServiceTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void CreatePeriodRecordServiceTemplate(final String title, final String description, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("brotherhood");

			final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create();
			miscellaneousRecord.setTitle(title);
			miscellaneousRecord.setDescription(description);

			this.miscellaneousRecordService.save(miscellaneousRecord);

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
	 * Una hermandad intenta mostrar un miscellaneousRecor que no le pertenece.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo findOne -> 7
	 * Sentencias totales -> 7 (100%)
	 * Sentence covegare positive test -> 7 (100%)
	 * Sentence coverage negative test -> 6 (85,7%)
	 * 
	 * d) Data coverage:-
	 */

	@Test
	public void ShowMiscellaneousRecordService() {
		final Object testingData[][] = {
			{//Positive test
				"brotherhood", super.getEntityId("miscellaneousRecord1"), null
			}, {//Negative test
				"brotherhood1", super.getEntityId("miscellaneousRecord1"), IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.ShowMiscellaneousRecordServiceTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void ShowMiscellaneousRecordServiceTemplate(final String authority, final int miscellaneousRecordId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
			Assert.notNull(miscellaneousRecord);

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
	 * Una hermandad intenta eliminar un miscellaneousRecor que no le pertenece.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo findOne -> 7
	 * Sentencias metodo delete -> 9
	 * Sentencias metodo getHistoryByMiscellaneousRecord -> 1
	 * Sentencias totales -> 17 (100%)
	 * Sentence covegare positive test -> 17 (100%)
	 * Sentence coverage negative test -> 6 (35,2%)
	 * 
	 * d) Data coverage:-
	 */

	@Test
	public void DeleteMiscellaneousRecordService() {
		final Object testingData[][] = {
			{//Positive test
				"brotherhood", super.getEntityId("miscellaneousRecord1"), null
			}, {//Negative test
				"brotherhood1", super.getEntityId("miscellaneousRecord1"), NullPointerException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.DeleteMiscellaneousRecordServiceTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void DeleteMiscellaneousRecordServiceTemplate(final String authority, final int miscellaneousRecordId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
			Assert.notNull(miscellaneousRecord);
			final History h = this.historyService.getHistotyByMiscellaneousRecord(miscellaneousRecord.getId());
			this.miscellaneousRecordService.delete(miscellaneousRecord, h.getId());

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
