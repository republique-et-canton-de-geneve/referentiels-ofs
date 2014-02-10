package ch.ge.cti.ct.referentiels.formeJuridique.service.jmx;

import java.util.Map.Entry;

import org.apache.commons.lang.StringEscapeUtils;

import ch.ge.cti.ct.referentiels.ofs.cache.CacheManager;
import ch.ge.cti.ct.referentiels.ofs.service.jmx.CacheStatUtil;
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
	    xml.append("<referentiel-formes-juridiques-statistiques>\n");
	    xml.append("  <cache>\n");
	    for (CacheEnum cache : CacheEnum.values()) {
		CacheStatUtil.renderCacheStats(cache.name(),
			CacheManager.instance.getCaches().get(cache.name()),
			xml);
	    }
	    xml.append("  </cache>\n");
	    for (Entry<Call, Stat> entry : StatistiquesServiceSingleton.instance
		    .getStatistiques().entrySet()) {
		if (entry.getKey().getParametre() != null) {
		    xml.append("  <call method=\"")
			    .append(entry.getKey().getMethode())
			    .append("\" parametre=\"")
			    .append(entry.getKey().getParametre())
			    .append("\" count=\"")
			    .append(entry.getValue().getCalls().longValue())
			    .append("\" total-laps-nano=\"")
			    .append(entry.getValue().getLaps().longValue())
			    .append("\"/>\n");
		} else {
		    xml.append("  <call method=\"")
			    .append(entry.getKey().getMethode())
			    .append("\" count=\"")
			    .append(entry.getValue().getCalls().longValue())
			    .append("\" total-laps-nano=\"")
			    .append(entry.getValue().getLaps().longValue())
			    .append("\"/>\n");
		}
	    }
	    xml.append("</referentiel-formes-juridiques-statistiques>");
	    return xml.toString();
	}
    };

    private enum CacheEnum {
	cantons, districts, communes;
    }

}
