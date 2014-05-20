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
	final ReferentielProfessions ref1 = ReferentielDataSingleton.instance
		.getData();
	assertNotNull(ref1);
	final ReferentielProfessions ref2 = ReferentielDataSingleton.instance
		.getData();
	assertNotNull(ref2);
	assertEquals(ref1, ref2);
    }

    @Test
    public void testGetReferentielFile() throws Exception {

	assertNotNull(ReferentielDataSingleton.instance.getReferentielFile());
	assertTrue(ReferentielDataSingleton.instance.getReferentielFile()
		.toURL().getFile().endsWith("CH1_BN+HCL_SBN+2.0.xml"));
    }
}
