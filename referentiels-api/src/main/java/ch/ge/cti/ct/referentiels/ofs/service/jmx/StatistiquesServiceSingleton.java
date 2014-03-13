package ch.ge.cti.ct.referentiels.ofs.service.jmx;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

/**
 * Singleton de stockage des données statistiques d'utilisation du service
 * 
 * @author desmazieresj
 * 
 */
public enum StatistiquesServiceSingleton {
    /** instance du singleton */
    instance;

    /** données statistiques par méthode / paramètre */
    private final Map<Call, Stat> stats = new Hashtable<Call, Stat>();

    /**
     * Enregistrement d'un appel de méthode
     * 
     * @param clazz
     *            identifiant de la classe
     * @param method
     *            identifiant de la méthode
     * @param params
     *            liste des paramètres d'appel
     * @param laps
     *            temps d'exécution (en nanosecondes)
     */
    public void registerCall(final Class<? extends Object> clazz,
	    final Method method, final Object[] params, final long laps) {
	final Call key = new Call().withClasse(clazz).withMethod(method);
	Stat count = null;
	if (!stats.containsKey(key)) {
	    count = new Stat();
	    stats.put(key, count);
	} else {
	    count = stats.get(key);
	}
	count.getCalls().incrementAndGet();
	count.getLaps().addAndGet(laps);
    }

    /**
     * Réinitialisation des statistiques
     */
    public void reset(final Class<? extends Object> clazz) {
	final Call[] calls = new Call[stats.size()];
	stats.keySet().toArray(calls);
	for (final Call key : calls) {
	    if (key.getClasse().equals(clazz.getName())) {
		stats.remove(key);
	    }
	}
    }

    /**
     * Lecture des statistiques courantes
     * 
     * @return statistiques (cumul depuis le dernier lancement uniquement)
     */
    public Map<Call, Stat> getStatistiques(final Class<? extends Object> clazz) {
	final Map<Call, Stat> filteredMap = Maps.filterKeys(stats,
		new ClassFilterPredicate(clazz.getName()));
	return filteredMap;
    }

    private static class ClassFilterPredicate implements Predicate<Call> {
	private final String classeFilter;

	ClassFilterPredicate(final String classeFilter) {
	    this.classeFilter = classeFilter;
	}

	@Override
	public boolean apply(final Call call) {
	    return classeFilter.equals(call.getClasse());
	}

    }
}
