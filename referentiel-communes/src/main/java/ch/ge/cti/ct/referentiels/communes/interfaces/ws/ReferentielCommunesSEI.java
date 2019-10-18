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
package ch.ge.cti.ct.referentiels.communes.interfaces.ws;

import ch.ge.cti.ct.referentiels.communes.data.Canton;
import ch.ge.cti.ct.referentiels.communes.data.District;
import ch.ge.cti.ct.referentiels.communes.data.Municipality;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CantonWS;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CommuneWS;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.DistrictWS;
import ch.ge.cti.ct.referentiels.communes.service.ReferentielCommunesService;
import ch.ge.cti.ct.referentiels.communes.service.impl.ReferentielCommunesServiceImpl;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsExceptionIntercept;
import ch.ge.cti.ct.referentiels.ofs.cache.Cachable;
import ch.ge.cti.ct.referentiels.ofs.cache.ReferentielOfsCacheIntercept;
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
import java.util.stream.Collectors;

/**
 * Exposition du service au format JAX-WS
 *
 * @author DESMAZIERESJ
 */
@WebService(name = ReferentielCommunesWS.WEBSERVICE_NAME, serviceName = ReferentielCommunesWS.SERVICE_NAME, portName = ReferentielCommunesWS.PORT_NAME, targetNamespace = ReferentielCommunesWS.TARGET_NAMESPACE, endpointInterface = "ch.ge.cti.ct.referentiels.communes.interfaces.ws.ReferentielCommunesWS")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
@Interceptors({ReferentielOfsExceptionIntercept.class, ReferentielOfsCacheIntercept.class})
public class ReferentielCommunesSEI implements ReferentielCommunesWS {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReferentielCommunesSEI.class);

    private final ReferentielCommunesService service = new ReferentielCommunesServiceImpl();

    @Override
    @WebMethod(operationName = "getCantons", action = "getCantons")
    @WebResult(name = "canton")
    @Cachable(name = "cantons", size = Cachable.SMALL)
    public List<CantonWS> getCantons() throws ReferentielOfsException {
        try {
            return service.getCantons().stream()
                    .map(c -> new CantonConverter().apply(c))
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw processException(e);
        }
    }

    @Override
    @WebMethod(operationName = "getCanton", action = "getCanton")
    @WebResult(name = "canton")
    public CantonWS getCanton(@WebParam(name = "canton") String codeCanton) throws ReferentielOfsException {
        try {
            return service.getCanton(codeCanton)
                    .map(c -> new CantonConverter().apply(c))
                    .orElse(null);
        } catch (RuntimeException e) {
            throw processException(e);
        }
    }

    @Override
    @WebMethod(operationName = "getDistrictsByCanton", action = "getDistrictsByCanton")
    @WebResult(name = "district")
    @Cachable(name = "districts", size = Cachable.MEDIUM)
    public List<DistrictWS> getDistrictsByCanton(@WebParam(name = "canton") String codeCanton)
            throws ReferentielOfsException {
        try {
            return service.getDistrictsByCanton(codeCanton).stream()
                    .map(d -> new DistrictConvert().apply(d))
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw processException(e);
        }
    }

    @Override
    @WebMethod(operationName = "getDistrict", action = "getDistrict")
    @WebResult(name = "district")
    public DistrictWS getDistrict(@WebParam(name = "district") int districtId)
            throws ReferentielOfsException {
        try {
            return service.getDistrict(districtId)
                    .map(d -> new DistrictConvert().apply(d))
                    .orElse(null);
        } catch (RuntimeException e) {
            throw processException(e);
        }
    }

    @Override
    @WebMethod(operationName = "getCommunesByCanton", action = "getCommunesByCanton")
    @WebResult(name = "commune")
    @Cachable(name = "communes", size = Cachable.LARGE)
    public List<CommuneWS> getCommunesByCanton(@WebParam(name = "canton") String codeCanton)
            throws ReferentielOfsException {
        try {
            return service.getMunicipalitiesByCanton(codeCanton).stream()
                    .map(m -> new MunicipalityConvert().apply(m))
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw processException(e);
        }
    }

    @Override
    @WebMethod(operationName = "getCommunesHistoriquesByCanton", action = "getCommunesHistoriquesByCanton")
    @WebResult(name = "commune")
    @Cachable(name = "communes", size = Cachable.LARGE)
    public List<CommuneWS> getCommunesHistoriquesByCanton(@WebParam(name = "canton") String codeCanton)
            throws ReferentielOfsException {
        return getCommunesByCanton(codeCanton);
    }

    @Override
    @WebMethod(operationName = "getCommune", action = "getCommune")
    @WebResult(name = "commune")
    public CommuneWS getCommune(@WebParam(name = "commune") int communeId)
            throws ReferentielOfsException {
        try {
            return service.getMunicipality(communeId)
                    .map(m -> new MunicipalityConvert().apply(m))
                    .orElse(null);
        } catch (RuntimeException e) {
            throw processException(e);
        }
    }

    @Override
    @WebMethod(operationName = "searchCommune", action = "searchCommune")
    @WebResult(name = "commune")
    public List<CommuneWS> searchCommune(@WebParam(name = "critere") String critere)
            throws ReferentielOfsException {
        try {
            return service.searchMunicipality(critere).stream()
                    .map(m -> new MunicipalityConvert().apply(m))
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw processException(e);
        }
    }

    /**
     * Méthode partagee de traitement des exceptions.
     * <br/>
     * Les exceptions sont encapsulées dans une ReferentielPaysTerritoiresException, sauf si ce sont déjà des
     * ReferentielPaysTerritoiresException
     */
    private ReferentielOfsException processException(Exception e) {
        LOGGER.error(e.getClass().getName(), e);
        // pas de double encapsulation
        if (e instanceof ReferentielOfsException) {
            return (ReferentielOfsException) e;
        }
        return new ReferentielOfsException("Erreur technique lors du traitement de la demande", e);
    }

    /**
     * Objet de copie d'un Canton en un CantonWS.
     * <br/>
     * On ne copie par l'arborescence descendante (districts).
     */
    private static class CantonConverter implements Function<Canton, CantonWS> {
        @Override
        public CantonWS apply(Canton cin) {
            if (cin == null) {
                return null;
            }
            CantonWS cout = new CantonWS();
            cout.setCode(cin.getCantonAbbreviation());
            cout.setNom(cin.getCantonLongName());
            cout.setNomCourt(cin.getCantonLongName());
            cout.setValidFrom(cin.getCantonDateOfChange());
            cout.setValidTo(null);
            return cout;
        }
    }

    /**
     * Objet de copie d'un District en un DistrictWS.
     */
    private static class DistrictConvert implements Function<District, DistrictWS> {
        @Override
        public DistrictWS apply(District din) {
            if (din == null) {
                return null;
            }
            DistrictWS dout = new DistrictWS();
            dout.setId(din.getDistrictHistId());
            dout.setNom(din.getDistrictLongName());
            dout.setNomCourt(din.getDistrictShortName());
            dout.setCodeCanton(din.getCanton().getCantonAbbreviation());
            return dout;
        }
    }

    /**
     * Objet de copie d'une Municipality en une Commune.
     */
    private static class MunicipalityConvert implements Function<Municipality, CommuneWS> {
        @Override
        public CommuneWS apply(Municipality cin) {
            if (cin == null) {
                return null;
            }
            CommuneWS cout = new CommuneWS();
            cout.setId(cin.getMunicipalityId());
            cout.setNom(cin.getMunicipalityLongName());
            cout.setNomCourt(cin.getMunicipalityShortName());
            cout.setCodeCanton(cin.getCantonAbbreviation());
            cout.setIdDistrict(cin.getDistrictHistId());
            return cout;
        }
    }

}
