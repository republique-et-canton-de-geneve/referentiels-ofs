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
package ch.ge.cti.ct.referentiels.etatCivil.interfaces.ws;

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

import ch.ge.cti.ct.referentiels.etatCivil.model.EtatCivil;
import ch.ge.cti.ct.referentiels.etatCivil.service.ReferentielEtatCivilServiceAble;
import ch.ge.cti.ct.referentiels.etatCivil.service.impl.ReferentielEtatCivilService;
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
@WebService(name = ReferentielEtatCivilWS.WEBSERVICE_NAME, serviceName = ReferentielEtatCivilWS.SERVICE_NAME, portName = ReferentielEtatCivilWS.PORT_NAME, targetNamespace = ReferentielEtatCivilWS.TARGET_NAMESPACE, endpointInterface = "ch.ge.cti.ct.referentiels.etatCivil.interfaces.ws.ReferentielEtatCivilWS")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
@Interceptors({ ReferentielOfsExceptionIntercept.class,
	ReferentielOfsCacheIntercept.class })
public class ReferentielEtatCivilSEI implements ReferentielEtatCivilWS,
	Loggable {

    /** Référence sur l'implémentation */
    private static final ReferentielEtatCivilServiceAble service = ReferentielEtatCivilService.INSTANCE;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielEtatCivilSEI.class);

    @Override
    public Logger log() {
	return LOG;
    }

    @Override
    @WebMethod(operationName = "getEtatsCivils", action = "getEtatsCivils")
    @WebResult(name = "etatCivil")
    public List<EtatCivil> getEtatsCivils() throws ReferentielOfsException {
	return service.getEtatsCivils();
    }

    @Override
    @WebMethod(operationName = "getEtatCivil", action = "getEtatCivil")
    @WebResult(name = "etatCivil")
    public EtatCivil getEtatCivil(
	    @WebParam(name = "etatCivil") final int etatCivilId)
	    throws ReferentielOfsException {
	if (etatCivilId <= 0) {
	    return null;
	}
	return service.getEtatCivil(etatCivilId);
    }
}
