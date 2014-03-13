package ch.ge.cti.ct.referentiels.professions.interfaces.ws;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.ClasseWS;

@PerfTest(invocations = 1000, threads = 20)
public class RefClassesTest extends AbstractRefWSTest {

    @Test
    @Required(percentile90 = 2, percentile95 = 5)
    public void test() throws ReferentielOfsException {
	final List<ClasseWS> classes = getWS().getClasses();
	assertEquals("La liste des groupes est incorrecte", 39, classes.size());
	for (final ClasseWS classe : classes) {
	    assertClasse("Référentiel: ", classe);
	}
    }
}
