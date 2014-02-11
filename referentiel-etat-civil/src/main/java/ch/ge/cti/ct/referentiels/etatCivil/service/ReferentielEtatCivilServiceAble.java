package ch.ge.cti.ct.referentiels.etatCivil.service;

import java.util.List;

import javax.ejb.Local;

import ch.ge.cti.ct.referentiels.etatCivil.model.EtatCivil;
import ch.ge.cti.ct.referentiels.etatCivil.model.ReferentielEtatCivil;
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
public interface ReferentielEtatCivilServiceAble {

    /**
     * Retourne l'int�gralit� du r�f�rentiel des pays
     * 
     * @return r�f�rentiel en entier
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    ReferentielEtatCivil getReferentiel() throws ReferentielOfsException;

    /**
     * Retourne la liste des �tats civils<br/>
     * 
     * @return liste des �tats civils
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<EtatCivil> getEtatsCivils() throws ReferentielOfsException;

    /**
     * Retourne la liste des �tats civils<br/>
     * 
     * @param etatCivilId
     *            identifiant de la forme juridique
     * @return liste des �tats civils
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    EtatCivil getEtatCivil(final int etatCivilId)
	    throws ReferentielOfsException;

}
