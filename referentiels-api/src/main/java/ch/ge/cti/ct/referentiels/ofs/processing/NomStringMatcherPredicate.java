package ch.ge.cti.ct.referentiels.ofs.processing;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;

/**
 * Comparateur de nom de classe IComplexType<br/>
 * La comparaison se fait sur la longueur du pattern, permettant une recherche
 * des nom "commen�ant par"<br/>
 * La comparaison de fait sur la cha�ne "normalis�" afin de permettre une
 * comparaison sans caract�res sp�ciaux et accentu�s
 * 
 * @author DESMAZIERESJ
 * 
 */
public class NomStringMatcherPredicate extends AbstractMatcherPredicate
	implements Predicate<IComplexType> {

    private final String matcher;

    public NomStringMatcherPredicate(final String matcher) {
	this.matcher = Strings.isNullOrEmpty(matcher) ? null
		: normalize(matcher);
    }

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