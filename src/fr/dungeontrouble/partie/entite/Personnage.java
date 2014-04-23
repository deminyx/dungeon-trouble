package fr.dungeontrouble.partie.entite;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public abstract class Personnage extends Entite {
	
	protected Texture textureArme;
	protected Sprite spriteArme;
	protected int nbCles;
	protected int score;
	
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
