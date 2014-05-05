package fr.dungeontrouble.partie;

import java.util.HashMap;

import org.jsfml.system.Vector2i;

import fr.dungeontrouble.partie.entite.Monstre;
import fr.dungeontrouble.partie.entite.Personnage;
import fr.dungeontrouble.partie.entite.Personnage.TypePersonnage;
import fr.dungeontrouble.partie.niveau.Niveau;

/**
 * Module Partie Classe principale décrivant l'ensemble des paramètres et
 * méthodes d'une partie de Dungeon Trouble
 * 
 * @author Maxime BELLIER
 * 
 */
public class Partie {

	private Niveau niveau;
	private static HashMap<Vector2i, Personnage> personnages;
	private static HashMap<Vector2i, Monstre> monstres;

	/**
	 * Constructeur pour partie 1 joueur
	 * 
	 * @param idmap
	 *            id du niveau joué
	 * @param p
	 *            personnage souhaité par J1
	 */
	public Partie(Niveau.IDNiveau idmap, TypePersonnage p) {

		switch (idmap) {

		case map1:
			niveau = new Niveau("map1.txt");
			// AJOUTER LE CHOIX DU PERSONNAGE
			personnages = new HashMap<Vector2i, Personnage>();
			personnages.put(new Vector2i(5, 5), new Personnage(p,new Vector2i(5, 5)));
			setMonstres(new HashMap<Vector2i, Monstre>());
			break;
		case map2:
			niveau = new Niveau("map2.txt");
			// AJOUTER LE CHOIX DU PERSONNAGE
			personnages = new HashMap<Vector2i, Personnage>();
			personnages.put(new Vector2i(16, 5), new Personnage(p,new Vector2i(16, 5)));
			setMonstres(new HashMap<Vector2i, Monstre>());
			break;
		case map3:
			break;
		}
	}

	/**
	 * 
	 * Constructeur pour partie 4 joueurs
	 * 
	 * @param idmap
	 *            id du niveau joué
	 * @param p1
	 *            personnage souhaité par J1
	 * @param p2
	 *            personnage souhaité par J2
	 * @param p3
	 *            personnage souhaité par J3
	 * @param p4
	 *            personnage souhaité par J4
	 */
	public Partie(Niveau.IDNiveau idmap, TypePersonnage p1, TypePersonnage p2,
			TypePersonnage p3, TypePersonnage p4) {

		switch (idmap) {

		case map1:
			niveau = new Niveau("map1.txt");
			// AJOUTER LE CHOIX DU PERSONNAGE
			personnages = new HashMap<Vector2i, Personnage>();
			personnages.put(new Vector2i(5, 5), new Personnage(p1,new Vector2i(5, 5)));
			personnages.put(new Vector2i(5, 6), new Personnage(p2,new Vector2i(5, 6)));
			personnages.put(new Vector2i(6, 5), new Personnage(p3,new Vector2i(6, 5)));
			personnages.put(new Vector2i(6, 6), new Personnage(p4,new Vector2i(6, 6)));
			setMonstres(new HashMap<Vector2i, Monstre>());
			break;
		case map2:
			niveau = new Niveau("map2.txt");
			// AJOUTER LE CHOIX DU PERSONNAGE
			personnages = new HashMap<Vector2i, Personnage>();
			personnages.put(new Vector2i(16, 5), new Personnage(p1,new Vector2i(16, 5)));
			personnages.put(new Vector2i(16, 6), new Personnage(p2,new Vector2i(16, 6)));
			personnages.put(new Vector2i(16, 7), new Personnage(p3,new Vector2i(16, 7)));
			personnages.put(new Vector2i(16, 8), new Personnage(p4,new Vector2i(16, 8)));
			setMonstres(new HashMap<Vector2i, Monstre>());
			break;
		case map3:
			break;
		}
	}

	public static HashMap<Vector2i, Monstre> getMonstres() {
		return monstres;
	}

	public static void setMonstres(HashMap<Vector2i, Monstre> monstre) {
		monstres = monstre;
	}

	public static HashMap<Vector2i, Personnage> getPersonnages() {
		return personnages;
	}
}
