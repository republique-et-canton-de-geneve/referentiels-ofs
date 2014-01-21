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
	/** nom de la méthode appelée */
	private String methode;
	/** valeur du paramètre (si il existe) */
	private String parametre;

	public String getMethode() {
		return methode;
	}

	public String getParametre() {
		return parametre;
	}

	/**
	 * initialisation de la méthode (en fluentAPI)
	 * 
	 * @param method méthode appelée
	 * @return this
	 */
	public Call withMethod(final Method method) {
		methode = method.getName();
		return this;
	}

	/**
	 * initialisation du paramètre d'appel (en fluentAPI)
	 * 
	 * @param params liste des paramètres passés à la méthode
	 * @return this
	 */
	public Call withParameters(final Object[] params) {
		if (params != null && params.length > 0) {
			if (params[0] != null) {
				parametre = params[0].toString();
			} else {
				parametre = "null";
			}
		}
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.methode, this.parametre);
	}

	@Override
	public boolean equals(Object obj) {
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
		return Objects.equal(this.methode, other.methode)
				&& Objects.equal(this.parametre, other.parametre);
	}

}
