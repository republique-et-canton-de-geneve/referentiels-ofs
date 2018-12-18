/*
 * Referentiels OFS
 *
 * Copyright (C) 2018 R√©publique et canton de Gen√®ve
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
package ch.ge.cti.ct.referentiels.etatCivil.interfaces.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.namespace.QName;

import ch.ge.cti.ct.referentiels.etatCivil.model.EtatCivil;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * Interface du service au format JAX-WS
 * 
 * @author DESMAZIERESJ
 * 
 */
@WebService(targetNamespace = ReferentielEtatCivilWS.TARGET_NAMESPACE)
public interface ReferentielEtatCivilWS {

    /** nom du service web */
    String WEBSERVICE_NAME = "referentiel-etat-civil-JAXWS";
    /** nom du service */
    String SERVICE_NAME = "referentiel-etat-civil-service";
    /** nom du port */
    String PORT_NAME = "referentiel-etat-civil-port";
    /** namespace du service */
    String TARGET_NAMESPACE = "http://ch.ge.cti.ct.referentiels.etat-civil/referentiel-etat-civil/";

    /** QName du service */
    QName SERVICE_QNAME = new QName(TARGET_NAMESPACE, SERVICE_NAME);
    /** QName du port */
    QName PORT_QNAME = new QName(TARGET_NAMESPACE, PORT_NAME);

    /**
     * Liste de tous les Ètats civils
     * 
     * @return liste des Ètats civils
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getEtatsCivils", action = "getEtatsCivils")
    @WebResult(name = "etatCivil")
    List<EtatCivil> getEtatsCivils() throws ReferentielOfsException;

    /**
     * Recherche d'un Ètat civil par son identifiant
     * 
     * @param etatCivilId
     *            identifiant Ètat civil
     * @return Ètat civil
     * @throws ReferentielOfsException
     *             exception de traitement
     */
    @WebMethod(operationName = "getEtatCivil", action = "getEtatCivil")
    @WebResult(name = "etatCivil")
    EtatCivil getEtatCivil(@WebParam(name = "etatCivil") final int etatCivilId)
	    throws ReferentielOfsException;

}