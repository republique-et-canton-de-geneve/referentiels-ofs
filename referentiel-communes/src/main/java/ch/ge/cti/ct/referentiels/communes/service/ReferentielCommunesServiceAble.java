package ch.ge.cti.ct.referentiels.communes.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import ch.ge.cti.ct.referentiels.communes.model.Canton;
import ch.ge.cti.ct.referentiels.communes.model.Commune;
import ch.ge.cti.ct.referentiels.communes.model.District;
import ch.ge.cti.ct.referentiels.communes.model.ReferentielCommunes;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * Définition du contrat du service de référentiel des communes<br/>
 * Le référentiel provient de l'office statistique Suisse dépendant de
 * l'administration fédérale<br/>
 * 
 * @see http 
 *      ://www.bfs.admin.ch/bfs/portal/fr/index/infothek/nomenklaturen/blank/
 *      blank/gem_liste/03.html<br/>
 *      Le référentiel des données est le suivant:<br/>
 *      <ul>
 *      <li>GDEKT 2/a Abréviation du canton</li>
 *      <li>GDEBZNR 4/n N° du district</li>
 *      <li>GDENR 4/n N° OFS de la commune</li>
 *      <li>GDENAME 60/a Nom officiel de la commune</li>
 *      <li>GDENAMK 24/a Nom abrégé de la commune</li>
 *      <li>GDEBZNA 60/a Nom du district</li>
 *      <li>GDEKTNA 60/a Nom du canton</li>
 *      <li>GDEMUTDAT date Date de la dernière modification (dd.mm.yyyy)</li>
 *      </ul>
 * 
 * @author desmazièresj
 * 
 */
@Local
public interface ReferentielCommunesServiceAble {

    /**
     * Retourne l'intégralité du référentiel des communes
     * 
     * @return référentiel en entier
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    ReferentielCommunes getReferentiel() throws ReferentielOfsException;

    /**
     * Retourne la liste des cantons<br/>
     * 
     * @return liste des cantons
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Canton> getCantons() throws ReferentielOfsException;

    /**
     * Retourne la liste des cantons valides pour la date de validation<br/>
     * 
     * @param dateValid
     *            date de validité
     * @return liste des cantons
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Canton> getCantons(final Date dateValid)
	    throws ReferentielOfsException;

    /**
     * Recherche un canton par son identifiant abrégé (2 n/a)<br/>
     * 
     * @param codeCanton
     *            identifiant de canton (2 caractères alpha)
     * @return objet canton
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    Canton getCanton(final String codeCanton) throws ReferentielOfsException;

    /**
     * Recherche un canton par son identifiant abrégé (2 n/a)<br/>
     * 
     * @param codeCanton
     *            identifiant de canton (2 caractères alpha)
     * @param dateValid
     *            date de validité
     * @return objet canton
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    Canton getCanton(final String codeCanton, final Date dateValid)
	    throws ReferentielOfsException;

    /**
     * Liste des disticts du canton<br/>
     * 
     * @param codeCanton
     *            identifiant de canton (2 caractères alpha)
     * @return liste des districts
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<District> getDistricts(final String codeCanton)
	    throws ReferentielOfsException;

    /**
     * Liste des disticts du canton valides pour la date de validation<br/>
     * 
     * @param codeCanton
     *            identifiant de canton (2 caractères alpha)
     * @param dateValid
     *            date de validité
     * @return liste des districts
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<District> getDistricts(final String codeCanton, final Date dateValid)
	    throws ReferentielOfsException;

    /**
     * recherche d'un district par son identifiant<br/>
     * 
     * @param idDistrict
     *            identifiant de district (numérique)
     * @return objet district
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    District getDistrict(final int idDistrict) throws ReferentielOfsException;

    /**
     * recherche d'un district par son identifiant<br/>
     * 
     * @param idDistrict
     *            identifiant de district (numérique)
     * @param dateValid
     *            date de validité
     * @return objet district
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    District getDistrict(final int idDistrict, final Date dateValid)
	    throws ReferentielOfsException;

    /**
     * Liste des communes d'un district
     * 
     * @param idDistrict
     *            identifiant de district (numérique)
     * @return liste des communes
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Commune> getCommunesByDistrict(final int idDistrict)
	    throws ReferentielOfsException;

    /**
     * Liste des communes d'un district valides pour la date de validation
     * 
     * @param idDistrict
     *            identifiant de district (numérique)
     * @param dateValid
     *            date de validité
     * @return liste des communes
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Commune> getCommunesByDistrict(final int idDistrict,
	    final Date dateValid) throws ReferentielOfsException;

    /**
     * Liste des communes d'un canton
     * 
     * @param codeCanton
     *            identifiant de canton (2 caractères alpha)
     * @return liste des communes
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Commune> getCommunesByCanton(final String codeCanton)
	    throws ReferentielOfsException;
    
    /**
     * Liste des communes historiques d'un canton
     * 
     * @param codeCanton
     *            identifiant de canton (2 caractères alpha)
     * @return liste des communes
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Commune> getCommunesHistoriquesByCanton(final String codeCanton)
	    throws ReferentielOfsException;

    /**
     * Liste des communes d'un canton valides pour la date de validation
     * 
     * @param codeCanton
     *            identifiant de canton (2 caractères alpha)
     * @param dateValid
     *            date de validité
     * @return liste des communes
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Commune> getCommunesByCanton(final String codeCanton,
	    final Date dateValid) throws ReferentielOfsException;

    /**
     * recherche d'une commune par son identifiant
     * 
     * @param idCommune
     *            identifiant de la commune
     * @return commune
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    Commune getCommune(final int idCommune) throws ReferentielOfsException;

    /**
     * recherche d'une commune par son identifiant
     * 
     * @param idCommune
     *            identifiant de la commune
     * @param dateValid
     *            date de validité
     * @return commune
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    Commune getCommune(final int idCommune, final Date dateValid)
	    throws ReferentielOfsException;

    /**
     * Recherche des communes par son nom
     * 
     * @param critere
     *            critère de recherche (sur le nom)
     * @return liste des communes
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Commune> searchCommune(final String critere)
	    throws ReferentielOfsException;

    /**
     * Recherche des communes par son nom
     * 
     * @param critere
     *            critère de recherche (sur le nom)
     * @param dateValid
     *            date de validité
     * @return liste des communes
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Commune> searchCommune(final String critere, final Date dateValid)
	    throws ReferentielOfsException;

    /**
     * Recherche des communes par son nom
     * 
     * @param regexp
     *            critère de recherche (sur le nom)
     * @return liste des communes
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Commune> searchCommuneRegexp(final String regexp)
	    throws ReferentielOfsException;

    /**
     * Recherche des communes par son nom
     * 
     * @param regexp
     *            critère de recherche (sur le nom)
     * @param dateValid
     *            date de validité
     * @return liste des communes
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Commune> searchCommuneRegexp(final String critere, final Date dateValid)
	    throws ReferentielOfsException;
}
