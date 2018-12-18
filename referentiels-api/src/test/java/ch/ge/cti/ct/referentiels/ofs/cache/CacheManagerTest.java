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
