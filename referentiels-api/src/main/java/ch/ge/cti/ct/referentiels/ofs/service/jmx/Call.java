package ch.ge.cti.ct.referentiels.ofs.service.jmx;

import java.lang.reflect.Method;

import com.google.common.base.Objects;

/**
 * Modélisation des appels de méthodes
 * 
 * @author DESMAZIERESJ
 * 
 */
public class Call {
    /** nom de la classe appelée */
    private String classe;
    /** nom de la méthode appelée */
    private String methode;

    public String getClasse() {
	return classe;
    }

    public String getMethode() {
	return methode;
    }

    /**
     * initialisation de la classe (en fluentAPI)
     * 
     * @param clazz
     *            classe appelée
     * @return this
     */
    public Call withClasse(final Class<? extends Object> clazz) {
	classe = clazz.getName();
	return this;
    }

    /**
     * initialisation de la méthode (en fluentAPI)
     * 
     * @param method
     *            méthode appelée
     * @return this
     */
    public Call withMethod(final Method method) {
	methode = method.getName();
	return this;
    }

    @Override
    public int hashCode() {
	return Objects.hashCode(this.classe, this.methode);
    }

    @Override
    public boolean equals(final Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Call other = (Call) obj;
	return Objects.equal(this.classe, other.classe)
		&& Objects.equal(this.methode, other.methode);
    }
}
