package ch.ge.cti.ct.referentiels.communes.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.communes.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.communes.model.ReferentielCommunes;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

public class ReferentielDataSingletonTest extends AbstractReferentielTest {

    @Test
    public void testGetData() throws ReferentielOfsException {
	final ReferentielCommunes ref1 = ReferentielDataSingleton.INSTANCE
		.getData();
	assertNotNull(ref1);
	final ReferentielCommunes ref2 = ReferentielDataSingleton.INSTANCE
		.getData();
	assertNotNull(ref2);
	assertEquals(ref1, ref2);
    }
}
