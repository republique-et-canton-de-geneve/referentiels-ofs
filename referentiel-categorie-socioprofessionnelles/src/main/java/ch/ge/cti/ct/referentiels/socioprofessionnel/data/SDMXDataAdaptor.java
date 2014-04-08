package ch.ge.cti.ct.referentiels.socioprofessionnel.data;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sdmxsource.sdmx.api.model.StructureWorkspace;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.data.AbstractSDMXDataAdaptor;
import ch.ge.cti.ct.referentiels.ofs.data.SDMXParser;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau1;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau2;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.ReferentielSocioprofessionnel;

@Service("sdmxDataReader")
public class SDMXDataAdaptor extends
	AbstractSDMXDataAdaptor<ReferentielSocioprofessionnel> {

    private static final String SPRING_CONTEXT_FILE = "referentiel-socioprofessionnel-context.xml";

    /** référentiel instancié par le parsing du fichier SDMX */
    private final ReferentielSocioprofessionnel referentielsocioprofessionnel = new ReferentielSocioprofessionnel();
    /** liste temporaires de Niveau1s */
    private final Map<String, Niveau1> niveau1Ref = new HashMap<String, Niveau1>();
    /** liste temporaires de Niveau2s */
    private final Map<String, Niveau2> niveau2Ref = new HashMap<String, Niveau2>();

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
    @Override
    public ReferentielSocioprofessionnel parse(final URL urlXML)
	    throws ReferentielOfsException {
	log().info("parse({})", urlXML);
	final Long start = System.currentTimeMillis();
	final StructureWorkspace workspace = getParser().parse(urlXML);
	populateMetaData(workspace);
	populateTempRefs(workspace);
	populateHierarchy(workspace);
	log().info("Chargement du référentiel {}: {} ms", urlXML,
		System.currentTimeMillis() - start);
	return referentielsocioprofessionnel;
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
	referentielsocioprofessionnel.setId(workspace.getStructureBeans(false)
		.getHeader().getId());
	referentielsocioprofessionnel.setDate(workspace
		.getStructureBeans(false).getHeader().getPrepared());
    }

    /**
     * Alimentation des Map temporaires avec les instances des différents objets
     * sans leurs relations
     * 
     * @param workspace
     *            données SDMX parsées
     */
    private void populateHierarchy(final StructureWorkspace workspace) {
	final SdmxBeans beans = workspace.getStructureBeans(false);
	final Set<HierarchicalCodelistBean> hclbs = beans
		.getHierarchicalCodelists();
	final HierarchicalCodelistBean hclb = hclbs.iterator().next();
	final HierarchyBean hb = hclb.getHierarchies().iterator().next();
	final List<HierarchicalCodeBean> cbNiveau1s = hb
		.getHierarchicalCodeBeans();
	for (final HierarchicalCodeBean cbNiveau1 : cbNiveau1s) {
	    final Niveau1 niveau1 = niveau1Ref.get(cbNiveau1.getCodeId());
	    referentielsocioprofessionnel.getNiveau1().add(niveau1);
	    for (final HierarchicalCodeBean cbNiveau2Ref : cbNiveau1
		    .getCodeRefs()) {
		final Niveau2 niveau2 = niveau2Ref
			.get(cbNiveau2Ref.getCodeId());
		niveau2.setNiveau1Id(niveau1.getId());
		niveau1.getNiveau2().add(niveau2);
	    }
	}
    }

    /**
     * Alimentation des références entre les objects
     * 
     * @param workspace
     *            données SDMX parsées
     */
    private void populateTempRefs(final StructureWorkspace workspace) {
	final Set<CodelistBean> cls = workspace.getStructureBeans(false)
		.getCodelists();
	for (final CodelistBean cl : cls) {
	    final List<CodeBean> cbs = cl.getItems();
	    for (final CodeBean cb : cbs) {
		if (CodeList.CL_CSP_LV1.name().equals(cl.getId())) {
		    final Niveau1 division = new Niveau1();
		    division.setId(Integer.parseInt(cb.getId()));
		    division.setNom(getName(cb, AnnotationType.fr));
		    niveau1Ref.put(cb.getId(), division);
		} else if (CodeList.CL_CSP_LV2.name().equals(cl.getId())) {
		    final Niveau2 classe = new Niveau2();
		    classe.setId(Integer.parseInt(cb.getId()));
		    classe.setNom(getName(cb, AnnotationType.fr));
		    niveau2Ref.put(cb.getId(), classe);
		}
	    }
	}
    }

    private String getName(final CodeBean cb, final AnnotationType lang) {
	for (final TextTypeWrapper ttw : cb.getNames()) {
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

    private enum CodeList {
	/** niveau1 */
	CL_CSP_LV1,
	/** niveau2 */
	CL_CSP_LV2
    }

    private enum AnnotationType {
	fr, en, de, it;
    }
}
