package ch.ge.cti.ct.referentiels.professions.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.model.Division;

@PerfTest(invocations = 1000, threads = 20)
public class RefDivisionsTest extends AbstractRefTest {

    @Test
    @Required(percentile90 = 30, percentile95 = 150)
    public void test() throws ReferentielOfsException {
	final List<Division> divisions = ReferentielProfessionsService.instance
		.getDivisions();
	assertEquals("La liste des divisions est incorrecte", 9,
		divisions.size());
	for (final Division division : divisions) {
	    assertDivision("Référentiel: ", division);
	}
    }
}
