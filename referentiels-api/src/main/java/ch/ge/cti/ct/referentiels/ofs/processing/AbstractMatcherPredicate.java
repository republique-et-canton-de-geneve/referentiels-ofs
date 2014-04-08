package ch.ge.cti.ct.referentiels.ofs.processing;

import java.text.Normalizer;
import java.util.regex.Pattern;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

import com.google.common.base.Predicate;

/**
 * Classe abstraite de prédicat de comparaison<br/>
 * Fournit des méthodes communes à tous les matchers
 * 
 * @author DESMAZIERESJ
 * 
 */
public abstract class AbstractMatcherPredicate implements
	Predicate<IComplexType> {
    /** regexp de normalisation (suppression des caractères spéciaux) */
    private static final Pattern NORMALIZER_REGEX = Pattern
	    .compile("[^\\p{ASCII}]");
    /** chaîne de remplacement des caractères spéciaux */
    private static final String NORMALIZER_REPLACE = "";

    /**
     * la comparaison se fait sans tenir compte des accents et caractères
     * spéciaux
     * 
     * @param value
     *            chaîne à normaliser
     * @return chaîne normalisée
     */
    protected final String normalize(final String value) {
	if (value != null) {
	    return NORMALIZER_REGEX
		    .matcher(Normalizer.normalize(value, Normalizer.Form.NFD))
		    .replaceAll(NORMALIZER_REPLACE).toLowerCase();
	}
	return null;
    }
}