package ch.ge.cti.ct.referentiels.ofs.processing;

import java.util.regex.Pattern;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;

public class NomRegexpMatcherPredicate extends AbstractMatcherPredicate
	implements Predicate<IComplexType> {

    private final Pattern regexp;

    public NomRegexpMatcherPredicate(final String regexp) {
	this.regexp = Strings.emptyToNull(regexp) == null ? null : Pattern
		.compile(normalize(Strings.nullToEmpty(regexp).trim()));
    }

    @Override
    public boolean apply(final IComplexType compleType) {
	if (regexp == null) {
	    return false;
	}
	return regexp.matcher(
		normalize(Strings.nullToEmpty(compleType.getNom()))).find();
    }

}