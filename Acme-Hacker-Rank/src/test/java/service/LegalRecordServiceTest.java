/*
 * // * RegistrationTest.java
 * // *
 * // * Copyright (C) 2019 Universidad de Sevilla
 * // *
 * // * The use of this project is hereby constrained to the conditions of the
 * // * TDG Licence, a copy of which you may download from
 * // * http://www.tdg-seville.info/License.html
 * //
 */

package service;

import java.util.Arrays;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.HistoryService;
import services.LegalRecordService;
import utilities.AbstractTest;
import domain.History;
import domain.LegalRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class LegalRecordServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private LegalRecordService	legalRecordService;

	@Autowired
	private HistoryService		historyService;


	/*
	 * a) Requeriment: An actor who is authenticated as a brotherhood must be able to:
	 * Manage their history, which includes listing, displaying, creating, updating,
	 * and deleting its records.
	 * 
	 * b) Broken bussines rule:
	 * casoNegativo : Una hermandad intenta modificar una legalRecord que no es suya
	 * 
	 * c) Sentence coverage: Compruebo dos metodos del servicio , findone y save. Cubro los dos metodos enteros con la prueba positiva ,
	 * en cambio con la prueba negativa solo cubro el 60% del total de lineas de código.
	 * 
	 * d) Data coverage:14,3%
	 */

	// Tests ------------------------------------------------------------------
	//anonymousUser
	@Test
	public void EditLegalRecord() {
		final Object testingData[][] = {
			{ //Prueba positiva
				"brotherhood", super.getEntityId("legalRecord1"), "Titulo Prueba", null
			}, { //Prueba negativa
				"brotherhood", super.getEntityId("legalRecord2"), "Titulo Prueba", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	// Ancillary methods ------------------------------------------------------

	protected void template(final String authority, final Integer legalRecordId, final String title, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			super.authenticate(authority);
			final LegalRecord legalRecord = this.legalRecordService.findOne(legalRecordId);
			legalRecord.setTitle(title);
			this.legalRecordService.save(legalRecord);

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: An actor who is authenticated as a brotherhood must be able to:
	 * Manage their history, which includes listing, displaying, creating, updating,
	 * and deleting its records.
	 * 
	 * b) Broken bussines rule:
	 * casoNegativo: Se intenta crear una hermandad con su title a nulL
	 * 
	 * c) Sentence coverage:Compruebo dos metodos del servicio , create y save. Cubro los dos metodos enteros con la prueba positiva ,
	 * en cambio con la prueba negativa cubro el 90% del total de lineas de código.
	 * 
	 * 
	 * d) Data coverage:16,7%( 1 atributo incorrecto/6 atributos)
	 */

	@Test
	public void CreateLegalRecord() {
		final Object testingData[][] = {
			{
				"Titulo Prueba", "PruebaTest Descripción", "PruebaTest LegalName", "12345j", Arrays.asList("law1", "law2"), null
			}, {
				null, "PruebaTest Descripción", "PruebaTest LegalName", "12345j", Arrays.asList("law1", "law2"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateCreate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Collection<String>) testingData[i][4], (Class<?>) testingData[i][5]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	// Ancillary methods ------------------------------------------------------

	protected void templateCreate(final String title, final String description, final String legalName, final String vatNumber, final Collection<String> laws, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			final LegalRecord legalRecord = this.legalRecordService.create();

			legalRecord.setTitle(title);
			legalRecord.setDescription(description);
			legalRecord.setLegalName(legalName);
			legalRecord.setVATNumber(vatNumber);
			legalRecord.setLaws(laws);
			this.legalRecordService.save(legalRecord);

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: An actor who is authenticated as a brotherhood must be able to:
	 * Manage their history, which includes listing, displaying, creating, updating,
	 * and deleting its records.
	 * 
	 * b) Broken bussines rule:
	 * casoNegativo : Una hermandad intenta borrar una legalRecord que no es suya
	 * 
	 * c) Sentence coverage:Compruebo dos metodos del servicio , findOne y delete. Cubro los dos metodos enteros con la prueba positiva ,
	 * en cambio con la prueba negativa cubro el 81,25% del total de lineas de código.
	 * 
	 * d) Data coverage:-
	 */

	@Test
	public void DeleteLegalRecord() {
		final Object testingData[][] = {
			{
				"brotherhood", super.getEntityId("legalRecord1"), super.getEntityId("history1"), null
			}, {
				"brotherhood", super.getEntityId("legalRecord2"), super.getEntityId("history2"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateDelete((String) testingData[i][0], (Integer) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	// Ancillary methods ------------------------------------------------------

	protected void templateDelete(final String authority, final Integer legalRecordId, final Integer historyId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			super.authenticate(authority);
			final LegalRecord legalRecord = this.legalRecordService.findOne(legalRecordId);
			this.legalRecordService.delete(legalRecord, historyId);
			super.authenticate(null);

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: An actor who is authenticated as a brotherhood must be able to:
	 * Manage their history, which includes listing, displaying, creating, updating,
	 * and deleting its records.
	 * 
	 * b) Broken bussines rule:
	 * casoNegativo 4 : Una hermandad intenta listar las legalRecord que no le pertenecen
	 * 
	 * c) Sentence coverage:Compruebo el findOne de history . Cubro los el metodo entero con la prueba positiva ,
	 * en cambio con la prueba negativa cubro el 66,7% del total de lineas de código.
	 * 
	 * d) Data coverage:-
	 */
	@Test
	public void listLegalRecord() {
		final Object testingData[][] = {
			{
				"brotherhood", super.getEntityId("history1"), null
			}, {
				"brotherhood1", super.getEntityId("history1"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateList((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	// Ancillary methods ------------------------------------------------------

	protected void templateList(final String authority, final Integer historyId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			super.authenticate(authority);
			final History history = this.historyService.findOne(historyId);
			Assert.notNull(history);

			super.authenticate(null);

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
}
