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
package ch.ge.cti.ct.referentiels.communes.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

/**
 * DTO servant à XStream pour lire un element "canton" du fichier XML de l'OFS.
 * @author Yves Dubois-Pelerin
 */
public class District {

    private Integer districtHistId = null;

    private Integer districtId = null;

    private Integer cantonId = null;

    private String districtLongName = null;

    private String districtShortName = null;

    private Integer districtEntryMode = null;

    private Integer districtAdmissionNumber = null;

    private Integer districtAdmissionMode = null;

    private Date districtAdmissionDate = null;

    private Integer districtAbolitionMode = null;

    private Integer districtAbolitionNumber = null;

    private Date districtAbolitionDate = null;

    private Date districtDateOfChange = null;

    /**
     * Champ absent dans le fichier XML.
     * Permet au service de gerer plus aisement les codes des cantons des district.
     */
    private Canton canton = null;

    public Integer getDistrictHistId() {
        return districtHistId;
    }

    public void setDistrictHistId(Integer districtHistId) {
        this.districtHistId = districtHistId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getCantonId() {
        return cantonId;
    }

    public void setCantonId(Integer cantonId) {
        this.cantonId = cantonId;
    }

    public String getDistrictLongName() {
        return districtLongName;
    }

    public void setDistrictLongName(String districtLongName) {
        this.districtLongName = districtLongName;
    }

    public String getDistrictShortName() {
        return districtShortName;
    }

    public void setDistrictShortName(String districtShortName) {
        this.districtShortName = districtShortName;
    }

    public Integer getDistrictEntryMode() {
        return districtEntryMode;
    }

    public void setDistrictEntryMode(Integer districtEntryMode) {
        this.districtEntryMode = districtEntryMode;
    }

    public Integer getDistrictAdmissionNumber() {
        return districtAdmissionNumber;
    }

    public void setDistrictAdmissionNumber(Integer districtAdmissionNumber) {
        this.districtAdmissionNumber = districtAdmissionNumber;
    }

    public Integer getDistrictAdmissionMode() {
        return districtAdmissionMode;
    }

    public void setDistrictAdmissionMode(Integer districtAdmissionMode) {
        this.districtAdmissionMode = districtAdmissionMode;
    }

    public Date getDistrictAdmissionDate() {
        return districtAdmissionDate;
    }

    public void setDistrictAdmissionDate(Date districtAdmissionDate) {
        this.districtAdmissionDate = districtAdmissionDate;
    }

    public Integer getDistrictAbolitionMode() {
        return districtAbolitionMode;
    }

    public void setDistrictAbolitionMode(Integer districtAbolitionMode) {
        this.districtAbolitionMode = districtAbolitionMode;
    }

    public Integer getDistrictAbolitionNumber() {
        return districtAbolitionNumber;
    }

    public void setDistrictAbolitionNumber(Integer districtAbolitionNumber) {
        this.districtAbolitionNumber = districtAbolitionNumber;
    }

    public Date getDistrictAbolitionDate() {
        return districtAbolitionDate;
    }

    public void setDistrictAbolitionDate(Date districtAbolitionDate) {
        this.districtAbolitionDate = districtAbolitionDate;
    }

    public Date getDistrictDateOfChange() {
        return districtDateOfChange;
    }

    public void setDistrictDateOfChange(Date districtDateOfChange) {
        this.districtDateOfChange = districtDateOfChange;
    }

    public Canton getCanton() {
        return canton;
    }

    public void setCanton(Canton canton) {
        this.canton = canton;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

}
