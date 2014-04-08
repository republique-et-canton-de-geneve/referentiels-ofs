package ch.ge.cti.ct.referentiels.ofs.service.jmx;

import com.google.common.cache.Cache;

/**
 * Classe de rendering XML des statistiques pour un cache donné
 * 
 * @author DESMAZIERESJ
 * 
 */
public final class CacheStatUtil {

    private static final long MICROSECONDS_COEFF = 1000000l;

    private CacheStatUtil() {
    }

    /**
     * rendering des données de cache
     * 
     * @param cacheName
     *            nom du cache
     * @param cache
     *            cache
     * @param xml
     *            output
     */
    public static void renderCacheStats(final String cacheName,
	    final Cache<String, ? extends Object> cache, final StringBuilder xml) {
	xml.append("    <cache name=\"").append(cacheName).append("\"");
	if (cache != null && cache.stats() != null) {
	    xml.append(" requestCount=\"").append(cache.stats().requestCount())
		    .append("\"");
	    xml.append(" hitCount=\"").append(cache.stats().hitCount())
		    .append("\"");
	    xml.append(" missCount=\"").append(cache.stats().missCount())
		    .append("\"");
	    xml.append(" loadCount=\"").append(cache.stats().loadCount())
		    .append("\"");
	    xml.append(" loadSuccessCount=\"")
		    .append(cache.stats().loadSuccessCount()).append("\"");
	    xml.append(" loadExceptionCount=\"")
		    .append(cache.stats().loadExceptionCount()).append("\"");
	    xml.append(" evictionCount=\"")
		    .append(cache.stats().evictionCount()).append("\"");
	    xml.append(" totalLoadTime=\"")
		    .append(cache.stats().totalLoadTime() / MICROSECONDS_COEFF)
		    .append("\"");
	    xml.append(" averageLoadPenalty=\"")
		    .append((long) cache.stats().averageLoadPenalty()
			    / MICROSECONDS_COEFF).append("\"");
	}
	xml.append("/>\n");
    }
}
