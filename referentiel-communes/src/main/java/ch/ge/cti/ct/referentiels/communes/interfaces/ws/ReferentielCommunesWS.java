package ch.ge.cti.ct.referentiels.communes.interfaces.ws;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.namespace.QName;

import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CantonWS;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CommuneWS;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.DistrictWS;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * Interface du service au format JAX-WS
 * 
 * @author DESMAZIERESJ
 * 
 */
@WebService(targetNamespace = ReferentielCommunesWS.TARGET_NAMESPACE)
public interface ReferentielCommunesWS {

    /** nom du service web */
    String WEBSERVICE_NAME = "referentiel-communes-JAXWS";
    /** nom du service */
    String SERVICE_NAME = "referentiel-communes-service";
    /** nom du port */
    String PORT_NAME = "referentiel-communes-port";
    /** namespace du service */
    String TARGET_NAMESPACE = "http://ch.ge.cti.ct.referentiels.communes/referentiel-communes/";

    /** QName du service */
    QName SERVICE_QNAME = new QName(TARGET_NAMESPACE, SERVICE_NAME);
    /** QName du port */
    QName PORT_QNAME = new QName(TARGET_NAMESPACE, PORT_NAME);

    /**
     * Liste de tous les cantons actifs à la date du jour
     * 
     * @return liste de tous les cantons
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getCantons", action = "getCantons")
    @WebResult(name = "canton")
    List<CantonWS> getCantons() throws ReferentielOfsException;

    /**
     * Recherche de canton par son code
     * 
     * @param codeCanton
     *            identifiant du canton
     * @return canton
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getCanton", action = "getCanton")
    @WebResult(name = "canton")
    CantonWS getCanton(@WebParam(name = "canton") final String codeCanton)
	    throws ReferentielOfsException;

    /**
     * Liste des districts d'un canton
     * 
     * @param codeCanton
     *            identifiant du canton
     * @return liste des districts
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getDistrictsByCanton", action = "getDistrictsByCanton")
    @WebResult(name = "district")
    List<DistrictWS> getDistrictsByCanton(
	    @WebParam(name = "canton") final String codeCanton)
	    throws ReferentielOfsException;

    /**
     * recherche d'un district par son identifiant
     * 
     * @param districtId
     *            identifiant du district
     * @return district
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getDistrict", action = "getDistrict")
    @WebResult(name = "district")
    DistrictWS getDistrict(@WebParam(name = "district") final int districtId)
	    throws ReferentielOfsException;

    /**
     * Recherches des communes d'un district
     * 
     * @param districtId
     *            identifiant du district
     * @return liste des communes
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getCommunesByDistrict", action = "getCommunesByDistrict")
    @WebResult(name = "commune")
    List<CommuneWS> getCommunesByDistrict(
	    @WebParam(name = "district") final int districtId)
	    throws ReferentielOfsException;

    /**
     * Recherche des communes d'un canton
     * 
     * @param codeCanton
     *            identifiant du canton
     * @return liste des communes
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getCommunesByCanton", action = "getCommunesByCanton")
    @WebResult(name = "commune")
    List<CommuneWS> getCommunesByCanton(
	    @WebParam(name = "canton") final String codeCanton)
	    throws ReferentielOfsException;

    /**
     * Recherche de commune par son identifiant
     * 
     * @param communeId
     *            identifiant de commune
     * @return commune
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getCommune", action = "getCommune")
    @WebResult(name = "commune")
    CommuneWS getCommune(@WebParam(name = "commune") final int communeId)
	    throws ReferentielOfsException;

    /**
     * Recherche de canton valide à la date données
     * 
     * @param codeCanton
     *            identifiant du canton
     * @param dateValid
     *            date de vérification de validité
     * @return
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getCantonDate", action = "getCantonDate")
    @WebResult(name = "canton")
    CantonWS getCantonDate(@WebParam(name = "canton") final String codeCanton,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException;

    /**
     * Liste des cantons valides pour une date donnée
     * 
     * @param dateValid
     *            date de vérification de validité
     * @return liste des cantons valides
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getCantonsDate", action = "getCantonsDate")
    @WebResult(name = "canton")
    List<CantonWS> getCantonsDate(
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException;

    /**
     * Recherche de district valide à une date donnée
     * 
     * @param districtId
     *            identifiant de district
     * @param dateValid
     *            date de vérification de validité
     * @return
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getDistrictDate", action = "getDistrictDate")
    @WebResult(name = "district")
    DistrictWS getDistrictDate(
	    @WebParam(name = "district") final int districtId,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException;

    /**
     * Liste des districts d'une canton, valides à une date donnée
     * 
     * @param codeCanton
     *            identifiant de canton
     * @param dateValid
     *            date de vérification de validité
     * @return liste des districts
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getDistrictsByCantonDate", action = "getDistrictsByCantonDate")
    @WebResult(name = "district")
    List<DistrictWS> getDistrictsByCantonDate(
	    @WebParam(name = "canton") final String codeCanton,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException;

    /**
     * Liste des communes d'un district, valides pour une date donnée
     * 
     * @param districtId
     *            identifiant du district
     * @param dateValid
     *            date de vérification de validité
     * @return liste des communes valides
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getCommunesByDistrictDate", action = "getCommunesByDistrictDate")
    @WebResult(name = "commune")
    List<CommuneWS> getCommunesByDistrictDate(
	    @WebParam(name = "district") final int districtId,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException;

    /**
     * Liste des communes d'un canton, valides pour une date donnée
     * 
     * @param codeCanton
     *            identifiant du canton
     * @param dateValid
     *            date de vérification de validité
     * @return liste des communes valides
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getCommunesByCantonDate", action = "getCommunesByCantonDate")
    @WebResult(name = "commune")
    List<CommuneWS> getCommunesByCantonDate(
	    @WebParam(name = "canton") final String codeCanton,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException;

    /**
     * Recherche des communes correspondant à un critère (filtre sur le début du
     * nom)
     * 
     * @param critere
     *            critère de recherche
     * @return liste des communes
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "searchCommune", action = "searchCommune")
    @WebResult(name = "commune")
    List<CommuneWS> searchCommune(
	    @WebParam(name = "critere") final String critere)
	    throws ReferentielOfsException;

    /**
     * Recherche des communes correspondant à un critère (filtre sur le début du
     * nom), valides pour une date donnée
     * 
     * @param critere
     *            critère de recherche
     * @param dateValid
     *            date de vérification de validité
     * @return liste des communes
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "searchCommuneDate", action = "searchCommuneDate")
    @WebResult(name = "commune")
    List<CommuneWS> searchCommuneDate(
	    @WebParam(name = "critere") final String critere,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException;

    /**
     * Recherche des communes correspondant à une expression régulière
     * 
     * @param regexp
     *            critère de recherche
     * @return liste des communes
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "searchCommuneRegexp", action = "searchCommuneRegexp")
    @WebResult(name = "commune")
    List<CommuneWS> searchCommuneRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException;

    /**
     * Recherche des communes correspondant à une expression régulière
     * 
     * @param regexp
     *            critère de recherche
     * @param dateValid
     *            date de vérification de validité
     * @return liste des communes
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "searchCommuneDateRegexp", action = "searchCommuneDateRegexp")
    @WebResult(name = "commune")
    List<CommuneWS> searchCommuneDateRegexp(
	    @WebParam(name = "regexp") final String regexp,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException;
}
