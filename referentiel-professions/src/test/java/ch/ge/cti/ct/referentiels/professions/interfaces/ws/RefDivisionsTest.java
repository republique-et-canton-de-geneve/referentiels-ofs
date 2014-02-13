package ch.ge.cti.ct.referentiels.professions.interfaces.ws;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.DivisionWS;

@PerfTest(invocations = 1000, threads = 20)
public class RefDivisionsTest extends AbstractRefWSTest {

    @Test
    @Required(percentile90 = 1, percentile95 = 1)
    public void test() throws ReferentielOfsException {
	final List<DivisionWS> divisions = getWS().getDivisions();
	assertEquals("La liste des divisions est incorrecte", 9,
		divisions.size());
	for (final DivisionWS division : divisions) {
	    assertDivision("Référentiel: ", division);
	}
    }
}
