package fr.dungeontrouble.affichage;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.Vertex;
import org.jsfml.graphics.VertexArray;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

/**
 * Classe concernant l'affichage du terrain de jeu
 * @author Valentin PORCHET
 *
 */
public class AffichageJeu extends Affichage {
	public static int NBCOLONNES = 100; // Constante : nombre de colonnes de la map
	public static int NBLIGNES = 100; // Constante : nombre de lignes de la map
	public static int SIZE = 50; // Constante : taille des cases de la map
	
	private int[][] level; // A prendre comme référence je pense, dans la classe Map
	
	private VertexArray levelArray;
	private Texture terrain;
	
	////////////////////////////////////////
	// A BOUGER DANS LA FUTURE CLASSE MAP //
	////////////////////////////////////////
	
	/**
	 * Fonction permettant de charger une carte depuis un fichier
	 * @param path Chemin vers le fichier contenant la carte
	 * @return Tableau d'entiers décrivant la carte à charger
	 */	
	public static int[][] loadMap(String path){
		
		// On instancie le tableau qui contiendra la map
		int[][] lvl = new int[NBLIGNES][NBCOLONNES];
		
		try{			
			// L'expression régulière qui délimite les champs
			Scanner scanner = new Scanner(new File(path));
			scanner.useDelimiter(Pattern.compile("\\s*[\\,\r]\\s*"));
			
			// On boucle sur chaque champ detecté
			int champ, compteur=0;
			while (scanner.hasNext()) {
				// Si le champ n'est pas un entier, une exception de type InputMismatchException sera levée
				champ = scanner.nextInt();
						    			   
				// Utilisation du champ...
				lvl[compteur/NBCOLONNES][compteur%NBCOLONNES] = champ;
				compteur++;
			}
			
			// On ferme le scanner
			scanner.close();
		}
		catch (Exception e){
			e.printStackTrace(); // On catch l'exception dans le bas d'un problème
		}
		
		return lvl;
	}
	
	////////////////////////////////////////
	////////////////////////////////////////
	
	/**
	 * Fonction permettant d'initialiser les vertex pour l'affichage du niveau
	 * @param lvl Tableau d'entiers correspondant au niveau
	 * @return Tableau de Vertex du niveau chargé
	 */
	public static VertexArray loadVertex(int[][] lvl){
		// Instanciation de l'array à retourner
		VertexArray array = new VertexArray(PrimitiveType.QUADS);
		int tempX, tempY;
		
		// Pour chaque chaque case, on rajoute 4 vertex correspond aux 4 coins,
		// Avec les coordonnées de la texture correspondante
		for (int i=0; i < NBCOLONNES; i++)
		{
			for (int j=0; j < NBLIGNES; j++)
			{
				tempX = lvl[j][i] % 14;
				tempY = lvl[j][i] / 14;
				array.add(new Vertex(new Vector2f(i*SIZE,j*SIZE), new Vector2f(tempX*SIZE,tempY*SIZE)));		
				array.add(new Vertex(new Vector2f((i+1)*SIZE,j*SIZE), new Vector2f((tempX+1)*SIZE,tempY*SIZE)));		
				array.add(new Vertex(new Vector2f((i+1)*SIZE,(j+1)*SIZE), new Vector2f((tempX+1)*SIZE,(tempY+1)*SIZE)));		
				array.add(new Vertex(new Vector2f(i*SIZE,(j+1)*SIZE), new Vector2f(tempX*SIZE,(tempY+1)*SIZE)));
			}
		}
		
		return array;
	}
	
	
	/**
	 * Constructeur par défaut de l'affichage du jeu
	 */
	public AffichageJeu(){
		level = loadMap("maps/map.txt");
		levelArray = loadVertex(level);		
		terrain = loadTexture("img/terrain.png");	
		
		vue = new View(new FloatRect(0, 0, 11*SIZE, HAUTEUR)); // On définit la taille de la vue
		vue.setViewport(new FloatRect(0, 0, 11/16.f, 1)); // On définit la zone où elle va s'afficher
	}	

	/**
	 * Fonction d'affichage du jeu
	 */
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		target.setView(vue); // On applique la vue
		RenderStates newStates = new RenderStates(this.terrain); // On crée un nouvel état de rendu prenant en compte les textures
		target.draw(this.levelArray, newStates); // On affiche le tableau de vertex associé au nouvel état de rendu
	}	
}
