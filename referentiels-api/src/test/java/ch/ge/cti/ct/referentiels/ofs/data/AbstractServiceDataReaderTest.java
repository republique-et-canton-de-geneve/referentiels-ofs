package ch.ge.cti.ct.referentiels.ofs.data;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

public class AbstractServiceDataReaderTest extends AbstractDataTest {

    final TestServiceDataReader serviceDataReader = new TestServiceDataReader();

    @Test
    public void testRead() throws Exception {
	final String result = serviceDataReader.read();
	assertEquals("Le résultat du parsing est incorrect", "OK", result);
    }

    @Test
    public void testGetXmlFile() throws Exception {
	final URI url = serviceDataReader.getXmlFile();
	assertEquals("URL du fichier XML incorrecte", "test.xml", url.getPath()
		.substring(url.getPath().lastIndexOf('/') + 1));
    }

    public static class TestServiceDataReader extends
	    AbstractServiceDataReader<String> {

	@Override
	protected String getConfigurationEntry() {
	    return "referentiel.test.file";
	}

	@Override
	protected AbstractSDMXDataAdaptor<String> getDataAdaptor() {
	    return new TestSDMXDataAdaptor(new SDMXParser(null, null));
	}
    }

    public static class TestSDMXDataAdaptor extends
	    AbstractSDMXDataAdaptor<String> {

	protected TestSDMXDataAdaptor(final SDMXParser parser) {
	    super(parser);
	}

	@Override
	public String parse(final URI urlXML) throws ReferentielOfsException {
	    return "OK";
	}
    }

}
