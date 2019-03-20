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
package ch.ge.cti.ct.referentiels.pays.interfaces.ws;

import ch.ge.cti.ct.referentiels.ofs.Loggable;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsExceptionIntercept;
import ch.ge.cti.ct.referentiels.ofs.cache.Cachable;
import ch.ge.cti.ct.referentiels.ofs.cache.ReferentielOfsCacheIntercept;
import ch.ge.cti.ct.referentiels.pays.data.Country;
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.PaysWS;
import ch.ge.cti.ct.referentiels.pays.service.ReferentielPaysTerritoiresServiceAble;
import ch.ge.cti.ct.referentiels.pays.service.impl.ReferentielPaysTerritoiresService;
import com.google.common.base.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Exposition du service au format JAX-WS.
 * 
 * @author DESMAZIERESJ
 * @author Yves Dubois-Pelerin
 */
@WebService(
        name = ReferentielPaysTerritoiresWS.WEBSERVICE_NAME,
        serviceName = ReferentielPaysTerritoiresWS.SERVICE_NAME,
        portName = ReferentielPaysTerritoiresWS.PORT_NAME,
        targetNamespace = ReferentielPaysTerritoiresWS.TARGET_NAMESPACE,
        endpointInterface = "ch.ge.cti.ct.referentiels.pays.interfaces.ws.ReferentielPaysTerritoiresWS")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
@Interceptors({ReferentielOfsExceptionIntercept.class, ReferentielOfsCacheIntercept.class})
public class ReferentielPaysTerritoiresSEI implements
    ReferentielPaysTerritoiresWS, Loggable {

    private static final Logger LOG = LoggerFactory.getLogger(ReferentielPaysTerritoiresSEI.class);

    private final ReferentielPaysTerritoiresServiceAble service = new ReferentielPaysTerritoiresService();

    @Override
    public Logger log() {
    return LOG;
    }

    @Override
    @WebMethod(operationName = "getPays", action = "getPays")
    @WebResult(name = "pays")
    @Cachable(name = "pays", size = Cachable.LARGE)
    public List<PaysWS> getPays() throws ReferentielOfsException {
        try {
            return service.getPays2().stream()
                    .map(c -> new CountryConverter().apply(c))
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw processException(e);
        }
    }

    @Override
    @WebMethod(operationName = "getPaysByIso2", action = "getPaysByIso2")
    @WebResult(name = "pays")
    public PaysWS getPaysByIso2(@WebParam(name = "iso2") String iso2) throws ReferentielOfsException {
        try {
            Optional<Country> c = service.getPaysByIso2(iso2);
            return c.isPresent() ? new CountryConverter().apply(c.get()) : null;
        } catch (RuntimeException e) {
            throw processException(e);
        }
    }

    @Override
    @WebMethod(operationName = "getPaysByIso3", action = "getPaysByIso3")
    @WebResult(name = "pays")
    public PaysWS getPaysByIso3(@WebParam(name = "iso3") String iso3) throws ReferentielOfsException {
        try {
            Optional<Country> c = service.getPaysByIso3(iso3);
            return c.isPresent() ? new CountryConverter().apply(c.get()) : null;
        } catch (RuntimeException e) {
            throw processException(e);
        }
    }

    @Override
    @WebMethod(operationName = "searchPays", action = "searchPays")
    @WebResult(name = "pays")
    public List<PaysWS> searchPays(@WebParam(name = "critere") String critere) throws ReferentielOfsException {
        try {
            return service.searchPays2(critere).stream()
                    .map(c -> new CountryConverter().apply(c))
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw processException(e);
        }
    }

    /**
     * Méthode partagée de traitement des exceptions<br/>
     * Les exceptions sont encapsulées dans une ReferentielPaysTerritoiresException<br/>
     * Sauf si ce sont déjà des ReferentielPaysTerritoiresException
     * 
     * @param e exception
     * @return ReferentielPaysTerritoiresException exception encapsulée
     */
    private ReferentielOfsException processException(final Exception e) {
        LOG.error(e.getClass().getName(), e);
        // pas de double encapsulation
        if (e instanceof ReferentielOfsException) {
            return (ReferentielOfsException) e;
        }
        return new ReferentielOfsException("Erreur technique lors du traitement de la demande", e);
    }

    /**
     * Fonction (closure) de copie du Country en PaysWS
     */
    private static class CountryConverter implements Function<Country, PaysWS> {
        @Override
        public PaysWS apply(final Country cin) {
            if (cin == null) {
                return null;
            }
            PaysWS cout = new PaysWS();
            cout.setId(cin.getId());
            cout.setNom(cin.getShortNameFr());
            cout.setNomLong(cin.getOfficialNameFr());
            cout.setIso2(cin.getIso2Id());
            cout.setIso3(cin.getIso3Id());
            cout.setContinentId(cin.getContinent());
            cout.setRegionId(cin.getRegion());
            return cout;
        }
    }

}
