package fr.dungeontrouble.partie.niveau;

import java.util.HashMap;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2i;

import fr.dungeontrouble.partie.Partie;
import fr.dungeontrouble.partie.entite.Monstre;
import fr.dungeontrouble.partie.entite.Monstre.TypeMonstre;

/**
 * Classe associée aux générateurs de monstres du jeu.
 * 
 * @author Maxime BELLIER
 * 
 */
public class Generateur extends Objet {

	TypeMonstre t;	// Type de Générateur (fantôme)
	HashMap<Vector2i, Boolean> casesGenerables; // HashMap des cases où l'on peut y creer des monstres
	public static int nbMonstres=0;

	/**
	 * Constructeur de Generateur prenant en paramètre son type
	 * 
	 * @param t type : enum TypeGenerateur{ fantome, gobelin }
	 * @param pos Position du générateur à générer
	 */
	public Generateur(TypeMonstre t, Vector2i pos) {
		this.position = pos;								// Initialisation de la position
		
		casesGenerables = new HashMap<Vector2i, Boolean>(); 
		casesGenerables.put(new Vector2i(this.position.x - 1, this.position.y), // Ajout des 4 cases entourant le générateur
				false);
		casesGenerables.put(new Vector2i(this.position.x + 1, this.position.y),
				false);
		casesGenerables.put(new Vector2i(this.position.x, this.position.y - 1),
				false);
		casesGenerables.put(new Vector2i(this.position.x, this.position.y + 1),
				false);
		VerifCaseMursGenerateurs(); // Appel à la méthodes pour la supression 
									// éventuelle de certaines cases générables

		this.t = t;
		
		switch (t) {		// Ajout du sprite selon le type de générateur
		case Fantome:
			this.sprite = new Sprite(texture, new IntRect(0, 0, 50, 50));
			break;
		case Gobelin:
			this.sprite = new Sprite(texture, new IntRect(50, 0, 50, 50));
			break;
		}
	}

	/**
	 * Fonction qui vérifie si des murs sont sur les points de générations de
	 * monstres
	 * 
	 * @author Maxime BELLIER
	 * 
	 */
	public void VerifCaseMursGenerateurs() {

		if (Niveau.getNiveau()[position.x - 1][position.y] > 0) {
			casesGenerables.remove(new Vector2i(position.x-1, position.y));
		}
		if (Niveau.getNiveau()[position.x][position.y-1] > 0) {
			casesGenerables.remove(new Vector2i(position.x, position.y-1));
		}
		if (Niveau.getNiveau()[position.x+1][position.y] > 0) {
			casesGenerables.remove(new Vector2i(position.x+1, position.y));
		}
		if (Niveau.getNiveau()[position.x][position.y+1] > 0) {
			casesGenerables.remove(new Vector2i(position.x, position.y+1));
		}

	}

	/**
	 * Fonction générant les monstres si cases générables libres
	 * 
	 * @author Maxime BELLIER
	 */
	public void genererMonstres() {
		nbMonstres++;					//Ajout d'un monstre
		for (Vector2i v : casesGenerables.keySet()) { // Pour toutes cases générables ....
			if ((Partie.getMonstres().containsKey(v))
					|| (Partie.getPersonnages().containsKey(v))
					|| (Niveau.getObjets().containsKey(v))) {
				casesGenerables.put(v, false);		// On vérifie que l'on a encore rien géneré dessus.
			} else {
				casesGenerables.put(v, true);
			}
		}
		for (Vector2i v : casesGenerables.keySet()) {
			if (casesGenerables.get(v) == true) {
				Monstre m = new Monstre(t,v);
				Partie.getMonstres().put(m.getPosition(), m); // ... puis on ajoute une nouvelle instanciation de monstres.
				casesGenerables.put(v, false);
			}
		}
	}
}
