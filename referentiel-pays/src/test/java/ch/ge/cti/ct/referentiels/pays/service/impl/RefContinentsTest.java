package ch.ge.cti.ct.referentiels.pays.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.databene.contiperf.PerfTest;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.model.Continent;

@PerfTest(invocations = 1000, threads = 20)
public class RefContinentsTest extends AbstractRefTest {

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<Continent> continents = ReferentielPaysTerritoiresService.INSTANCE
		.getContinents();
	assertEquals("Ls liste des continents est incorrecte", 6,
		continents.size());
	for (Continent continent : continents) {
	    assertContinent(continent);
	}
    }

}
