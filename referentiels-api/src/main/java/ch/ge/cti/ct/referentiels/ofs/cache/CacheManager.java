/*
 * Referentiels OFS
 *
 * Copyright (C) 2018 RÃ©publique et canton de GenÃ¨ve
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
