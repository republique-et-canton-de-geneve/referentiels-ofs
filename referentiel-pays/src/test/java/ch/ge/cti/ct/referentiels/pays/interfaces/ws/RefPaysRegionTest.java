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
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.PaysWS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefPaysRegionTest extends AbstractRefWSTest {

    private final int continentId;
    private final int regionId;
    private final int countPays;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ 0, 0, 0 },
	/* 1 */{ 1, 0, 0 },
	/* 2 */{ 1, 11, 8 },
	/* 3 */{ 2, 21, 10 },
	/* 4 */{ 2, 22, 8 },
	/* 5 */{ 3, 31, 5 },
	/* 6 */{ 3, 32, 28 },
	/* 7 */{ 4, 41, 1 },
	/* 8 */{ 4, 42, 8 },
	/* 9 */{ 5, 51, 10 },
	/* 10 */{ 5, 52, 0 },
	/* 11 */{ 6, 61, 3 },
	/* 12 */{ 6, 62, 0 },
	/* 13 */{ 7, 0, 0 },
	/* */
	});
    }

    public RefPaysRegionTest(final int continentId, final int regionId,
	    final int countPays) throws Exception {
	this.continentId = continentId;
	this.regionId = regionId;
	this.countPays = countPays;
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<PaysWS> payss = getWS().getPaysByRegion(regionId);

	assertEquals("Liste des pays de la région [" + regionId
		+ "] est incorrecte", countPays, payss.size());
	for (PaysWS pays : payss) {
	    assertPays(pays, continentId, regionId);
	}
    }
}
