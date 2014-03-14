package ch.ge.cti.ct.referentiels.ofs.processing;

import java.io.Serializable;
import java.util.Comparator;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

/**
 * Implémentation de l'interface Comparator pour le tri sur le nom des entités
 * de référentiels
 * 
 * @author DESMAZIERESJ
 * 
 */
public class NomComparator implements Comparator<IComplexType>, Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = 1601290705999124051L;

    @Override
    public int compare(final IComplexType ct0, final IComplexType ct1) {
	return ComparisonChain
		.start()
		.compare(ct0.getNom(), ct1.getNom(),
			Ordering.natural().nullsLast()).result();
    }
}
