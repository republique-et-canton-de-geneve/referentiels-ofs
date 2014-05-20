package ch.ge.cti.ct.referentiels.professions.data;

import static org.junit.Assert.assertNotNull;

import java.net.URI;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.professions.model.ReferentielProfessions;

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
	final ReferentielProfessions ref = service.read();
	assertNotNull(ref);
    }
}
