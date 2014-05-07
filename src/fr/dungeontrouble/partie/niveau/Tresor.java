package fr.dungeontrouble.partie.niveau;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;

/**
 * Classe associée aux trésors de monstres du jeu.
 * @author Maxime BELLIER
 *
 */
public class Tresor extends Objet {

	final int nbpoints=100;
	/**
	 * Constructeur Tresor initialisant le sprite de l'objet Trésor.
	 */
	
	public Tresor() {
		this.sprite = new Sprite (texture,new IntRect(0,50,50,50));
	}
}
