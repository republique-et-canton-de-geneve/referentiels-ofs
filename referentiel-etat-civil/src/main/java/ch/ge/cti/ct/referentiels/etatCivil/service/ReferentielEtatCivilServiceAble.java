package ch.ge.cti.ct.referentiels.etatCivil.service;

import java.util.List;

import javax.ejb.Local;

import ch.ge.cti.ct.referentiels.etatCivil.model.EtatCivil;
import ch.ge.cti.ct.referentiels.etatCivil.model.ReferentielEtatCivil;
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
public interface ReferentielEtatCivilServiceAble {

    /**
     * Retourne l'intégralité du référentiel des pays
     * 
     * @return référentiel en entier
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    ReferentielEtatCivil getReferentiel() throws ReferentielOfsException;

    /**
     * Retourne la liste des états civils<br/>
     * 
     * @return liste des états civils
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<EtatCivil> getEtatsCivils() throws ReferentielOfsException;

    /**
     * Retourne la liste des états civils<br/>
     * 
     * @param etatCivilId
     *            identifiant de la forme juridique
     * @return liste des états civils
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    EtatCivil getEtatCivil(final int etatCivilId)
	    throws ReferentielOfsException;

}
