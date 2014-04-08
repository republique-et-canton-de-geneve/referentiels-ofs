package ch.ge.cti.ct.referentiels.ofs.processing;

import java.io.Serializable;
import java.util.Comparator;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

/**
 * Impl�mentation de l'interface Comparator pour le tri sur le nom des entit�s
 * de r�f�rentiels
 * 
 * @author DESMAZIERESJ
 * 
 */
public class NomComparator implements Comparator<IComplexType>, Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = 1601290705999124051L;

    /**
     * M�thode d'ex�cution du comparateur
     * 
     * @param ct0
     *            IComplexType de r�f�rence
     * @param ct1
     *            IComplexType � comparer
     * @return r�sultat de comparaison
     */
    @Override
    public int compare(final IComplexType ct0, final IComplexType ct1) {
	return ComparisonChain
		.start()
		.compare(ct0.getNom(), ct1.getNom(),
			Ordering.natural().nullsLast()).result();
    }
}
