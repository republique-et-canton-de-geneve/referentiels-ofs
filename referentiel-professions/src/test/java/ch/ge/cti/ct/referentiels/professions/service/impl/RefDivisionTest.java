package ch.ge.cti.ct.referentiels.professions.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.model.Division;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefDivisionTest extends AbstractRefTest {

    private final int divisionId;
    private final String divisionNom;
    private final int countClasses;

    public RefDivisionTest(final int divisionId, final String divisionNom,
	    final int countClasses) {
	this.divisionId = divisionId;
	this.divisionNom = divisionNom;
	this.countClasses = countClasses;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays
		.asList(new Object[][] {
			/* 0 */{ -1, null, 0 },
			/* 1 */{ 0, null, 0 },
			/* 2 */{
				1,
				"Professions de l'agriculture, de l'économie forestière et de l'élevage",
				1 },
			/* 3 */{
				2,
				"Professions de l'industrie et des arts et métiers (sauf construction)",
				9 },
			/* 4 */{
				3,
				"Professions de la technique et de l'informatique",
				6 },
			/* 5 */{
				4,
				"Professions de la construction et de l'exploitation minière",
				2 },
			/* 6 */{
				5,
				"Professions commerciales et professions des transports et de la circulation",
				4 },
			/* 7 */{
				6,
				"Professions de l'hôtellerie, de la restauration et des services personnels",
				2 },
			/* 8 */{
				7,
				"Professions du management, de l'administration, de la banque et des assurances et professions judiciaires",
				5 },
			/* 9 */{
				8,
				"Professions de la santé, de l'enseignement et de la culture et professions scientifiques",
				7 },
			/* 10 */{ 9, "Indications non classificables", 3 },
			/* 11 */{ 10, null, 0 }
		/* */
		});
    }

    @Test
    @Required(percentile90 = 25, percentile95 = 100)
    public void test() throws ReferentielOfsException {
	final Division division = ReferentielProfessionsService.instance
		.getDivision(divisionId);
	if (divisionNom == null) {
	    assertNull("Division [" + divisionId + "] est incorrect", division);
	} else {
	    assertEquals("Division [" + divisionId + "].id est incorrect",
		    divisionId, division.getId());
	    assertEquals("Division [" + divisionId + "].nom est incorrect",
		    divisionNom, division.getNom());
	    assertEquals("Liste des classe de division [" + divisionId
		    + "] est incorrecte", countClasses, division.getClasse()
		    .size());
	    assertDivision("Division [" + divisionId + "]: ", division);

	}
    }
}
