package ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws.model.Niveau1WS;

@PerfTest(invocations = 1000, threads = 20)
public class RefNiveaux1Test extends AbstractRefWSTest {

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<Niveau1WS> niveaux1 = getWS().getNiveaux1();
	assertEquals("La liste des niveaux1 est incorrecte", 17,
		niveaux1.size());
	for (final Niveau1WS niveau1WS : niveaux1) {
	    assertNiveau1WS("Référentiel: ", niveau1WS);
	}
    }
}
