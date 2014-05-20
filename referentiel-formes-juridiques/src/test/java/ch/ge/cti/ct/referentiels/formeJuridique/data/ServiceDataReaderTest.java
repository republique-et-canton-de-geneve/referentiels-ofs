package ch.ge.cti.ct.referentiels.formeJuridique.data;

import static org.junit.Assert.assertNotNull;

import java.net.URI;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.formeJuridique.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.formeJuridique.model.ReferentielFormesJuridiques;
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
	final ReferentielFormesJuridiques ref = service.read();
	assertNotNull(ref);
    }
}
