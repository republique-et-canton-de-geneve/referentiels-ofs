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
package ch.ge.cti.ct.referentiels.pays.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * DTO servant à XStream pour lire un element "country" du fichier XML de l'OFS.
 * @author Yves Dubois-Pelerin
 */
public class Country {

    private int id = 0;

    private String iso2Id = null;

    private String iso3Id = null;

    private String shortNameFr = null;

    private String officialNameFr = null;

    private int continent = 0;

    private int region = 0;

    private boolean state = false;

    private boolean entryValid = false;

    private boolean recognizedCh = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIso2Id() {
        return iso2Id;
    }

    public void setIso2Id(String iso2Id) {
        this.iso2Id = iso2Id;
    }

    public String getIso3Id() {
        return iso3Id;
    }

    public void setIso3Id(String iso3Id) {
        this.iso3Id = iso3Id;
    }

    public String getShortNameFr() {
        return shortNameFr;
    }

    public void setShortNameFr(String shortNameFr) {
        this.shortNameFr = shortNameFr;
    }

    public String getOfficialNameFr() {
        return officialNameFr;
    }

    public void setOfficialNameFr(String officialNameFr) {
        this.officialNameFr = officialNameFr;
    }

    public int getContinent() {
        return continent;
    }

    public void setContinent(int continent) {
        this.continent = continent;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isEntryValid() {
        return entryValid;
    }

    public void setEntryValid(boolean entryValid) {
        this.entryValid = entryValid;
    }

    public boolean isRecognizedCh() {
        return recognizedCh;
    }

    public void setRecognizedCh(boolean recognizedCh) {
        this.recognizedCh = recognizedCh;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

}
