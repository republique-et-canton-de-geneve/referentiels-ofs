package ch.ge.cti.ct.referentiels.ofs.processing;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

import com.google.common.base.Predicate;

public class NomStringMatcherPredicate extends AbstractMatcherPredicate
	implements Predicate<IComplexType> {

    private final String matcher;

    public NomStringMatcherPredicate(final String matcher) {
	this.matcher = normalize(matcher);
    }

    @Override
    public boolean apply(final IComplexType compleType) {
	return matcher.equals(normalize(compleType.getNom().substring(0,
		Math.min(compleType.getNom().length(), matcher.length()))));
    }

}