package ch.ge.cti.ct.referentiels.professions.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

import ch.ge.cti.ct.referentiels.professions.interfaces.ws.ReferentielProfessionsWS;

@WebServiceClient(name = ReferentielProfessionsWS.WEBSERVICE_NAME, targetNamespace = ReferentielProfessionsWS.TARGET_NAMESPACE)
public class ReferentielProfessionsClient extends Service {

    /**
     * Constructeur privé: classe instanciable uniquement par la factory
     * 
     * @param wsdlLocation
     *            chemin du wsdl (incluant le "?wsdl")
     */
    private ReferentielProfessionsClient(final URL wsdlLocation) {
	super(wsdlLocation, ReferentielProfessionsWS.SERVICE_QNAME);
    }

    /**
     * Instanciation du client WS
     * 
     * @return instance du client WS
     */
    @WebEndpoint(name = "referentiel-formes-juridiques")
    public ReferentielProfessionsWS getReferentielProfessionsPort() {
	return super.getPort(ReferentielProfessionsWS.PORT_QNAME,
		ReferentielProfessionsWS.class);
    }

    /**
     * Factory du client WS
     * 
     */
    public static class Factory {
	/**
	 * Instanciation du client WS pour un wsdl donné
	 * 
	 * @param wsdlUrl
	 *            url du wsdl (incluant le "?wsdl")
	 * @return client WS
	 * @throws MalformedURLException
	 *             exception url wsdl invalide
	 */
	public static ReferentielProfessionsWS getClient(final String wsdlUrl)
		throws MalformedURLException {
	    final ReferentielProfessionsClient client = new ReferentielProfessionsClient(
		    new URL(wsdlUrl));
	    return client.getReferentielProfessionsPort();
	}
    }

}
