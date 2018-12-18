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
package ch.ge.cti.ct.referentiels.ofs.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class JaxbDateAdapterTest extends AbstractDataTest {

    final JaxbDateAdapter jda = new JaxbDateAdapter();

    @Test
    public void testUnmarshalString() throws Exception {
	Date date = jda.unmarshal("01.01.1960");
	final Calendar cal = new GregorianCalendar();
	cal.setTime(date);
	assertEquals("Date.jour est incorrect", 1,
		cal.get(Calendar.DAY_OF_MONTH));
	assertEquals("Date.mois est incorrect", 0, cal.get(Calendar.MONTH));
	assertEquals("Date.annee est incorrect", 1960, cal.get(Calendar.YEAR));

	date = jda.unmarshal("");
	assertNull(date);

	date = jda.unmarshal("");
	assertNull(date);
    }

    @Test
    public void testMarshalDate() throws Exception {
	final Calendar cal = new GregorianCalendar();
	cal.set(Calendar.DAY_OF_MONTH, 1);
	cal.set(Calendar.MONTH, 0);
	cal.set(Calendar.YEAR, 1960);
	String dateStr = jda.marshal(cal.getTime());
	assertEquals("Date au format String incorrecte", "01.01.1960", dateStr);

	dateStr = jda.marshal(null);
	assertEquals("", dateStr);
    }

}
