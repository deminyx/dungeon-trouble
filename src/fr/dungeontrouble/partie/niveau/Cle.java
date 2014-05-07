package fr.dungeontrouble.partie.niveau;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;

/**
 * Classe associée aux objets "clés" du jeu.
 * @author Maxime BELLIER
 *
 */
public class Cle extends Objet {

	/**
	 * Constructeur liés aux "clés" initialisant le sprite des celles-çi.
	 * @author Maxime BELLIER
	 *
	 */
	
	public Cle() {
		this.sprite = new Sprite (texture,new IntRect(50,50,50,50));
	}
	
}
