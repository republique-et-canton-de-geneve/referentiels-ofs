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
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.RegionWS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefRegionTest extends AbstractRefWSTest {

    private final int regionId;
    private final int continentId;
    private final String regionName;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ -1, 0, null },
	/* 1 */{ 0, 0, null },
	/* 2 */{ 1, 0, null },
	/* 3 */{ 11, 1, "Europe septentrionale" },
	/* 4 */{ 12, 1, "Europe orientale" },
	/* 5 */{ 13, 1, "Europe centrale" },
	/* 6 */{ 14, 1, "Europe occidentale" },
	/* 7 */{ 15, 1, "Europe méridionale occidentale" },
	/* 8 */{ 18, 0, null },
	/* 9 */{ 21, 2, "Afrique septentrionale" },
	/* 10 */{ 22, 2, "Afrique orientale" },
	/* 11 */{ 28, 0, null },
	/* 12 */{ 31, 3, "Amérique du Nord" },
	/* 13 */{ 32, 3, "Caraïbes" },
	/* 14 */{ 37, 0, null },
	/* 15 */{ 41, 4, "Asie septentrionale" },
	/* 16 */{ 42, 4, "Asie orientale" },
	/* 17 */{ 48, 0, null },
	/* 18 */{ 51, 5, "Océanie septentrionale" },
	/* 19 */{ 52, 0, null },
	/* 20 */{ 53, 5, "Océanie centrale" },
	/* 21 */{ 57, 0, null },
	/* 22 */{ 61, 6, "Antarctique" },
	/* 23 */{ 62, 0, null },
	/* */
	});
    }

    public RefRegionTest(final int regionId, final int continentId,
	    final String regionName) throws Exception {
	this.regionId = regionId;
	this.continentId = continentId;
	this.regionName = regionName;
    }

    @Test
    @Required(percentile90 = 30, percentile95 = 150)
    public void test() throws ReferentielOfsException {
	final RegionWS region = getWS().getRegion(regionId);
	if (regionName == null) {
	    assertNull("Region[" + regionId + "] est incorrecte", region);
	} else {
	    assertEquals("Region[" + regionId + "].id est incorrect", regionId,
		    region.getId());
	    assertEquals("Region[" + regionId + "].continentId est incorrect",
		    continentId, region.getContinentId());
	    assertEquals("Region[" + regionId + "].nom est incorrect",
		    regionName, region.getNom());
	    assertRegion(region, continentId);
	}
    }
}
