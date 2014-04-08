package ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws;

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
import ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws.model.Niveau1WS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefNiveau1Test extends AbstractRefWSTest {

    private final int niveau1Id;
    private final String niveau1Nom;

    public RefNiveau1Test(final int niveau1Id, final String niveau1Nom) {
	this.niveau1Id = niveau1Id;
	this.niveau1Nom = niveau1Nom;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays
		.asList(new Object[][] {
			/* 0 */{ -1, null },
			/* 1 */{ 0, null },
			/* 2 */{ 10, "Dirigeant-e-s" },
			/* 3 */{ 20, "Professions libérales et assimilées" },
			/* 4 */{ 30, "Autres indépendant-e-s" },
			/* 5 */{ 60,
				"Non-manuel-le-s qualifié-e-s : employé-e-s" },
			/* 6 */{
				91,
				"Personnes actives occupées non attribuables (information manquante ou non plausible)" },
			/* 7 */{ 97, "Autres personnes non actives" },
			/* 8 */{ 98, "Enfants de moins de 15 ans" },
			/* 9 */{ 100, null }
		/* */
		});
    }

    @Test
    @Required(percentile90 = 30, percentile95 = 150)
    public void test() throws ReferentielOfsException {
	final Niveau1WS niveau1WS = getWS().getNiveau1(niveau1Id);
	if (niveau1Nom == null) {
	    assertNull("Niveau1WS [" + niveau1Id + "] est incorrect", niveau1WS);
	} else {
	    assertEquals("Niveau1WS [" + niveau1Id + "].id est incorrect",
		    niveau1Id, niveau1WS.getId());
	    assertEquals("Niveau1WS [" + niveau1Id + "].nom est incorrect",
		    niveau1Nom, niveau1WS.getNom());
	    assertNiveau1WS("Niveau1WS [" + niveau1Id + "]: ", niveau1WS);
	}
    }
}
