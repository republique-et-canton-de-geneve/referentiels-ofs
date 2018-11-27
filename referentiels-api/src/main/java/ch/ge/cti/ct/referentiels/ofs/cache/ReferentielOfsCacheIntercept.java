package ch.ge.cti.ct.referentiels.ofs.cache;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import ch.ge.cti.ct.referentiels.ofs.Loggable;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheLoader.InvalidCacheLoadException;

/**
 * Intercepteur des appels afin de gérer le cache sur les méthodes<br/>
 * Cet intercepteur exploite l'annotation
 * ch.ge.cti.ct.referentiels.communes.interfaces.ejb.Cachable
 * 
 * @author DESMAZIERESJ
 * 
 */
public class ReferentielOfsCacheIntercept {

    private static final String DEFAULT_KEY = "_nokey_";

    /**
     * interception de toutes les méthodes pour gestion du cache
     * 
     * @param ctx
     *            contexte d'appel
     * @return résultat du traitement
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
		// le service a retourné null
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
     * Récupération de l'INSTANCE du cache si elle est définie pour la méthode
     * 
     * @param method
     *            méthode invoquée
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
     * Construction de la clef de cache pour l'invocation de la méthode<br/>
     * Le premier paramètre d'appel est utilisé comme clef<br/>
     * Si la méthode n'a pas de paramètre, alors on retourne une constante <br/>
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
