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
	final ReferentielFormesJuridiques ref1 = ReferentielDataSingleton.INSTANCE
		.getData();
	assertNotNull(ref1);
	final ReferentielFormesJuridiques ref2 = ReferentielDataSingleton.INSTANCE
		.getData();
	assertNotNull(ref2);
	assertEquals(ref1, ref2);
    }

}
