package ch.ge.cti.ct.referentiels.etatCivil.data;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.etatCivil.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.etatCivil.model.ReferentielEtatCivil;

public class SDMXDataAdaptorTest extends AbstractReferentielTest {

    private static SDMXDataAdaptor adaptor;

    @BeforeClass
    public static void setupSDMXDataAdaptorClass() throws Exception {
	adaptor = ctxt.getBean(SDMXDataAdaptor.class);
	assertNotNull(adaptor);
    }

    @Test
    public void testParse() throws Exception {
	final File file = new File("src/test/resources/" + SDMX_FILE);
	final ReferentielEtatCivil ref = adaptor.parse(file.toURI());
	assertNotNull("Erreur d'instanciation du référentiel", ref);
    }

    @Test
    public void testFactory() throws Exception {
	assertNotNull(SDMXDataAdaptor.Factory.getInstance());
    }
}
