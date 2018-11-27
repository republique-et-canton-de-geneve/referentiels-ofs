package ch.ge.cti.ct.referentiels.ofs.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * Gestionnaire de cache <br/>
 * Singleton de centralisation des caches
 * 
 * @author DESMAZIERESJ
 * 
 */
public enum CacheManager {

    /** INSTANCE du singleton */
    INSTANCE;

    /** liste des caches instanciés */
    private final Map<String, Cache<String, Object>> caches = new HashMap<String, Cache<String, Object>>();

    /**
     * Recherche / instanciation d'une INSTANCE de cache
     * 
     * @param name
     *            nom du cache
     * @param stats
     *            activation des statistiques
     * @param size
     *            taille maximale du cache
     * @return cache
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Cache<String, Object> getCache(final String name,
	    final boolean stats, final int size) {
	if (caches.containsKey(name)) {
	    return caches.get(name);
	}
	final CacheBuilder builder = CacheBuilder.newBuilder().weakValues().maximumSize(size);
	if (stats) {
	    builder.recordStats();
	}
	final Cache<String, Object> cache = builder.build();
	caches.put(name, cache);
	return cache;
    }

    /**
     * Getter du cache<br/>
     * Exposé pour permettre une exploitation par le MBean
     * 
     * @return caches
     */
    public Map<String, Cache<String, Object>> getCaches() {
	return Collections.unmodifiableMap(caches);
    }

}
