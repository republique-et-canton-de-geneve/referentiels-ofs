package ch.ge.cti.ct.referentiels.ofs.service.jmx;

/**
 * Interface de classes pouvant �tre trait�es pour le rendering (g�n�ration de
 * rapport JMX)
 * 
 * @author DESMAZIERESJ
 * 
 */
public interface Renderable {
    /**
     * D�finition de la m�thode de rendering
     * 
     * @return donn�es mises en forme
     */
    String render();
}
