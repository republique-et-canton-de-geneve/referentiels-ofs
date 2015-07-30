package ch.ge.cti.ct.referentiels.pays.interfaces.ws;

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
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.RegionWS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefRegionsContinentTest extends AbstractRefWSTest {

    private final int continentId;
    private final int countRegions;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ -1, 0 },
	/* 1 */{ 0, 0 },
	/* 2 */{ 1, 7 },
	/* 3 */{ 2, 7 },
	/* 4 */{ 3, 6 },
	/* 5 */{ 4, 7 },
	/* 6 */{ 5, 4 },
	/* 7 */{ 6, 1 },
	/* 8 */{ 7, 0 }
	/* */
	});
    }

    public RefRegionsContinentTest(final int continentId, final int countRegions)
	    throws Exception {
	this.continentId = continentId;
	this.countRegions = countRegions;
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<RegionWS> regions = getWS().getRegionsByContinent(
		continentId);
	assertEquals("Liste des régions du continent [" + continentId
		+ "] est incorrecte", countRegions, regions.size());
	for (RegionWS region : regions) {
	    assertRegion(region, continentId);
	}
    }
}
