package ch.ge.cti.ct.referentiels.communes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.joda.time.format.DateTimeFormatter;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;

import ch.ge.cti.ct.act.configuration.DistributionFactory;

public class AbstractReferentielTest {

    protected static ApplicationContext ctxt;

    @BeforeClass
    public static void setupClass() throws Exception {
	setupDistribution();
	ctxt = new ClassPathXmlApplicationContext(
		"referentiel-communes-context.xml");
    }

    public static void setupDistribution() throws Exception {
	final File targetDir = new File("target/test-classes");
	final File distributionFile = new File(targetDir,
		"Distribution.properties");
	System.setProperty("distribution.properties", distributionFile.toURI()
		.toURL().toString());

	final Properties props = new Properties();
	final File xmlFile = new File(
		"src/test/resources/CH1_RN+HCL_HGDE_HIST+1.0.xml");
	props.setProperty("referentiel.communes.file", xmlFile.toURI().toURL()
		.toString());
	OutputStream os = null;
	try {
	    os = new FileOutputStream(distributionFile);
	    props.store(os, ".Chemins mis à jour au démarrage du test.");
	} finally {
	    os.close();
	}

	DistributionFactory.setDisableJNDI(true);
	DistributionFactory.getConfiguration();
    }

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
