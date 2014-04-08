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

    @WebMethod(operationName = "getCantons", action = "getCantons")
    @WebResult(name = "canton")
    List<CantonWS> getCantons() throws ReferentielOfsException;

    @WebMethod(operationName = "getCanton", action = "getCanton")
    @WebResult(name = "canton")
    CantonWS getCanton(@WebParam(name = "canton") final String codeCanton)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getDistrictsByCanton", action = "getDistrictsByCanton")
    @WebResult(name = "district")
    List<DistrictWS> getDistrictsByCanton(
	    @WebParam(name = "canton") final String codeCanton)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getDistrict", action = "getDistrict")
    @WebResult(name = "district")
    DistrictWS getDistrict(@WebParam(name = "district") final int districtId)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getCommunesByDistrict", action = "getCommunesByDistrict")
    @WebResult(name = "commune")
    List<CommuneWS> getCommunesByDistrict(
	    @WebParam(name = "district") final int districtId)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getCommunesByCanton", action = "getCommunesByCanton")
    @WebResult(name = "commune")
    List<CommuneWS> getCommunesByCanton(
	    @WebParam(name = "canton") final String codeCanton)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getCommune", action = "getCommune")
    @WebResult(name = "commune")
    CommuneWS getCommune(@WebParam(name = "commune") final int communeId)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getCantonDate", action = "getCantonDate")
    @WebResult(name = "canton")
    CantonWS getCantonDate(@WebParam(name = "canton") final String codeCanton,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getCantonsDate", action = "getCantonsDate")
    @WebResult(name = "canton")
    List<CantonWS> getCantonsDate(
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getDistrictDate", action = "getDistrictDate")
    @WebResult(name = "district")
    DistrictWS getDistrictDate(
	    @WebParam(name = "district") final int districtId,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getDistrictsByCantonDate", action = "getDistrictsByCantonDate")
    @WebResult(name = "district")
    List<DistrictWS> getDistrictsByCantonDate(
	    @WebParam(name = "canton") final String codeCanton,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getCommunesByDistrictDate", action = "getCommunesByDistrictDate")
    @WebResult(name = "commune")
    List<CommuneWS> getCommunesByDistrictDate(final int districtId,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getCommunesByCantonDate", action = "getCommunesByCantonDate")
    @WebResult(name = "commune")
    List<CommuneWS> getCommunesByCantonDate(
	    @WebParam(name = "canton") final String codeCanton,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "searchCommune", action = "searchCommune")
    @WebResult(name = "commune")
    List<CommuneWS> searchCommune(
	    @WebParam(name = "critere") final String critere)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "searchCommuneDate", action = "searchCommuneDate")
    @WebResult(name = "commune")
    List<CommuneWS> searchCommuneDate(
	    @WebParam(name = "critere") final String critere,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException;
}
