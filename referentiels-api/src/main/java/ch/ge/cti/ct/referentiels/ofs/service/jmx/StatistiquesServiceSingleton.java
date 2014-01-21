package ch.ge.cti.ct.referentiels.ofs.service.jmx;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

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
	 * @param method identifiant de la méthode
	 * @param params liste des paramètres d'appel
	 * @param laps temps d'exécution (en nanosecondes)
	 */
	public void registerCall(final Method method, final Object[] params,
			final long laps) {
		final Call key = new Call().withMethod(method).withParameters(params);
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
	public void reset() {
		stats.clear();
	}

	/**
	 * Lecture des statistiques courantes
	 * 
	 * @return statistiques (cumul depuis le dernier lancement uniquement)
	 */
	public Map<Call, Stat> getStatistiques() {
		return stats;
	}
}
