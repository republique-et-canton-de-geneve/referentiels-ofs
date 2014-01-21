package ch.ge.cti.ct.referentiels.ofs.service.jmx;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

/**
 * Singleton de stockage des donn�es statistiques d'utilisation du service
 * 
 * @author desmazieresj
 * 
 */
public enum StatistiquesServiceSingleton {
	/** instance du singleton */
	instance;

	/** donn�es statistiques par m�thode / param�tre */
	private final Map<Call, Stat> stats = new Hashtable<Call, Stat>();

	/**
	 * Enregistrement d'un appel de m�thode
	 * 
	 * @param method identifiant de la m�thode
	 * @param params liste des param�tres d'appel
	 * @param laps temps d'ex�cution (en nanosecondes)
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
	 * R�initialisation des statistiques
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
