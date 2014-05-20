package ch.ge.cti.ct.referentiels.etatCivil.data;

import static org.junit.Assert.assertNotNull;

import java.net.URI;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.etatCivil.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.etatCivil.model.ReferentielEtatCivil;
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
	final ReferentielEtatCivil ref = service.read();
	assertNotNull(ref);
    }
}
