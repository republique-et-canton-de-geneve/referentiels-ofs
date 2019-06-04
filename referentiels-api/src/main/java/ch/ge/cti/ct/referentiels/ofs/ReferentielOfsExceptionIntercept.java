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
package ch.ge.cti.ct.referentiels.ofs;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * Intercepteur des appels afin de g�rer les exceptions sur les m�thodes<br/>
 * 
 * @author DESMAZIERESJ
 */
public class ReferentielOfsExceptionIntercept {

    /**
     * interception de toutes les m�thodes pour gestion du cache
     * 
     * @param ctx contexte d'appel
     * @return r�sultat du traitement
     * @throws Exception toute exception...
     */
    @AroundInvoke
    public Object processException(InvocationContext ctx) throws Exception {
	try {
	    return ctx.proceed();
	} catch (final Exception e) {
	    throw handleException(e, (Loggable) ctx.getTarget());
	}
    }

    /**
     * M�thode partag�e de traitement des exceptions<br/>
     * Les exceptions sont encapsul�es dans une
     * ReferentielFormesJuridiquesException<br/>
     * Sauf si ce sont d�j� des ReferentielFormesJuridiquesException
     * 
     * @param e exception
     * @return ReferentielFormesJuridiquesException exception encapsul�e
     */
    protected ReferentielOfsException handleException(Exception e, Loggable service) {
	service.log().error(e.getClass().getName(), e);
	// pas de double encapsulation
	if (e instanceof ReferentielOfsException) {
	    return (ReferentielOfsException) e;
	}
	return new ReferentielOfsException(
		"Erreur technique lors du traitement de la demande", e);
    }

}
