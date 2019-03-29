
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.HistoryService;
import services.PeriodRecordService;
import utilities.AbstractTest;
import domain.History;
import domain.PeriodRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PeriodRecordServiceTest extends AbstractTest {

	@Autowired
	private PeriodRecordService	periodRecordService;

	@Autowired
	private HistoryService		historyService;


	/*
	 * a) Requeriment: An actor who is authenticated as a brotherhood must be able to:
	 * Manage their history, which includes listing, displaying, creating, updating,
	 * and deleting its records.
	 * 
	 * b) Broken bussines rule:
	 * Case 2 and 3: se intenta crear un period record violando una atributo
	 * 
	 * c) Sentence coverage: 87%
	 * 
	 * d) Data coverage: 12.5% (1 atributo incorrecto/8 atributos)
	 */

	@Test
	public void CreatePeriodRecordService() {
		final Object testingData[][] = {
			{//Positive test
				"Nuevo title", "Description", 2020, 2020, null
			}, {//Negative test: startYear no válido
				"Nuevo title", "Description", 1, 2020, IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreatePeriodRecordServiceTemplate((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void CreatePeriodRecordServiceTemplate(final String title, final String description, final Integer startYear, final Integer endYear, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("brotherhood");

			final PeriodRecord p = this.periodRecordService.create();
			p.setTitle(title);
			p.setDescription(description);
			p.setStarYear(startYear);
			p.setEndYear(endYear);

			this.periodRecordService.save(p);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: An actor who is authenticated as a brotherhood must be able to:
	 * Manage their history, which includes listing, displaying, creating, updating,
	 * and deleting its records.
	 * 
	 * b) Broken bussines rule:
	 * Case 2 and 3: Un actor que no es la hermandad a la que
	 * pertenece el period record, intenta modificarlo
	 * 
	 * c) Sentence coverage: 80% = (95% + 65%)/2
	 * 
	 * d) Data coverage: 45%
	 */

	@Test
	public void EditPeriodRecordService() {
		final Object testingData[][] = {
			{//Positive test
				"brotherhood", super.getEntityId("periodRecord1"), "Nuevo title", null
			}, {//Negative test: Case 2
				"brotherhood1", super.getEntityId("periodRecord1"), "Nuevo title", IllegalArgumentException.class
			}, {//Negative test: Case 3
				"member", super.getEntityId("periodRecord1"), "Nuevo title", IllegalArgumentException.class,
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.PeriodRecordServiceTemplate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void PeriodRecordServiceTemplate(final String authority, final int id, final String title, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final PeriodRecord p = this.periodRecordService.findOneWithoutCredentials(id);
			p.setTitle(title);
			this.periodRecordService.save(p);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: An actor who is authenticated as a brotherhood must be able to:
	 * Manage their history, which includes listing, displaying, creating, updating,
	 * and deleting its records.
	 * 
	 * b) Broken bussines rule:
	 * Case 2 and 3: Un actor que no es la hermandad a la que
	 * pertenece el period record, intenta obtenerlo
	 * 
	 * c) Sentence coverage: 85% = (100% + 71%)/2
	 * 
	 * d) Data coverage: -
	 */

	@Test
	public void ShowPeriodRecordService() {
		final Object testingData[][] = {
			{//Positive test
				"brotherhood", super.getEntityId("periodRecord1"), null
			}, {//Negative test: Case 2
				"brotherhood1", super.getEntityId("periodRecord1"), IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.ShowPeriodRecordServiceTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void ShowPeriodRecordServiceTemplate(final String authority, final int id, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final PeriodRecord p = this.periodRecordService.findOne(id);
			Assert.notNull(p);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: An actor who is authenticated as a brotherhood must be able to:
	 * Manage their history, which includes listing, displaying, creating, updating,
	 * and deleting its records.
	 * 
	 * b) Broken bussines rule:
	 * Case 2 and 3: Un actor que no es la hermandad a la que
	 * pertenece el period record, intenta borrarlo
	 * 
	 * c) Sentence coverage: 77.5% = (100+55)/2
	 * 
	 * d) Data coverage: -
	 */

	@Test
	public void DeletePeriodRecordService() {
		final Object testingData[][] = {
			{//Positive test
				"brotherhood", super.getEntityId("periodRecord1"), null
			}, {//Negative test
				"brotherhood1", super.getEntityId("periodRecord1"), NullPointerException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.DeletePeriodRecordServiceTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void DeletePeriodRecordServiceTemplate(final String authority, final int id, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final PeriodRecord p = this.periodRecordService.findOne(id);
			Assert.notNull(p);
			final History h = this.historyService.getHistotyByPeriodRecord(p.getId());
			this.periodRecordService.delete(p, h.getId());

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
