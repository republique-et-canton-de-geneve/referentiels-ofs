package ch.ge.cti.ct.referentiels.pays.data;

import static org.junit.Assert.assertNotNull;

import java.net.URL;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.pays.model.ReferentielPaysTerritoires;

public class ServiceDataReaderTest extends AbstractReferentielTest {

	@Test
	public void testGetXmlFile() throws ReferentielOfsException {
		final ServiceDataReader service = new ServiceDataReader();
		final URL url = service.getXmlFile();
		assertNotNull(url);
	}

	@Test
	public void testRead() throws ReferentielOfsException {
		final ServiceDataReader service = new ServiceDataReader();
		final ReferentielPaysTerritoires ref = service.read();
		assertNotNull(ref);
	}
}
