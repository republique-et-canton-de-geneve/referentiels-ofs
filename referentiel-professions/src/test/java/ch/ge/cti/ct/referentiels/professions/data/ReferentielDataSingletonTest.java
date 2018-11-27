package ch.ge.cti.ct.referentiels.professions.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.professions.model.ReferentielProfessions;

public class ReferentielDataSingletonTest extends AbstractReferentielTest {

    @Test
    public void testGetData() throws ReferentielOfsException {
	final ReferentielProfessions ref1 = ReferentielDataSingleton.INSTANCE
		.getData();
	assertNotNull(ref1);
	final ReferentielProfessions ref2 = ReferentielDataSingleton.INSTANCE
		.getData();
	assertNotNull(ref2);
	assertEquals(ref1, ref2);
    }
}
