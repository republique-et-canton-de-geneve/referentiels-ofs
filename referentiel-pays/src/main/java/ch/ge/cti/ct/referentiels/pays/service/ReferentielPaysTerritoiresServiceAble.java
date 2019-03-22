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
package ch.ge.cti.ct.referentiels.pays.service;

import ch.ge.cti.ct.referentiels.pays.data.Country;

import java.util.List;
import java.util.Optional;

/**
 * Définition du contrat du service de référentiel des pays<br/>
 * Le référentiel provient de l'office statistique Suisse dépendant de
 * l'administration fédérale.
 * <br/>
 * Attention : depuis février 2019, la liste des pays fournie par l'OFS ne contient plus la définitions des
 * continents ni des régions. Par conséquent, les méthodes afférantes dans cette classe ont été supprimées.
 *
 * @author desmazièresj
 */
public interface ReferentielPaysTerritoiresServiceAble {

    /**
     * Recherche d'un pays par son code ISO 2.
     *
     * @param iso2 code ISO 2 du pays
     * @return pays
     */
    Optional<Country> getPaysByIso2(String iso2);

    /**
     * Recherche d'un pays par son code ISO 3.
     *
     * @param iso3 code ISO 3 du pays
     * @return pays
     */
    Optional<Country> getPaysByIso3(String iso3);

    /**
     * Liste des pays.
     * 
     * @return pays
     */
    List<Country> getPays();

    /**
     * Recheche de pays par nom.
     * 
     * @param critere critère de recherche
     * @return pays liste de pays
     */
    List<Country> searchPays(String critere);

}
