package ch.ge.cti.ct.referentiels.etatCivil.service.jmx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.ge.cti.ct.act.configuration.DistributionFactory;
import ch.ge.cti.ct.referentiels.etatCivil.interfaces.ws.ReferentielEtatCivilSEI;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.service.jmx.StatistiquesServiceSingleton;

public class ReferentielEtatCivilMgtTest {

    private ReferentielEtatCivilMgt rmgt = null;

    @BeforeClass
    public static void setupClass() {
	DistributionFactory.setDisableJNDI(true);
    }

    @Before
    public void initialize() throws ExecutionException {
	rmgt = new ReferentielEtatCivilMgt();

	addStat();
    }

    private void addStat() throws ExecutionException {
	for (int i = 0; i < ReferentielEtatCivilSEI.class.getMethods().length; i++) {
	    StatistiquesServiceSingleton.instance.registerCall(
		    ReferentielEtatCivilSEI.class,
		    ReferentielEtatCivilSEI.class.getMethods()[i], null, 1);
	}
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