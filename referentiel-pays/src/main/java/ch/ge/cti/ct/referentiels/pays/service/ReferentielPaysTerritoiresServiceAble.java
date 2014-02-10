package ch.ge.cti.ct.referentiels.pays.service;

import java.util.List;

import javax.ejb.Local;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.model.Continent;
import ch.ge.cti.ct.referentiels.pays.model.Pays;
import ch.ge.cti.ct.referentiels.pays.model.ReferentielPaysTerritoires;
import ch.ge.cti.ct.referentiels.pays.model.Region;

/**
 * D�finition du contrat du service de r�f�rentiel des pays<br/>
 * Le r�f�rentiel provient de l'office statistique Suisse d�pendant de
 * l'administration f�d�rale<br/>
 * 
 * @author desmazi�resj
 * 
 */
@Local
public interface ReferentielPaysTerritoiresServiceAble {

    /**
     * Retourne l'int�gralit� du r�f�rentiel des pays
     * 
     * @return r�f�rentiel en entier
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    ReferentielPaysTerritoires getReferentiel() throws ReferentielOfsException;

    /**
     * Retourne la liste des continents<br/>
     * 
     * @return liste des continents
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Continent> getContinents() throws ReferentielOfsException;

    /**
     * Recherche un continent par son identifiant<br/>
     * 
     * @param continentId
     *            identifiant de continent
     * @return objet continent
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    Continent getContinent(final int continentId)
	    throws ReferentielOfsException;

    /**
     * Liste des regions du continent<br/>
     * 
     * @return liste des regions
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Region> getRegions() throws ReferentielOfsException;

    /**
     * Liste des regions du continent<br/>
     * 
     * @param continentId
     *            identifiant de continent
     * @return liste des regions
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Region> getRegions(final int continentId)
	    throws ReferentielOfsException;

    /**
     * recherche d'une region par son identifiant<br/>
     * 
     * @param regionId
     *            identifiant de region (num�rique)
     * @return objet region
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    Region getRegion(final int regionId) throws ReferentielOfsException;

    /**
     * Liste des pays d'un continent
     * 
     * @param continentId
     *            identifiant de continent (2 caract�res alpha)
     * @return liste des pays
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Pays> getPaysByContinent(final int continentId)
	    throws ReferentielOfsException;

    /**
     * Liste des pays d'une region
     * 
     * @param regionId
     *            identifiant de region (num�rique)
     * @return liste des pays
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Pays> getPaysByRegion(final int regionId)
	    throws ReferentielOfsException;

    /**
     * recherche d'un pays par son code ISO2
     * 
     * @param idPays
     *            iso2 de la pays
     * @return pays
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    Pays getPaysByISO2(final String iso2) throws ReferentielOfsException;

    /**
     * recherche d'un pays par son code ISO2
     * 
     * @param idPays
     *            iso2 de la pays
     * @return pays
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    Pays getPaysByISO3(final String iso3) throws ReferentielOfsException;

    /**
     * liste de pays
     * 
     * @param crit�re
     *            identifiant de la pays
     * @return pays
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Pays> getPays() throws ReferentielOfsException;

    /**
     * recheche de pays par comparaison de nom
     * 
     * @param critere
     *            crit�re de recherche
     * @return pays liste de pays
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Pays> searchPays(final String critere) throws ReferentielOfsException;

    /**
     * recherche de pays sur nom par regexp
     * 
     * @param critere
     *            crit�re de recherche
     * @return pays liste de pays
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<Pays> searchPaysRegexp(final String critere)
	    throws ReferentielOfsException;

}
