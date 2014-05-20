package ch.ge.cti.ct.referentiels.professions.data;

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
import ch.ge.cti.ct.referentiels.professions.model.Classe;
import ch.ge.cti.ct.referentiels.professions.model.Division;
import ch.ge.cti.ct.referentiels.professions.model.Genre;
import ch.ge.cti.ct.referentiels.professions.model.Groupe;
import ch.ge.cti.ct.referentiels.professions.model.ReferentielProfessions;

/**
 * Classe d'adapation de la structure de parsing SDMX à une structure
 * intermédiaire proche du service à rendre
 * 
 * @author DESMAZIERESJ
 * 
 */
@Service("sdmxDataReader")
public class SDMXDataAdaptor extends
	AbstractSDMXDataAdaptor<ReferentielProfessions> {

    private static final String SPRING_CONTEXT_FILE = "referentiel-professions-context.xml";

    /** référentiel instancié par le parsing du fichier SDMX */
    private final ReferentielProfessions referentielProfessions = new ReferentielProfessions();
    /** liste temporaires de Divisions */
    private final Map<String, Division> divisionRef = new HashMap<String, Division>();
    /** liste temporaires de Classes */
    private final Map<String, Classe> classeRef = new HashMap<String, Classe>();
    /** liste temporaires de Groupes */
    private final Map<String, Groupe> groupeRef = new HashMap<String, Groupe>();
    /** liste temporaires de Genres */
    private final Map<String, Genre> genreRef = new HashMap<String, Genre>();

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
    public ReferentielProfessions parse(final URL urlXML)
	    throws ReferentielOfsException {
	log().info("parse({})", urlXML);
	final Long start = System.currentTimeMillis();
	final StructureWorkspace workspace = getParser().parse(urlXML);
	populateMetaData(workspace);
	populateTempRefs(workspace);
	populateHierarchy(workspace);
	log().info("Chargement du référentiel {}: {} ms", urlXML,
		System.currentTimeMillis() - start);
	return referentielProfessions;
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
	referentielProfessions.setId(workspace.getStructureBeans(false)
		.getHeader().getId());
	referentielProfessions.setDate(workspace.getStructureBeans(false)
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
	final Set<HierarchicalCodelistBean> hclbs = beans
		.getHierarchicalCodelists();
	final HierarchicalCodelistBean hclb = hclbs.iterator().next();
	final HierarchyBean hb = hclb.getHierarchies().iterator().next();
	final List<HierarchicalCodeBean> cbDivisions = hb
		.getHierarchicalCodeBeans();
	for (final HierarchicalCodeBean cbDivision : cbDivisions) {
	    final Division division = divisionRef.get(cbDivision.getCodeId());
	    referentielProfessions.getDivision().add(division);
	    for (final HierarchicalCodeBean cbClasseRef : cbDivision
		    .getCodeRefs()) {
		final Classe classe = classeRef.get(cbClasseRef.getCodeId());
		classe.setDivisionId(division.getId());
		division.getClasse().add(classe);
		for (final HierarchicalCodeBean cbGroupeRef : cbClasseRef
			.getCodeRefs()) {
		    final Groupe groupe = groupeRef.get(cbGroupeRef.getId());
		    groupe.setDivisionId(division.getId());
		    groupe.setClasseId(classe.getId());
		    classe.getGroupe().add(groupe);
		    for (final HierarchicalCodeBean cbGenreRef : cbGroupeRef
			    .getCodeRefs()) {
			final Genre genre = genreRef.get(cbGenreRef.getId());
			genre.setDivisionId(division.getId());
			genre.setClasseId(classe.getId());
			genre.setGroupeId(groupe.getId());
			groupe.getGenre().add(genre);
		    }
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
		if (CodeList.CL_SBN_BERUFSABTEILUNG.name().equals(cl.getId())) {
		    final Division division = new Division();
		    division.setId(Integer.parseInt(cb.getId()));
		    division.setNom(getName(cb, AnnotationType.fr));
		    divisionRef.put(cb.getId(), division);
		} else if (CodeList.CL_SBN_BERUFSKLASSE.name().equals(
			cl.getId())) {
		    final Classe classe = new Classe();
		    classe.setId(Integer.parseInt(cb.getId()));
		    classe.setNom(getName(cb, AnnotationType.fr));
		    classeRef.put(cb.getId(), classe);
		} else if (CodeList.CL_SBN_BERUFSGRUPPE.name().equals(
			cl.getId())) {
		    final Groupe groupe = new Groupe();
		    groupe.setId(Integer.parseInt(cb.getId()));
		    groupe.setNom(getName(cb, AnnotationType.fr));
		    groupeRef.put(cb.getId(), groupe);
		} else if (CodeList.CL_SBN_BERUFSART.name().equals(cl.getId())) {
		    final Genre genre = new Genre();
		    genre.setId(Integer.parseInt(cb.getId()));
		    genre.setNom(getName(cb, AnnotationType.fr));
		    genreRef.put(cb.getId(), genre);
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
	/** division */
	CL_SBN_BERUFSABTEILUNG,
	/** classe */
	CL_SBN_BERUFSKLASSE,
	/** groupe */
	CL_SBN_BERUFSGRUPPE,
	/** genre */
	CL_SBN_BERUFSART;
    }

    private enum AnnotationType {
	fr, en, de, it;
    }
}
