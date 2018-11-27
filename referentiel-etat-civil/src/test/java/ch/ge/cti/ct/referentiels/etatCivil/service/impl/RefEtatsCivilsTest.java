package ch.ge.cti.ct.referentiels.etatCivil.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.databene.contiperf.PerfTest;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.etatCivil.model.EtatCivil;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

@PerfTest(invocations = 1000, threads = 20)
public class RefEtatsCivilsTest extends AbstractRefTest {

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<EtatCivil> formesJuridiques = ReferentielEtatCivilService.INSTANCE
		.getEtatsCivils();
	assertEquals("La liste des états civils est incorrecte", 7,
		formesJuridiques.size());
	for (EtatCivil etatCivil : formesJuridiques) {
	    assertEtatCivil(etatCivil);
	}
    }

}
