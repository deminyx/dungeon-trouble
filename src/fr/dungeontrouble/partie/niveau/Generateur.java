package fr.dungeontrouble.partie.niveau;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;

/**
 * Classe associée aux générateurs de monstres du jeu.
 * 
 * @author Maxime BELLIER
 * 
 */
public class Generateur extends Objet {

	enum TypeGenerateur {
		fantome, gobelin
	};

	TypeGenerateur t;

	public Generateur(TypeGenerateur t) {
		this.t = t;
		switch (t) {
		case fantome:
			this.sprite = new Sprite(texture, new IntRect(0, 0, 50, 50));
			break;
		case gobelin:
			this.sprite = new Sprite(texture, new IntRect(50, 0, 50, 50));
			break;
		}

	}
}
