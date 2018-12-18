/*
 * Referentiels OFS
 *
 * Copyright (C) 2018 République et canton de Genève
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
package ch.ge.cti.ct.referentiels.formeJuridique.interfaces.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.namespace.QName;

import ch.ge.cti.ct.referentiels.formeJuridique.model.FormeJuridique;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * Interface du service au format JAX-WS
 * 
 * @author DESMAZIERESJ
 * 
 */
@WebService(targetNamespace = ReferentielFormesJuridiquesWS.TARGET_NAMESPACE)
public interface ReferentielFormesJuridiquesWS {

    /** nom du service web */
    String WEBSERVICE_NAME = "referentiel-formes-juridiques-JAXWS";
    /** nom du service */
    String SERVICE_NAME = "referentiel-formes-juridiques-service";
    /** nom du port */
    String PORT_NAME = "referentiel-formes-juridiques-port";
    /** namespace du service */
    String TARGET_NAMESPACE = "http://ch.ge.cti.ct.referentiels.formes-juridiques/referentiel-formes-juridiques/";

    /** QName du service */
    QName SERVICE_QNAME = new QName(TARGET_NAMESPACE, SERVICE_NAME);
    /** QName du port */
    QName PORT_QNAME = new QName(TARGET_NAMESPACE, PORT_NAME);

    /**
     * Liste de toutes les formes juridiques
     * 
     * @return liste des formes juridiques
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getFormesJuridiques", action = "getFormesJuridiques")
    @WebResult(name = "formeJuridique")
    List<FormeJuridique> getFormesJuridiques() throws ReferentielOfsException;

    /**
     * Recherche d'une forme juridique par son identifiant
     * 
     * @param formeJuridiqueId
     *            identifiant de forme juridique
     * @return forme juridique
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getFormeJuridique", action = "getFormeJuridique")
    @WebResult(name = "formeJuridique")
    FormeJuridique getFormeJuridique(
	    @WebParam(name = "formeJuridique") final int formeJuridiqueId)
	    throws ReferentielOfsException;

}