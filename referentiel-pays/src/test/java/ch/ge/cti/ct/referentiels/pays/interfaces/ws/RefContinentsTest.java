package ch.ge.cti.ct.referentiels.pays.interfaces.ws;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.ContinentWS;

@PerfTest(invocations = 1000, threads = 20)
public class RefContinentsTest extends AbstractRefWSTest {

    @Test
    @Required(percentile90 = 1, percentile95 = 1)
    public void test() throws ReferentielOfsException {
	final List<ContinentWS> continents = getWS().getContinents();
	assertEquals("Ls liste des continents est incorrecte", 6,
		continents.size());
	for (ContinentWS continent : continents) {
	    assertContinent(continent);
	}
    }

}
