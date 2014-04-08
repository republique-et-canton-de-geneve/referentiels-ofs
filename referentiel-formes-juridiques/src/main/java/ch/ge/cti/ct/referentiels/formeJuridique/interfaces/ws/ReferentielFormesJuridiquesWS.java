package ch.ge.cti.ct.referentiels.formeJuridique.interfaces.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.namespace.QName;

import ch.ge.cti.ct.referentiels.formeJuridique.model.FormeJuridique;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

@WebService(targetNamespace = ReferentielFormesJuridiquesWS.TARGET_NAMESPACE)
public interface ReferentielFormesJuridiquesWS {

    /** nom du service web */
    String WEBSERVICE_NAME = "referentiel-formes-juridiques-JAXWS";
    /** nom du service */
    String SERVICE_NAME = "referentiel-formes-juridiques-service";
    /** nom du port */
    String PORT_NAME = "referentiel-formes-juridiques-port";
    /** namespace du service */
    String TARGET_NAMESPACE = "http://ch.ge.cti.ct.referentiels.formes-juridiques/referentiel-formes-juridiques/";

    /** QName du service */
    QName SERVICE_QNAME = new QName(TARGET_NAMESPACE, SERVICE_NAME);
    /** QName du port */
    QName PORT_QNAME = new QName(TARGET_NAMESPACE, PORT_NAME);

    @WebMethod(operationName = "getFormesJuridiques", action = "getFormesJuridiques")
    @WebResult(name = "formeJuridique")
    List<FormeJuridique> getFormesJuridiques() throws ReferentielOfsException;

    @WebMethod(operationName = "getFormeJuridique", action = "getFormeJuridique")
    @WebResult(name = "formeJuridique")
    FormeJuridique getFormeJuridique(
	    @WebParam(name = "formeJuridique") final int formeJuridiqueId)
	    throws ReferentielOfsException;

}