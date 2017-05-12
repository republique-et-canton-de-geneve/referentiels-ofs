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
 * D�finition du contrat du service de r�f�rentiel des communes<br/>
 * Le r�f�rentiel provient de l'office statistique Suisse d�pendant de
 * l'administration f�d�rale<br/>
 * 
 * @see http 
 *      ://www.bfs.admin.ch/bfs/portal/fr/index/infothek/nomenklaturen/blank/
 *      blank/gem_liste/03.html<br/>
 *      Le r�f�rentiel des donn�es est le suivant:<br/>
 *      <ul>
 *      <li>GDEKT 2/a Abr�viation du canton</li>
 *      <li>GDEBZNR 4/n N� du district</li>
 *      <li>GDENR 4/n N� OFS de la commune</li>
 *      <li>GDENAME 60/a Nom officiel de la commune</li>
 *      <li>GDENAMK 24/a Nom abr�g� de la commune</li>
 *      <li>GDEBZNA 60/a Nom du district</li>
 *      <li>GDEKTNA 60/a Nom du canton</li>
 *      <li>GDEMUTDAT date Date de la derni�re modification (dd.mm.yyyy)</li>
 *      </ul>
 * 
 * @author desmazi�resj
 * 
 */
@Local
public interface ReferentielCommunesServiceAble {

    /**
     * Retourne l'int�gralit� du r�f�rentiel des communes
     * 
     * @return r�f�rentiel en entier
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
     *            date de validit�
     * @return liste des cantons
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Canton> getCantons(final Date dateValid)
	    throws ReferentielOfsException;

    /**
     * Recherche un canton par son identifiant abr�g� (2 n/a)<br/>
     * 
     * @param codeCanton
     *            identifiant de canton (2 caract�res alpha)
     * @return objet canton
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    Canton getCanton(final String codeCanton) throws ReferentielOfsException;

    /**
     * Recherche un canton par son identifiant abr�g� (2 n/a)<br/>
     * 
     * @param codeCanton
     *            identifiant de canton (2 caract�res alpha)
     * @param dateValid
     *            date de validit�
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
     *            identifiant de canton (2 caract�res alpha)
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
     *            identifiant de canton (2 caract�res alpha)
     * @param dateValid
     *            date de validit�
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
     *            identifiant de district (num�rique)
     * @return objet district
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    District getDistrict(final int idDistrict) throws ReferentielOfsException;

    /**
     * recherche d'un district par son identifiant<br/>
     * 
     * @param idDistrict
     *            identifiant de district (num�rique)
     * @param dateValid
     *            date de validit�
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
     *            identifiant de district (num�rique)
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
     *            identifiant de district (num�rique)
     * @param dateValid
     *            date de validit�
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
     *            identifiant de canton (2 caract�res alpha)
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
     *            identifiant de canton (2 caract�res alpha)
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
     *            identifiant de canton (2 caract�res alpha)
     * @param dateValid
     *            date de validit�
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
     *            date de validit�
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
     *            crit�re de recherche (sur le nom)
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
     *            crit�re de recherche (sur le nom)
     * @param dateValid
     *            date de validit�
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
     *            crit�re de recherche (sur le nom)
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
     *            crit�re de recherche (sur le nom)
     * @param dateValid
     *            date de validit�
     * @return liste des communes
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Commune> searchCommuneRegexp(final String critere, final Date dateValid)
	    throws ReferentielOfsException;
}
