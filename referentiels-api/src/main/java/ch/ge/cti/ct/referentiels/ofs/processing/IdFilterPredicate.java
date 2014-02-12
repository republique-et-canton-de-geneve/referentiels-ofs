package ch.ge.cti.ct.referentiels.ofs.processing;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

import com.google.common.base.Predicate;

/**
 * Impl�mentation du pr�dicat de filtrage sur l'id
 * 
 * @author DESMAZIERESJ
 * 
 */
public class IdFilterPredicate implements Predicate<IComplexType> {
    private final int id;

    public IdFilterPredicate(final int id) {
	this.id = id;
    }

    @Override
    public boolean apply(final IComplexType ct) {
	return ct.getId() == id;
    }
}