package fr.dungeontrouble.partie;

import java.util.HashMap;

import org.jsfml.system.Vector2f;

import fr.dungeontrouble.partie.entite.Monstre;
import fr.dungeontrouble.partie.entite.Personnage;
import fr.dungeontrouble.partie.niveau.Niveau;

/**
 * Module Partie Classe principale d�crivant l'ensemble des param�tres et
 * m�thodes d'une partie de Dungeon Trouble
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
	 *            id du niveau jou�
	 * @param p
	 *            personnage souhait� par J1
	 */
	public Partie(Niveau.IDNiveau idmap, Personnage p) {

		switch (idmap) {

		case map1:
			break;
		case map2:
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
	 *            id du niveau jou�
	 * @param p1
	 *            personnage souhait� par J1
	 * 
	 * @param p2
	 *            personnage souhait� par J2
	 * @param p3
	 *            personnage souhait� par J3
	 * @param p4
	 *            personnage souhait� par J4
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
