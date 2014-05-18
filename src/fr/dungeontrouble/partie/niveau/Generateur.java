package fr.dungeontrouble.partie.niveau;

import java.util.HashMap;
import java.util.Random;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import fr.dungeontrouble.partie.Partie;
import fr.dungeontrouble.partie.entite.Monstre;
import fr.dungeontrouble.partie.entite.Monstre.TypeMonstre;

/**
 * Classe associée aux générateurs de monstres du jeu.
 * 
 * @author Maxime BELLIER
 * @author Valentin PORCHET
 */
public class Generateur extends Objet {

	TypeMonstre t;	// Type de Générateur (fantôme)
	HashMap<Vector2i, Boolean> casesGenerables; // HashMap des cases où l'on peut y creer des monstres
	public static int nbMonstres=0;
	private float timeLimit;
	private Clock clock;

	/**
	 * Constructeur de Generateur prenant en paramètre son type
	 * 
	 * @param t type : enum TypeGenerateur{ fantome, gobelin }
	 * @param pos Position du générateur à générer
	 */
	public Generateur(TypeMonstre t, Vector2i pos) {
		this.position = pos;								// Initialisation de la position
		this.clock = new Clock();
		
		casesGenerables = new HashMap<Vector2i, Boolean>(); 
		casesGenerables.put(new Vector2i(this.position.x - 1, this.position.y), // Ajout des 4 cases entourant le générateur
				false);
		casesGenerables.put(new Vector2i(this.position.x + 1, this.position.y),
				false);
		casesGenerables.put(new Vector2i(this.position.x, this.position.y - 1),
				false);
		casesGenerables.put(new Vector2i(this.position.x, this.position.y + 1),
				false);		

		this.t = t;
		this.timeLimit = (float)((new Random()).nextDouble()+1.9);
		
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
		if (Niveau.getNiveau()[position.x][position.y-1] > 0) {
			casesGenerables.remove(new Vector2i(position.x, position.y-1));
		}
		if (Niveau.getNiveau()[position.x-1][position.y] > 0) {
			casesGenerables.remove(new Vector2i(position.x-1, position.y));
		}
		if (Niveau.getNiveau()[position.x][position.y+1] > 0) {
			casesGenerables.remove(new Vector2i(position.x, position.y+1));
		}
		if (Niveau.getNiveau()[position.x+1][position.y] > 0) {
			casesGenerables.remove(new Vector2i(position.x+1, position.y));
		}
	}

	/**
	 * Fonction générant les monstres si cases générables libres
	 * 
	 * @author Maxime BELLIER
	 * @param centreDeVue Coordonnées du centre de la vue actuellement
	 */
	public void genererMonstres(Vector2f centreDeVue) {
		// Si le générateur est visible, alors on génère des monstres
		if ((this.sprite.getPosition().x >= centreDeVue.x - 275)&&
			(this.sprite.getPosition().x <= centreDeVue.x + 275)&&
			(this.sprite.getPosition().y >= centreDeVue.y - 300)&&
			(this.sprite.getPosition().y <= centreDeVue.y + 300)&&
			clock.getElapsedTime().asSeconds() > this.timeLimit)
		{
			for (Vector2i v : casesGenerables.keySet()) { // Pour toutes cases générables ....
				if ((Partie.getMonstres().containsKey(new Vector2i(v.y,v.x)))
					|| (Partie.getPersonnages().containsKey(new Vector2i(v.y,v.x)))
					|| (Niveau.getObjets().containsKey(new Vector2i(v.y,v.x)))) {
					casesGenerables.put(v, false);		// On vérifie que l'on a encore rien géneré dessus.
				} else  {
					casesGenerables.put(v, true);
				}
			}
			for (Vector2i v : casesGenerables.keySet()) {
				if ((casesGenerables.get(v) == true)){
					Monstre m = new Monstre(t,v);
					Partie.getMonstres().put(m.getPosition(), m); // ... puis on ajoute une nouvelle instanciation de monstres.
					nbMonstres++;					//Ajout d'un monstre
					casesGenerables.put(v, false);
				}
			}
			clock.restart();
		}
	}
}
