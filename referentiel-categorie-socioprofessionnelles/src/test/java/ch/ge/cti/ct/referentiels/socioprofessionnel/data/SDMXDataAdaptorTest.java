package ch.ge.cti.ct.referentiels.socioprofessionnel.data;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.socioprofessionnel.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.ReferentielSocioprofessionnel;

public class SDMXDataAdaptorTest extends AbstractReferentielTest {

    private static SDMXDataAdaptor adaptor;

    @BeforeClass
    public static void setupSDMXDataAdaptorClass() throws Exception {
	adaptor = ctxt.getBean(SDMXDataAdaptor.class);
	assertNotNull(adaptor);
    }

    @Test
    public void testParse() throws Exception {
	final File file = new File("src/test/resources/CH1_BN+HCL_CSP+2.0.xml");
	final ReferentielSocioprofessionnel ref = adaptor.parse(file.toURI()
		.toURL());
	assertNotNull("Erreur d'instanciation du référentiel", ref);
    }
}
