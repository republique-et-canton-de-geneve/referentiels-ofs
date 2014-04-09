package ch.ge.cti.ct.referentiels.formeJuridique.data;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.formeJuridique.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.formeJuridique.model.ReferentielFormesJuridiques;

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
	final ReferentielFormesJuridiques ref = adaptor.parse(file.toURI()
		.toURL());
	assertNotNull("Erreur d'instanciation du référentiel", ref);
    }

    @Test
    public void testFactory() throws Exception {
	assertNotNull(SDMXDataAdaptor.Factory.getInstance());
    }
}
