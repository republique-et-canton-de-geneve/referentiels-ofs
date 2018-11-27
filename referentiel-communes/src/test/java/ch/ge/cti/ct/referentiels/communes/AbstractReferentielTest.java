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
