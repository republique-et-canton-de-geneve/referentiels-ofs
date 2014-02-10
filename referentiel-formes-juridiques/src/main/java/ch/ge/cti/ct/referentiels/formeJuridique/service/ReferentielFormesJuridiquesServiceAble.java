package ch.ge.cti.ct.referentiels.formeJuridique.service;

import java.util.List;

import javax.ejb.Local;

import ch.ge.cti.ct.referentiels.formeJuridique.model.FormeJuridique;
import ch.ge.cti.ct.referentiels.formeJuridique.model.ReferentielFormesJuridiques;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * Définition du contrat du service de référentiel des pays<br/>
 * Le référentiel provient de l'office statistique Suisse dépendant de
 * l'administration fédérale<br/>
 * 
 * @author desmazièresj
 * 
 */
@Local
public interface ReferentielFormesJuridiquesServiceAble {

    /**
     * Retourne l'intégralité du référentiel des pays
     * 
     * @return référentiel en entier
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
