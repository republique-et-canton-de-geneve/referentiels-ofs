package ch.ge.cti.ct.referentiels.ofs.processing;

import java.util.Comparator;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

/**
 * Implémentation de l'interface Comparator pour le tri sur le nom des entités
 * de référentiels
 * 
 * @author DESMAZIERESJ
 * 
 */
public class NomComparator implements Comparator<IComplexType> {
    @Override
    public int compare(final IComplexType ct0, final IComplexType ct1) {
	return ct0.getNom().compareTo(ct1.getNom());
    }
}
