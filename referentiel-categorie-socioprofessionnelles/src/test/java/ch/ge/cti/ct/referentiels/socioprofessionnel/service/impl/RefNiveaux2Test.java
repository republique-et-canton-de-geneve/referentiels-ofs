package ch.ge.cti.ct.referentiels.socioprofessionnel.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau2;

@PerfTest(invocations = 1000, threads = 20)
public class RefNiveaux2Test extends AbstractRefTest {

    @Test
    @Required(percentile90 = 30, percentile95 = 150)
    public void test() throws ReferentielOfsException {
	final List<Niveau2> niveaux2s = ReferentielSocioprofessionnelService.instance
		.getNiveaux2();
	assertEquals("La liste des groupes est incorrecte", 31,
		niveaux2s.size());
	for (final Niveau2 niveau2 : niveaux2s) {
	    assertNiveau2("Référentiel: ", niveau2);
	}
    }
}
