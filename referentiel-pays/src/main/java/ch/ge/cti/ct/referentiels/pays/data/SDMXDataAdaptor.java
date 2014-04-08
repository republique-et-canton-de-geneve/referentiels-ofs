package ch.ge.cti.ct.referentiels.pays.data;

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
import ch.ge.cti.ct.referentiels.pays.model.Continent;
import ch.ge.cti.ct.referentiels.pays.model.Pays;
import ch.ge.cti.ct.referentiels.pays.model.ReferentielPaysTerritoires;
import ch.ge.cti.ct.referentiels.pays.model.Region;

/**
 * Classe d'adapation de la structure de parsing SDMX à une structure
 * intermédiaire proche du service à rendre
 * 
 * @author DESMAZIERESJ
 * 
 */
@Service("sdmxDataReader")
public class SDMXDataAdaptor extends
	AbstractSDMXDataAdaptor<ReferentielPaysTerritoires> {

    private static final String SPRING_CONTEXT_FILE = "referentiel-pays-context.xml";

    private static final String NULL_VALUE = "";

    /** référentiel instancié par le parsing du fichier SDMX */
    private final ReferentielPaysTerritoires referentielPaysTerritoires = new ReferentielPaysTerritoires();
    /** liste temporaires de cantons */
    private final Map<String, Continent> continentRef = new HashMap<String, Continent>();
    /** liste temporaires de districts */
    private final Map<String, Region> regionRef = new HashMap<String, Region>();
    /** liste temporaires de communes */
    private final Map<String, Pays> paysRef = new HashMap<String, Pays>();

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
    public ReferentielPaysTerritoires parse(final URL urlXML)
	    throws ReferentielOfsException {
	log().info("parse({})", urlXML);
	final Long start = System.currentTimeMillis();
	final StructureWorkspace workspace = getParser().parse(urlXML);
	populateMetaData(workspace);
	populateTempRefs(workspace);
	populateHierarchy(workspace);
	log().info("Chargement du référentiel {}: {} ms", urlXML,
		System.currentTimeMillis() - start);
	return referentielPaysTerritoires;
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
	referentielPaysTerritoires.setId(workspace.getStructureBeans(false)
		.getHeader().getId());
	referentielPaysTerritoires.setDate(workspace.getStructureBeans(false)
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
	final SdmxBeans beans = workspace.getStructureBeans(false);
	// 1. <HierarchicalCodelists>
	final Set<HierarchicalCodelistBean> hclbs = beans
		.getHierarchicalCodelists();
	// 2. <structure:HierarchicalCodelist id="HCL_COUNTRIESGEO"
	// version="1.0" agencyID="CH1_RN">
	final HierarchicalCodelistBean hclb = hclbs.iterator().next();
	// 3. <structure:Hierarchy id="HR_COUNTRIESGEO">
	final HierarchyBean hb = hclb.getHierarchies().iterator().next();
	// 4. <structure:CodeRef>: liste continents
	final List<HierarchicalCodeBean> cbContinents = hb
		.getHierarchicalCodeBeans();
	for (final HierarchicalCodeBean cbContinent : cbContinents) {
	    final Continent continent = continentRef.get(cbContinent
		    .getCodeId());
	    referentielPaysTerritoires.getContinent().add(continent);
	    final Set<HierarchicalCodeBean> cbRegionPayss = cbContinent
		    .getComposites(HierarchicalCodeBean.class);
	    for (final HierarchicalCodeBean cbRegionPays : cbRegionPayss) {
		if (ListAlias.REGION.code.equals(cbRegionPays
			.getCodelistAliasRef())) {
		    final Region region = regionRef.get(cbRegionPays
			    .getCodeId());
		    region.setContinentId(continent.getId());
		    continent.getRegion().add(region);
		} else if (ListAlias.PAYS.code.equals(cbRegionPays
			.getCodelistAliasRef())) {
		    final Region region = regionRef.get(cbRegionPays
			    .getIdentifiableParent().getId());
		    final Pays pays = paysRef.get(cbRegionPays.getCodeId());
		    pays.setContinentId(continent.getId());
		    pays.setRegionId(region.getId());
		    if (pays.getIso2() == null) {
			pays.setIso2(NULL_VALUE);
		    }
		    if (pays.getIso3() == null) {
			pays.setIso3(NULL_VALUE);
		    }
		    region.getPays().add(pays);
		}
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
		if (CodeList.CL_CONTINENTS.name().equals(cl.getId())) {
		    final Continent continent = new Continent();
		    continent.setId(Integer.parseInt(cb.getId()));
		    continent.setNom(getName(cb, AnnotationType.fr));
		    continentRef.put(cb.getId(), continent);
		} else if (CodeList.CL_REGIONS.name().equals(cl.getId())) {
		    final Region region = new Region();
		    region.setId(Integer.parseInt(cb.getId()));
		    region.setNom(getName(cb, AnnotationType.fr));
		    regionRef.put(cb.getId(), region);
		} else if (CodeList.CL_STATES_AND_TERRITORIES.name().equals(
			cl.getId())) {
		    final Pays pays = new Pays();
		    pays.setId(Integer.parseInt(cb.getId()));
		    pays.setIso2(getAnnotation(cb, AnnotationType.fr,
			    AnnotationType.SG_ISO2.name(), null));
		    pays.setIso3(getAnnotation(cb, AnnotationType.fr,
			    AnnotationType.SG_ISO3.name(), null));
		    pays.setNom(getName(cb, AnnotationType.fr));
		    pays.setNomLong(getAnnotation(cb, AnnotationType.fr,
			    AnnotationType.SG_NAME.name(), pays.getNom()));
		    paysRef.put(cb.getId(), pays);
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

    private String getAnnotation(final CodeBean cb, final AnnotationType lang,
	    final String name, final String defaultValue) {
	String value = null;
	if (cb.getAnnotationsByType(name).iterator().hasNext()) {
	    for (final TextTypeWrapper ttw : cb.getAnnotationsByType(name)
		    .iterator().next().getText()) {
		value = ttw.getValue();
		if (lang.name().equals(ttw.getLocale())) {
		    return ttw.getValue();
		}
	    }
	}
	return value == null ? defaultValue : value;
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
	CL_STATES_AND_TERRITORIES, CL_CONTINENTS, CL_REGIONS;
    }

    private enum ListAlias {
	CONTINENT("1"), REGION("2"), PAYS("3");
	private final String code;

	private ListAlias(final String code) {
	    this.code = code;
	}

	@SuppressWarnings("unused")
	public String code() {
	    return code;
	}
    }

    private enum AnnotationType {
	SG_NAME, SG_ISO2, SG_ISO3, fr, en, de, it;
    }
}
