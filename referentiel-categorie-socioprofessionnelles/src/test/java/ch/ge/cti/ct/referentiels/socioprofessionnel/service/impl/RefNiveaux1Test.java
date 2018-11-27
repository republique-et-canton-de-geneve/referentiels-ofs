package ch.ge.cti.ct.referentiels.socioprofessionnel.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.databene.contiperf.PerfTest;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau1;

@PerfTest(invocations = 1000, threads = 20)
public class RefNiveaux1Test extends AbstractRefTest {

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<Niveau1> niveaux1 = ReferentielSocioprofessionnelService.INSTANCE
		.getNiveaux1();
	assertEquals("La liste des niveaux1 est incorrecte", 17,
		niveaux1.size());
	for (final Niveau1 niveau1 : niveaux1) {
	    assertNiveau1("Référentiel: ", niveau1);
	}
    }
}
