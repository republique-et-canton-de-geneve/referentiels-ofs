package ch.ge.cti.ct.referentiels.pays.interfaces.ws;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.RegionWS;

@PerfTest(invocations = 1000, threads = 20)
public class RefRegionsTest extends AbstractRefWSTest {

    @Test
    @Required(percentile90 = 1, percentile95 = 1)
    public void test() throws ReferentielOfsException {
	final List<RegionWS> regions = getWS().getRegions();
	assertEquals("La liste des regions est incorrecte", 32, regions.size());
	for (RegionWS region : regions) {
	    assertRegion(region, -1);
	}
    }

}
