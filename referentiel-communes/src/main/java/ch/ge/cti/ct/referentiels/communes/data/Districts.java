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

import java.util.ArrayList;
import java.util.List;

/**
 * Classe servant a XStream pour convertir en Java un element "districts" du fichier XML de l'OFS.
 * @author Yves Dubois-Pelerin
 */
public class Districts {

    private List<District> districtList = new ArrayList<>();

    public List<District> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<District> districtList) {
        this.districtList = districtList;
    }

}
