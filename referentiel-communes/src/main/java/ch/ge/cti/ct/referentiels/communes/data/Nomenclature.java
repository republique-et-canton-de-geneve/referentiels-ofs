/*
 * Referentiels OFS
 *
 * Copyright (C) 2018 R�publique et canton de Gen�ve
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
package ch.ge.cti.ct.referentiels.communes.data;

/**
 * Classe servant a XStream pour convertir en Java l'element racine "nomenclature" du fichier XML de l'OFS.
 * @author Yves Dubois-Pelerin
 */
public class Nomenclature {

    private Cantons cantons;

    private Districts districts;

    private Municipalities municipalities;

    public Cantons getCantons() {
        return cantons;
    }

    public void setCantons(Cantons cantons) {
        this.cantons = cantons;
    }

    public Districts getDistricts() {
        return districts;
    }

    public void setDistricts(Districts districts) {
        this.districts = districts;
    }

    public Municipalities getMunicipalities() {
        return municipalities;
    }

    public void setMunicipalities(Municipalities municipalities) {
        this.municipalities = municipalities;
    }

}
