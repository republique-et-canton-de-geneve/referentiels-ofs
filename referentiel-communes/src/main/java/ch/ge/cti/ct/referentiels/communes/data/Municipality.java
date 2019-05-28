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
 * DTO servant à XStream pour lire un element "municipality" du fichier XML de l'OFS.
 * @author Yves Dubois-Pelerin
 */
public class Municipality {

    private Integer historyMunicipalityId = null;

    private Integer districtHistId = null;

    private String cantonAbbreviation = null;

    private Integer municipalityId = null;

    private String municipalityLongName = null;

    private String municipalityShortName = null;

    private Integer municipalityEntryMode = null;

    private Integer municipalityStatus = null;

    private Integer municipalityAdmissionNumber = null;

    private Integer municipalityAdmissionMode = null;

    private Date municipalityAdmissionDate = null;

    private Integer municipalityAbolitionNumber = null;

    private Integer municipalityAbolitionMode = null;

    private Date municipalityAbolitionDate = null;

    private Date municipalityDateOfChange = null;

    public Integer getHistoryMunicipalityId() {
        return historyMunicipalityId;
    }

    public void setHistoryMunicipalityId(Integer historyMunicipalityId) {
        this.historyMunicipalityId = historyMunicipalityId;
    }

    public Integer getDistrictHistId() {
        return districtHistId;
    }

    public void setDistrictHistId(Integer districtHistId) {
        this.districtHistId = districtHistId;
    }

    public String getCantonAbbreviation() {
        return cantonAbbreviation;
    }

    public void setCantonAbbreviation(String cantonAbbreviation) {
        this.cantonAbbreviation = cantonAbbreviation;
    }

    public Integer getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(Integer municipalityId) {
        this.municipalityId = municipalityId;
    }

    public String getMunicipalityLongName() {
        return municipalityLongName;
    }

    public void setMunicipalityLongName(String municipalityLongName) {
        this.municipalityLongName = municipalityLongName;
    }

    public String getMunicipalityShortName() {
        return municipalityShortName;
    }

    public void setMunicipalityShortName(String municipalityShortName) {
        this.municipalityShortName = municipalityShortName;
    }

    public Integer getMunicipalityEntryMode() {
        return municipalityEntryMode;
    }

    public void setMunicipalityEntryMode(Integer municipalityEntryMode) {
        this.municipalityEntryMode = municipalityEntryMode;
    }

    public Integer getMunicipalityStatus() {
        return municipalityStatus;
    }

    public void setMunicipalityStatus(Integer municipalityStatus) {
        this.municipalityStatus = municipalityStatus;
    }

    public Integer getMunicipalityAdmissionNumber() {
        return municipalityAdmissionNumber;
    }

    public void setMunicipalityAdmissionNumber(Integer municipalityAdmissionNumber) {
        this.municipalityAdmissionNumber = municipalityAdmissionNumber;
    }

    public Integer getMunicipalityAdmissionMode() {
        return municipalityAdmissionMode;
    }

    public void setMunicipalityAdmissionMode(Integer municipalityAdmissionMode) {
        this.municipalityAdmissionMode = municipalityAdmissionMode;
    }

    public Date getMunicipalityAdmissionDate() {
        return municipalityAdmissionDate;
    }

    public void setMunicipalityAdmissionDate(Date municipalityAdmissionDate) {
        this.municipalityAdmissionDate = municipalityAdmissionDate;
    }

    public Integer getMunicipalityAbolitionNumber() {
        return municipalityAbolitionNumber;
    }

    public void setMunicipalityAbolitionNumber(Integer municipalityAbolitionNumber) {
        this.municipalityAbolitionNumber = municipalityAbolitionNumber;
    }

    public Integer getMunicipalityAbolitionMode() {
        return municipalityAbolitionMode;
    }

    public void setMunicipalityAbolitionMode(Integer municipalityAbolitionMode) {
        this.municipalityAbolitionMode = municipalityAbolitionMode;
    }

    public Date getMunicipalityAbolitionDate() {
        return municipalityAbolitionDate;
    }

    public void setMunicipalityAbolitionDate(Date municipalityAbolitionDate) {
        this.municipalityAbolitionDate = municipalityAbolitionDate;
    }

    public Date getMunicipalityDateOfChange() {
        return municipalityDateOfChange;
    }

    public void setMunicipalityDateOfChange(Date municipalityDateOfChange) {
        this.municipalityDateOfChange = municipalityDateOfChange;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

}
