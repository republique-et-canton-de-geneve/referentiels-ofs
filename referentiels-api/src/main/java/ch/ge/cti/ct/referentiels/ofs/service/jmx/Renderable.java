package ch.ge.cti.ct.referentiels.ofs.service.jmx;

/**
 * Interface de classes pouvant être traitées pour le rendering (génération de
 * rapport JMX)
 * 
 * @author DESMAZIERESJ
 * 
 */
public interface Renderable {
    /**
     * Définition de la méthode de rendering
     * 
     * @return données mises en forme
     */
    String render();
}
