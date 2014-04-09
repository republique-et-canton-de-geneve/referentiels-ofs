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
