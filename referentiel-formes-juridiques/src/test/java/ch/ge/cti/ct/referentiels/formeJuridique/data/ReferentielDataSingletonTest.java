package ch.ge.cti.ct.referentiels.formeJuridique.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.formeJuridique.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.formeJuridique.model.ReferentielFormesJuridiques;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

public class ReferentielDataSingletonTest extends AbstractReferentielTest {

    @Test
    public void testGetData() throws ReferentielOfsException {
	final ReferentielFormesJuridiques ref1 = ReferentielDataSingleton.instance
		.getData();
	assertNotNull(ref1);
	final ReferentielFormesJuridiques ref2 = ReferentielDataSingleton.instance
		.getData();
	assertNotNull(ref2);
	assertEquals(ref1, ref2);
    }

    @Test
    public void testGetReferentielFile() throws ReferentielOfsException {

	assertNotNull(ReferentielDataSingleton.instance.getReferentielFile());
	assertTrue(ReferentielDataSingleton.instance.getReferentielFile()
		.getFile().endsWith("CH1_BUR+CL_LEGALFORMS+2.0.xml"));
    }
}
