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
package ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.namespace.QName;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws.model.Niveau1WS;
import ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws.model.Niveau2WS;

/**
 * Contrat du WebService ReferentielSocioprofessionnelWS
 * 
 * @author DESMAZIERESJ
 * 
 */
@WebService(targetNamespace = ReferentielSocioprofessionnelWS.TARGET_NAMESPACE)
public interface ReferentielSocioprofessionnelWS {

    /** nom du service web */
    String WEBSERVICE_NAME = "referentiel-socioprofessionnel-JAXWS";
    /** nom du service */
    String SERVICE_NAME = "referentiel-socioprofessionnel-service";
    /** nom du port */
    String PORT_NAME = "referentiel-socioprofessionnel-port";
    /** namespace du service */
    String TARGET_NAMESPACE = "http://ch.ge.cti.ct.referentiels.socioprofessionnel/referentiel-socioprofessionnel/";

    /** QName du service */
    QName SERVICE_QNAME = new QName(TARGET_NAMESPACE, SERVICE_NAME);
    /** QName du port */
    QName PORT_QNAME = new QName(TARGET_NAMESPACE, PORT_NAME);

    /**
     * Liste de toutes les entités de niveau 1
     * 
     * @return liste des entités de niveau 1
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getNiveaux1", action = "getNiveaux1")
    @WebResult(name = "niveau1")
    List<Niveau1WS> getNiveaux1() throws ReferentielOfsException;

    /**
     * recherche d'entité de niveau 1 par id
     * 
     * @param niveau1Id
     *            identifiant d'entité
     * @return entité de niveau 1
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getNiveau1", action = "getNiveau1")
    @WebResult(name = "niveau1")
    Niveau1WS getNiveau1(@WebParam(name = "niveau1Id") final int niveau1Id)
	    throws ReferentielOfsException;

    /**
     * Recherche de la liste des entités de niveau 1 commençant par un pattern
     * 
     * @param pattern
     *            critère de recherche
     * @return liste des entités de niveau 1 correspondant au critère
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "searchNiveaux1", action = "searchNiveaux1")
    @WebResult(name = "niveau1")
    List<Niveau1WS> searchNiveaux1(
	    @WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException;

    /**
     * Recherche de la liste des entités de niveau 1 vérifiant une expression
     * régulière
     * 
     * @param regexp
     *            expression régulière de recherche
     * @return liste des entités de niveau 1 vérifiant l'expression régulière
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "searchNiveaux1Regexp", action = "searchNiveaux1Regexp")
    @WebResult(name = "niveau1")
    List<Niveau1WS> searchNiveaux1Regexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException;

    /**
     * Liste de toutes les entités de niveau 2
     * 
     * @return liste entités de niveau 2
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getNiveaux2", action = "getNiveaux2")
    @WebResult(name = "niveau2")
    List<Niveau2WS> getNiveaux2() throws ReferentielOfsException;

    /**
     * Liste des entités de niveau 2 d'une entité de niveau 1
     * 
     * @param niveau1Id
     *            identifiant entité niveau 1
     * @return liste des entités de niveau 2
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getNiveaux2ByNiveau1", action = "getNiveaux2ByNiveau1")
    @WebResult(name = "niveau2")
    List<Niveau2WS> getNiveaux2ByNiveau1(
	    @WebParam(name = "niveau1Id") final int niveau1Id)
	    throws ReferentielOfsException;

    /**
     * recherche d'entité de niveau 2 par id
     * 
     * @param niveau2Id
     *            identifiant d'entité
     * @return entité
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getNiveau2", action = "getNiveau2")
    @WebResult(name = "niveau2")
    Niveau2WS getNiveau2(@WebParam(name = "niveau2Id") final int niveau2Id)
	    throws ReferentielOfsException;

    /**
     * Recherche de la liste des entités de niveau 2 commençant par un pattern
     * 
     * @param pattern
     *            critère de recherche
     * @return liste des entités de niveau 2 correspondant au critère
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "searchNiveaux2", action = "searchNiveaux2")
    @WebResult(name = "niveau2")
    List<Niveau2WS> searchNiveaux2(
	    @WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException;

    /**
     * Recherche de la liste des entités de niveau 2 vérifiant une expression
     * régulière
     * 
     * @param regexp
     *            expression régulière de recherche
     * @return liste des entités de niveau 2 vérifiant l'expression régulière
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "searchNiveaux2Regexp", action = "searchNiveaux2Regexp")
    @WebResult(name = "niveau2")
    List<Niveau2WS> searchNiveaux2Regexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException;
}