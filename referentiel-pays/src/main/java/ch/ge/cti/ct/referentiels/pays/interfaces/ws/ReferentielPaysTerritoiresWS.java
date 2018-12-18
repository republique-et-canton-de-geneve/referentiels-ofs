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

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.namespace.QName;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.ContinentWS;
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.PaysWS;
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.RegionWS;

/**
 * Interface du service au format JAX-WS
 * 
 * @author DESMAZIERESJ
 * 
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

    /**
     * Liste de tous les continents
     * 
     * @return liste des continents
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getContinents", action = "getContinents")
    @WebResult(name = "continent")
    List<ContinentWS> getContinents() throws ReferentielOfsException;

    /**
     * Recherhce de continent par son identifiant
     * 
     * @param continentId
     *            identifiant continent
     * @return continent
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getContinent", action = "getContinent")
    @WebResult(name = "continent")
    ContinentWS getContinent(@WebParam(name = "continent") final int continentId)
	    throws ReferentielOfsException;

    /**
     * Liste des régions d'un continent
     * 
     * @param continentId
     *            identifiant région
     * @return liste des régions
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getRegionsByContinent", action = "getRegionsByContinent")
    @WebResult(name = "region")
    List<RegionWS> getRegionsByContinent(
	    @WebParam(name = "continent") final int continentId)
	    throws ReferentielOfsException;

    /**
     * Liste de toutes les régions
     * 
     * @return liste des régions
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getRegions", action = "getRegions")
    @WebResult(name = "region")
    List<RegionWS> getRegions() throws ReferentielOfsException;

    /**
     * Recherche de région par son identifiant
     * 
     * @param regionId
     *            identifiant région
     * @return région
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getRegion", action = "getRegion")
    @WebResult(name = "region")
    RegionWS getRegion(@WebParam(name = "region") final int regionId)
	    throws ReferentielOfsException;

    /**
     * Liste des pays d'une région
     * 
     * @param regionId
     *            identifiant région
     * @return liste des pays
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getPaysByRegion", action = "getPaysByRegion")
    @WebResult(name = "pays")
    List<PaysWS> getPaysByRegion(@WebParam(name = "region") final int regionId)
	    throws ReferentielOfsException;

    /**
     * Liste des pays d'un continent
     * 
     * @param continentId
     *            identifiant région
     * @return liste des pays
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getPaysByContinent", action = "getPaysByContinent")
    @WebResult(name = "pays")
    List<PaysWS> getPaysByContinent(
	    @WebParam(name = "continent") final int continentId)
	    throws ReferentielOfsException;

    /**
     * 
     * @return
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getPays", action = "getPays")
    @WebResult(name = "pays")
    List<PaysWS> getPays() throws ReferentielOfsException;

    /**
     * Recherche de pays par son code ISO2
     * 
     * @param iso2
     *            code ISO2
     * @return pays
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getPaysByIso2", action = "getPaysByIso2")
    @WebResult(name = "pays")
    PaysWS getPaysByIso2(@WebParam(name = "iso2") final String iso2)
	    throws ReferentielOfsException;

    /**
     * Recherche de pays par son code ISO3
     * 
     * @param iso3
     *            code ISO3
     * @return pays
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getPaysByIso3", action = "getPaysByIso3")
    @WebResult(name = "pays")
    PaysWS getPaysByIso3(@WebParam(name = "iso3") final String iso3)
	    throws ReferentielOfsException;

    /**
     * Recherche de la liste des pays commençant par un pattern
     * 
     * @param critere
     *            critère de recherche
     * @return liste de pays
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "searchPays", action = "searchPays")
    @WebResult(name = "pays")
    List<PaysWS> searchPays(@WebParam(name = "critere") final String critere)
	    throws ReferentielOfsException;

    /**
     * Recherche de la liste des pays vérifiant une expression régulière
     * 
     * @param regexp
     *            expression régulière de recherche
     * @return liste de pays
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "searchPaysRegexp", action = "searchPaysRegexp")
    @WebResult(name = "pays")
    List<PaysWS> searchPaysRegexp(@WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException;

}