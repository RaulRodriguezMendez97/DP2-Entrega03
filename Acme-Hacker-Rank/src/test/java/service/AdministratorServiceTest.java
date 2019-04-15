
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;

import services.AdministratorService;
import services.CreditCardService;
import utilities.AbstractTest;
import domain.Administrator;
import domain.CreditCard;
import forms.RegistrationForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CreditCardService		creditCardService;


	/*
	 * a) Requeriment: An actor who is authenticated as an administrator
	 * must be able to create user acconunts for new administrators.
	 * 
	 * b) Broken bussines rule:
	 * Se intenta crear un nuevo administrador sin email
	 * 
	 * c) Sentence coverage: 71.5%
	 * 
	 * d) Data coverage: 9.1% (1 atributo incorrecto/11 atributos)
	 */

	@Test
	public void CreateAdmnistratorService() {
		final Object testingData[][] = {
			{//Positive test
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevoUsername", "NuevaPassWord", "NuevoBrandName", "NuevoholderName", "9876785", "8", "2020", "876", null
			//			}, {//Negative test: email vacio
			//
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateAdministradorTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (int) testingData[i][8], (int) testingData[i][9], (int) testingData[i][10], (int) testingData[i][11], (Class<?>) testingData[i][11]);
	}
	protected void CreateAdministradorTemplate(final String name, final String surnames, final String vatNumber, final String email, final String username, final String password, final String brandName, final String holderName, final int number,
		final int expirationMonth, final int expirationYear, final int cW, final Class<?> expected) {
		Class<?> caught;
		Administrator admin = null;
		CreditCard creditcard = null;
		caught = null;
		try {

			RegistrationForm registrationForm = new RegistrationForm();

			registrationForm = registrationForm.createToAdmin();

			registrationForm.setBrandName(brandName);
			registrationForm.setHolderName(holderName);
			registrationForm.setNumber(number);
			registrationForm.setExpirationMonth(expirationMonth);
			registrationForm.setExpirationYear(expirationYear);
			registrationForm.setCW(cW);

			registrationForm.setName(name);
			registrationForm.setSurnames(surnames);
			registrationForm.setVatNumber(vatNumber);
			registrationForm.setEmail(email);
			registrationForm.getUserAccount().setUsername(username);
			registrationForm.getUserAccount().setPassword(password);

			final BindingResult binding = null;

			creditcard = this.creditCardService.reconstruct(registrationForm, binding);
			registrationForm.setCreditCard(creditcard);
			admin = this.administratorService.reconstruct(registrationForm, binding);
			final CreditCard creditCardSave = this.creditCardService.save(creditcard);
			admin.setCreditCard(creditCardSave);
			this.administratorService.save(admin);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
