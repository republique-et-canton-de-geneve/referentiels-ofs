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

import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CantonWS;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CommuneWS;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.DistrictWS;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

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
@WebService(targetNamespace = ReferentielCommunesWS.TARGET_NAMESPACE)
public interface ReferentielCommunesWS {

    /**
     * nom du service web
     */
    String WEBSERVICE_NAME = "referentiel-communes-JAXWS";

    /**
     * nom du service
     */
    String SERVICE_NAME = "referentiel-communes-service";

    /**
     * nom du port
     */
    String PORT_NAME = "referentiel-communes-port";

    /**
     * namespace du service
     */
    String TARGET_NAMESPACE = "http://ch.ge.cti.ct.referentiels.communes/referentiel-communes/";

    /**
     * QName du service
     */
    QName SERVICE_QNAME = new QName(TARGET_NAMESPACE, SERVICE_NAME);

    /**
     * QName du port
     */
    QName PORT_QNAME = new QName(TARGET_NAMESPACE, PORT_NAME);

    /**
     * Liste de tous les cantons actifs à la date du jour.
     *
     * @return liste de tous les cantons
     * @throws ReferentielOfsException exception de traitement
     */
    @WebMethod(operationName = "getCantons", action = "getCantons")
    @WebResult(name = "canton")
    List<CantonWS> getCantons()
            throws ReferentielOfsException;

    /**
     * Recherche de canton par son code.
     *
     * @param codeCanton identifiant du canton
     * @return canton
     * @throws ReferentielOfsException exception de traitement
     */
    @WebMethod(operationName = "getCanton", action = "getCanton")
    @WebResult(name = "canton")
    CantonWS getCanton(
            @WebParam(name = "canton") String codeCanton)
            throws ReferentielOfsException;

    /**
     * Liste des districts d'un canton.
     *
     * @param codeCanton identifiant du canton
     * @return liste des districts
     * @throws ReferentielOfsException exception de traitement
     */
    @WebMethod(operationName = "getDistrictsByCanton", action = "getDistrictsByCanton")
    @WebResult(name = "district")
    List<DistrictWS> getDistrictsByCanton(
            @WebParam(name = "canton") String codeCanton)
            throws ReferentielOfsException;

    /**
     * Recherche d'un district par son identifiant.
     *
     * @param districtId identifiant (numero d'historisation) du district
     * @return district
     * @throws ReferentielOfsException exception de traitement
     */
    @WebMethod(operationName = "getDistrict", action = "getDistrict")
    @WebResult(name = "district")
    DistrictWS getDistrict(@WebParam(name = "district") int districtId)
            throws ReferentielOfsException;

    /**
     * Recherche des communes d'un canton.
     *
     * @param codeCanton identifiant du canton
     * @return liste des communes
     * @throws ReferentielOfsException exception de traitement
     */
    @WebMethod(operationName = "getCommunesByCanton", action = "getCommunesByCanton")
    @WebResult(name = "commune")
    List<CommuneWS> getCommunesByCanton(
            @WebParam(name = "canton") String codeCanton)
            throws ReferentielOfsException;

    /**
     * Recherche des communes d'un canton.
     *
     * @param codeCanton identifiant du canton
     * @return liste des communes
     * @throws ReferentielOfsException exception de traitement
     */
    @WebMethod(operationName = "getCommunesHistoriquesByCanton", action = "getCommunesHistoriquesByCanton")
    @WebResult(name = "commune")
    List<CommuneWS> getCommunesHistoriquesByCanton(
            @WebParam(name = "canton") String codeCanton)
            throws ReferentielOfsException;

    /**
     * Recherche de commune par son identifiant.
     *
     * @param communeId identifiant de commune
     * @return commune
     * @throws ReferentielOfsException exception de traitement
     */
    @WebMethod(operationName = "getCommune", action = "getCommune")
    @WebResult(name = "commune")
    CommuneWS getCommune(
            @WebParam(name = "commune") int communeId)
            throws ReferentielOfsException;

    /**
     * Recherche des communes correspondant à un critère (filtre sur le début du nom).
     *
     * @param critere critere de recherche
     * @return liste de communes
     * @throws ReferentielOfsException exception de traitement
     */
    @WebMethod(operationName = "searchCommune", action = "searchCommune")
    @WebResult(name = "commune")
    List<CommuneWS> searchCommune(
            @WebParam(name = "critere") String critere)
            throws ReferentielOfsException;

}
