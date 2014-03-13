package ch.ge.cti.ct.referentiels.ofs.service.jmx;

import java.lang.reflect.Method;

import com.google.common.base.Objects;

/**
 * Mod�lisation des appels de m�thodes
 * 
 * @author DESMAZIERESJ
 * 
 */
public class Call {
    /** nom de la classe appel�e */
    private String classe;
    /** nom de la m�thode appel�e */
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
     *            classe appel�e
     * @return this
     */
    public Call withClasse(final Class<? extends Object> clazz) {
	classe = clazz.getName();
	return this;
    }

    /**
     * initialisation de la m�thode (en fluentAPI)
     * 
     * @param method
     *            m�thode appel�e
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
