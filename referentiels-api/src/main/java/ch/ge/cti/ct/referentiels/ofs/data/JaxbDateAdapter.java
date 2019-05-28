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
package ch.ge.cti.ct.referentiels.ofs.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang.StringUtils;

/**
 * Adapter pour le traitement des dates lors du parsing JAXB<br/>
 * Permet de supporter des champs définis comme xs:date dans le xsd d'être
 * spécifiés au format dd.MM.yyyy dans le fichier XML
 * 
 * @author desmazieresj
 * @see javax.xml.bind.annotation.adapters.XmlAdapter
 */
public class JaxbDateAdapter extends XmlAdapter<String, Date> {

    private static final String BLANK = "";
    /** format de date supporté */
    private final DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    /**
     * Convertion String -> Date
     * 
     * @param date
     *            date au format String (dd.MM.yyyy)
     * @return date
     * @exception exception
     *                de convertion
     */
    @Override
    public Date unmarshal(final String date) throws Exception {
	if (StringUtils.isNotBlank(date)) {
	    return df.parse(date);
	}
	return null;
    }

    /**
     * Convertion Date -> String
     * 
     * @param date
     *            date
     * @return date au format String (dd.MM.yyyy)
     * @exception exception
     *                de convertion
     */
    @Override
    public String marshal(final Date date) {
	if (date != null) {
	    return df.format(date);
	}
	return BLANK;
    }
}
