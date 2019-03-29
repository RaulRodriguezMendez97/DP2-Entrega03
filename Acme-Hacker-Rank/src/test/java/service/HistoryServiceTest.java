
package service;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.LoginService;
import services.BrotherhoodService;
import services.HistoryService;
import services.InceptionRecordService;
import utilities.AbstractTest;
import domain.History;
import domain.InceptionRecord;
import domain.LegalRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HistoryServiceTest extends AbstractTest {

	@Autowired
	private HistoryService			historyService;
	@Autowired
	private InceptionRecordService	inceptionRecordService;
	@Autowired
	private BrotherhoodService		brotherhoodService;


	@Test
	public void HistoryService() {

		/*
		 * a) Requeriment: An actor who is authenticated as a brotherhood must be able to:
		 * Manage their history, which includes listing, displaying, creating, updating,
		 * and deleting its records.
		 * 
		 * b) Broken bussines rule:
		 * Case 2 and 3: Un actor que no es la hermandad a la que
		 * pertenece el history, intenta acceder a ello y modificarlo
		 * 
		 * c) Sentence coverage: 90%
		 * 
		 * d) Data coverage: 60%
		 */
		final Object testingData[][] = {
			{//Positive test
				"brotherhood", super.getEntityId("history1"), null
			}, {//Negative test: Case 2
				"brotherhood1", super.getEntityId("history1"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.HistoryServiceTemplateSave((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);

		/*
		 * a) Requeriment: An actor who is authenticated as a brotherhood must be able to:
		 * Manage their history, which includes listing, displaying, creating, updating,
		 * and deleting its records.
		 * 
		 * b) Broken bussines rule:
		 * Case 2 and 3: Un actor que no es la hermandad a la que
		 * pertenece el inpcetion record, intenta acceder a ello y modificarlo
		 * 
		 * c) Sentence coverage: 90%
		 * 
		 * d) Data coverage: 53%
		 */
		final Object testingData2[][] = {
			{//Positive test
				"brotherhood", super.getEntityId("inceptionRecord1"), null
			}, {//Negative test: Case 2
				"brotherhood", -1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData2.length; i++)
			this.HistoryServiceTemplateCreate((String) testingData2[i][0], (int) testingData2[i][1], (Class<?>) testingData2[i][2]);
	}
	protected void HistoryServiceTemplateSave(final String authority, final int id, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final History h = this.historyService.findOne2(id);
			h.setLegalRecords(new HashSet<LegalRecord>());
			this.historyService.save(h);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void HistoryServiceTemplateCreate(final String authority, final int id, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final History h = this.historyService.create();
			final InceptionRecord inception = this.inceptionRecordService.findOne(id);
			h.setInceptionRecord(inception);
			h.setBrotherhood(this.brotherhoodService.brotherhoodUserAccount(LoginService.getPrincipal().getId()));
			this.historyService.save(h);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment:An actor who is not authenticated must be able to:
	 * 1. Display the history of every brotherhood that he or she can display.
	 * b) Broken bussines rule:
	 * Se intenta poner la id de la broherhood en null
	 * 
	 * c) Sentence coverage:Compruebo el getHistoryByBrotherhood del servicio de history .Cubro el metodo entero con la prueba positiva ,
	 * en cambio con la prueba negativa cubro el 50%% del total de lineas de código.
	 * 
	 * d) Data coverage:-
	 */

	//anonymousUser
	@Test
	public void listAnonymousUser() {
		final Object testingData[][] = {
			{
				super.getEntityId("brotherhood1"), null
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.listAnonymousUser((Integer) testingData[i][0], (Class<?>) testingData[i][1]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	// Ancillary methods ------------------------------------------------------

	protected void listAnonymousUser(final Integer brotherhoodId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			final Collection<History> histories = this.historyService.getHistoryByBrotherhood(brotherhoodId);

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
}
