package ch.ge.cti.ct.referentiels.professions.interfaces.ws;

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
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.DivisionWS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefDivisionTest extends AbstractRefWSTest {

    private final int divisionId;
    private final String divisionNom;

    public RefDivisionTest(final int divisionId, final String divisionNom) {
	this.divisionId = divisionId;
	this.divisionNom = divisionNom;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays
		.asList(new Object[][] {
			/* 0 */{ -1, null },
			/* 1 */{ 0, null },
			/* 2 */{ 1,
				"Professions de l'agriculture, de l'économie forestière et de l'élevage" },
			/* 3 */{ 2,
				"Professions de l'industrie et des arts et métiers (sauf construction)" },
			/* 4 */{ 3,
				"Professions de la technique et de l'informatique" },
			/* 5 */{ 4,
				"Professions de la construction et de l'exploitation minière" },
			/* 6 */{ 5,
				"Professions commerciales et professions des transports et de la circulation" },
			/* 7 */{ 6,
				"Professions de l'hôtellerie, de la restauration et des services personnels" },
			/* 8 */{
				7,
				"Professions du management, de l'administration, de la banque et des assurances et professions judiciaires" },
			/* 9 */{
				8,
				"Professions de la santé, de l'enseignement et de la culture et professions scientifiques" },
			/* 10 */{ 9, "Indications non classificables" },
			/* 11 */{ 10, null }
		/* */
		});
    }

    @Test
    @Required(percentile90 = 2, percentile95 = 5)
    public void test() throws ReferentielOfsException {
	final DivisionWS division = getWS().getDivision(divisionId);
	if (divisionNom == null) {
	    assertNull("DivisionWS [" + divisionId + "] est incorrect",
		    division);
	} else {
	    assertEquals("DivisionWS [" + divisionId + "].id est incorrect",
		    divisionId, division.getId());
	    assertEquals("DivisionWS [" + divisionId + "].nom est incorrect",
		    divisionNom, division.getNom());
	    assertDivision("DivisionWS [" + divisionId + "]: ", division);

	}
    }
}
