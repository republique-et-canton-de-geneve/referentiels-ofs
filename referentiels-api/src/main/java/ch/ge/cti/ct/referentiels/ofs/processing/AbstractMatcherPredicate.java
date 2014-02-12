package ch.ge.cti.ct.referentiels.ofs.processing;

import java.text.Normalizer;
import java.util.regex.Pattern;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

import com.google.common.base.Predicate;

public abstract class AbstractMatcherPredicate implements
	Predicate<IComplexType> {
    private static final Pattern NORMALIZER_REGEX = Pattern
	    .compile("[^\\p{ASCII}]");
    private static final String NORMALIZER_REPLACE = "";

    /**
     * la comparaison se fait sans tenir compte des accents et caractères
     * spéciaux
     */
    protected final String normalize(final String value) {
	return NORMALIZER_REGEX
		.matcher(Normalizer.normalize(value, Normalizer.Form.NFD))
		.replaceAll(NORMALIZER_REPLACE).toLowerCase();
    }
}