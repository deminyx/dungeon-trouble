package fr.dungeontrouble.partie.niveau;

import java.util.HashMap;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2i;

import fr.dungeontrouble.partie.Partie;
import fr.dungeontrouble.partie.entite.Monstre;

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
	HashMap<Vector2i, Boolean> casesGenerables;

	/**
	 * Constructeur de Generateur prenant en paramètre son type
	 * 
	 * @author Maxime BELLIER
	 * @param t
	 *            type : enum TypeGenerateur{ fantome, gobelin };
	 */
	public Generateur(TypeGenerateur t) {
		casesGenerables = new HashMap<Vector2i, Boolean>();
		casesGenerables.put(new Vector2i(this.position.x - 1, this.position.y),
				false);
		casesGenerables.put(new Vector2i(this.position.x + 1, this.position.y),
				false);
		casesGenerables.put(new Vector2i(this.position.x, this.position.y - 1),
				false);
		casesGenerables.put(new Vector2i(this.position.x, this.position.y + 1),
				false);
		VerifCaseMursGenerateurs();

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
	/**
	 * Fonction qui vérifie si des murs sont sur les points de générations de monstres
	 * 
	 * @author Maxime BELLIER
	 * 
	 */
	public void VerifCaseMursGenerateurs() {

		if (Niveau.getNiveau()[position.y - 1][position.x] > 0) {
			casesGenerables.remove(new Vector2i(position.x, position.y - 1));
		}
		if (Niveau.getNiveau()[position.y][position.x - 1] > 0) {
			casesGenerables.remove(new Vector2i(position.x - 1, position.y));
		}
		if (Niveau.getNiveau()[position.y + 1][position.x] > 0) {
			casesGenerables.remove(new Vector2i(position.x, position.y + 1));
		}
		if (Niveau.getNiveau()[position.y][position.x + 1] > 0) {
			casesGenerables.remove(new Vector2i(position.x + 1, position.y));
		}

	}
	/**
	 * Fonction générant les monstres si cases générables libres
	 * 
	 * @author Maxime BELLIER
	 */
	public void genererMonstres() {

		for (Vector2i v : casesGenerables.keySet()) {
			if ((Partie.getMonstres().containsKey(v))
					|| (Partie.getPersonnages().containsKey(v))) {
				casesGenerables.put(v, false);
			} else {
				casesGenerables.put(v, true);
			}
		}
		for (Vector2i v : casesGenerables.keySet()) {
			if (casesGenerables.get(v) == true) {
				Partie.getMonstres().put(v, new Monstre());
				casesGenerables.put(v, false);
			}
		}
	}
}
