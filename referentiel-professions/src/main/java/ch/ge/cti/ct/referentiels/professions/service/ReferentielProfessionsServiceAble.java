package ch.ge.cti.ct.referentiels.professions.service;

import java.util.List;

import javax.ejb.Local;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.model.Classe;
import ch.ge.cti.ct.referentiels.professions.model.Division;
import ch.ge.cti.ct.referentiels.professions.model.Genre;
import ch.ge.cti.ct.referentiels.professions.model.Groupe;
import ch.ge.cti.ct.referentiels.professions.model.ReferentielProfessions;

/**
 * Définition du contrat du service de référentiel des professions<br/>
 * Le référentiel provient de l'office statistique Suisse dépendant de
 * l'administration fédérale<br/>
 * 
 * @author desmazièresj
 * 
 */
@Local
public interface ReferentielProfessionsServiceAble {

    /**
     * Retourne l'intégralité du référentiel des professions
     * 
     * @return référentiel en entier
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    ReferentielProfessions getReferentiel() throws ReferentielOfsException;

    /**
     * Retourne la liste des divisions professionnelles
     * 
     * @return liste des divisions professionnelles
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Division> getDivisions() throws ReferentielOfsException;

    /**
     * Retourne la division par id
     * 
     * @param divisionId
     *            identifiant de la division
     * @return division
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    Division getDivision(final int divisionId) throws ReferentielOfsException;

    /**
     * Retourne la liste des divisions dont le nom commence par le pattern<br/>
     * La recherche et case-insensitive et ne tient pas compte des accents et
     * caractères spéciaux
     * 
     * @param pattern
     *            critère de recherche
     * @return liste des divisions
     * @throws ReferentielOfsException
     *             erreur de traitemen
     */
    List<Division> searchDivision(final String pattern)
	    throws ReferentielOfsException;

    /**
     * Retourne la liste des divisions dont le nom matche avec la regexp<br/>
     * La recherche et case-insensitive et ne tient pas compte des accents et
     * caractères spéciaux
     * 
     * @param regexp
     *            regexp de recherche
     * @return liste des divisions
     * @throws ReferentielOfsException
     *             erreur de traitemen
     */
    List<Division> searchDivisionRegexp(final String regexp)
	    throws ReferentielOfsException;

    /**
     * Retourne la liste des classes
     * 
     * @return liste des classes
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Classe> getClasses() throws ReferentielOfsException;

    /**
     * Retourne la liste des classes d'une division
     * 
     * @param divisionId
     *            identifiant de la division
     * @return liste des classes
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Classe> getClasses(final int divisionId)
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
    Classe getClasse(final int classeId) throws ReferentielOfsException;

    /**
     * Retourne la liste des classes dont le nom commence par le pattern<br/>
     * La recherche et case-insensitive et ne tient pas compte des accents et
     * caractères spéciaux
     * 
     * @param pattern
     *            critère de recherche
     * @return liste des classes
     * @throws ReferentielOfsException
     *             erreur de traitemen
     */
    List<Classe> searchClasse(final String pattern)
	    throws ReferentielOfsException;

    /**
     * Retourne la liste des classes dont le nom matche avec la regexp<br/>
     * La recherche et case-insensitive et ne tient pas compte des accents et
     * caractères spéciaux
     * 
     * @param regexp
     *            regexp de recherche
     * @return liste des classes
     * @throws ReferentielOfsException
     *             erreur de traitemen
     */
    List<Classe> searchClasseRegexp(final String regexp)
	    throws ReferentielOfsException;

    /**
     * Retourne la liste des groupes
     * 
     * @return liste des groupes
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Groupe> getGroupes() throws ReferentielOfsException;

    /**
     * Retourne la liste des groupes d'une division
     * 
     * @param divisionId
     *            identifiant de la division
     * @return liste des groupes
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Groupe> getGroupes(final int divisionId)
	    throws ReferentielOfsException;

    /**
     * Retourne une groupe pour un identifiant
     * 
     * @param groupeId
     *            identifiant de groupe
     * @return groupe
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    Groupe getGroupe(final int groupeId) throws ReferentielOfsException;

    /**
     * Retourne la liste des groupes dont le nom commence par le pattern<br/>
     * La recherche et case-insensitive et ne tient pas compte des accents et
     * caractères spéciaux
     * 
     * @param pattern
     *            critère de recherche
     * @return liste des groupes
     * @throws ReferentielOfsException
     *             erreur de traitemen
     */
    List<Groupe> searchGroupe(final String pattern)
	    throws ReferentielOfsException;

    /**
     * Retourne la liste des groupes dont le nom matche avec la regexp<br/>
     * La recherche et case-insensitive et ne tient pas compte des accents et
     * caractères spéciaux
     * 
     * @param regexp
     *            regexp de recherche
     * @return liste des groupes
     * @throws ReferentielOfsException
     *             erreur de traitemen
     */
    List<Groupe> searchGroupeRegexp(final String regexp)
	    throws ReferentielOfsException;

    /**
     * Retourne la liste des genres
     * 
     * @return liste des genres
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Genre> getGenres() throws ReferentielOfsException;

    /**
     * Retourne la liste des genres d'une groupe
     * 
     * @param groupeId
     *            identifiant de la groupe
     * @return liste des genres
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Genre> getGenres(final int groupeId) throws ReferentielOfsException;

    /**
     * Retourne une genre pour un identifiant
     * 
     * @param genreId
     *            identifiant de genre
     * @return genre
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    Genre getGenre(final int genreId) throws ReferentielOfsException;

    /**
     * Retourne la liste des genres dont le nom commence par le pattern<br/>
     * La recherche et case-insensitive et ne tient pas compte des accents et
     * caractères spéciaux
     * 
     * @param pattern
     *            critère de recherche
     * @return liste des genres
     * @throws ReferentielOfsException
     *             erreur de traitemen
     */
    List<Genre> searchGenre(final String pattern)
	    throws ReferentielOfsException;

    /**
     * Retourne la liste des genres dont le nom matche avec la regexp<br/>
     * La recherche et case-insensitive et ne tient pas compte des accents et
     * caractères spéciaux
     * 
     * @param regexp
     *            regexp de recherche
     * @return liste des genres
     * @throws ReferentielOfsException
     *             erreur de traitemen
     */
    List<Genre> searchGenreRegexp(final String regexp)
	    throws ReferentielOfsException;

}
