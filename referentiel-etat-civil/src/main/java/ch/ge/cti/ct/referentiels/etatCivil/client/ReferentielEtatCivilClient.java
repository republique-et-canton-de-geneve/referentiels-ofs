package ch.ge.cti.ct.referentiels.etatCivil.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

import ch.ge.cti.ct.referentiels.etatCivil.interfaces.ws.ReferentielEtatCivilWS;

@WebServiceClient(name = ReferentielEtatCivilWS.WEBSERVICE_NAME, targetNamespace = ReferentielEtatCivilWS.TARGET_NAMESPACE)
public class ReferentielEtatCivilClient extends Service {

    /**
     * Constructeur privé: classe instanciable uniquement par la factory
     * 
     * @param wsdlLocation
     *            chemin du wsdl (incluant le "?wsdl")
     */
    private ReferentielEtatCivilClient(final URL wsdlLocation) {
	super(wsdlLocation, ReferentielEtatCivilWS.SERVICE_QNAME);
    }

    /**
     * Instanciation du client WS
     * 
     * @return instance du client WS
     */
    @WebEndpoint(name = "referentiel-formes-juridiques")
    public ReferentielEtatCivilWS getReferentielEtatCivilPort() {
	return super.getPort(ReferentielEtatCivilWS.PORT_QNAME,
		ReferentielEtatCivilWS.class);
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
	public static ReferentielEtatCivilWS getClient(final String wsdlUrl)
		throws MalformedURLException {
	    final ReferentielEtatCivilClient client = new ReferentielEtatCivilClient(
		    new URL(wsdlUrl));
	    return client.getReferentielEtatCivilPort();
	}
    }

}
