package ch.ge.cti.ct.referentiels.communes.data;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sdmxsource.sdmx.api.model.StructureWorkspace;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import ch.ge.cti.ct.referentiels.communes.model.Canton;
import ch.ge.cti.ct.referentiels.communes.model.Commune;
import ch.ge.cti.ct.referentiels.communes.model.District;
import ch.ge.cti.ct.referentiels.communes.model.ReferentielCommunes;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.data.AbstractSDMXDataAdaptor;
import ch.ge.cti.ct.referentiels.ofs.data.SDMXParser;

/**
 * Classe d'adapation de la structure de parsing SDMX à une structure
 * intermédiaire proche du service à rendre
 * 
 * @author DESMAZIERESJ
 * 
 */
@Service("sdmxDataReader")
public class SDMXDataAdaptor extends
	AbstractSDMXDataAdaptor<ReferentielCommunes> {

    private static final String SPRING_CONTEXT_FILE = "referentiel-communes-context.xml";

    /** référentiel instancié par le parsing du fichier SDMX */
    private final ReferentielCommunes referentielCommunes = new ReferentielCommunes();
    /** liste temporaires de cantons */
    private final Map<String, Canton> cantonRef = new HashMap<String, Canton>();
    /** liste temporaires de districts */
    private final Map<String, District> districtRef = new HashMap<String, District>();
    /** liste temporaires de communes */
    private final Map<String, Commune> communeRef = new HashMap<String, Commune>();

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
     * @return INSTANCE du référentiel
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    @Override
    public ReferentielCommunes parse(final URL urlXML)
	    throws ReferentielOfsException {
	log().info("parse({})", urlXML);
	final Long start = System.currentTimeMillis();
	final StructureWorkspace workspace = getParser().parse(urlXML);
	populateMetaData(workspace);
	populateTempRefs(workspace);
	populateHierarchy(workspace);
	log().info("Chargement du référentiel {}: {} ms", urlXML,
		System.currentTimeMillis() - start);
	return referentielCommunes;
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
	referentielCommunes.setId(workspace.getStructureBeans(false)
		.getHeader().getId());
	referentielCommunes.setDate(workspace.getStructureBeans(false)
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
	// 2. <structure:HierarchicalCodelist id="HCL_HGDE_HIST" version="1.0"
	// agencyID="CH1_RN" validFrom="1960-01-01">
	final HierarchicalCodelistBean hclb = hclbs.iterator().next();
	// 3. <structure:Hierarchy id="HR_HGDE_HIST">
	final HierarchyBean hb = hclb.getHierarchies().iterator().next();
	// 4. <structure:CodeRef>: liste cantons
	final List<HierarchicalCodeBean> cbKTs = hb.getHierarchicalCodeBeans();
	for (final HierarchicalCodeBean cbKT : cbKTs) {
	    final Canton kt = cantonRef.get(cbKT.getCodeId());
	    kt.setValidFrom(getValidFrom(cbKT));
	    kt.setValidTo(cbKT.getValidTo() == null ? null : cbKT.getValidTo()
		    .getDate());
	    referentielCommunes.getCanton().add(kt);
	    final Set<HierarchicalCodeBean> cbBEZGDEs = cbKT
		    .getComposites(HierarchicalCodeBean.class);
	    for (final HierarchicalCodeBean cbBEZGDE : cbBEZGDEs) {
		if (ListAlias.BEZ.name().equals(cbBEZGDE.getCodelistAliasRef())) {
		    final District bez = districtRef.get(cbBEZGDE.getCodeId());
		    bez.setValidFrom(getValidFrom(cbBEZGDE));
		    bez.setValidTo(cbBEZGDE.getValidTo() == null ? null
			    : cbBEZGDE.getValidTo().getDate());
		    bez.setCodeCanton(kt.getCode());
		    kt.getDistrict().add(bez);
		} else if (ListAlias.GDE.name().equals(
			cbBEZGDE.getCodelistAliasRef())) {
		    final District bez = districtRef.get(cbBEZGDE
			    .getIdentifiableParent().getId());
		    final Commune gde = communeRef.get(cbBEZGDE.getCodeId());
		    gde.setValidFrom(getValidFrom(cbBEZGDE));
		    gde.setValidTo(cbBEZGDE.getValidTo() == null ? null
			    : cbBEZGDE.getValidTo().getDate());
		    gde.setCodeCanton(kt.getCode());
		    gde.setIdDistrict(bez.getId());
		    bez.getCommune().add(gde);
		}
	    }
	}
    }

    /** Date minimale de validité: 01-01-1960=pas de date from */
    private static final Date START = new Date(-315619200000l);

    /**
     * Génération de la date validFrom <br/>
     * Si la valeur est égale à 1960-01-01, alors pas de date validFrom
     * 
     * @param hcb
     *            objet SDMX
     * @return date
     */
    private Date getValidFrom(final HierarchicalCodeBean hcb) {
	Date validFrom = hcb.getValidFrom() == null ? null : hcb.getValidFrom()
		.getDate();
	if (validFrom != null && validFrom.equals(START)) {
	    validFrom = null;
	}
	return validFrom;
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
		if (CodeList.CL_HGDE_KT.name().equals(cl.getId())) {
		    final Canton kt = new Canton();
		    kt.setCode(cb
			    .getAnnotationsByType(AnnotationType.ABBREV.name())
			    .iterator().next().getText().iterator().next()
			    .getValue());
		    kt.setNom(cb.getName());
		    kt.setNomCourt(kt.getCode());
		    cantonRef.put(cb.getId(), kt);
		} else if (CodeList.CL_HGDE_BEZ.name().equals(cl.getId())) {
		    final District bez = new District();
		    bez.setId(Integer.parseInt(cb
			    .getAnnotationsByType(
				    AnnotationType.CODE_OFS.name()).iterator()
			    .next().getText().iterator().next().getValue()));
		    bez.setNom(cb.getName());
		    if (cb.getAnnotationsByType(AnnotationType.ABBREV.name())
			    .iterator().hasNext()) {
			bez.setNomCourt(cb
				.getAnnotationsByType(
					AnnotationType.ABBREV.name())
				.iterator().next().getText().iterator().next()
				.getValue());
		    } else {
			bez.setNomCourt(bez.getNom());
		    }
		    districtRef.put(cb.getId(), bez);
		} else if (CodeList.CL_HGDE_GDE.name().equals(cl.getId())) {
		    final Commune gde = new Commune();
		    gde.setId(Integer.parseInt(cb
			    .getAnnotationsByType(
				    AnnotationType.CODE_OFS.name()).iterator()
			    .next().getText().iterator().next().getValue()));
		    gde.setNom(cb.getName());
		    if (cb.getAnnotationsByType(AnnotationType.ABBREV.name())
			    .iterator().hasNext()) {
			gde.setNomCourt(cb
				.getAnnotationsByType(
					AnnotationType.ABBREV.name())
				.iterator().next().getText().iterator().next()
				.getValue());
		    } else {
			gde.setNomCourt(gde.getNom());
		    }
		    communeRef.put(cb.getId(), gde);
		}
	    }
	}
    }

    /**
     * Factory du reader
     * 
     */
    public static class Factory {

    	private Factory() {
	    }

	/**
	 * Instanciation du reader
	 * 
	 * @return INSTANCE de reader SDMX
	 */
	public static SDMXDataAdaptor getInstance() {
	    @SuppressWarnings("resource")
	    final ApplicationContext ctxt = new ClassPathXmlApplicationContext(
		    SPRING_CONTEXT_FILE);
	    return ctxt.getBean(SDMXDataAdaptor.class);
	}
    }

    private enum CodeList {
	CL_HGDE_KT, CL_HGDE_BEZ, CL_HGDE_GDE
    }

    private enum ListAlias {
	KT, BEZ, GDE
    }

    private enum AnnotationType {
	ABBREV, CODE_OFS, INSCRIPTION, RADIATION, REC_TYPE
    }
}
