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
	final ReferentielCommunes ref1 = ReferentielDataSingleton.instance
		.getData();
	assertNotNull(ref1);
	final ReferentielCommunes ref2 = ReferentielDataSingleton.instance
		.getData();
	assertNotNull(ref2);
	assertEquals(ref1, ref2);
    }

    @Test
    public void testGetReferentielFile() throws Exception {

	assertNotNull(ReferentielDataSingleton.instance.getReferentielFile());
	assertTrue(ReferentielDataSingleton.instance.getReferentielFile()
		.toURL().getFile().endsWith("CH1_RN+HCL_HGDE_HIST+1.0.xml"));
    }

}
