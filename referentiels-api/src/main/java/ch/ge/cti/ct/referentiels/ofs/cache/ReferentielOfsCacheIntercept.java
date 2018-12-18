/*
 * Referentiels OFS
 *
 * Copyright (C) 2018 République et canton de Genève
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

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import ch.ge.cti.ct.referentiels.ofs.Loggable;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheLoader.InvalidCacheLoadException;

/**
 * Intercepteur des appels afin de g�rer le cache sur les m�thodes<br/>
 * Cet intercepteur exploite l'annotation
 * ch.ge.cti.ct.referentiels.communes.interfaces.ejb.Cachable
 * 
 * @author DESMAZIERESJ
 * 
 */
public class ReferentielOfsCacheIntercept {

    private static final String DEFAULT_KEY = "_nokey_";

    /**
     * interception de toutes les m�thodes pour gestion du cache
     * 
     * @param ctx
     *            contexte d'appel
     * @return r�sultat du traitement
     * @throws Exception
     *             toute exception ..
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @AroundInvoke
    public Object processCache(final InvocationContext ctx) throws Exception {// NOSONAR
	final Cache<String, ?> cache = getCache(ctx.getMethod());

	if (cache == null) {
	    return ctx.proceed();
	} else {
	    final String key = getKey(ctx);
	    if (key == null) {
		return null;
	    }
	    final boolean[] fromCache = { true };
	    try {
		return cache.get(key, new Callable() {
		    @Override
		    public Object call() throws Exception {// NOSONAR
			fromCache[0] = false;
			return ctx.proceed();
		    }
		});
	    } catch (final InvalidCacheLoadException e) {
		// le service a retourn� null
		return null;
	    } finally {
		final Loggable service = (Loggable) ctx.getTarget();
		if (service.log().isDebugEnabled()) {
		    final StringBuilder sb = new StringBuilder(ctx.getMethod()
			    .getName());
		    sb.append('(');
		    for (final Object param : ctx.getParameters()) {
			if (sb.length() > 0) {
			    sb.append(',');
			}
			sb.append('\'');
			sb.append(param.toString());
			sb.append('\'');
		    }
		    sb.append(')');
		    if (fromCache[0]) {
			sb.append(" (from cache)");
		    }
		    service.log().debug(sb.toString());
		}
	    }
	}
    }

    /**
     * R�cup�ration de l'INSTANCE du cache si elle est d�finie pour la m�thode
     * 
     * @param method
     *            m�thode invoqu�e
     * @return cache
     */
    private Cache<String, ?> getCache(final Method method) {
	final Cachable cachable = method.getAnnotation(Cachable.class);
	if (cachable != null) {
	    return CacheManager.INSTANCE.getCache(cachable.name(),
		    cachable.stats(), cachable.size());
	}
	return null;
    }

    /**
     * Construction de la clef de cache pour l'invocation de la m�thode<br/>
     * Le premier param�tre d'appel est utilis� comme clef<br/>
     * Si la m�thode n'a pas de param�tre, alors on retourne une constante <br/>
     *
     * @param ctx contexte d'appel
     * @return clef de cache
     */
    private String getKey(final InvocationContext ctx) {
	if (ctx.getParameters().length > 0) {
	    if (ctx.getParameters()[0] == null) {
		return null;
	    }
	    return ctx.getParameters()[0].toString();
	} else {
	    return DEFAULT_KEY;
	}
    }
}
