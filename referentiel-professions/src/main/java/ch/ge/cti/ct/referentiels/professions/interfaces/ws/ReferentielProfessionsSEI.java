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

import java.util.LinkedList;
import java.util.List;

import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.ofs.Loggable;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsExceptionIntercept;
import ch.ge.cti.ct.referentiels.ofs.cache.Cachable;
import ch.ge.cti.ct.referentiels.ofs.cache.ReferentielOfsCacheIntercept;
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
@WebService(name = ReferentielProfessionsWS.WEBSERVICE_NAME, serviceName = ReferentielProfessionsWS.SERVICE_NAME, portName = ReferentielProfessionsWS.PORT_NAME, targetNamespace = ReferentielProfessionsWS.TARGET_NAMESPACE, endpointInterface = "ch.ge.cti.ct.referentiels.professions.interfaces.ws.ReferentielProfessionsWS")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
@Interceptors({ ReferentielOfsExceptionIntercept.class,
	ReferentielOfsCacheIntercept.class })
public class ReferentielProfessionsSEI implements ReferentielProfessionsWS,
	Loggable {

    /** Référence sur l'implémentation */
    private static final ReferentielProfessionsServiceAble service = ReferentielProfessionsService.INSTANCE;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielProfessions.class);

    @Override
    public Logger log() {
	return LOG;
    }

    @Override
    @WebMethod(operationName = "getClasse", action = "getClasse")
    @WebResult(name = "classe")
    public ClasseWS getClasse(@WebParam(name = "classeId") final int classeId)
	    throws ReferentielOfsException {
	LOG.debug("getClasse(classeId='{}')", classeId);
	if (classeId <= 0) {
	    return null;
	}
	return new ClasseConvert().apply(service.getClasse(classeId));
    }

    @Override
    @WebMethod(operationName = "getClasses", action = "getClasses")
    @WebResult(name = "classe")
    @Cachable(name = "classes", size = Cachable.MEDIUM)
    public List<ClasseWS> getClasses() throws ReferentielOfsException {
	return FluentIterable.from(service.getClasses())
		.transform(new ClasseConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "getClassesByDivision", action = "getClassesByDivision")
    @WebResult(name = "classe")
    @Cachable(name = "classes", size = Cachable.MEDIUM)
    public List<ClasseWS> getClassesByDivision(
	    @WebParam(name = "divisionId") final int divisionId)
	    throws ReferentielOfsException {
	if (divisionId <= 0) {
	    return new LinkedList<ClasseWS>();
	}
	return FluentIterable.from(service.getClasses(divisionId))
		.transform(new ClasseConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "getDivision", action = "getDivision")
    @WebResult(name = "division")
    public DivisionWS getDivision(
	    @WebParam(name = "divisionId") final int divisionId)
	    throws ReferentielOfsException {
	if (divisionId <= 0) {
	    return null;
	}
	return new DivisionConvert().apply(service.getDivision(divisionId));
    }

    @Override
    @WebMethod(operationName = "getDivisions", action = "getDivisions")
    @WebResult(name = "division")
    @Cachable(name = "divisions", size = Cachable.MEDIUM)
    public List<DivisionWS> getDivisions() throws ReferentielOfsException {
	return FluentIterable.from(service.getDivisions())
		.transform(new DivisionConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "getGenre", action = "getGenre")
    @WebResult(name = "genre")
    public GenreWS getGenre(@WebParam(name = "genreId") final int genreId)
	    throws ReferentielOfsException {
	if (genreId <= 0) {
	    return null;
	}
	return new GenreConvert().apply(service.getGenre(genreId));
    }

    @Override
    @WebMethod(operationName = "getGenres", action = "getGenres")
    @WebResult(name = "genre")
    @Cachable(name = "genres", size = Cachable.LARGE)
    public List<GenreWS> getGenres() throws ReferentielOfsException {
	return FluentIterable.from(service.getGenres())
		.transform(new GenreConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "getGenresByGroup", action = "getGenresByGroup")
    @WebResult(name = "genre")
    @Cachable(name = "genres", size = Cachable.LARGE)
    public List<GenreWS> getGenresByGroup(
	    @WebParam(name = "groupeId") final int groupeId)
	    throws ReferentielOfsException {
	if (groupeId <= 0) {
	    return new LinkedList<GenreWS>();
	}
	return FluentIterable.from(service.getGenres(groupeId))
		.transform(new GenreConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "getGroupe", action = "getGroupe")
    @WebResult(name = "groupe")
    public GroupeWS getGroupe(@WebParam(name = "groupeId") final int groupeId)
	    throws ReferentielOfsException {
	if (groupeId <= 0) {
	    return null;
	}
	return new GroupeConvert().apply(service.getGroupe(groupeId));
    }

    @Override
    @WebMethod(operationName = "getGroupes", action = "getGroupes")
    @WebResult(name = "groupe")
    @Cachable(name = "groupes", size = Cachable.LARGE)
    public List<GroupeWS> getGroupes() throws ReferentielOfsException {
	return FluentIterable.from(service.getGroupes())
		.transform(new GroupeConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "getGroupesByClasse", action = "getGroupesByClasse")
    @WebResult(name = "groupe")
    @Cachable(name = "groupes", size = Cachable.LARGE)
    public List<GroupeWS> getGroupesByClasse(
	    @WebParam(name = "classeId") final int classeId)
	    throws ReferentielOfsException {
	if (classeId <= 0) {
	    return new LinkedList<GroupeWS>();
	}
	return FluentIterable.from(service.getGroupes(classeId))
		.transform(new GroupeConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "searchClasse", action = "searchClasse")
    @WebResult(name = "classe")
    public List<ClasseWS> searchClasse(
	    @WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(pattern)) {
	    return new LinkedList<ClasseWS>();
	}
	return FluentIterable.from(service.searchClasse(pattern))
		.transform(new ClasseConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "searchClasseRegexp", action = "searchClasseRegexp")
    @WebResult(name = "classe")
    public List<ClasseWS> searchClasseRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(regexp)) {
	    return new LinkedList<ClasseWS>();
	}
	return FluentIterable.from(service.searchClasseRegexp(regexp))
		.transform(new ClasseConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "searchDivision", action = "searchDivision")
    @WebResult(name = "division")
    public List<DivisionWS> searchDivision(
	    @WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(pattern)) {
	    return new LinkedList<DivisionWS>();
	}
	return FluentIterable.from(service.searchDivision(pattern))
		.transform(new DivisionConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "searchDivisionRegexp", action = "searchDivisionRegexp")
    @WebResult(name = "division")
    public List<DivisionWS> searchDivisionRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(regexp)) {
	    return new LinkedList<DivisionWS>();
	}
	return FluentIterable.from(service.searchDivisionRegexp(regexp))
		.transform(new DivisionConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "searchGenre", action = "searchGenre")
    @WebResult(name = "genre")
    public List<GenreWS> searchGenre(
	    @WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(pattern)) {
	    return new LinkedList<GenreWS>();
	}
	return FluentIterable.from(service.searchGenre(pattern))
		.transform(new GenreConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "searchGenreRegexp", action = "searchGenreRegexp")
    @WebResult(name = "genre")
    public List<GenreWS> searchGenreRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(regexp)) {
	    return new LinkedList<GenreWS>();
	}
	return FluentIterable.from(service.searchGenreRegexp(regexp))
		.transform(new GenreConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "searchGroupe", action = "searchGroupe")
    @WebResult(name = "groupe")
    public List<GroupeWS> searchGroupe(
	    @WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(pattern)) {
	    return new LinkedList<GroupeWS>();
	}
	return FluentIterable.from(service.searchGroupe(pattern))
		.transform(new GroupeConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "searchGroupeRegexp", action = "searchGroupeRegexp")
    @WebResult(name = "groupe")
    public List<GroupeWS> searchGroupeRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(regexp)) {
	    return new LinkedList<GroupeWS>();
	}
	return FluentIterable.from(service.searchGroupeRegexp(regexp))
		.transform(new GroupeConvert()).toList();
    }

    /**
     * Fonction (closure) de copie du continent en DivisionWS<br/>
     * On ne copie par l'arborescence descendante (Classe)
     */
    private static class DivisionConvert implements
	    Function<Division, DivisionWS> {
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
    private static class ClasseConvert implements Function<Classe, ClasseWS> {
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
    private static class GroupeConvert implements Function<Groupe, GroupeWS> {
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
    private static class GenreConvert implements Function<Genre, GenreWS> {
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
