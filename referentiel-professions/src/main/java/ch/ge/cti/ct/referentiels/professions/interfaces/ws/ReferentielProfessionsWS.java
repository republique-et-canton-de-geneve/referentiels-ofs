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
package ch.ge.cti.ct.referentiels.professions.interfaces.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.namespace.QName;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.cache.Cachable;
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.ClasseWS;
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.DivisionWS;
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.GenreWS;
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.GroupeWS;

/**
 * Interface du service au format JAX-WS
 * 
 * @author DESMAZIERESJ
 * 
 */
@WebService(targetNamespace = ReferentielProfessionsWS.TARGET_NAMESPACE)
public interface ReferentielProfessionsWS {

    /** nom du service web */
    String WEBSERVICE_NAME = "referentiel-professions-JAXWS";
    /** nom du service */
    String SERVICE_NAME = "referentiel-professions-service";
    /** nom du port */
    String PORT_NAME = "referentiel-professions-port";
    /** namespace du service */
    String TARGET_NAMESPACE = "http://ch.ge.cti.ct.referentiels.professions/referentiel-professions/";

    /** QName du service */
    QName SERVICE_QNAME = new QName(TARGET_NAMESPACE, SERVICE_NAME);
    /** QName du port */
    QName PORT_QNAME = new QName(TARGET_NAMESPACE, PORT_NAME);

    /**
     * Recherche de classe par identifiant
     * 
     * @param classeId
     *            identifiant de classe
     * @return classe
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getClasse", action = "getClasse")
    @WebResult(name = "classe")
    ClasseWS getClasse(@WebParam(name = "classeId") final int classeId)
	    throws ReferentielOfsException;

    /**
     * Liste de toutes les classes
     * 
     * @return liste de classes
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebResult(name = "classe")
    @Cachable(name = "classes", size = Cachable.MEDIUM)
    List<ClasseWS> getClasses() throws ReferentielOfsException;

    /**
     * 
     * @param divisionId
     *            identifiant de division
     * @return liste de classes
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getClassesByDivision", action = "getClassesByDivision")
    @WebResult(name = "classe")
    List<ClasseWS> getClassesByDivision(
	    @WebParam(name = "divisionId") final int divisionId)
	    throws ReferentielOfsException;

    /**
     * Recherche de division par son identifiant
     * 
     * @param divisionId
     *            identifiant de division
     * @return division
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getDivision", action = "getDivision")
    @WebResult(name = "division")
    DivisionWS getDivision(@WebParam(name = "divisionId") final int divisionId)
	    throws ReferentielOfsException;

    /**
     * Liste de toutes les divisions
     * 
     * @return liste de divisions
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getDivisions", action = "getDivisions")
    @WebResult(name = "division")
    List<DivisionWS> getDivisions() throws ReferentielOfsException;

    /**
     * Recherche de genre par identifiant
     * 
     * @param genreId
     *            identifiant de genre
     * @return genre
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getGenre", action = "getGenre")
    @WebResult(name = "genre")
    GenreWS getGenre(@WebParam(name = "genreId") final int genreId)
	    throws ReferentielOfsException;

    /**
     * Liste de tous les genres
     * 
     * @return liste de genres
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getGenres", action = "getGenres")
    @WebResult(name = "genre")
    List<GenreWS> getGenres() throws ReferentielOfsException;

    /**
     * Liste des genres d'un groupe
     * 
     * @param groupeId
     *            identifiant de groupe
     * @return liste de genres
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getGenresByGroup", action = "getGenresByGroup")
    @WebResult(name = "genre")
    List<GenreWS> getGenresByGroup(
	    @WebParam(name = "groupeId") final int groupeId)
	    throws ReferentielOfsException;

    /**
     * Recherche de groupe par son identifiant
     * 
     * @param groupeId
     *            identifiant de groupe
     * @return groupe
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getGroupe", action = "getGroupe")
    @WebResult(name = "groupe")
    GroupeWS getGroupe(@WebParam(name = "groupeId") final int groupeId)
	    throws ReferentielOfsException;

    /**
     * Liste de tous les groupes
     * 
     * @return liste de groupes
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getGroupes", action = "getGroupes")
    @WebResult(name = "groupe")
    List<GroupeWS> getGroupes() throws ReferentielOfsException;

    /**
     * Liste des groupe d'une classe
     * 
     * @param classeId
     *            identifiant de classe
     * @return liste de groupes
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getGroupesByClasse", action = "getGroupesByClasse")
    @WebResult(name = "groupe")
    List<GroupeWS> getGroupesByClasse(
	    @WebParam(name = "classeId") final int classeId)
	    throws ReferentielOfsException;

    /**
     * Recherche de la liste des classes commençant par un pattern
     * 
     * @param pattern
     *            critère de recherche
     * @return liste de classes
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "searchClasse", action = "searchClasse")
    @WebResult(name = "classe")
    List<ClasseWS> searchClasse(@WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException;

    /**
     * Recherche de la liste des classes vérifiant une expression régulière
     * 
     * @param regexp
     *            expression régulière de recherche
     * @return liste de classes
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "searchClasseRegexp", action = "searchClasseRegexp")
    @WebResult(name = "classe")
    List<ClasseWS> searchClasseRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException;

    /**
     * Recherche de la liste des divisions commençant par un pattern
     * 
     * @param pattern
     *            critère de recherche
     * @return liste de divisions
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "searchDivision", action = "searchDivision")
    @WebResult(name = "division")
    List<DivisionWS> searchDivision(
	    @WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException;

    /**
     * Recherche de la liste des divisions vérifiant une expression régulière
     * 
     * @param regexp
     *            expression régulière de recherche
     * @return liste de divisions
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "searchDivisionRegexp", action = "searchDivisionRegexp")
    @WebResult(name = "division")
    List<DivisionWS> searchDivisionRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException;

    /**
     * Recherche de la liste des genres commençant par un pattern
     * 
     * @param pattern
     *            critère de recherche
     * @return liste de genres
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "searchGenre", action = "searchGenre")
    @WebResult(name = "genre")
    List<GenreWS> searchGenre(@WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException;

    /**
     * Recherche de la liste des genres vérifiant une expression régulière
     * 
     * @param regexp
     *            expression régulière de recherche
     * @return liste de genres
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "searchGenreRegexp", action = "searchGenreRegexp")
    @WebResult(name = "genre")
    List<GenreWS> searchGenreRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException;

    /**
     * Recherche de la liste des groupes commençant par un pattern
     * 
     * @param pattern
     *            critère de recherche
     * @return liste de groupes
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "searchGroupe", action = "searchGroupe")
    @WebResult(name = "groupe")
    List<GroupeWS> searchGroupe(@WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException;

    /**
     * Recherche de la liste des groupes vérifiant une expression régulière
     * 
     * @param regexp
     *            expression régulière de recherche
     * @return liste de groupes
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "searchGroupeRegexp", action = "searchGroupeRegexp")
    @WebResult(name = "groupe")
    List<GroupeWS> searchGroupeRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException;

}