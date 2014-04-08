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
     * M�thode d'ex�cution du pr�dicat
     * 
     * @param donn�es
     *            � tester par le pr�dicat
     * @return flag d'identit� des identifiants
     */
    @Override
    public boolean apply(final IComplexType ct) {
	return ct.getId() == id;
    }
}