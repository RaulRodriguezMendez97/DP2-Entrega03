
package service;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.InceptionRecordService;
import utilities.AbstractTest;
import domain.InceptionRecord;
import domain.Picture;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class InceptionRecordServiceTest extends AbstractTest {

	@Autowired
	private InceptionRecordService	inceptionRecordService;


	@Test
	public void InceptionRecordService() {

		/*
		 * a) Requeriment: An actor who is authenticated as a brotherhood must be able to:
		 * Manage their history, which includes listing, displaying, creating, updating,
		 * and deleting its records.
		 * 
		 * b) Broken bussines rule:
		 * Case 2 and 3: Un actor que no es la hermandad a la que
		 * pertenece el inpcetion record, modificarlo
		 * 
		 * c) Sentence coverage: 80%
		 * 
		 * d) Data coverage: 50%
		 */
		final Object testingData[][] = {
			{//Positive test
				"brotherhood", super.getEntityId("inceptionRecord1"), "Nuevo title", "Descripcion", null
			}, {//Negative test
				"brotherhood1", super.getEntityId("inceptionRecord1"), "Nuevo title", "Descripcion", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.InceptionRecordServiceTemplateSave((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);

		/*
		 * a) Requeriment: An actor who is authenticated as a brotherhood must be able to:
		 * Manage their history, which includes listing, displaying, creating, updating,
		 * and deleting its records.
		 * 
		 * b) Broken bussines rule:
		 * Case 2 and 3: Un actor que no es la hermandad a la que
		 * pertenece el inpcetion record, intenta crear un inception record
		 * 
		 * c) Sentence coverage: 80%
		 * 
		 * d) Data coverage: 20%
		 */
		final Object testingData2[][] = {
			{//Positive test
				"brotherhood", "Nuevo title", "Descripcion", null
			}, {//Negative test
				"brotherhood1", "", "Descripcion", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.InceptionRecordServiceTemplateCreate((String) testingData2[i][0], (String) testingData2[i][1], (String) testingData2[i][2], (Class<?>) testingData2[i][3]);

		/*
		 * a) Requeriment: An actor who is authenticated as a brotherhood must be able to:
		 * Manage their history, which includes listing, displaying, creating, updating,
		 * and deleting its records.
		 * 
		 * b) Broken bussines rule:
		 * Case 2 and 3: Un actor que no es la hermandad a la que
		 * pertenece el inpcetion record, intenta borrrala
		 * 
		 * c) Sentence coverage: 90%
		 * 
		 * d) Data coverage: 100%
		 */
		final Object testingData3[][] = {
			{//Positive test
				"brotherhood", super.getEntityId("inceptionRecord1"), null
			}, {//Negative test
				"brotherhood", super.getEntityId("inceptionRecord2"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.InceptionRecordServiceTemplateDelete((String) testingData3[i][0], (int) testingData3[i][1], (Class<?>) testingData3[i][2]);
	}
	protected void InceptionRecordServiceTemplateSave(final String authority, final int id, final String title, final String description, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final InceptionRecord i = this.inceptionRecordService.findOneIfItIsMine(id);
			i.setTitle(title);
			i.setDescription(description);
			i.setPictures(new HashSet<Picture>());
			this.inceptionRecordService.save(i);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void InceptionRecordServiceTemplateCreate(final String authority, final String title, final String description, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final InceptionRecord i = this.inceptionRecordService.create();
			i.setTitle(title);
			i.setDescription(description);
			i.setPictures(new HashSet<Picture>());
			this.inceptionRecordService.save(i);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void InceptionRecordServiceTemplateDelete(final String authority, final int id, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final InceptionRecord i = this.inceptionRecordService.findOne(id);
			this.inceptionRecordService.delete(i);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
