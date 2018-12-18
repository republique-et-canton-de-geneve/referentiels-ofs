/*
 * Referentiels OFS
 *
 * Copyright (C) 2018 RÃ©publique et canton de GenÃ¨ve
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.ge.cti.ct.referentiels.socioprofessionnel.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

import ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws.ReferentielSocioprofessionnelWS;

/**
 * Client du WebService ReferentielSocioprofessionnelWS
 * 
 * @author DESMAZIERESJ
 * 
 */
@WebServiceClient(name = ReferentielSocioprofessionnelWS.WEBSERVICE_NAME, targetNamespace = ReferentielSocioprofessionnelWS.TARGET_NAMESPACE)
public final class ReferentielSocioprofessionnelClient extends Service {

    /**
     * Constructeur privé: classe instanciable uniquement par la factory
     * 
     * @param wsdlLocation
     *            chemin du wsdl (incluant le "?wsdl")
     */
    private ReferentielSocioprofessionnelClient(final URL wsdlLocation) {
	super(wsdlLocation, ReferentielSocioprofessionnelWS.SERVICE_QNAME);
    }

    /**
     * Instanciation du client WS
     * 
     * @return INSTANCE du client WS
     */
    @WebEndpoint(name = "referentiel-formes-juridiques")
    public ReferentielSocioprofessionnelWS getReferentielSocioprofessionnelPort() {
	return super.getPort(ReferentielSocioprofessionnelWS.PORT_QNAME,
		ReferentielSocioprofessionnelWS.class);
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
	public static ReferentielSocioprofessionnelWS getClient(
		final String wsdlUrl) throws MalformedURLException {
	    final ReferentielSocioprofessionnelClient client = new ReferentielSocioprofessionnelClient(
		    new URL(wsdlUrl));
	    return client.getReferentielSocioprofessionnelPort();
	}
    }

}
