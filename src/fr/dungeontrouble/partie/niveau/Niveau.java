package fr.dungeontrouble.partie.niveau;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import fr.dungeontrouble.partie.niveau.Generateur.TypeGenerateur;
import fr.dungeontrouble.partie.niveau.Porte.TypePorte;

/**
 * Classe regroupant toutes informations d'un niveau en cours. Est gérée par
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
	 * @param level Tableau d'entiers dècrivant la carte è charger
	 * @param objets HashMap contenant l'intégralité des objets
	 */
	public static void loadMap(String path, int[][] level, HashMap<Vector2f,Objet> objets) {

		String temp = new String(); // Chaine qui contiendra chaque ligne

		try {
			/*
			 * On utilise deux Scanner : un pour extraire chaque ligne et un
			 * autre pour les analyser
			 */
			// Scanner extracteur de lignes
			Scanner scannerLine = new Scanner(new File(path));

			// Scanner analyseur de lignes
			Scanner scannerTemp = new Scanner(temp);

			// On boucle sur chaque champ detecte
			int champ, compteur = 0;

			while (scannerLine.hasNextLine()) {
				temp = scannerLine.nextLine(); // Récupération de la ligne
				scannerTemp = new Scanner(temp); // Assignation au scanner
				scannerTemp.useDelimiter(","); // Délimitation : virgules
				boolean nouvellePorte = true;
				
				// Tant qu'il reste des entiers sur la ligne
				while (scannerTemp.hasNext()) {
					champ = scannerTemp.nextInt();

					// Utilisation du champ...
					
					// Entiers correspondant à des objets sur la map
					if ((champ == 11)||(champ == 12)||(champ == 13)||
							((champ >= 20)&&(champ <= 24))||(champ >= 26)){
						Objet obj;
						switch(champ){
							case 11:
//								Vector2f pos = new Vector2f(compteur / NBCOLONNES, compteur % NBCOLONNES);
//								// On a une nouvelle porte si il n'y a pas déjà de porte en haut ou à gauche
//								nouvellePorte =(!(
//									( // Si ce n'est pas en haut et qu'il y a une porte au dessus
//										(pos.y != 0)&&
//										(objets.containsKey(new Vector2f(pos.x, pos.y-1))&&
//										(objets.get(new Vector2f(pos.x, pos.y-1)) instanceof Porte))
//									)
//								||
//									( // Si ce n'est pas à gauche et qu'il y a une porte à gauche
//										(pos.x != 0)&&
//										(objets.containsKey(new Vector2f(pos.x-1, pos.y))&&
//										(objets.get(new Vector2f(pos.x-1, pos.y-1)) instanceof Porte))
//									)
//								));
								obj = new Porte(TypePorte.MorceauPorteHorizontal, nouvellePorte); 
								break;
							case 12:
								obj = new Generateur(TypeGenerateur.fantome);								
								break;
							case 13:
								obj = new Generateur(TypeGenerateur.gobelin);
								break;
							case 20:
								obj = new Porte(TypePorte.BoutPorteDroite, nouvellePorte);
								break;
							case 21:
								obj = new Porte(TypePorte.BoutPorteGauche, nouvellePorte);
								break;
							case 22:
								obj = new Porte(TypePorte.BoutPorteBas, nouvellePorte);
								break;
							case 23:
								obj = new Porte(TypePorte.BoutPorteHaut, nouvellePorte);
								break;
							case 24:
								obj = new Porte(TypePorte.MorceauPorteVertical, nouvellePorte);
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
			e.printStackTrace(); // On catch l'exception en cas de problème
		}
	}
	
	public Niveau(String path){
		niveau = new int[NBLIGNES][NBCOLONNES];
		objets = new HashMap<Vector2f,Objet>();
		
		// Chargement des textures des objets
		try {
			Objet.texture = new Texture();
			Objet.texture.loadFromFile(Paths.get("img/objects.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		loadMap(path,niveau,objets);
	}
	
	
}
