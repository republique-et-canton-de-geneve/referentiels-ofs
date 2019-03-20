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

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.PaysWS;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import java.util.List;

/**
 * Interface du service au format JAX-WS
 * 
 * @author DESMAZIERESJ
 */
@WebService(targetNamespace = ReferentielPaysTerritoiresWS.TARGET_NAMESPACE)
public interface ReferentielPaysTerritoiresWS {

    /** nom du service web */
    String WEBSERVICE_NAME = "referentiel-pays-JAXWS";

    /** nom du service */
    String SERVICE_NAME = "referentiel-pays-service";

    /** nom du port */
    String PORT_NAME = "referentiel-pays-port";

    /** namespace du service */
    String TARGET_NAMESPACE = "http://ch.ge.cti.ct.referentiels.pays/referentiel-pays/";

    /** QName du service */
    QName SERVICE_QNAME = new QName(TARGET_NAMESPACE, SERVICE_NAME);

    /** QName du port */
    QName PORT_QNAME = new QName(TARGET_NAMESPACE, PORT_NAME);

    @WebMethod(operationName = "getPays", action = "getPays")
    @WebResult(name = "pays")
    List<PaysWS> getPays() throws ReferentielOfsException;

    /**
     * Recherche de pays par son code ISO2.
     * 
     * @param iso2 code ISO2
     * @return pays
     */
    @WebMethod(operationName = "getPaysByIso2", action = "getPaysByIso2")
    @WebResult(name = "pays")
    PaysWS getPaysByIso2(@WebParam(name = "iso2") String iso2) throws ReferentielOfsException;

    /**
     * Recherche de pays par son code ISO3.
     * 
     * @param iso3 code ISO3
     * @return pays
     */
    @WebMethod(operationName = "getPaysByIso3", action = "getPaysByIso3")
    @WebResult(name = "pays")
    PaysWS getPaysByIso3(@WebParam(name = "iso3") String iso3) throws ReferentielOfsException;

    /**
     * Recherche de la liste des pays commençant par un pattern
     * 
     * @param critere critère de recherche
     * @return liste de pays
     */
    @WebMethod(operationName = "searchPays", action = "searchPays")
    @WebResult(name = "pays")
    List<PaysWS> searchPays(@WebParam(name = "critere") String critere) throws ReferentielOfsException;

}
