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
 * le tag NOSONAR est ajouté pour supprimer les warnings
 * <ul>
 * <li>"Signature Declare Throws Exception", car on implémente une interface
 * prédéfinie</li>
 * </ul>
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
    public Date unmarshal(final String date) throws Exception {// NOSONAR
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
    public String marshal(final Date date) throws Exception {// NOSONAR
	if (date != null) {
	    return df.format(date);
	}
	return BLANK;
    }
}
