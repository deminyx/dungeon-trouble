package fr.dungeontrouble.partie.entite;

public class Monstre extends Entite {
	
	//enumeration des differents monstres
	public enum TypeMonstre{
		Fantome,
		Gobelin
	};
	
	protected int pdv;
	
	public int getPdv() {
		return pdv;
	}
	public void setPdv(int pdv) {
		this.pdv = pdv;
	}
	
	@Override
	public void faireAction() {
		// TODO Auto-generated method stub
		
	}
}
