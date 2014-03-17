package ch.ge.cti.ct.referentiels.ofs.processing;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

public class _ComplexType implements IComplexType {
    private final int id;
    private final String nom;

    public _ComplexType(final int id, final String nom) {
	this.id = id;
	this.nom = nom;
    }

    @Override
    public int getId() {
	return id;
    }

    @Override
    public String getNom() {
	return nom;
    }

}