package ch.ge.cti.ct.referentiels.professions.interfaces.ws;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import org.apache.commons.lang.StringUtils;
import org.jboss.wsf.spi.annotation.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.cache.Cachable;
import ch.ge.cti.ct.referentiels.ofs.cache.ReferentielOfsCacheIntercept;
import ch.ge.cti.ct.referentiels.ofs.service.jmx.ReferentielStatsIntercept;
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.ClasseWS;
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.DivisionWS;
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.GenreWS;
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.GroupeWS;
import ch.ge.cti.ct.referentiels.professions.model.Classe;
import ch.ge.cti.ct.referentiels.professions.model.Division;
import ch.ge.cti.ct.referentiels.professions.model.Genre;
import ch.ge.cti.ct.referentiels.professions.model.Groupe;
import ch.ge.cti.ct.referentiels.professions.model.ReferentielProfessions;
import ch.ge.cti.ct.referentiels.professions.service.ReferentielProfessionsServiceAble;
import ch.ge.cti.ct.referentiels.professions.service.impl.ReferentielProfessionsService;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

/**
 * Exposition du service au format JAX-WS
 * 
 * @author DESMAZIERESJ
 * 
 */
@Stateless
@WebService(name = "referentiel-professions-JAXWS", serviceName = "referentiel-professions", portName = "referentiel-professions", targetNamespace = "http://ch.ge.cti.ct.referentiels.professions/referentiel-professions")
@WebContext(contextRoot = "/referentiels-ofs/professions", urlPattern = "/referentiel-professions")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
@Interceptors({ ReferentielStatsIntercept.class,
	ReferentielOfsCacheIntercept.class })
public class ReferentielProfessionsSEI {
    /* définition des tailles des modules de cache */
    private static final int DIVISIONS_CACHE = 200;
    private static final int CLASSES_CACHE = 500;
    private static final int GROUPES_CACHE = 1000;
    private static final int GENRES_CACHE = 1000;

    /** Référence sur l'implémentation */
    private final ReferentielProfessionsServiceAble service = ReferentielProfessionsService.instance;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielProfessions.class);

    @WebMethod(operationName = "getClasse", action = "getClasse")
    @WebResult(name = "classe")
    public ClasseWS getClasse(@WebParam(name = "classeId") final int classeId)
	    throws ReferentielOfsException {
	LOG.debug("getClasse(classeId='{}')", classeId);
	if (classeId <= 0) {
	    return null;
	}
	try {
	    return new ClasseConvert().apply(service.getClasse(classeId));
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getClasses", action = "getClasses")
    @WebResult(name = "classe")
    @Cachable(name = "classes", size = CLASSES_CACHE)
    public List<ClasseWS> getClasses() throws ReferentielOfsException {
	LOG.debug("getClasses()");
	try {
	    return FluentIterable.from(service.getClasses())
		    .transform(new ClasseConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getClassesByDivision", action = "getClassesByDivision")
    @WebResult(name = "classe")
    @Cachable(name = "classes", size = CLASSES_CACHE)
    public List<ClasseWS> getClassesByDivision(
	    @WebParam(name = "divisionId") final int divisionId)
	    throws ReferentielOfsException {
	LOG.debug("getClassesByDivision(divisionId='{}')", divisionId);
	if (divisionId <= 0) {
	    return new LinkedList<ClasseWS>();
	}
	try {
	    return FluentIterable.from(service.getClasses(divisionId))
		    .transform(new ClasseConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getDivision", action = "getDivision")
    @WebResult(name = "division")
    public DivisionWS getDivision(
	    @WebParam(name = "divisionId") final int divisionId)
	    throws ReferentielOfsException {
	LOG.debug("getDivision(divisionId='{}')", divisionId);
	if (divisionId <= 0) {
	    return null;
	}
	try {
	    return new DivisionConvert().apply(service.getDivision(divisionId));
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getDivisions", action = "getDivisions")
    @WebResult(name = "division")
    @Cachable(name = "divisions", size = DIVISIONS_CACHE)
    public List<DivisionWS> getDivisions() throws ReferentielOfsException {
	LOG.debug("getDivisions()");
	try {
	    return FluentIterable.from(service.getDivisions())
		    .transform(new DivisionConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getGenre", action = "getGenre")
    @WebResult(name = "genre")
    public GenreWS getGenre(@WebParam(name = "genreId") final int genreId)
	    throws ReferentielOfsException {
	LOG.debug("getGenre(genreId='{}')", genreId);
	if (genreId <= 0) {
	    return null;
	}
	try {
	    return new GenreConvert().apply(service.getGenre(genreId));
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getGenres", action = "getGenres")
    @WebResult(name = "genre")
    @Cachable(name = "genres", size = GENRES_CACHE)
    public List<GenreWS> getGenres() throws ReferentielOfsException {
	LOG.debug("getGenres()");
	try {
	    return FluentIterable.from(service.getGenres())
		    .transform(new GenreConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getGenresByGroup", action = "getGenresByGroup")
    @WebResult(name = "genre")
    @Cachable(name = "genres", size = GENRES_CACHE)
    public List<GenreWS> getGenresByGroup(
	    @WebParam(name = "groupeId") final int groupeId)
	    throws ReferentielOfsException {
	LOG.debug("getGenresByGroup(groupeId='{}')", groupeId);
	if (groupeId <= 0) {
	    return new LinkedList<GenreWS>();
	}
	try {
	    return FluentIterable.from(service.getGenres(groupeId))
		    .transform(new GenreConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getGroupe", action = "getGroupe")
    @WebResult(name = "groupe")
    public GroupeWS getGroupe(@WebParam(name = "groupeId") final int groupeId)
	    throws ReferentielOfsException {
	LOG.debug("getGroupe(groupeId='{}')", groupeId);
	if (groupeId <= 0) {
	    return null;
	}
	try {
	    return new GroupeConvert().apply(service.getGroupe(groupeId));
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getGroupes", action = "getGroupes")
    @WebResult(name = "groupe")
    @Cachable(name = "groupes", size = GROUPES_CACHE)
    public List<GroupeWS> getGroupes() throws ReferentielOfsException {
	LOG.debug("getGroupes()");
	try {
	    return FluentIterable.from(service.getGroupes())
		    .transform(new GroupeConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getGroupesByClasse", action = "getGroupesByClasse")
    @WebResult(name = "groupe")
    @Cachable(name = "groupes", size = GROUPES_CACHE)
    public List<GroupeWS> getGroupesByClasse(
	    @WebParam(name = "classeId") final int classeId)
	    throws ReferentielOfsException {
	LOG.debug("getGroupesByClasse(classeId='{}')", classeId);
	if (classeId <= 0) {
	    return new LinkedList<GroupeWS>();
	}
	try {
	    return FluentIterable.from(service.getGroupes(classeId))
		    .transform(new GroupeConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "searchClasse", action = "searchClasse")
    @WebResult(name = "classe")
    public List<ClasseWS> searchClasse(
	    @WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException {
	LOG.debug("searchClasse(pattern='{}')", pattern);
	if (StringUtils.isBlank(pattern)) {
	    return new LinkedList<ClasseWS>();
	}
	try {
	    return FluentIterable.from(service.searchClasse(pattern))
		    .transform(new ClasseConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "searchClasseRegexp", action = "searchClasseRegexp")
    @WebResult(name = "classe")
    public List<ClasseWS> searchClasseRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException {
	LOG.debug("searchClasseRegexp(regexp='{}')", regexp);
	if (StringUtils.isBlank(regexp)) {
	    return new LinkedList<ClasseWS>();
	}
	try {
	    return FluentIterable.from(service.searchClasseRegexp(regexp))
		    .transform(new ClasseConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "searchDivision", action = "searchDivision")
    @WebResult(name = "division")
    public List<DivisionWS> searchDivision(
	    @WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException {
	LOG.debug("searchDivision(pattern='{}')", pattern);
	if (StringUtils.isBlank(pattern)) {
	    return new LinkedList<DivisionWS>();
	}
	try {
	    return FluentIterable.from(service.searchDivision(pattern))
		    .transform(new DivisionConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "searchDivisionRegexp", action = "searchDivisionRegexp")
    @WebResult(name = "division")
    public List<DivisionWS> searchDivisionRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException {
	LOG.debug("searchDivisionRegexp(regexp='{}')", regexp);
	if (StringUtils.isBlank(regexp)) {
	    return new LinkedList<DivisionWS>();
	}
	try {
	    return FluentIterable.from(service.searchDivisionRegexp(regexp))
		    .transform(new DivisionConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "searchGenre", action = "searchGenre")
    @WebResult(name = "genre")
    public List<GenreWS> searchGenre(
	    @WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException {
	LOG.debug("searchGenre(pattern='{}')", pattern);
	if (StringUtils.isBlank(pattern)) {
	    return new LinkedList<GenreWS>();
	}
	try {
	    return FluentIterable.from(service.searchGenre(pattern))
		    .transform(new GenreConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "searchGenreRegexp", action = "searchGenreRegexp")
    @WebResult(name = "genre")
    public List<GenreWS> searchGenreRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException {
	LOG.debug("searchGenreRegexp(regexp='{}')", regexp);
	if (StringUtils.isBlank(regexp)) {
	    return new LinkedList<GenreWS>();
	}
	try {
	    return FluentIterable.from(service.searchGenreRegexp(regexp))
		    .transform(new GenreConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "searchGroupe", action = "searchGroupe")
    @WebResult(name = "groupe")
    public List<GroupeWS> searchGroupe(
	    @WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException {
	LOG.debug("searchGroupe(pattern='{}')", pattern);
	if (StringUtils.isBlank(pattern)) {
	    return new LinkedList<GroupeWS>();
	}
	try {
	    return FluentIterable.from(service.searchGroupe(pattern))
		    .transform(new GroupeConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "searchGroupeRegexp", action = "searchGroupeRegexp")
    @WebResult(name = "groupe")
    public List<GroupeWS> searchGroupeRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException {
	LOG.debug("searchGroupeRegexp(regexp='{}')", regexp);
	if (StringUtils.isBlank(regexp)) {
	    return new LinkedList<GroupeWS>();
	}
	try {
	    return FluentIterable.from(service.searchGroupeRegexp(regexp))
		    .transform(new GroupeConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    /**
     * Méthode partagée de traitement des exceptions<br/>
     * Les exceptions sont encapsulées dans une
     * ReferentielPaysTerritoiresException<br/>
     * Sauf si ce sont déjà des ReferentielPaysTerritoiresException
     * 
     * @param e
     *            exception
     * @return ReferentielPaysTerritoiresException exception encapsulée
     */
    private ReferentielOfsException processException(final Exception e) {
	// pas de double encapsulation
	if (e instanceof ReferentielOfsException) {
	    return (ReferentielOfsException) e;
	}
	return new ReferentielOfsException(
		"Erreur technique lors du traitement de la demande", e);
    }

    /**
     * Fonction (closure) de copie du continent en DivisionWS<br/>
     * On ne copie par l'arborescence descendante (Classe)
     */
    private class DivisionConvert implements Function<Division, DivisionWS> {
	@Override
	public DivisionWS apply(final Division cin) {
	    if (cin == null) {
		return null;
	    }
	    final DivisionWS cout = new DivisionWS();
	    cout.setId(cin.getId());
	    cout.setNom(cin.getNom());
	    return cout;
	}
    }

    /**
     * Fonction (closure) de copie du continent en ClasseWS<br/>
     * On ne copie par l'arborescence descendante (Classe)
     */
    private class ClasseConvert implements Function<Classe, ClasseWS> {
	@Override
	public ClasseWS apply(final Classe cin) {
	    if (cin == null) {
		return null;
	    }
	    final ClasseWS cout = new ClasseWS();
	    cout.setId(cin.getId());
	    cout.setNom(cin.getNom());
	    cout.setDivisionId(cin.getDivisionId());
	    return cout;
	}
    }

    /**
     * Fonction (closure) de copie du continent en GroupeWS<br/>
     * On ne copie par l'arborescence descendante (Groupe)
     */
    private class GroupeConvert implements Function<Groupe, GroupeWS> {
	@Override
	public GroupeWS apply(final Groupe cin) {
	    if (cin == null) {
		return null;
	    }
	    final GroupeWS cout = new GroupeWS();
	    cout.setId(cin.getId());
	    cout.setNom(cin.getNom());
	    cout.setDivisionId(cin.getDivisionId());
	    cout.setClasseId(cin.getClasseId());
	    return cout;
	}
    }

    /**
     * Fonction (closure) de copie du continent en GenreWS<br/>
     * On ne copie par l'arborescence descendante (Genre)
     */
    private class GenreConvert implements Function<Genre, GenreWS> {
	@Override
	public GenreWS apply(final Genre cin) {
	    if (cin == null) {
		return null;
	    }
	    final GenreWS cout = new GenreWS();
	    cout.setId(cin.getId());
	    cout.setNom(cin.getNom());
	    cout.setDivisionId(cin.getDivisionId());
	    cout.setClasseId(cin.getClasseId());
	    cout.setGroupeId(cin.getGroupeId());
	    return cout;
	}
    }
}
