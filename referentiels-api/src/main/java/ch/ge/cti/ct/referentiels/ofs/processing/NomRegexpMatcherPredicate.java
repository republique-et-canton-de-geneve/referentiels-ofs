package ch.ge.cti.ct.referentiels.ofs.processing;

import java.util.regex.Pattern;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;

/**
 * Impl�mentation du pr�dicat de filtrage par regexp sur le nom
 * 
 * @author DESMAZIERESJ
 * 
 */
public class NomRegexpMatcherPredicate extends AbstractMatcherPredicate
	implements Predicate<IComplexType> {

    private final Pattern regexp;

    /**
     * Constructeur
     * 
     * @param regexp
     *            expression r�guli�re au format String
     */
    public NomRegexpMatcherPredicate(final String regexp) {
	this.regexp = Strings.emptyToNull(regexp) == null ? null : Pattern
		.compile(normalize(Strings.nullToEmpty(regexp).trim()));
    }

    /**
     * M�thode d'ex�cution du pr�dicat
     * 
     * @param entit�
     *            � tester
     * @return r�sultat de la comparaison
     */
    @Override
    public boolean apply(final IComplexType compleType) {
	if (regexp == null) {
	    return false;
	}
	return regexp.matcher(
		normalize(Strings.nullToEmpty(compleType.getNom()))).find();
    }

}