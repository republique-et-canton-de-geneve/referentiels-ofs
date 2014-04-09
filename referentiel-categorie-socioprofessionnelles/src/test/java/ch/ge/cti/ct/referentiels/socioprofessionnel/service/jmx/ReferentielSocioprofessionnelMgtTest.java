package ch.ge.cti.ct.referentiels.socioprofessionnel.service.jmx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.ge.cti.ct.act.configuration.DistributionFactory;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.cache.CacheManager;
import ch.ge.cti.ct.referentiels.ofs.service.jmx.StatistiquesServiceSingleton;
import ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws.ReferentielSocioprofessionnelSEI;

import com.google.common.cache.Cache;

public class ReferentielSocioprofessionnelMgtTest {

    private static final String[][] DATA = { { "call1", "_call1_" },
	    { "call1", "_call2_" }, { "call1", "_call3_" },
	    { "call2", "_call1_" }, { "call3", "_call1_" },
	    { "call3", "_call2_" } };

    private ReferentielSocioprofessionnelMgt rmgt = null;

    @BeforeClass
    public static void setupClass() {
	DistributionFactory.setDisableJNDI(true);
    }

    @Before
    public void initialize() throws ExecutionException {
	rmgt = new ReferentielSocioprofessionnelMgt();

	for (final String[] data : DATA) {
	    addStat(data[0], data[1]);
	}
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void addStat(final String key, final String value)
	    throws ExecutionException {
	getCache().get(key, new Callable() {
	    @Override
	    public Object call() throws Exception {
		return value;
	    }
	});
	for (int i = 0; i < ReferentielSocioprofessionnelSEI.class.getMethods().length; i++) {
	    StatistiquesServiceSingleton.instance.registerCall(
		    ReferentielSocioprofessionnelSEI.class,
		    ReferentielSocioprofessionnelSEI.class.getMethods()[i],
		    null, 1);
	}
    }

    private Cache<String, ?> getCache() {
	return CacheManager.instance.getCache("divisions", true, 100);
    }

    @Test
    public void testGetReferentielFile() throws ReferentielOfsException {
	assertTrue(rmgt.getReferentielFile().getFile()
		.endsWith("CH1_BN+HCL_CSP+2.0.xml"));
    }

    @Test
    public void testDisplayStatitiques() throws ExecutionException {
	final String XML = rmgt.displayStatitiques("XML");
	assertTrue(XML.length() > 20);
	final String HTML = rmgt.displayStatitiques("HTML");
	assertFalse(XML.equals(HTML));
	final String BLANK = rmgt.displayStatitiques("");
	assertEquals(XML, BLANK);

	addStat("key", "value");
	for (final String[] data : DATA) {
	    addStat(data[0], data[1]);
	}
	assertFalse(XML.equals(rmgt.displayStatitiques("XML")));

	rmgt.resetStatistiques();
    }

}
