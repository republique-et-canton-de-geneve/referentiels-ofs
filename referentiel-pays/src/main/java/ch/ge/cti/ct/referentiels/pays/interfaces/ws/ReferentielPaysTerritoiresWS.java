package ch.ge.cti.ct.referentiels.pays.interfaces.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.namespace.QName;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.ContinentWS;
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.PaysWS;
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.RegionWS;

@WebService(name = ReferentielPaysTerritoiresWS.WEBSERVICE_NAME, serviceName = ReferentielPaysTerritoiresWS.SERVICE_NAME, portName = ReferentielPaysTerritoiresWS.PORT_NAME, targetNamespace = ReferentielPaysTerritoiresWS.TARGET_NAMESPACE)
public interface ReferentielPaysTerritoiresWS {

    /** nom du service web */
    String WEBSERVICE_NAME = "referentiel-pays-JAXWS";
    /** nom du service */
    String SERVICE_NAME = "referentiel-pays-service";
    /** nom du port */
    String PORT_NAME = "referentiel-pays-port";
    /** namespace du service */
    String TARGET_NAMESPACE = "http://ch.ge.cti.ct.referentiels.pays/referentiel-pays/";

    /** QName du service */
    QName SERVICE_QNAME = new QName(TARGET_NAMESPACE, SERVICE_NAME);
    /** QName du port */
    QName PORT_QNAME = new QName(TARGET_NAMESPACE, PORT_NAME);

    @WebMethod(operationName = "getContinents", action = "getContinents")
    @WebResult(name = "continent")
    public List<ContinentWS> getContinents() throws ReferentielOfsException;

    @WebMethod(operationName = "getContinent", action = "getContinent")
    @WebResult(name = "continent")
    public ContinentWS getContinent(
	    @WebParam(name = "continent") final int continentId)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getRegionsByContinent", action = "getRegionsByContinent")
    @WebResult(name = "region")
    public List<RegionWS> getRegionsByContinent(
	    @WebParam(name = "continent") final int continentId)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getRegions", action = "getRegions")
    @WebResult(name = "region")
    public List<RegionWS> getRegions() throws ReferentielOfsException;

    @WebMethod(operationName = "getRegion", action = "getRegion")
    @WebResult(name = "region")
    public RegionWS getRegion(@WebParam(name = "region") final int regionId)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getPaysByRegion", action = "getPaysByRegion")
    @WebResult(name = "pays")
    public List<PaysWS> getPaysByRegion(
	    @WebParam(name = "region") final int regionId)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getPaysByContinent", action = "getPaysByContinent")
    @WebResult(name = "pays")
    public List<PaysWS> getPaysByContinent(
	    @WebParam(name = "continent") final int continentId)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getPays", action = "getPays")
    @WebResult(name = "pays")
    public List<PaysWS> getPays() throws ReferentielOfsException;

    @WebMethod(operationName = "getPaysByIso2", action = "getPaysByIso2")
    @WebResult(name = "pays")
    public PaysWS getPaysByIso2(@WebParam(name = "iso2") final String iso2)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getPaysByIso3", action = "getPaysByIso3")
    @WebResult(name = "pays")
    public PaysWS getPaysByIso3(@WebParam(name = "iso3") final String iso3)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "searchPays", action = "searchPays")
    @WebResult(name = "pays")
    public List<PaysWS> searchPays(
	    @WebParam(name = "critere") final String critere)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "searchPaysRegexp", action = "searchPaysRegexp")
    @WebResult(name = "pays")
    public List<PaysWS> searchPaysRegexp(
	    @WebParam(name = "critere") final String critere)
	    throws ReferentielOfsException;

}