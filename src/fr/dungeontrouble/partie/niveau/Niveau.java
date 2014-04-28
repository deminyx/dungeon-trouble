package fr.dungeontrouble.partie.niveau;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

import org.jsfml.system.Vector2f;

import fr.dungeontrouble.affichage.Affichage;
import fr.dungeontrouble.partie.niveau.Generateur.TypeGenerateur;
import fr.dungeontrouble.partie.niveau.Porte.TypePorte;

/**
 * Classe regroupant toutes informations d'un niveau en cours. Est g�r�e par
 * Partie
 * 
 * @author Maxime BELLIER
 * @author Valentin PORCHET
 */

public class Niveau {
	public static int NBCOLONNES = 100; // Constante : nombre de colonnes de la										// map
	public static int NBLIGNES = 100; // Constante : nombre de lignes de la map
	public static int SIZE = 50; // Constante : taille des cases de la map
	
	private HashMap<Vector2f,Objet> objets; // HashMap des objets de la map
	private int[][] niveau; // Indices correspondant au niveau
	public enum IDNiveau {map1,map2,map3};
	private static IDNiveau idNiveau;
	
	public HashMap<Vector2f, Objet> getObjets() {
		return objets;
	}

	public int[][] getNiveau() {
		return niveau;
	}
	
	public static IDNiveau getIdNiveau() {
		return idNiveau;
	}

	/**
	 * Fonction permettant de charger une carte depuis un fichier
	 * @param path Chemin vers le fichier contenant la carte
	 * @param level Tableau d'entiers d�crivant la carte � charger
	 * @param objets HashMap contenant l'int�gralit� des objets
	 */
	public static void loadMap(String path, int[][] level, HashMap<Vector2f,Objet> objets) {

		String temp = new String(); // Chaine qui contiendra chaque ligne

		try {
			/*
			 * On utilise deux Scanner : un pour extraire chaque ligne et un
			 * autre pour les analyser
			 */
			// Scanner extracteur de lignes
			InputStream inputStream = temp.getClass().getResourceAsStream("/"+path);
			Scanner scannerLine = new Scanner(inputStream);

			// Scanner analyseur de lignes
			Scanner scannerTemp = new Scanner(temp);

			// On boucle sur chaque champ detecte
			int champ, compteur = 0;

			while (scannerLine.hasNextLine()) {
				temp = scannerLine.nextLine(); // R�cup�ration de la ligne
				scannerTemp = new Scanner(temp); // Assignation au scanner
				scannerTemp.useDelimiter(","); // D�limitation : virgules
				
				// Tant qu'il reste des entiers sur la ligne
				while (scannerTemp.hasNext()) {
					champ = scannerTemp.nextInt();

					// Utilisation du champ...
					
					// Entiers correspondant � des objets sur la map
					if ((champ == 11)||(champ == 12)||(champ == 13)||
							((champ >= 20)&&(champ <= 24))||(champ >= 26)){
						Objet obj;
						switch(champ){
							case 11:
								obj = assignPorte(compteur, objets, TypePorte.MorceauPorteHorizontal);								
								break;
							case 12:
								obj = new Generateur(TypeGenerateur.fantome);								
								break;
							case 13:
								obj = new Generateur(TypeGenerateur.gobelin);
								break;
							case 20:
								obj = assignPorte(compteur, objets, TypePorte.BoutPorteDroite);	
								break;
							case 21:
								obj = assignPorte(compteur, objets, TypePorte.BoutPorteGauche);
								break;
							case 22:
								obj = assignPorte(compteur, objets, TypePorte.BoutPorteBas);
								break;
							case 23:
								obj = assignPorte(compteur, objets,	TypePorte.BoutPorteHaut);
								break;
							case 24:
								obj = assignPorte(compteur, objets,	TypePorte.MorceauPorteVertical);
								break;
							case 26:
								obj = new Tresor();
								break;
							case 27:
								obj = new Cle();
								break;	
							default: // Pour la compilation
								obj = new Tresor();
								break;
						}
						
						obj.setPosition(new Vector2f(compteur / NBCOLONNES, compteur % NBCOLONNES));
						obj.getSprite().setPosition(new Vector2f(
								(float)((compteur % NBCOLONNES)*50), 
								(float)((compteur / NBCOLONNES)*50)));
						objets.put(obj.getPosition(),obj);
						
						// Si c'est une porte, alors en fonction de son type on a un certain bloc
						if ((obj instanceof Porte)&&(champ != 11)&&(champ != 24)){							
							level[compteur / NBCOLONNES][compteur % NBCOLONNES] = champ - 13;
						}
						else{
							level[compteur / NBCOLONNES][compteur % NBCOLONNES] = 0;
						}
					} else{
						level[compteur / NBCOLONNES][compteur % NBCOLONNES] = champ;
					}
					
					compteur++;
				}
			}

			// On ferme les Scanner
			scannerLine.close();
			scannerTemp.close();
		} catch (Exception e) {
			e.printStackTrace(); // On catch l'exception en cas de probl�me
		}
	}
	
	/**
	 * M�thode de v�rification de pr�sence de bout de porte sur une case au dessus ou � gauche
	 * @param nouvellePorte Bool�an que l'on met � true si on n'a pas trouv� de bout de porte
	 * @param idPorte Id de porte � laquelle on affecte l'id d'un bout de porte si trouv�
	 * @param compteur Compteur de cases dans l'algorithme de lecture de fichier contenant la carte
	 * @param objets HashMap des objets d�j� trouv�s sur la map
	 */
	public static int verifPorte(int compteur, HashMap<Vector2f,Objet> objets){		
		Vector2f pos = new Vector2f(compteur / NBCOLONNES, compteur % NBCOLONNES);
		Integer idPorte = -1;
		
		// On a une nouvelle porte si il n'y a pas d�j� de porte en haut ou � gauche
		if ( // Si ce on n'est pas en haut et qu'il y a une porte au dessus
				(pos.y != 0)&&
				(objets.containsKey(new Vector2f(pos.x, pos.y-1))&&
				(objets.get(new Vector2f(pos.x, pos.y-1)) instanceof Porte))
			){
			idPorte = ((Porte)(objets.get(new Vector2f(pos.x, pos.y-1)))).getIdPorteCourante();
		}
		else if( // Si ce on n'est pas � gauche et qu'il y a une porte � gauche
				(pos.x != 0)&&
				(objets.containsKey(new Vector2f(pos.x-1, pos.y))&&
				(objets.get(new Vector2f(pos.x-1, pos.y)) instanceof Porte))
			){
			idPorte = ((Porte)(objets.get(new Vector2f(pos.x-1, pos.y)))).getIdPorteCourante();
		}
		
		return idPorte;
	}
	
	/**
	 * M�thode qui, coupl�e � la v�rification de pr�sence de porte, appelle le constructeur de Porte ad�quat
	 * @param nouvellePorte Bool�an que l'on met � true si on n'a pas trouv� de bout de porte
	 * @param idPorte Id de porte � laquelle on affecte l'id d'un bout de porte si trouv�
	 * @param compteur Compteur de cases dans l'algorithme de lecture de fichier contenant la carte
	 * @param objets HashMap des objets d�j� trouv�s sur la map
	 * @param type Type du bout de Porte � cr�er
	 * @return Objet Porte qui sera cr��e
	 */
	public static Objet assignPorte(int compteur, HashMap<Vector2f,Objet> objets, TypePorte type){
		// Initialisation de la variable interne
		Integer idPorte = verifPorte(compteur, objets);

		/* Si on a trouv� que le bout actuel est sur une nouvelle
		porte, on g�n�re avec un nouvel ID */	
		if (idPorte == -1){
			return new Porte(type);
		}
		else{
			return new Porte(type, idPorte);
		}
	}
	
	public Niveau(String path){
		niveau = new int[NBLIGNES][NBCOLONNES];
		objets = new HashMap<Vector2f,Objet>();
		
		// Chargement des textures des objets
		Objet.texture = Affichage.loadTexture("objects.png");
		
		loadMap(path,niveau,objets);
	}
	
	
}
