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
package ch.ge.cti.ct.referentiels.formeJuridique.interfaces.ws;

import java.util.List;

import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.formeJuridique.model.FormeJuridique;
import ch.ge.cti.ct.referentiels.formeJuridique.service.ReferentielFormesJuridiquesServiceAble;
import ch.ge.cti.ct.referentiels.formeJuridique.service.impl.ReferentielFormesJuridiquesService;
import ch.ge.cti.ct.referentiels.ofs.Loggable;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsExceptionIntercept;
import ch.ge.cti.ct.referentiels.ofs.cache.ReferentielOfsCacheIntercept;

/**
 * Exposition du service au format JAX-WS
 * 
 * @author DESMAZIERESJ
 * 
 */
@WebService(name = ReferentielFormesJuridiquesWS.WEBSERVICE_NAME, serviceName = ReferentielFormesJuridiquesWS.SERVICE_NAME, portName = ReferentielFormesJuridiquesWS.PORT_NAME, targetNamespace = ReferentielFormesJuridiquesWS.TARGET_NAMESPACE, endpointInterface = "ch.ge.cti.ct.referentiels.formeJuridique.interfaces.ws.ReferentielFormesJuridiquesWS")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
@Interceptors({ ReferentielOfsExceptionIntercept.class,
	ReferentielOfsCacheIntercept.class })
public class ReferentielFormesJuridiquesSEI implements
	ReferentielFormesJuridiquesWS, Loggable {

    /** Référence sur l'implémentation */
    private static final ReferentielFormesJuridiquesServiceAble service = ReferentielFormesJuridiquesService.INSTANCE;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielFormesJuridiquesSEI.class);

    @Override
    public Logger log() {
	return LOG;
    }

    @Override
    @WebMethod(operationName = "getFormesJuridiques", action = "getFormesJuridiques")
    @WebResult(name = "formeJuridique")
    public List<FormeJuridique> getFormesJuridiques()
	    throws ReferentielOfsException {
	return service.getFormesJuridiques();
    }

    @Override
    @WebMethod(operationName = "getFormeJuridique", action = "getFormeJuridique")
    @WebResult(name = "formeJuridique")
    public FormeJuridique getFormeJuridique(
	    @WebParam(name = "formeJuridique") final int formeJuridiqueId)
	    throws ReferentielOfsException {
	if (formeJuridiqueId <= 0) {
	    return null;
	}
	return service.getFormeJuridique(formeJuridiqueId);
    }

}
