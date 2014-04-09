package ch.ge.cti.ct.referentiels.communes.service.jmx;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.cache.CacheManager;

import com.google.common.cache.Cache;

public class DisplayModeTest {

    private static final String[][] DATA = { { "call1", "_call1_" },
	    { "call1", "_call2_" }, { "call1", "_call3_" },
	    { "call2", "_call1_" }, { "call3", "_call1_" },
	    { "call3", "_call2_" } };

    private Cache<String, ?> cache = null;

    @Before
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void initializeClass() throws Exception {
	cache = CacheManager.instance.getCache("JUnit", true, 100);
	for (final String[] data : DATA) {
	    cache.get(data[0], new Callable() {
		@Override
		public Object call() throws Exception {
		    return data[1];
		}
	    });
	}
    }

    @Test
    public void testXML() {
	final String XML = DisplayMode.XML.render();
	assertNotNull(XML);
	assertTrue(StringUtils.isNotBlank(XML));
    }

    @Test
    public void testHTML() {
	final String HTML = DisplayMode.HTML.render();
	assertNotNull(HTML);
	assertTrue(StringUtils.isNotBlank(HTML));
    }
}
