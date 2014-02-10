package ch.ge.cti.ct.referentiels.pays.service.impl;

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
import ch.ge.cti.ct.referentiels.pays.model.Region;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefRegionTest extends AbstractRefTest {

    private final int regionId;
    private final int continentId;
    private final String regionName;
    private final int countPays;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ -1, 0, 0, null },
	/* 1 */{ 0, 0, 0, null },
	/* 2 */{ 1, 0, 0, null },
	/* 3 */{ 11, 1, 8, "Europe septentrionale" },
	/* 4 */{ 12, 1, 13, "Europe orientale" },
	/* 5 */{ 13, 1, 8, "Europe centrale" },
	/* 6 */{ 14, 1, 12, "Europe occidentale" },
	/* 7 */{ 15, 1, 4, "Europe méridionale occidentale" },
	/* 8 */{ 18, 0, 0, null },
	/* 9 */{ 21, 2, 10, "Afrique septentrionale" },
	/* 10 */{ 22, 2, 8, "Afrique orientale" },
	/* 11 */{ 28, 0, 0, null },
	/* 12 */{ 31, 3, 5, "Amérique du Nord" },
	/* 13 */{ 32, 3, 28, "Caraïbes" },
	/* 14 */{ 37, 0, 0, null },
	/* 15 */{ 41, 4, 1, "Asie septentrionale" },
	/* 16 */{ 42, 4, 8, "Asie orientale" },
	/* 17 */{ 48, 0, 0, null },
	/* 18 */{ 51, 5, 10, "Océanie septentrionale" },
	/* 19 */{ 52, 0, 0, null },
	/* 20 */{ 53, 5, 13, "Océanie centrale" },
	/* 21 */{ 57, 0, 0, null },
	/* 22 */{ 61, 6, 3, "Antarctique" },
	/* 23 */{ 62, 0, 0, null },
	/* */
	});
    }

    public RefRegionTest(final int regionId, final int continentId,
	    final int countPays, final String regionName) throws Exception {
	this.regionId = regionId;
	this.continentId = continentId;
	this.countPays = countPays;
	this.regionName = regionName;
    }

    @Test
    @Required(percentile90 = 1, percentile95 = 1)
    public void test() throws ReferentielOfsException {
	final Region region = ReferentielPaysTerritoiresService.instance
		.getRegion(regionId);
	if (regionName == null) {
	    assertNull("Region[" + regionId + "] est incorrecte", region);
	} else {
	    assertEquals("Region[" + regionId + "].id est incorrect", regionId,
		    region.getId());
	    assertEquals("Region[" + regionId + "].continentId est incorrect",
		    continentId, region.getContinentId());
	    assertEquals("Region[" + regionId + "].nom est incorrect",
		    regionName, region.getNom());
	    assertEquals("Region[" + regionId + "].pays.size est incorrect",
		    countPays, region.getPays().size());
	    assertRegion(region, continentId);
	}
    }
}
