package fr.dungeontrouble.partie;

import java.util.HashMap;

import org.jsfml.system.Vector2i;

import fr.dungeontrouble.partie.entite.Monstre;
import fr.dungeontrouble.partie.entite.Personnage;
import fr.dungeontrouble.partie.entite.Personnage.TypePersonnage;
import fr.dungeontrouble.partie.niveau.Niveau;

/**
 * Module Partie Classe principale d�crivant l'ensemble des param�tres et
 * m�thodes d'une partie de Dungeon Trouble
 * 
 * @author Maxime BELLIER
 * 
 */
public class Partie {

	private static Niveau niveau;
	private static HashMap<Vector2i, Personnage> personnages;
	private static Personnage p1;
	private static Personnage p2;
	private static Personnage p3;
	private static Personnage p4;
	private static HashMap<Vector2i, Monstre> monstres;

	/**
	 * Constructeur pour partie 1 joueur
	 * 
	 * @param idmap
	 *            id du niveau jou�
	 * @param p
	 *            personnage souhait� par J1
	 * @return 
	 */
	public static void InitPartie(Niveau.IDNiveau idmap, TypePersonnage p) {

		switch (idmap) {

		case map1:
			niveau = new Niveau("map1.txt");
			// AJOUTER LE CHOIX DU PERSONNAGE
			personnages = new HashMap<Vector2i, Personnage>();
			personnages.put(new Vector2i(5, 5), new Personnage(p,new Vector2i(5, 5)));
			setMonstres(new HashMap<Vector2i, Monstre>());
			p1=personnages.get(new Vector2i(5, 5));
			break;
		case map2:
			niveau = new Niveau("map2.txt");
			// AJOUTER LE CHOIX DU PERSONNAGE
			personnages = new HashMap<Vector2i, Personnage>();
			personnages.put(new Vector2i(16, 5), new Personnage(p,new Vector2i(16, 5)));
			setMonstres(new HashMap<Vector2i, Monstre>());
			p1=personnages.get(new Vector2i(16, 5));
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
	 *            id du niveau jou�
	 * @param p1
	 *            personnage souhait� par J1
	 * @param p2
	 *            personnage souhait� par J2
	 * @param p3
	 *            personnage souhait� par J3
	 * @param p4
	 *            personnage souhait� par J4
	 */
	public static void InitPartie(Niveau.IDNiveau idmap) {

		switch (idmap) {

		case map1:
			niveau = new Niveau("map1.txt");
			// AJOUTER LE CHOIX DU PERSONNAGE
			personnages = new HashMap<Vector2i, Personnage>();
			personnages.put(new Vector2i(5, 5), new Personnage(TypePersonnage.guerrier,new Vector2i(5, 5)));
			p1=personnages.get(new Vector2i(5, 5));
			personnages.put(new Vector2i(5, 6), new Personnage(TypePersonnage.elfe,new Vector2i(5, 6)));
			p2=personnages.get(new Vector2i(5, 6));
			personnages.put(new Vector2i(6, 5), new Personnage(TypePersonnage.magicien,new Vector2i(6, 5)));
			p3=personnages.get(new Vector2i(6, 5));
			personnages.put(new Vector2i(6, 6), new Personnage(TypePersonnage.valkyrie,new Vector2i(6, 6)));
			p4=personnages.get(new Vector2i(6, 6));
			setMonstres(new HashMap<Vector2i, Monstre>());
			break;
		case map2:
			niveau = new Niveau("map2.txt");
			// AJOUTER LE CHOIX DU PERSONNAGE
			personnages = new HashMap<Vector2i, Personnage>();
			personnages.put(new Vector2i(16, 5), new Personnage(TypePersonnage.guerrier,new Vector2i(16, 5)));
			p1=personnages.get(new Vector2i(16, 5));
			personnages.put(new Vector2i(16, 6), new Personnage(TypePersonnage.elfe,new Vector2i(16, 6)));
			p2=personnages.get(new Vector2i(16, 6));
			personnages.put(new Vector2i(16, 7), new Personnage(TypePersonnage.magicien,new Vector2i(16, 7)));
			p3=personnages.get(new Vector2i(16, 7));
			personnages.put(new Vector2i(16, 8), new Personnage(TypePersonnage.valkyrie,new Vector2i(16, 8)));
			p4=personnages.get(new Vector2i(16, 8));
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

	public static Personnage getP1() {
		return p1;
	}

	public static void setP1(Personnage p1) {
		Partie.p1 = p1;
	}

	public static Personnage getP2() {
		return p2;
	}

	public static void setP2(Personnage p2) {
		Partie.p2 = p2;
	}

	public static Personnage getP3() {
		return p3;
	}

	public static void setP3(Personnage p3) {
		Partie.p3 = p3;
	}

	public static Personnage getP4() {
		return p4;
	}

	public static void setP4(Personnage p4) {
		Partie.p4 = p4;
	}


}
