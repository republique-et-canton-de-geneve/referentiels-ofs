package ch.ge.cti.ct.referentiels.socioprofessionnel.service;

import java.util.List;

import javax.ejb.Local;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau1;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau2;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.ReferentielSocioprofessionnel;

/**
 * D�finition du contrat du service de r�f�rentiel des cat�gories
 * socioprofessionnelles<br/>
 * Le r�f�rentiel provient de l'office statistique Suisse d�pendant de
 * l'administration f�d�rale<br/>
 * 
 * @author desmazi�resj
 * 
 */
@Local
public interface ReferentielSocioprofessionnelServiceAble {

    /**
     * Retourne l'int�gralit� du r�f�rentiel des cat�gories
     * socioprofessionnelles
     * 
     * @return r�f�rentiel en entier
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    ReferentielSocioprofessionnel getReferentiel()
	    throws ReferentielOfsException;

    /**
     * Retourne la liste des Niveau1s professionnelles
     * 
     * @return liste des Niveau1s professionnelles
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Niveau1> getNiveaux1() throws ReferentielOfsException;

    /**
     * Retourne la Niveau1 par id
     * 
     * @param niveau1Id
     *            identifiant de la Niveau1
     * @return Niveau1
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    Niveau1 getNiveau1(final int niveau1Id) throws ReferentielOfsException;

    /**
     * Retourne la liste des Niveau1s dont le nom commence par le pattern<br/>
     * La recherche et case-insensitive et ne tient pas compte des accents et
     * caract�res sp�ciaux
     * 
     * @param pattern
     *            crit�re de recherche
     * @return liste des Niveau1s
     * @throws ReferentielOfsException
     *             erreur de traitemen
     */
    List<Niveau1> searchNiveaux1(final String pattern)
	    throws ReferentielOfsException;

    /**
     * Retourne la liste des Niveau1s dont le nom matche avec la regexp<br/>
     * La recherche et case-insensitive et ne tient pas compte des accents et
     * caract�res sp�ciaux
     * 
     * @param regexp
     *            regexp de recherche
     * @return liste des Niveau1s
     * @throws ReferentielOfsException
     *             erreur de traitemen
     */
    List<Niveau1> searchNiveaux1Regexp(final String regexp)
	    throws ReferentielOfsException;

    /**
     * Retourne la liste des Niveaux2
     * 
     * @return liste des Niveaux2
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Niveau2> getNiveaux2() throws ReferentielOfsException;

    /**
     * Retourne la liste des Niveaux2 d'une Niveau1
     * 
     * @param Niveau1Id
     *            identifiant de la Niveau1
     * @return liste des Niveaux2
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Niveau2> getNiveaux2(final int niveau1Id)
	    throws ReferentielOfsException;

    /**
     * Retourne une classe pour un identifiant
     * 
     * @param classeId
     *            identifiant de classe
     * @return classe
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    Niveau2 getNiveau2(final int niveau2Id) throws ReferentielOfsException;

    /**
     * Retourne la liste des Niveaux2 dont le nom commence par le pattern<br/>
     * La recherche et case-insensitive et ne tient pas compte des accents et
     * caract�res sp�ciaux
     * 
     * @param pattern
     *            crit�re de recherche
     * @return liste des Niveaux2
     * @throws ReferentielOfsException
     *             erreur de traitemen
     */
    List<Niveau2> searchNiveaux2(final String pattern)
	    throws ReferentielOfsException;

    /**
     * Retourne la liste des Niveaux2 dont le nom matche avec la regexp<br/>
     * La recherche et case-insensitive et ne tient pas compte des accents et
     * caract�res sp�ciaux
     * 
     * @param regexp
     *            regexp de recherche
     * @return liste des Niveaux2
     * @throws ReferentielOfsException
     *             erreur de traitemen
     */
    List<Niveau2> searchNiveaux2Regexp(final String regexp)
	    throws ReferentielOfsException;
}
