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

    @WebMethod(operationName = "getClasse", action = "getClasse")
    @WebResult(name = "classe")
    ClasseWS getClasse(@WebParam(name = "classeId") final int classeId)
	    throws ReferentielOfsException;

    @WebResult(name = "classe")
    @Cachable(name = "classes", size = Cachable.MEDIUM)
    List<ClasseWS> getClasses() throws ReferentielOfsException;

    @WebMethod(operationName = "getClassesByDivision", action = "getClassesByDivision")
    @WebResult(name = "classe")
    List<ClasseWS> getClassesByDivision(
	    @WebParam(name = "divisionId") final int divisionId)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getDivision", action = "getDivision")
    @WebResult(name = "division")
    DivisionWS getDivision(@WebParam(name = "divisionId") final int divisionId)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getDivisions", action = "getDivisions")
    @WebResult(name = "division")
    List<DivisionWS> getDivisions() throws ReferentielOfsException;

    @WebMethod(operationName = "getGenre", action = "getGenre")
    @WebResult(name = "genre")
    GenreWS getGenre(@WebParam(name = "genreId") final int genreId)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getGenres", action = "getGenres")
    @WebResult(name = "genre")
    List<GenreWS> getGenres() throws ReferentielOfsException;

    @WebMethod(operationName = "getGenresByGroup", action = "getGenresByGroup")
    @WebResult(name = "genre")
    List<GenreWS> getGenresByGroup(
	    @WebParam(name = "groupeId") final int groupeId)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getGroupe", action = "getGroupe")
    @WebResult(name = "groupe")
    GroupeWS getGroupe(@WebParam(name = "groupeId") final int groupeId)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "getGroupes", action = "getGroupes")
    @WebResult(name = "groupe")
    List<GroupeWS> getGroupes() throws ReferentielOfsException;

    @WebMethod(operationName = "getGroupesByClasse", action = "getGroupesByClasse")
    @WebResult(name = "groupe")
    List<GroupeWS> getGroupesByClasse(
	    @WebParam(name = "classeId") final int classeId)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "searchClasse", action = "searchClasse")
    @WebResult(name = "classe")
    List<ClasseWS> searchClasse(@WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "searchClasseRegexp", action = "searchClasseRegexp")
    @WebResult(name = "classe")
    List<ClasseWS> searchClasseRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "searchDivision", action = "searchDivision")
    @WebResult(name = "division")
    List<DivisionWS> searchDivision(
	    @WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "searchDivisionRegexp", action = "searchDivisionRegexp")
    @WebResult(name = "division")
    List<DivisionWS> searchDivisionRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "searchGenre", action = "searchGenre")
    @WebResult(name = "genre")
    List<GenreWS> searchGenre(@WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "searchGenreRegexp", action = "searchGenreRegexp")
    @WebResult(name = "genre")
    List<GenreWS> searchGenreRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "searchGroupe", action = "searchGroupe")
    @WebResult(name = "groupe")
    List<GroupeWS> searchGroupe(@WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException;

    @WebMethod(operationName = "searchGroupeRegexp", action = "searchGroupeRegexp")
    @WebResult(name = "groupe")
    List<GroupeWS> searchGroupeRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException;

}