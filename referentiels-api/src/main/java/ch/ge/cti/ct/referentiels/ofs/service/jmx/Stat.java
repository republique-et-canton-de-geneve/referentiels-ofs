package ch.ge.cti.ct.referentiels.ofs.service.jmx;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Mod�lisation des donn�es statistiques pour chaque m�thode <br/>
 * <ul>
 * <li>nombre d'appels</li>
 * <li>temps total d'ex�cution en nanosecondes</li>
 * </ul>
 * 
 * @author DESMAZIERESJ
 * 
 */
public class Stat {
	/** nombre d'appels � la m�thode */
	private AtomicLong calls = new AtomicLong(0);
	/** temps total d'ex�cution en nanosecondes */
	private AtomicLong laps = new AtomicLong(0);

	public AtomicLong getCalls() {
		return calls;
	}

	public AtomicLong getLaps() {
		return laps;
	}

}
