package ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.namespace.QName;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws.model.Niveau1WS;
import ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws.model.Niveau2WS;

@WebService(targetNamespace = ReferentielSocioprofessionnelWS.TARGET_NAMESPACE)
public interface ReferentielSocioprofessionnelWS {

    /** nom du service web */
    String WEBSERVICE_NAME = "referentiel-socioprofessionel-JAXWS";
    /** nom du service */
    String SERVICE_NAME = "referentiel-socioprofessionel-service";
    /** nom du port */
    String PORT_NAME = "referentiel-socioprofessionel-port";
    /** namespace du service */
    String TARGET_NAMESPACE = "http://ch.ge.cti.ct.referentiels.socioprofessionel/referentiel-socioprofessionel/";

    /** QName du service */
    QName SERVICE_QNAME = new QName(TARGET_NAMESPACE, SERVICE_NAME);
    /** QName du port */
    QName PORT_QNAME = new QName(TARGET_NAMESPACE, PORT_NAME);

    @WebMethod(operationName = "getNiveaux1", action = "getNiveaux1")
    @WebResult(name = "niveau1")
    List<Niveau1WS> getNiveaux1() throws ReferentielOfsException;

    @WebMethod(operationName = "getNiveau1", action = "getNiveau1")
    @WebResult(name = "niveau1")
    Niveau1WS getNiveau1(@WebParam(name = "niveau1Id") final int niveau1Id)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "searchNiveaux1", action = "searchNiveaux1")
    @WebResult(name = "niveau1")
    List<Niveau1WS> searchNiveaux1(
	    @WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "searchNiveaux1Regexp", action = "searchNiveaux1Regexp")
    @WebResult(name = "niveau1")
    List<Niveau1WS> searchNiveaux1Regexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getNiveaux2", action = "getNiveaux2")
    @WebResult(name = "niveau2")
    List<Niveau2WS> getNiveaux2() throws ReferentielOfsException;

    @WebMethod(operationName = "getNiveaux2ByNiveau1", action = "getNiveaux2ByNiveau1")
    @WebResult(name = "niveau2")
    List<Niveau2WS> getNiveaux2ByNiveau1(
	    @WebParam(name = "niveau1Id") final int niveau1Id)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getNiveau2", action = "getNiveau2")
    @WebResult(name = "niveau2")
    Niveau2WS getNiveau2(@WebParam(name = "niveau2Id") final int niveau2Id)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "searchNiveaux2", action = "searchNiveaux2")
    @WebResult(name = "niveau2")
    List<Niveau2WS> searchNiveaux2(
	    @WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "searchNiveaux2Regexp", action = "searchNiveaux2Regexp")
    @WebResult(name = "niveau2")
    List<Niveau2WS> searchNiveaux2Regexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException;
}