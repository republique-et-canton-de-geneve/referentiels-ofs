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
package ch.ge.cti.ct.referentiels.communes;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.ApplicationContext;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;

public abstract class AbstractReferentielTest {

    protected static ApplicationContext ctxt;

    // public static final DateFormat df = new SimpleDateFormat("dd.MM.yyyy",
    // Locale.getDefault());
    private static DateTimeFormatter dtf = new DateTimeFormatterFactory(
	    "dd.MM.yyyy").createDateTimeFormatter();

    public static String format(final Date date) {
	return date == null ? "" : dtf.print(date.getTime());
    }

    public static Date date(final String dateStr) throws ParseException {
	if (StringUtils.isBlank(dateStr))
	    return null;
	return dtf.parseLocalDate(dateStr).toDate();
    }
}
