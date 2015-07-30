package ch.ge.cti.ct.referentiels.pays.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.model.Pays;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 200, threads = 5)
public class RefPaysRegexpSearchTest extends AbstractRefTest {

    private final String critere;
    private final int paysCount;
    private final int continentId;
    private final int regionId;
    private final String paysNom;
    private final String iso2;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
		/* 0 */{ null, 0, 0, 0, null, null },
		/* 1 */{ "", 0, 0, 0, null, null },
		/* 2 */{ "France", 2, 0, 0, null, null },
		/* 3 */{ "france", 2, 0, 0, null, null },
		/* 4 */{ "FRANCE", 2, 0, 0, null, null },
		/* 5 */{ "FRA", 5, 0, 0, null, null },
		/* 6 */{ "FR", 8, 0, 0, null, null },
		/* 7 */{ "Iles Féroé", 1, 1, 11, "Iles Féroé", "FO" },
		/* 8 */{ "Iles Féroe", 1, 1, 11, "Iles Féroé", "FO" },
		/* 9 */{ "Iles Fêroe", 1, 1, 11, "Iles Féroé", "FO" },
		/* 10 */{ "Iles Feroe", 1, 1, 11, "Iles Féroé", "FO" },
		/* 11 */{ "Iles", 17, 0, 0, null, null },
		/* 12 */{ "Iles ", 17, 0, 0, null, null },
		/* 13 */{ "îles ", 17, 0, 0, null, null },
		/* 14 */{ "yougoslavie", 0, 0, 0, null, null },
		/* 15 */{ "Province de Voïvodine", 1, 1, 17,
			"Province de Voïvodine", "" },
		/* 16 */{ "Province de Voivodine", 1, 1, 17,
			"Province de Voïvodine", "" },
	/* */
	});
    }

    public RefPaysRegexpSearchTest(final String critere, final int paysCount,
	    final int continentId, final int regionId, final String paysNom,
	    final String iso2) throws Exception {
	this.critere = critere;
	this.paysCount = paysCount;
	this.continentId = continentId;
	this.regionId = regionId;
	this.paysNom = paysNom;
	this.iso2 = iso2;
    }

    @Test
    public void test() throws ReferentielOfsException {
	final List<Pays> payss = ReferentielPaysTerritoiresService.instance
		.searchPaysRegexp(critere);

	assertEquals("Pays[" + critere + "].size est incorrect", paysCount,
		payss.size());
	for (final Pays pays : payss) {
	    if (continentId > 0) {
		assertEquals("Pays[" + critere + "].continentId est incorrect",
			continentId, pays.getContinentId());
	    }
	    if (regionId > 0) {
		assertEquals("Pays[" + critere + "].regionId est incorrect",
			regionId, pays.getRegionId());
	    }
	    if (paysNom != null) {
		assertEquals("Pays[" + critere + "].nom est incorrect",
			paysNom, pays.getNom());
	    }
	    if (iso2 != null) {
		assertEquals("Pays[" + critere + "].iso2 est incorrect", iso2,
			pays.getIso2());
	    }
	}
    }
}
