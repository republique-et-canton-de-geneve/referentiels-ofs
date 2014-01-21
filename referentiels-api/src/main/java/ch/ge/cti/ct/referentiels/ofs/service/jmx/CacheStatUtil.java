package ch.ge.cti.ct.referentiels.ofs.service.jmx;

import com.google.common.cache.Cache;

public final class CacheStatUtil {

	private CacheStatUtil() {
	}

	/**
	 * rendering des données de cache
	 * 
	 * @param cacheName nom du cache
	 * @param cache cache
	 * @param xml output
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
					.append((long) cache.stats().totalLoadTime() / 1000000l)
					.append("\"");
			xml.append(" averageLoadPenalty=\"")
					.append((long) cache.stats().averageLoadPenalty() / 1000000l)
					.append("\"");
		}
		xml.append("/>\n");
	}

}
