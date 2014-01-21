package ch.ge.cti.ct.referentiels.ofs.service.jmx;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Modélisation des données statistiques pour chaque méthode <br/>
 * <ul>
 * <li>nombre d'appels</li>
 * <li>temps total d'exécution en nanosecondes</li>
 * </ul>
 * 
 * @author DESMAZIERESJ
 * 
 */
public class Stat {
	/** nombre d'appels à la méthode */
	private AtomicLong calls = new AtomicLong(0);
	/** temps total d'exécution en nanosecondes */
	private AtomicLong laps = new AtomicLong(0);

	public AtomicLong getCalls() {
		return calls;
	}

	public AtomicLong getLaps() {
		return laps;
	}

}
