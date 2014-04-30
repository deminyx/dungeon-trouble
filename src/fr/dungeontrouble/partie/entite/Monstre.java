package fr.dungeontrouble.partie.entite;

import org.jsfml.system.Vector2i;

public class Monstre extends Entite {
	
	//enumeration des differents monstres
	public enum TypeMonstre{
		Fantome,
		Gobelin
	};
	
	protected int pdv;
	
	
	
	public Monstre(Monstre monstre,Vector2i position){
		super(position);
		this.pdv = 5000;//valeur à modifier
		
	}
	
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
