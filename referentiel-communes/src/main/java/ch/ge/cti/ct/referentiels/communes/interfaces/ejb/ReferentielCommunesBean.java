package ch.ge.cti.ct.referentiels.communes.interfaces.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import ch.ge.cti.ct.referentiels.communes.model.Canton;
import ch.ge.cti.ct.referentiels.communes.model.Commune;
import ch.ge.cti.ct.referentiels.communes.model.District;
import ch.ge.cti.ct.referentiels.communes.model.ReferentielCommunes;
import ch.ge.cti.ct.referentiels.communes.service.ReferentielCommunesServiceAble;
import ch.ge.cti.ct.referentiels.communes.service.impl.ReferentielCommunesService;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.cache.Cachable;

/**
 * Exposition du service au format EJB3 - stateless
 * 
 * @author DESMAZIERESJ
 * 
 */
@Stateless
@Local(ReferentielCommunesServiceAble.class)
public class ReferentielCommunesBean implements ReferentielCommunesServiceAble {

	@Override
	public ReferentielCommunes getReferentiel()
			throws ReferentielOfsException {
		return ReferentielCommunesService.instance.getReferentiel();
	}

	@Override
	public List<Canton> getCantons() throws ReferentielOfsException {
		return ReferentielCommunesService.instance.getCantons();
	}

	@Override
	public Canton getCanton(final String codeCanton)
			throws ReferentielOfsException {
		return ReferentielCommunesService.instance.getCanton(codeCanton);
	}

	@Override
	public List<District> getDistricts(final String codeCanton)
			throws ReferentielOfsException {
		return ReferentielCommunesService.instance.getDistricts(codeCanton);
	}

	@Override
	public District getDistrict(final int districtId)
			throws ReferentielOfsException {
		return ReferentielCommunesService.instance.getDistrict(districtId);
	}

	@Override
	public List<Commune> getCommunesByDistrict(final int districtId)
			throws ReferentielOfsException {
		return ReferentielCommunesService.instance
				.getCommunesByDistrict(districtId);
	}

	@Override
	@Cachable(name = "communes", size = 200)
	public List<Commune> getCommunesByCanton(final String codeCanton)
			throws ReferentielOfsException {
		return ReferentielCommunesService.instance.getCommunesByCanton(codeCanton);
	}

	@Override
	public Commune getCommune(final int communeId)
			throws ReferentielOfsException {
		return ReferentielCommunesService.instance.getCommune(communeId);
	}

	@Override
	public List<Canton> getCantons(final Date dateValid)
			throws ReferentielOfsException {
		return ReferentielCommunesService.instance.getCantons(dateValid);
	}

	@Override
	public List<District> getDistricts(final String codeCanton, final Date dateValid)
			throws ReferentielOfsException {
		return ReferentielCommunesService.instance.getDistricts(codeCanton, dateValid);
	}

	@Override
	public List<Commune> getCommunesByDistrict(final int idDistrict, final Date dateValid)
			throws ReferentielOfsException {
		return ReferentielCommunesService.instance.getCommunesByDistrict(idDistrict, dateValid);
	}

	@Override
	public List<Commune> getCommunesByCanton(final String codeCanton, final Date dateValid)
			throws ReferentielOfsException {
		return ReferentielCommunesService.instance.getCommunesByCanton(codeCanton, dateValid);
	}

}
