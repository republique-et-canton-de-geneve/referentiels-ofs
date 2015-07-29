package ch.ge.cti.ct.referentiels.communes.service.jmx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.communes.interfaces.ws.ReferentielCommunesSEI;
import ch.ge.cti.ct.referentiels.ofs.cache.CacheManager;
import ch.ge.cti.ct.referentiels.ofs.service.jmx.StatistiquesServiceSingleton;

import com.google.common.cache.Cache;

public class ReferentielCommunesMgtTest {

    private static final String[][] DATA = { { "call1", "_call1_" },
	    { "call1", "_call2_" }, { "call1", "_call3_" },
	    { "call2", "_call1_" }, { "call3", "_call1_" },
	    { "call3", "_call2_" } };

    private ReferentielCommunesMgt rmgt = null;

    @Before
    public void initialize() throws ExecutionException {
	rmgt = new ReferentielCommunesMgt();

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
	for (int i = 0; i < ReferentielCommunesSEI.class.getMethods().length; i++) {
	    StatistiquesServiceSingleton.instance.registerCall(
		    ReferentielCommunesSEI.class,
		    ReferentielCommunesSEI.class.getMethods()[i], null, 1);
	}
    }

    private Cache<String, ?> getCache() {
	return CacheManager.instance.getCache("cantons", true, 100);
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
