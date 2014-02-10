package ch.ge.cti.ct.referentiels.formeJuridique.service;

import java.util.List;

import javax.ejb.Local;

import ch.ge.cti.ct.referentiels.formeJuridique.model.FormeJuridique;
import ch.ge.cti.ct.referentiels.formeJuridique.model.ReferentielFormesJuridiques;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * D�finition du contrat du service de r�f�rentiel des pays<br/>
 * Le r�f�rentiel provient de l'office statistique Suisse d�pendant de
 * l'administration f�d�rale<br/>
 * 
 * @author desmazi�resj
 * 
 */
@Local
public interface ReferentielFormesJuridiquesServiceAble {

    /**
     * Retourne l'int�gralit� du r�f�rentiel des pays
     * 
     * @return r�f�rentiel en entier
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    ReferentielFormesJuridiques getReferentiel() throws ReferentielOfsException;

    /**
     * Retourne la liste des formes juridiques<br/>
     * 
     * @return liste des formes juridiques
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<FormeJuridique> getFormesJuridiques() throws ReferentielOfsException;

    /**
     * Retourne la liste des formes juridiques<br/>
     * 
     * @param formeJuridiqueId
     *            identifiant de la forme juridique
     * @return liste des formes juridiques
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    FormeJuridique getFormeJuridique(final int formeJuridiqueId)
	    throws ReferentielOfsException;

}
