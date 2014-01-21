package ch.ge.cti.ct.referentiels.communes.data;

import static org.junit.Assert.assertNotNull;

import java.net.URL;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.communes.model.ReferentielCommunes;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

public class ServiceDataReaderTest extends AbstractReferentielTest {

	@Test
	public void testRead() throws ReferentielOfsException {
		final ServiceDataReader service = new ServiceDataReader();
		final ReferentielCommunes ref = service.read();
		assertNotNull(ref);
	}

	@Test
	public void testGetURL() throws ReferentielOfsException {
		final ServiceDataReader service = new ServiceDataReader();
		final URL url = service.getURL();
		assertNotNull(url);
	}
}
