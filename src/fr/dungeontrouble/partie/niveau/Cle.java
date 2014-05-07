package fr.dungeontrouble.partie.niveau;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;

/**
 * Classe associ�e aux objets "cl�s" du jeu.
 * @author Maxime BELLIER
 *
 */
public class Cle extends Objet {

	/**
	 * Constructeur li�s aux "cl�s" initialisant le sprite des celles-�i.
	 * @author Maxime BELLIER
	 *
	 */
	
	public Cle() {
		this.sprite = new Sprite (texture,new IntRect(50,50,50,50));
	}
	
}
