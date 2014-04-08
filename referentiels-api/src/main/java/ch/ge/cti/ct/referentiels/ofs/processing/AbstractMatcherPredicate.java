package ch.ge.cti.ct.referentiels.ofs.processing;

import java.text.Normalizer;
import java.util.regex.Pattern;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

import com.google.common.base.Predicate;

/**
 * Classe abstraite de pr�dicat de comparaison<br/>
 * Fournit des m�thodes communes � tous les matchers
 * 
 * @author DESMAZIERESJ
 * 
 */
public abstract class AbstractMatcherPredicate implements
	Predicate<IComplexType> {
    /** regexp de normalisation (suppression des caract�res sp�ciaux) */
    private static final Pattern NORMALIZER_REGEX = Pattern
	    .compile("[^\\p{ASCII}]");
    /** cha�ne de remplacement des caract�res sp�ciaux */
    private static final String NORMALIZER_REPLACE = "";

    /**
     * la comparaison se fait sans tenir compte des accents et caract�res
     * sp�ciaux
     * 
     * @param value
     *            cha�ne � normaliser
     * @return cha�ne normalis�e
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