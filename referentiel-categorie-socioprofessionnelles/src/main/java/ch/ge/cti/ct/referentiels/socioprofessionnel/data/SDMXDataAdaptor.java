/*
 * Referentiels OFS
 *
 * Copyright (C) 2018 République et canton de Genève
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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

/**
 * Classe d'adapation de la structure de parsing SDMX � une structure
 * interm�diaire proche du service � rendre
 * 
 * @author DESMAZIERESJ
 * 
 */
@Service("sdmxDataReader")
public class SDMXDataAdaptor extends
	AbstractSDMXDataAdaptor<ReferentielSocioprofessionnel> {

    private static final String SPRING_CONTEXT_FILE = "referentiel-socioprofessionnel-context.xml";

    /** r�f�rentiel instanci� par le parsing du fichier SDMX */
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
     * Ex�cution du traitement du fichier SDMX
     * 
     * @param urlXML
     *            url du fichier SDMX
     * @return INSTANCE du r�f�rentiel
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
	log().info("Chargement du r�f�rentiel {}: {} ms", urlXML,
		System.currentTimeMillis() - start);
	return referentielsocioprofessionnel;
    }

    /**
     * Alimentation des meta-donn�es du r�f�rentiel <br/>
     * <ul>
     * <li>identifiant du fichier SDMX</li>
     * <li>date de g�n�ration du fichier SDMX</li>
     * </ul>
     * 
     * @param workspace
     *            donn�es SDMX pars�es
     */
    private void populateMetaData(final StructureWorkspace workspace) {
	referentielsocioprofessionnel.setId(workspace.getStructureBeans(false)
		.getHeader().getId());
	referentielsocioprofessionnel.setDate(workspace
		.getStructureBeans(false).getHeader().getPrepared());
    }

    /**
     * Alimentation des Map temporaires avec les instances des diff�rents objets
     * sans leurs relations
     * 
     * @param workspace
     *            donn�es SDMX pars�es
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
     * Alimentation des r�f�rences entre les objects
     * 
     * @param workspace
     *            donn�es SDMX pars�es
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
	/** niveau1 */
	CL_CSP_LV1,
	/** niveau2 */
	CL_CSP_LV2
    }

    private enum AnnotationType {
	fr, en, de, it;
    }
}
