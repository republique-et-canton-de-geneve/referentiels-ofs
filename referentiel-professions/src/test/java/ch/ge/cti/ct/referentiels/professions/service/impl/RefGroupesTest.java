package ch.ge.cti.ct.referentiels.professions.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.databene.contiperf.PerfTest;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.model.Groupe;

@PerfTest(invocations = 1000, threads = 20)
public class RefGroupesTest extends AbstractRefTest {

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<Groupe> groupes = ReferentielProfessionsService.INSTANCE
		.getGroupes();
	assertEquals("La liste des groupes est incorrecte", 88, groupes.size());
	for (final Groupe groupe : groupes) {
	    assertGroupe("Référentiel: ", groupe);
	}
    }
}
