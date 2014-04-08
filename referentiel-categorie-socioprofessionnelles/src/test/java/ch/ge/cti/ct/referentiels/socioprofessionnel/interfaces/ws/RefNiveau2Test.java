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
import ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws.model.Niveau2WS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefNiveau2Test extends AbstractRefWSTest {

    private final int niveau2WSId;
    private final String Niveau2WSNom;

    public RefNiveau2Test(final int niveau2WSId, final String niveau2WSNom) {
	this.niveau2WSId = niveau2WSId;
	this.Niveau2WSNom = niveau2WSNom;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ -1, null },
	/* 1 */{ 0, null },
	/* 2 */{ 101, "Dirigeant-e-s du secteur priv�" },
	/* 3 */{ 102, "Dirigeant-e-s du secteur public" },
	/* 4 */{ 201, "Professions lib�rales et assimil�es (secteur priv�)" },
	/* 5 */{ 301, "Autres ind�pendant-e-s (secteur priv�)" },
	/* 6 */{ 99, null }
	/* */
	});
    }

    @Test
    @Required(percentile90 = 30, percentile95 = 150)
    public void test() throws ReferentielOfsException {
	final Niveau2WS niveau2WS = getWS().getNiveau2(niveau2WSId);
	if (Niveau2WSNom == null) {
	    assertNull("Niveau2WS [" + niveau2WSId + "] est incorrect",
		    niveau2WS);
	} else {
	    assertEquals("Niveau2WS [" + niveau2WSId + "].id est incorrect",
		    niveau2WSId, niveau2WS.getId());
	    assertEquals("Niveau2WS [" + niveau2WSId + "].nom est incorrect",
		    Niveau2WSNom, niveau2WS.getNom());
	    assertNiveau2WS("Niveau2WS [" + niveau2WSId + "]: ", niveau2WS);

	}
    }
}