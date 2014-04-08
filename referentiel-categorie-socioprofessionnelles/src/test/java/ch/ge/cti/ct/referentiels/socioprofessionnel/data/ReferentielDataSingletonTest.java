package ch.ge.cti.ct.referentiels.socioprofessionnel.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.socioprofessionnel.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.ReferentielSocioprofessionnel;

public class ReferentielDataSingletonTest extends AbstractReferentielTest {

    @Test
    public void testGetData() throws ReferentielOfsException {
	final ReferentielSocioprofessionnel ref1 = ReferentielDataSingleton.instance
		.getData();
	assertNotNull(ref1);
	final ReferentielSocioprofessionnel ref2 = ReferentielDataSingleton.instance
		.getData();
	assertNotNull(ref2);
	assertEquals(ref1, ref2);
    }

}