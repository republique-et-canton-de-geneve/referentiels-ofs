package ch.ge.cti.ct.referentiels.socioprofessionnel.data;

import static org.junit.Assert.assertNotNull;

import java.net.URI;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.socioprofessionnel.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.ReferentielSocioprofessionnel;

public class ServiceDataReaderTest extends AbstractReferentielTest {

    @Test
    public void testGetXmlFile() throws ReferentielOfsException {
	final ServiceDataReader service = new ServiceDataReader();
	final URI url = service.getXmlFile();
	assertNotNull(url);
    }

    @Test
    public void testRead() throws ReferentielOfsException {
	final ServiceDataReader service = new ServiceDataReader();
	final ReferentielSocioprofessionnel ref = service.read();
	assertNotNull(ref);
    }
}
