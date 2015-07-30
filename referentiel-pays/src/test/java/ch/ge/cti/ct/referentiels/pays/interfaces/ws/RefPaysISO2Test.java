package ch.ge.cti.ct.referentiels.pays.interfaces.ws;

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
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.PaysWS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefPaysISO2Test extends AbstractRefWSTest {

    private final String iso2;
    private final int continentId;
    private final int regionId;
    private final String paysNom;
    private final String paysNomLong;
    private final String iso3;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays
		.asList(new Object[][] {
			/* 0 */{ null, 0, 0, null, null, null },
			/* 1 */{ "", 0, 0, null, null, null },
			/* 2 */{ "CH", 1, 13, "Suisse", "Confédération suisse",
				"CHE" },
			/* 3 */{ "AL", 1, 17, "Albanie",
				"République d'Albanie", "ALB" },
			/* 4 */{ "FO", 1, 11, "Iles Féroé", "Iles Féroé", "FRO" },
			/* 5 */{
				"GB",
				1,
				14,
				"Royaume-Uni",
				"Royaume-Uni de Grande-Bretagne et d'Irlande du Nord",
				"GBR" },
			/* 6 */{ "TR", 1, 16, "Turquie",
				"République de Turquie", "TUR" },
			/* 7 */{ "SK", 1, 13, "Slovaquie",
				"République slovaque", "SVK" },
			/* 8 */{ "LT", 1, 12, "Lituanie",
				"République de Lituanie", "LTU" },
			/* 9 */{ "MD", 1, 12, "Moldova",
				"République de Moldova", "MDA" },
			/* 10 */{ "ET", 2, 22, "Ethiopie",
				"République fédérale démocratique d'Ethiopie",
				"ETH" },
		/* */
		});
    }

    public RefPaysISO2Test(final String iso2, final int continentId,
	    final int regionId, final String paysNom, final String paysNomLong,
	    final String iso3) throws Exception {
	this.iso2 = iso2;
	this.continentId = continentId;
	this.regionId = regionId;
	this.paysNom = paysNom;
	this.paysNomLong = paysNomLong;
	this.iso3 = iso3;
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final PaysWS pays = getWS().getPaysByIso2(iso2);

	if (paysNom == null) {
	    assertNull("Pays[" + iso2 + "] est incorrect", pays);
	} else {
	    assertPays(pays, continentId, regionId);
	    assertEquals("Pays[" + iso3 + "].iso2 est incorrect", iso2,
		    pays.getIso2());
	    assertEquals("Pays[" + iso3 + "].iso3 est incorrect", iso3,
		    pays.getIso3());
	    assertEquals("Pays[" + iso2 + "].nom est incorrect", paysNom,
		    pays.getNom());
	    assertEquals("Pays[" + iso2 + "].nomLong est incorrect",
		    paysNomLong, pays.getNomLong());
	}
    }
}
