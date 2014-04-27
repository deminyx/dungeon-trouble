package fr.dungeontrouble.partie.niveau;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;

/**
 * Classe associée aux portes du jeu.
 * 
 * @author Maxime BELLIER
 * 
 */
public class Porte extends Objet {
	private static int compteurId = 0;
	private int idPorteCourante = 0;

	private enum TypePorte {
		BoutPorteGauche, BoutPorteDroite, BoutPorteHaut, BoutPorteBas, MorceauPorteHorizontal, MorceauPorteVertical
	}

	public Porte(TypePorte t, boolean b) {
		if (b) {
			compteurId++;
		}
		idPorteCourante = compteurId;
		this.sprite = new Sprite(texture, new IntRect(100+(50*(t.ordinal()%3)),(50*(t.ordinal()/3)), 50, 50));


	}
}
