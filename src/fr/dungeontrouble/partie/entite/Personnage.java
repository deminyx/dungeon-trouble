package fr.dungeontrouble.partie.entite;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public class Personnage extends Entite {
	
	//enumeration des differents personnages
	public enum TypePersonnage
	{
		guerrier,
		elfe,
		magicien,
		valkyrie
	};
	
	protected Texture textureArme;
	protected Sprite spriteArme;
	protected int nbCles;
	protected int score;
	
	TypePersonnage perso;
	
	public Personnage(TypePersonnage perso) { // A modifier !!!
		// TODO Auto-generated constructor stub
		
		this.perso = perso;
		
		switch (perso) {
		case guerrier :
			this.nbCles = 0;
			this.score = 5000;
			break;
			
		case elfe :
			this.nbCles = 0;
			this.score = 5000;
			break;
		
		case magicien : 
			this.nbCles = 0;
			this.score = 5000;
			break;
		
		case valkyrie :
			this.nbCles = 0;
			this.score = 5000;
			break;
			
		}
	}
	
	
	
	@Override
	public void faireAction() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Méthode de déplacement d'un personnage selon une direction
	 * @param direction 0 = gauche, 1 = haut, 2 = droite, 3 = bas
	 */
	public void seDeplacer(int direction){
		
	}
	
	/**
	 * Méthode de vérification de sortie du jeu
	 */
	public void verifierSortie(){
		
	}
}
