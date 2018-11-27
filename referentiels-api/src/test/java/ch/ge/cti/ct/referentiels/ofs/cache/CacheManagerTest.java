package ch.ge.cti.ct.referentiels.ofs.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.google.common.cache.Cache;

public class CacheManagerTest {

    @Test
    public void testGetCache() {
	final Cache<String, ? extends Object> cache0 = CacheManager.INSTANCE
		.getCache("cache0", false, 100);
	assertNotNull("Erreur d'instanciation du cache", cache0);
    }

    @Test
    public void testGetCaches() {
	final int initCache = CacheManager.INSTANCE.getCaches().size();
	final Cache<String, ? extends Object> cache1 = CacheManager.INSTANCE
		.getCache("cache1", false, 100);
	assertNotNull("Erreur d'instanciation du cache", cache1);
	assertEquals("Erreur de récupération des caches", initCache + 1,
		CacheManager.INSTANCE.getCaches().size());
	final Cache<String, ? extends Object> cache2 = CacheManager.INSTANCE
		.getCache("cache2", false, 100);
	assertNotNull("Erreur d'instanciation du cache", cache2);
	assertEquals("Erreur de récupération des caches", initCache + 2,
		CacheManager.INSTANCE.getCaches().size());
	final Cache<String, ? extends Object> cache3 = CacheManager.INSTANCE
		.getCache("cache2", false, 100);
	assertNotNull("Erreur d'instanciation du cache", cache3);
	assertEquals("Erreur de récupération des caches", initCache + 2,
		CacheManager.INSTANCE.getCaches().size());
    }

}
