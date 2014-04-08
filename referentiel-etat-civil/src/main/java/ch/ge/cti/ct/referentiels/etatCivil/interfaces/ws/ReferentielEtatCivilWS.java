package ch.ge.cti.ct.referentiels.etatCivil.interfaces.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.namespace.QName;

import ch.ge.cti.ct.referentiels.etatCivil.model.EtatCivil;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * Interface du service au format JAX-WS
 * 
 * @author DESMAZIERESJ
 * 
 */
@WebService(targetNamespace = ReferentielEtatCivilWS.TARGET_NAMESPACE)
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

    /**
     * Liste de tous les états civils
     * 
     * @return liste des états civils
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getEtatsCivils", action = "getEtatsCivils")
    @WebResult(name = "etatCivil")
    List<EtatCivil> getEtatsCivils() throws ReferentielOfsException;

    /**
     * Recherche d'un état civil par son identifiant
     * 
     * @param etatCivilId
     *            identifiant état civil
     * @return état civil
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getEtatCivil", action = "getEtatCivil")
    @WebResult(name = "etatCivil")
    EtatCivil getEtatCivil(@WebParam(name = "etatCivil") final int etatCivilId)
	    throws ReferentielOfsException;

}