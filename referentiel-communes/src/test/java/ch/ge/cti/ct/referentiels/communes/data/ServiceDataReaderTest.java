package ch.ge.cti.ct.referentiels.communes.data;

import static org.junit.Assert.assertNotNull;

import java.net.URI;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.communes.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.communes.model.ReferentielCommunes;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

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
	final ReferentielCommunes ref = service.read();
	assertNotNull(ref);
    }
}
