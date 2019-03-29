
package service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	@Autowired
	private services.BrotherhoodService	BrotherhoodService;


	/*
	 * a) Requeriment: An actor who is authenticated as an administrator must be able to:
	 * Display a dashboard with the following information:
	 * The average, the minimum, the maximum, and the standard
	 * deviation of the number of records per history.
	 * 
	 * b) Broken bussines rule:
	 * Case 2 and 3: Un actor que no es un administrador, intenta accerder al dashboard
	 * 
	 * c) Sentence coverage: 100%
	 * 
	 * d) Data coverage: -
	 */

	@Test
	public void AdministratorDashboard() {
		final Object testingData[][] = {
			{//Positive test
				"admin", null
			}, {//Negative test: Case 2
				"brotherhood1", IllegalArgumentException.class
			}, {//Negative test: Case 3
				"member", IllegalArgumentException.class,
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.AdministratorDashboardTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void AdministratorDashboardTemplate(final String authority, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final Collection<Object[]> AvgMinMaxDesv = this.BrotherhoodService.getMaxMinAvgDesvMembersBrotherhood();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
