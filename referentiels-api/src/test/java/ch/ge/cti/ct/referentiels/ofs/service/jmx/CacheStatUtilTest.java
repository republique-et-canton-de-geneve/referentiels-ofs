package ch.ge.cti.ct.referentiels.ofs.service.jmx;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Callable;

import org.junit.Before;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.cache.CacheManager;

import com.google.common.cache.Cache;

public class CacheStatUtilTest {
    private Cache<String, ? extends Object> cache = null;

    @Before
    public void setup() throws Exception {
	cache = CacheManager.instance.getCache("_cache_", true, 10);
    }

    @Test
    public void testRenderCacheStats() throws Exception {
	StringBuilder xml = new StringBuilder();

	CacheStatUtil.renderCacheStats("_cache_", cache, xml);
	assertEquals(
		"<cache name=\"_cache_\" requestCount=\"0\" hitCount=\"0\" missCount=\"0\" loadCount=\"0\" loadSuccessCount=\"0\" loadExceptionCount=\"0\" evictionCount=\"0\"",
		xml.toString().trim().substring(0, 141));

	xml = new StringBuilder();
	populate("method1");
	CacheStatUtil.renderCacheStats("_cache_", cache, xml);
	assertEquals(
		"<cache name=\"_cache_\" requestCount=\"1\" hitCount=\"0\" missCount=\"1\" loadCount=\"1\" loadSuccessCount=\"1\" loadExceptionCount=\"0\" evictionCount=\"0\"",
		xml.toString().trim().substring(0, 141));

	xml = new StringBuilder();
	populate("method2");
	CacheStatUtil.renderCacheStats("_cache_", cache, xml);
	assertEquals(
		"<cache name=\"_cache_\" requestCount=\"2\" hitCount=\"0\" missCount=\"2\" loadCount=\"2\" loadSuccessCount=\"2\" loadExceptionCount=\"0\" evictionCount=\"0\"",
		xml.toString().trim().substring(0, 141));

	xml = new StringBuilder();
	populate("method2");
	CacheStatUtil.renderCacheStats("_cache_", cache, xml);
	assertEquals(
		"<cache name=\"_cache_\" requestCount=\"3\" hitCount=\"1\" missCount=\"2\" loadCount=\"2\" loadSuccessCount=\"2\" loadExceptionCount=\"0\" evictionCount=\"0\"",
		xml.toString().trim().substring(0, 141));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void populate(final String cachedValue) throws Exception {
	cache.get(cachedValue, new Callable() {
	    @Override
	    public Object call() throws Exception {
		return true;
	    }
	});
    }

}
