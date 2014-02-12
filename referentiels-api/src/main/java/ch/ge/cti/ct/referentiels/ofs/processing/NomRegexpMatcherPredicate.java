package ch.ge.cti.ct.referentiels.ofs.processing;

import java.util.regex.Pattern;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

import com.google.common.base.Predicate;

public class NomRegexpMatcherPredicate extends AbstractMatcherPredicate
	implements Predicate<IComplexType> {

    private final Pattern regexp;

    public NomRegexpMatcherPredicate(final String regexp) {
	this.regexp = Pattern.compile(normalize(regexp.trim()));
    }

    @Override
    public boolean apply(final IComplexType compleType) {
	return regexp.matcher(normalize(compleType.getNom())).find();
    }

}