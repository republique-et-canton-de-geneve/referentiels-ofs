package ch.ge.cti.ct.referentiels.professions.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.model.Classe;

@PerfTest(invocations = 1000, threads = 20)
public class RefClassesTest extends AbstractRefTest {

    @Test
    @Required(percentile90 = 25, percentile95 = 100)
    public void test() throws ReferentielOfsException {
	final List<Classe> classes = ReferentielProfessionsService.instance
		.getClasses();
	assertEquals("La liste des groupes est incorrecte", 39, classes.size());
	for (final Classe classe : classes) {
	    assertClasse("R�f�rentiel: ", classe);
	}
    }
}
