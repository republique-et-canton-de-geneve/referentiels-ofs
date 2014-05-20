package ch.ge.cti.ct.referentiels.ofs.service.jmx;

import java.net.URI;

import org.jboss.system.ServiceMBean;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * D�finition du service JMX
 * 
 * @author DESMAZIERESJ
 * 
 */
public interface ReferentielMgtMBean extends ServiceMBean {
    /**
     * nom du fichier r�f�rentiel des communes
     * 
     * @return nom du fichier XML des communes
     * @throws ReferentielOfsException
     *             erreur de lecture
     */
    URI getReferentielFile() throws ReferentielOfsException;

    /**
     * Lectures des donn�es statistiques au format sp�cifi�
     * 
     * @param mode
     *            format des donn�es: XML ou HTML (en cas d'erreur, c'est XML)
     * @return donn�es statistiques mises en forme
     */
    String displayStatitiques(final String mode);

    /**
     * R�initialisation des statistiques
     */
    void resetStatistiques();

}
