package ch.ge.cti.ct.referentiels.etatCivil.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.etatCivil.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.etatCivil.model.ReferentielEtatCivil;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

public class ReferentielDataSingletonTest extends AbstractReferentielTest {

    @Test
    public void testGetData() throws ReferentielOfsException {
	final ReferentielEtatCivil ref1 = ReferentielDataSingleton.instance
		.getData();
	assertNotNull(ref1);
	final ReferentielEtatCivil ref2 = ReferentielDataSingleton.instance
		.getData();
	assertNotNull(ref2);
	assertEquals(ref1, ref2);
    }

}
