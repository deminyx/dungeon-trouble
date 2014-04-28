package fr.dungeontrouble.partie;

import java.util.HashMap;

import org.jsfml.system.Vector2f;

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

	Niveau niveau;
	HashMap<Vector2f, Personnage> personnages;
	HashMap<Vector2f, Monstre> monstres;

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
			personnages = new HashMap<Vector2f,Personnage>();
			personnages.put(new Vector2f(5,5), new Personnage(p));
			monstres = new HashMap<Vector2f,Monstre>();
			break;
		case map2:
			niveau = new Niveau("map2.txt");
			// AJOUTER LE CHOIX DU PERSONNAGE
			personnages = new HashMap<Vector2f,Personnage>();
			personnages.put(new Vector2f(16,5), new Personnage(p));
			monstres = new HashMap<Vector2f,Monstre>();
			break;
		case map3:
			break;
		}
	}

	/**
	 * 
	 * Constructeur pour partie 1 joueur
	 * 
	 * @param idmap
	 *            id du niveau joué
	 * @param p1
	 *            personnage souhaité par J1
	 * 
	 * @param p2
	 *            personnage souhaité par J2
	 * @param p3
	 *            personnage souhaité par J3
	 * @param p4
	 *            personnage souhaité par J4
	 */
	public Partie(Niveau.IDNiveau idmap, Personnage p1, Personnage p2,
			Personnage p3, Personnage p4) {

		switch (idmap) {

		case map1:
			break;
		case map2:
			break;
		case map3:
			break;
		}
	}
}
