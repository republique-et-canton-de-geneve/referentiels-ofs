package ch.ge.cti.ct.referentiels.professions.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;

import org.databene.contiperf.PerfTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.model.Groupe;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefGroupeTest extends AbstractRefTest {

    private final int groupeId;
    private final String groupeNom;
    private final int countGenre;

    public RefGroupeTest(final int groupeId, final String groupeNom,
	    final int countGenre) {
	this.groupeId = groupeId;
	this.groupeNom = groupeNom;
	this.countGenre = countGenre;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays
		.asList(new Object[][] {
			/* 0 */{ -1, null, 0 },
			/* 1 */{ 0, null, 0 },
			/* 2 */{ 111, "Professions de l'agriculture", 3 },
			/* 3 */{ 211, "Professions de l'industrie alimentaire",
				5 },
			/* 4 */{ 311, "Ingénieurs", 13 },
			/* 5 */{ 411, "Professions de l'industrie du bâtiment",
				8 },
			/* 6 */{ 511, "Professions de l'achat et de la vente",
				9 },
			/* 7 */{
				611,
				"Professions de la restauration et de l'hôtellerie",
				6 },
			/* 8 */{
				711,
				"Entrepreneurs, directeurs et fonctionnaires supérieurs",
				5 },
			/* 9 */{
				811,
				"Auteurs de textes, professionnels des médias: presse écrite, audio-visuel",
				4 },
			/* 10 */{ 911, "Professions du secteur tertiaire spa",
				3 },
			/* 11 */{ 99, null, 0 }
		/* */
		});
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final Groupe groupe = ReferentielProfessionsService.INSTANCE
		.getGroupe(groupeId);
	if (groupeNom == null) {
	    assertNull("Groupe [" + groupeId + "] est incorrect", groupe);
	} else {
	    assertNotNull("Groupe [" + groupeId + "] est incorrect", groupe);
	    assertEquals("Groupe [" + groupeId + "].id est incorrect",
		    groupeId, groupe.getId());
	    assertEquals("Groupe [" + groupeId + "].nom est incorrect",
		    groupeNom, groupe.getNom());
	    assertEquals("Liste des groupe de groupe [" + groupeId
		    + "] est incorrecte", countGenre, groupe.getGenre().size());
	    assertGroupe("Groupe [" + groupeId + "]: ", groupe);

	}
    }
}
