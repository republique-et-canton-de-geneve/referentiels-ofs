package ch.ge.cti.ct.referentiels.ofs.cache;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation de déclaration de méthode avec cache<br/>
 * Le premier paramètre est utilisé comme clef du cache
 * 
 * @author DESMAZIERESJ
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface Cachable {
    int SMALL = 10;
    int MEDIUM = 300;
    int LARGE = 1000;

    /** identifiant du cache */
    String name();

    /** taille maximale du cache */
    int size();

    /** activation des statistiques sur le cache */
    boolean stats() default true;
}
