package ch.ge.cti.ct.referentiels.pays.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

import ch.ge.cti.ct.referentiels.pays.interfaces.ws.ReferentielPaysTerritoiresWS;

/**
 * Client du WebService ReferentielPaysTerritoiresWS
 * 
 * @author DESMAZIERESJ
 * 
 */
@WebServiceClient(name = ReferentielPaysTerritoiresWS.WEBSERVICE_NAME, targetNamespace = ReferentielPaysTerritoiresWS.TARGET_NAMESPACE)
public final class ReferentielPaysTerritoiresClient extends Service {
    /**
     * Constructeur privé: classe instanciable uniquement par la factory
     * 
     * @param wsdlLocation
     *            chemin du wsdl (incluant le "?wsdl")
     */
    private ReferentielPaysTerritoiresClient(final URL wsdlLocation) {
	super(wsdlLocation, ReferentielPaysTerritoiresWS.SERVICE_QNAME);
    }

    /**
     * Instanciation du client WS
     * 
     * @return INSTANCE du client WS
     */
    @WebEndpoint(name = "referentiel-formes-juridiques")
    public ReferentielPaysTerritoiresWS getReferentielPaysTerritoiresPort() {
	return super.getPort(ReferentielPaysTerritoiresWS.PORT_QNAME,
		ReferentielPaysTerritoiresWS.class);
    }

    /**
     * Factory du client WS
     * 
     */
    public static class Factory {

    	private Factory() {
	    }

	/**
	 * Instanciation du client WS pour un wsdl donné
	 * 
	 * @param wsdlUrl
	 *            url du wsdl (incluant le "?wsdl")
	 * @return client WS
	 * @throws MalformedURLException
	 *             exception url wsdl invalide
	 */
	public static ReferentielPaysTerritoiresWS getClient(
		final String wsdlUrl) throws MalformedURLException {
	    final ReferentielPaysTerritoiresClient client = new ReferentielPaysTerritoiresClient(
		    new URL(wsdlUrl));
	    return client.getReferentielPaysTerritoiresPort();
	}
    }

}
