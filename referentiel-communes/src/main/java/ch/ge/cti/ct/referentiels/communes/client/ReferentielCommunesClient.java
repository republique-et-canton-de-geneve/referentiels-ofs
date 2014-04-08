package ch.ge.cti.ct.referentiels.communes.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

import ch.ge.cti.ct.referentiels.communes.interfaces.ws.ReferentielCommunesWS;

/**
 * Client du WebService ReferentielCommunesWS
 * 
 * @author DESMAZIERESJ
 * 
 */
@WebServiceClient(name = ReferentielCommunesWS.WEBSERVICE_NAME, targetNamespace = ReferentielCommunesWS.TARGET_NAMESPACE)
public final class ReferentielCommunesClient extends Service {

    /**
     * Constructeur privé: classe instanciable uniquement par la factory
     * 
     * @param wsdlLocation
     *            chemin du wsdl (incluant le "?wsdl")
     */
    private ReferentielCommunesClient(final URL wsdlLocation) {
	super(wsdlLocation, ReferentielCommunesWS.SERVICE_QNAME);
    }

    /**
     * Instanciation du client WS
     * 
     * @return instance du client WS
     */
    @WebEndpoint(name = "referentiel-communes")
    public ReferentielCommunesWS getReferentielCommunesPort() {
	return super.getPort(ReferentielCommunesWS.PORT_QNAME,
		ReferentielCommunesWS.class);
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
	public static ReferentielCommunesWS getClient(final String wsdlUrl)
		throws MalformedURLException {
	    final ReferentielCommunesClient client = new ReferentielCommunesClient(
		    new URL(wsdlUrl));
	    return client.getReferentielCommunesPort();
	}
    }
}
