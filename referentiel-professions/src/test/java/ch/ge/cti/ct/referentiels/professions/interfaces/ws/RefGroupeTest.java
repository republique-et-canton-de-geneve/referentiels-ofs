package ch.ge.cti.ct.referentiels.professions.interfaces.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.GroupeWS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefGroupeTest extends AbstractRefWSTest {

    private final int groupeId;
    private final String groupeNom;

    public RefGroupeTest(final int groupeId, final String groupeNom) {
	this.groupeId = groupeId;
	this.groupeNom = groupeNom;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays
		.asList(new Object[][] {
			/* 0 */{ -1, null },
			/* 1 */{ 0, null },
			/* 2 */{ 111, "Professions de l'agriculture" },
			/* 3 */{ 211, "Professions de l'industrie alimentaire" },
			/* 4 */{ 311, "Ingénieurs" },
			/* 5 */{ 411, "Professions de l'industrie du bâtiment" },
			/* 6 */{ 511, "Professions de l'achat et de la vente" },
			/* 7 */{ 611,
				"Professions de la restauration et de l'hôtellerie" },
			/* 8 */{ 711,
				"Entrepreneurs, directeurs et fonctionnaires supérieurs" },
			/* 9 */{ 811,
				"Auteurs de textes, professionnels des médias: presse écrite, audio-visuel" },
			/* 10 */{ 911, "Professions du secteur tertiaire spa" },
			/* 11 */{ 99, null }
		/* */
		});
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final GroupeWS groupe = getWS().getGroupe(groupeId);
	if (groupeNom == null) {
	    assertNull("GroupeWS [" + groupeId + "] est incorrect", groupe);
	} else {
	    assertNotNull("GroupeWS [" + groupeId + "] est incorrect", groupe);
	    assertEquals("GroupeWS [" + groupeId + "].id est incorrect",
		    groupeId, groupe.getId());
	    assertEquals("GroupeWS [" + groupeId + "].nom est incorrect",
		    groupeNom, groupe.getNom());
	    assertGroupe("GroupeWS [" + groupeId + "]: ", groupe);

	}
    }
}
