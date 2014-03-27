package ch.ge.cti.ct.referentiels.etatCivil.interfaces.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.namespace.QName;

import ch.ge.cti.ct.referentiels.etatCivil.model.EtatCivil;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

@WebService(name = ReferentielEtatCivilWS.WEBSERVICE_NAME, serviceName = ReferentielEtatCivilWS.SERVICE_NAME, portName = ReferentielEtatCivilWS.PORT_NAME, targetNamespace = ReferentielEtatCivilWS.TARGET_NAMESPACE)
public interface ReferentielEtatCivilWS {

    /** nom du service web */
    String WEBSERVICE_NAME = "referentiel-etat-civil-JAXWS";
    /** nom du service */
    String SERVICE_NAME = "referentiel-etat-civil-service";
    /** nom du port */
    String PORT_NAME = "referentiel-etat-civil-port";
    /** namespace du service */
    String TARGET_NAMESPACE = "http://ch.ge.cti.ct.referentiels.etat-civil/referentiel-etat-civil/";

    /** QName du service */
    QName SERVICE_QNAME = new QName(TARGET_NAMESPACE, SERVICE_NAME);
    /** QName du port */
    QName PORT_QNAME = new QName(TARGET_NAMESPACE, PORT_NAME);

    @WebMethod(operationName = "getEtatsCivils", action = "getEtatsCivils")
    @WebResult(name = "etatCivil")
    List<EtatCivil> getEtatsCivils() throws ReferentielOfsException;

    @WebMethod(operationName = "getEtatCivil", action = "getEtatCivil")
    @WebResult(name = "etatCivil")
    EtatCivil getEtatCivil(@WebParam(name = "etatCivil") final int etatCivilId)
	    throws ReferentielOfsException;

}