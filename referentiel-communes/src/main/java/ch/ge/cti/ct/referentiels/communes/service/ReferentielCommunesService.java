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
package ch.ge.cti.ct.referentiels.communes.service;

import ch.ge.cti.ct.referentiels.communes.data.Canton;
import ch.ge.cti.ct.referentiels.communes.data.District;
import ch.ge.cti.ct.referentiels.communes.data.Municipality;

import java.util.List;
import java.util.Optional;

/**
 * Contrat du service du référentiel des communes.
 * @author desmazièresj
 */
public interface ReferentielCommunesService {

    /**
     * Rend la liste des cantons.
     */
    List<Canton> getCantons();

    /**
     * Recherche un canton par son identifiant abrégé (par ex, "GE").
     */
    Optional<Canton> getCanton(String codeCanton);

    /**
     * Rend la liste des districts d'un canton.
     * @param codeCanton identifiant de canton (par ex, "GE")
     * @return liste des districts
     */
    List<District> getDistrictsByCanton(String codeCanton);

    /**
     * Recherche d'un district par son identifiant<br/>
     * @param districtHistId identifiant de district (numérique)
     * @return district
     */
    Optional<District> getDistrict(int districtHistId);

    /**
     * Rend la liste des municipalites d'un canton.
     * @param codeCanton identifiant de canton (par ex, "GE")
     * @return liste de municipalites
     */
    List<Municipality> getMunicipalitiesByCanton(String codeCanton);

    /**
     * Recherche d'une municipalite par son identifiant.
     * @param municipalityId identifiant de la municipalite
     * @return municipalite
     */
    Optional<Municipality> getMunicipality(int municipalityId);

    /**
     * Recherche des municipalite par leur nom.
     * @param critere critere de recherche (sur le nom)
     * @return liste de municipalites
     */
    List<Municipality> searchMunicipality(String critere);

}
