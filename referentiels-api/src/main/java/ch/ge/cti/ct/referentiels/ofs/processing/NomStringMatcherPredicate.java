package ch.ge.cti.ct.referentiels.ofs.processing;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;

/**
 * Comparateur de nom de classe IComplexType<br/>
 * La comparaison se fait sur la longueur du pattern, permettant une recherche
 * des nom "commençant par"<br/>
 * La comparaison de fait sur la chaîne "normalisé" afin de permettre une
 * comparaison sans caractères spéciaux et accentués
 * 
 * @author DESMAZIERESJ
 * 
 */
public class NomStringMatcherPredicate extends AbstractMatcherPredicate
	implements Predicate<IComplexType> {

    private final String matcher;

    /**
     * Constructeur
     * 
     * @param matcher
     *            chaîne de comparaison
     */
    public NomStringMatcherPredicate(final String matcher) {
	this.matcher = Strings.isNullOrEmpty(matcher) ? null
		: normalize(matcher);
    }

    /**
     * Méthode d'exécution du prédicat
     * 
     * @param entité
     *            à tester
     * @return résultat de la comparaison
     */
    @Override
    public boolean apply(final IComplexType compleType) {
	if (matcher == null && Strings.emptyToNull(compleType.getNom()) == null) {
	    return true;
	}
	if (matcher == null ^ Strings.emptyToNull(compleType.getNom()) == null) {
	    return false;
	}
	return matcher.equals(normalize(compleType.getNom()).substring(0,
		Math.min(compleType.getNom().length(), matcher.length())));
    }
}