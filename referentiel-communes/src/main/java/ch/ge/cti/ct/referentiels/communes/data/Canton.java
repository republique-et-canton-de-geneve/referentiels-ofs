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
public class Canton {

    private Integer cantonId = null;

    private String cantonAbbreviation = null;

    private String cantonLongName = null;

    private Date cantonDateOfChange = null;

    public int getCantonId() {
        return cantonId;
    }

    public void setCantonId(int cantonId) {
        this.cantonId = cantonId;
    }

    public String getCantonAbbreviation() {
        return cantonAbbreviation;
    }

    public void setCantonAbbreviation(String cantonAbbreviation) {
        this.cantonAbbreviation = cantonAbbreviation;
    }

    public String getCantonLongName() {
        return cantonLongName;
    }

    public void setCantonLongName(String cantonLongName) {
        this.cantonLongName = cantonLongName;
    }

    public Date getCantonDateOfChange() {
        return cantonDateOfChange;
    }

    public void setCantonDateOfChange(Date cantonDateOfChange) {
        this.cantonDateOfChange = cantonDateOfChange;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

}
