package ch.ge.cti.ct.referentiels.ofs.service.jmx;

import java.net.URI;

import org.jboss.system.ServiceMBean;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * Définition du service JMX
 * 
 * @author DESMAZIERESJ
 * 
 */
public interface ReferentielMgtMBean extends ServiceMBean {
    /**
     * nom du fichier référentiel des communes
     * 
     * @return nom du fichier XML des communes
     * @throws ReferentielOfsException
     *             erreur de lecture
     */
    URI getReferentielFile() throws ReferentielOfsException;

    /**
     * Lectures des données statistiques au format spécifié
     * 
     * @param mode
     *            format des données: XML ou HTML (en cas d'erreur, c'est XML)
     * @return données statistiques mises en forme
     */
    String displayStatitiques(final String mode);

    /**
     * Réinitialisation des statistiques
     */
    void resetStatistiques();

}
