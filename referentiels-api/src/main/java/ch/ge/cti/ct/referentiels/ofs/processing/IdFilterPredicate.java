package ch.ge.cti.ct.referentiels.ofs.processing;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

import com.google.common.base.Predicate;

/**
 * Implémentation du prédicat de filtrage sur l'id
 * 
 * @author DESMAZIERESJ
 * 
 */
public class IdFilterPredicate implements Predicate<IComplexType> {
    private final int id;

    /**
     * Constructeur
     * 
     * @param id
     *            identifiant
     */
    public IdFilterPredicate(final int id) {
	this.id = id;
    }

    /**
     * Méthode d'exécution du prédicat
     * 
     * @param données
     *            à tester par le prédicat
     * @return flag d'identité des identifiants
     */
    @Override
    public boolean apply(final IComplexType ct) {
	return ct.getId() == id;
    }
}