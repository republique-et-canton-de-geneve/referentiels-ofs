package ch.ge.cti.ct.referentiels.etatCivil.service.jmx;

import java.util.Map.Entry;

import org.apache.commons.lang.StringEscapeUtils;

import ch.ge.cti.ct.referentiels.etatCivil.interfaces.ws.ReferentielEtatCivilSEI;
import ch.ge.cti.ct.referentiels.ofs.service.jmx.Call;
import ch.ge.cti.ct.referentiels.ofs.service.jmx.Renderable;
import ch.ge.cti.ct.referentiels.ofs.service.jmx.Stat;
import ch.ge.cti.ct.referentiels.ofs.service.jmx.StatistiquesServiceSingleton;

/**
 * Mode de rendering des données statistiques
 * 
 * @author DESMAZIERESJ
 * 
 */
public enum DisplayMode implements Renderable {
    /** mode HTML: XML "escapé" */
    HTML {
	@Override
	public String render() {
	    final String xml = XML.render();
	    return StringEscapeUtils.escapeHtml(xml);
	}
    },
    /** XML */
    XML {
	@Override
	public String render() {
	    final StringBuilder xml = new StringBuilder();
	    xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
	    xml.append("<referentiel-etat-civil-statistiques>\n");
	    for (final Entry<Call, Stat> entry : StatistiquesServiceSingleton.instance
		    .getStatistiques(ReferentielEtatCivilSEI.class).entrySet()) {
		xml.append("  <call method=\"")
			.append(entry.getKey().getMethode())
			.append("\" count=\"")
			.append(entry.getValue().getCalls().longValue())
			.append("\" total-laps-nano=\"")
			.append(entry.getValue().getLaps().longValue())
			.append("\"/>\n");
	    }
	    xml.append("</referentiel-etat-civil-statistiques>");
	    return xml.toString();
	}
    };
}
