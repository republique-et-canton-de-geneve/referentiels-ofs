package ch.ge.cti.ct.referentiels.formeJuridique.service.jmx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.ge.cti.ct.act.configuration.DistributionFactory;
import ch.ge.cti.ct.referentiels.formeJuridique.interfaces.ws.ReferentielFormesJuridiquesSEI;
import ch.ge.cti.ct.referentiels.ofs.service.jmx.StatistiquesServiceSingleton;

public class ReferentielFormesJuridiquesMgtTest {

    private ReferentielFormesJuridiquesMgt rmgt = null;

    @BeforeClass
    public static void setupClass() {
	DistributionFactory.setDisableJNDI(true);
    }

    @Before
    public void initialize() throws ExecutionException {
	rmgt = new ReferentielFormesJuridiquesMgt();

	addStat();
    }

    private void addStat() throws ExecutionException {
	for (int i = 0; i < ReferentielFormesJuridiquesSEI.class.getMethods().length; i++) {
	    StatistiquesServiceSingleton.instance.registerCall(
		    ReferentielFormesJuridiquesSEI.class,
		    ReferentielFormesJuridiquesSEI.class.getMethods()[i], null,
		    1);
	}
    }

    @Test
    public void testGetReferentielFile() throws Exception {
	assertTrue(rmgt.getReferentielFile().toURL().getFile()
		.endsWith("CH1_BUR+CL_LEGALFORMS+2.0.xml"));
    }

    @Test
    public void testDisplayStatitiques() throws ExecutionException {
	final String XML = rmgt.displayStatitiques("XML");
	assertTrue(XML.length() > 20);
	final String HTML = rmgt.displayStatitiques("HTML");
	assertFalse(XML.equals(HTML));
	final String BLANK = rmgt.displayStatitiques("");
	assertEquals(XML, BLANK);
	rmgt.resetStatistiques();
    }

}
