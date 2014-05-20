package ch.ge.cti.ct.referentiels.pays.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.pays.model.ReferentielPaysTerritoires;

public class ReferentielDataSingletonTest extends AbstractReferentielTest {

    @Test
    public void testGetData() throws ReferentielOfsException {
	final ReferentielPaysTerritoires ref1 = ReferentielDataSingleton.instance
		.getData();
	assertNotNull(ref1);
	final ReferentielPaysTerritoires ref2 = ReferentielDataSingleton.instance
		.getData();
	assertNotNull(ref2);
	assertEquals(ref1, ref2);
    }

    @Test
    public void testGetReferentielFile() throws ReferentielOfsException {

	assertNotNull(ReferentielDataSingleton.instance.getReferentielFile());
	assertTrue(ReferentielDataSingleton.instance.getReferentielFile()
		.getFile().endsWith("CH1_RN+HCL_COUNTRIESGEO+1.0.xml"));
    }
}
