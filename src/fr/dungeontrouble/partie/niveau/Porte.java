package fr.dungeontrouble.partie.niveau;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;

/**
 * Classe associée aux portes du jeu.
 * 
 * @author Maxime BELLIER
 * @author Valentin PORCHET
 */
public class Porte extends Objet {
	
	private static int compteurId = 0;
	private int idPorteCourante = 0;

	public enum TypePorte {
		BoutPorteGauche, BoutPorteDroite, BoutPorteHaut, BoutPorteBas, MorceauPorteHorizontal, MorceauPorteVertical
	}

	/**
	 * Constructeur de la classe Porte simple
	 * @param t
	 * 			enum : TypePorte, le morceau de porte(extrémités ou non, et orientation ).
	 */
	public Porte(TypePorte t) {
		compteurId++;		
		idPorteCourante = compteurId;		
		this.sprite = new Sprite(texture, new IntRect(100+(50*(t.ordinal()%3)),(50*(t.ordinal()/3)), 50, 50));
	}
	
	/**
	 * Constructeur de la classe Porte
	 * @param t
	 * 			enum : TypePorte, le morceau de porte(extrémités ou non, et orientation ).
	 * @param id
	 * 			int : Identifiant unique de la porte associé à ce morceau de porte.
	 */
	public Porte(TypePorte t, int id){
		idPorteCourante = id;
		this.sprite = new Sprite(texture, new IntRect(100+(50*(t.ordinal()%3)),(50*(t.ordinal()/3)), 50, 50));
	}

	public int getIdPorteCourante() {
		return idPorteCourante;
	}
}
