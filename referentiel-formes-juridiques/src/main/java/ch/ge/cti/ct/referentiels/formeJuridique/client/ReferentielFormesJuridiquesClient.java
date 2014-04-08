package ch.ge.cti.ct.referentiels.formeJuridique.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

import ch.ge.cti.ct.referentiels.formeJuridique.interfaces.ws.ReferentielFormesJuridiquesWS;

/**
 * Client du WebService ReferentielFormesJuridiquesWS
 * 
 * @author DESMAZIERESJ
 * 
 */
@WebServiceClient(name = ReferentielFormesJuridiquesWS.WEBSERVICE_NAME, targetNamespace = ReferentielFormesJuridiquesWS.TARGET_NAMESPACE)
public final class ReferentielFormesJuridiquesClient extends Service {

    /**
     * Constructeur privé: classe instanciable uniquement par la factory
     * 
     * @param wsdlLocation
     *            chemin du wsdl (incluant le "?wsdl")
     */
    private ReferentielFormesJuridiquesClient(final URL wsdlLocation) {
	super(wsdlLocation, ReferentielFormesJuridiquesWS.SERVICE_QNAME);
    }

    /**
     * Instanciation du client WS
     * 
     * @return instance du client WS
     */
    @WebEndpoint(name = "referentiel-formes-juridiques")
    public ReferentielFormesJuridiquesWS getReferentielFormesJuridiquesPort() {
	return super.getPort(ReferentielFormesJuridiquesWS.PORT_QNAME,
		ReferentielFormesJuridiquesWS.class);
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
	public static ReferentielFormesJuridiquesWS getClient(
		final String wsdlUrl) throws MalformedURLException {
	    final ReferentielFormesJuridiquesClient client = new ReferentielFormesJuridiquesClient(
		    new URL(wsdlUrl));
	    return client.getReferentielFormesJuridiquesPort();
	}
    }
}
