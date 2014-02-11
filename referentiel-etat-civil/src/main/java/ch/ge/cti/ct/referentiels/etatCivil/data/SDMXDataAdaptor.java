package ch.ge.cti.ct.referentiels.etatCivil.data;

import java.net.URL;
import java.util.List;
import java.util.Set;

import org.sdmxsource.sdmx.api.model.StructureWorkspace;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import ch.ge.cti.ct.referentiels.etatCivil.model.EtatCivil;
import ch.ge.cti.ct.referentiels.etatCivil.model.ReferentielEtatCivil;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.data.AbstractSDMXDataAdaptor;
import ch.ge.cti.ct.referentiels.ofs.data.SDMXParser;

@Service("sdmxDataReader")
public class SDMXDataAdaptor extends
	AbstractSDMXDataAdaptor<ReferentielEtatCivil> {

    private static final String SPRING_CONTEXT_FILE = "referentiel-etat-civil-context.xml";

    /** référentiel instancié par le parsing du fichier SDMX */
    private ReferentielEtatCivil ReferentielEtatCivil = new ReferentielEtatCivil();

    /**
     * Constructeur avec injection du parseur SDMX
     * 
     * @param parser
     *            parseur
     */
    @Autowired(required = true)
    public SDMXDataAdaptor(final SDMXParser parser) {
	super(parser);
    }

    /**
     * Exécution du traitement du fichier SDMX
     * 
     * @param urlXML
     *            url du fichier SDMX
     * @return instance du référentiel
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    public ReferentielEtatCivil parse(final URL urlXML)
	    throws ReferentielOfsException {
	log().info("parse({})", urlXML);
	final Long start = System.currentTimeMillis();
	final StructureWorkspace workspace = getParser().parse(urlXML);
	populateMetaData(workspace);
	populateHierarchy(workspace);
	log().info("Chargement du référentiel {}: {} ms", urlXML,
		System.currentTimeMillis() - start);
	return ReferentielEtatCivil;
    }

    /**
     * Alimentation des meta-données du référentiel <br/>
     * <ul>
     * <li>identifiant du fichier SDMX</li>
     * <li>date de génération du fichier SDMX</li>
     * </ul>
     * 
     * @param workspace
     *            données SDMX parsées
     */
    private void populateMetaData(final StructureWorkspace workspace) {
	ReferentielEtatCivil.setId(workspace.getStructureBeans(false)
		.getHeader().getId());
	ReferentielEtatCivil.setDate(workspace.getStructureBeans(false)
		.getHeader().getPrepared());
    }

    /**
     * Alimentation des Map temporaires avec les instances des différents objets
     * sans leurs relations
     * 
     * @param workspace
     *            données SDMX parsées
     */
    private void populateHierarchy(final StructureWorkspace workspace) {
	final Set<CodelistBean> cls = workspace.getStructureBeans(false)
		.getCodelists();
	for (CodelistBean cl : cls) {
	    final List<CodeBean> cbs = cl.getItems();
	    for (CodeBean cb : cbs) {
		final EtatCivil formeJ = new EtatCivil();
		formeJ.setId(Integer.parseInt(cb.getId()));
		formeJ.setNom(getName(cb, AnnotationType.fr));
		ReferentielEtatCivil.getEtatCivil().add(formeJ);
	    }
	}
    }

    private String getName(final CodeBean cb, final AnnotationType lang) {
	for (TextTypeWrapper ttw : cb.getNames()) {
	    if (lang.name().equals(ttw.getLocale())) {
		return ttw.getValue();
	    }
	}
	return cb.getName();
    }

    /**
     * Factory du reader
     * 
     */
    public static class Factory {
	/**
	 * Instanciation du reader
	 * 
	 * @return instance de reader SDMX
	 */
	public static SDMXDataAdaptor getInstance() {
	    @SuppressWarnings("resource")
	    final ApplicationContext ctxt = new ClassPathXmlApplicationContext(
		    SPRING_CONTEXT_FILE);
	    return ctxt.getBean(SDMXDataAdaptor.class);
	}
    }

    private enum AnnotationType {
	fr, en, de, it;
    }
}
