package ch.ge.cti.ct.referentiels.pays.data;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

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

@Service("sdmxDataReader")
public class SDMXDataAdaptor extends
		AbstractSDMXDataAdaptor<ReferentielPaysTerritoires> {

	private static final String SPRING_CONTEXT_FILE = "referentiel-pays-context.xml";

	/** r�f�rentiel instanci� par le parsing du fichier SDMX */
	private ReferentielPaysTerritoires referentielPaysTerritoires = new ReferentielPaysTerritoires();
	/** liste temporaires de cantons */
	private Map<String, Continent> continentRef = new HashMap<String, Continent>();
	/** liste temporaires de districts */
	private Map<String, Region> regionRef = new HashMap<String, Region>();
	/** liste temporaires de communes */
	private Map<String, Pays> paysRef = new HashMap<String, Pays>();

	/**
	 * Constructeur avec injection du parseur SDMX
	 * 
	 * @param parser parseur
	 */
	@Autowired(required = true)
	public SDMXDataAdaptor(final SDMXParser parser) {
		super(parser);
	}

	/**
	 * Ex�cution du traitement du fichier SDMX
	 * 
	 * @param urlXML url du fichier SDMX
	 * @return instance du r�f�rentiel
	 * @throws ReferentielOfsException erreur de traitement
	 */
	public ReferentielPaysTerritoires parse(final URL urlXML)
			throws ReferentielOfsException {
		log().info("parse({})", urlXML);
		final Long start = System.currentTimeMillis();
		final StructureWorkspace workspace = getParser().parse(urlXML);
		populateMetaData(workspace);
		populateTempRefs(workspace);
		populateHierarchy(workspace);
		if (log().isDebugEnabled()) {
			dumpResult();
		}
		log().info("Chargement du r�f�rentiel {}: {} ms", urlXML,
				System.currentTimeMillis() - start);
		return referentielPaysTerritoires;
	}

	/**
	 * Alimentation des meta-donn�es du r�f�rentiel <br/>
	 * <ul>
	 * <li>identifiant du fichier SDMX</li>
	 * <li>date de g�n�ration du fichier SDMX</li>
	 * </ul>
	 * 
	 * @param workspace donn�es SDMX pars�es
	 */
	private void populateMetaData(final StructureWorkspace workspace) {
		referentielPaysTerritoires.setId(workspace.getStructureBeans(false)
				.getHeader().getId());
		referentielPaysTerritoires.setDate(workspace.getStructureBeans(false)
				.getHeader().getPrepared());
	}

	/**
	 * Alimentation des Map temporaires avec les instances des diff�rents objets
	 * sans leurs relations
	 * 
	 * @param workspace donn�es SDMX pars�es
	 */
	private void populateHierarchy(final StructureWorkspace workspace) {
		final SdmxBeans beans = workspace.getStructureBeans(false);
		// 1. <HierarchicalCodelists>
		final Set<HierarchicalCodelistBean> hclbs = beans
				.getHierarchicalCodelists();
		// 2. <structure:HierarchicalCodelist id="HCL_COUNTRIESGEO" version="1.0" agencyID="CH1_RN">
		final HierarchicalCodelistBean hclb = hclbs.iterator().next();
		// 3. <structure:Hierarchy id="HR_COUNTRIESGEO">
		final HierarchyBean hb = hclb.getHierarchies().iterator().next();
		// 4. <structure:CodeRef>: liste continents
		final List<HierarchicalCodeBean> cbContinents = hb
				.getHierarchicalCodeBeans();
		for (HierarchicalCodeBean cbContinent : cbContinents) {
			final Continent continent = continentRef.get(cbContinent
					.getCodeId());
			referentielPaysTerritoires.getContinent().add(continent);
			final Set<HierarchicalCodeBean> cbRegionPayss = cbContinent
					.getComposites(HierarchicalCodeBean.class);
			for (HierarchicalCodeBean cbRegionPays : cbRegionPayss) {
				if (ListAlias.REGION.code.equals(cbRegionPays
						.getCodelistAliasRef())) {
					final Region region = regionRef.get(cbRegionPays
							.getCodeId());
					continent.getRegion().add(region);
				} else if (ListAlias.PAYS.code.equals(cbRegionPays
						.getCodelistAliasRef())) {
					final Region region = regionRef.get(cbRegionPays
							.getIdentifiableParent().getId());
					final Pays pays = paysRef.get(cbRegionPays.getCodeId());
					region.getPays().add(pays);
				}
			}
		}
	}

	/**
	 * Alimentation des r�f�rences entre les objects
	 * 
	 * @param workspace donn�es SDMX pars�es
	 */
	private void populateTempRefs(final StructureWorkspace workspace) {
		final Set<CodelistBean> cls = workspace.getStructureBeans(false)
				.getCodelists();
		for (CodelistBean cl : cls) {
			final List<CodeBean> cbs = cl.getItems();
			for (CodeBean cb : cbs) {
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
		for (TextTypeWrapper ttw : cb.getNames()) {
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
			for (TextTypeWrapper ttw : cb.getAnnotationsByType(name).iterator()
					.next().getText()) {
				value = ttw.getValue();
				if (lang.name().equals(ttw.getLocale())) {
					return ttw.getValue();
				}
			}
		}
		return value == null ? defaultValue : value;
	}

	/**
	 * M�thode debug
	 */
	private void dumpResult() {
		try {
			log().debug("Continents:   {}", continentRef.size());
			log().debug("Regions: {}" + regionRef.size());
			log().debug("Pays:  {}" + paysRef.size());
			log().debug("Dump XML dans le fichier target/out.xml");

			final JAXBContext jc = JAXBContext
					.newInstance(ReferentielPaysTerritoires.class.getPackage()
							.getName());
			final Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty("jaxb.encoding", "UTF-8");
			marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
			marshaller.marshal(referentielPaysTerritoires, new File(
					"target/out.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}

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
	}

	private enum AnnotationType {
		SG_NAME, SG_ISO2, SG_ISO3, fr, en, de, it;
	}
}