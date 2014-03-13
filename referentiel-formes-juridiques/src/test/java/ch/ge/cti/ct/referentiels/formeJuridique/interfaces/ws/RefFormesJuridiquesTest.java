package ch.ge.cti.ct.referentiels.formeJuridique.interfaces.ws;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.formeJuridique.model.FormeJuridique;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

@PerfTest(invocations = 1000, threads = 20)
public class RefFormesJuridiquesTest extends AbstractRefWSTest {

    @Test
    @Required(percentile90 = 2, percentile95 = 5)
    public void test() throws ReferentielOfsException {
	final List<FormeJuridique> formesJuridiques = getWS()
		.getFormesJuridiques();
	assertEquals("La liste des formes juridiques est incorrecte", 27,
		formesJuridiques.size());
	for (FormeJuridique formeJuridique : formesJuridiques) {
	    assertFormeJuridique(formeJuridique);
	}
    }

}
