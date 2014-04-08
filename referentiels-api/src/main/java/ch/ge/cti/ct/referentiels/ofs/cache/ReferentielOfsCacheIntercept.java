package ch.ge.cti.ct.referentiels.ofs.cache;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheLoader.InvalidCacheLoadException;

/**
 * Intercepteur des appels afin de g�rer le cache sur les m�thodes<br/>
 * Cet intercepteur exploite l'annotation
 * ch.ge.cti.ct.referentiels.communes.interfaces.ejb.Cachable
 * 
 * le tag NOSONAR est ajout� pour supprimer les warnings
 * <ul>
 * <li>"Signature Declare Throws Exception", car on impl�mente une interface
 * pr�d�finie</li>
 * <li>"Preserve Stack Trace", car on ne veut pas remonter la stack incluant des
 * exceptions des d�pendances</li>
 * </ul>
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
	    try {
		return cache.get(key, new Callable() {
		    @Override
		    public Object call() throws Exception {// NOSONAR
			return ctx.proceed();
		    }
		});
	    } catch (final InvalidCacheLoadException e) {
		// le service a retourn� null
		return null;
	    } catch (final Exception e) {
		throw new ReferentielOfsException(e.getCause());// NOSONAR
	    }
	}
    }

    /**
     * R�cup�ration de l'instance du cache si elle est d�finie pour la m�thode
     * 
     * @param method
     *            m�thode invoqu�e
     * @return cache
     */
    private Cache<String, ?> getCache(final Method method) {
	final Cachable cachable = method.getAnnotation(Cachable.class);
	if (cachable != null) {
	    return CacheManager.instance.getCache(cachable.name(),
		    cachable.stats(), cachable.size());
	}
	return null;
    }

    /**
     * Construction de la clef de cache pour l'invocation de la m�thode<br/>
     * Le premier param�tre d'appel est utilis� comme clef<br/>
     * Si la m�thode n'a pas de param�tre, alors on retourne une constante <br/>
     * TODO: rendre + g�n�rique
     * 
     * @param ctx
     *            contexte d'appel
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
