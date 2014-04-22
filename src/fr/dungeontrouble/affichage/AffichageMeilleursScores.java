package fr.dungeontrouble.affichage;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Vector;

import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 * Classe concernant l'affichage des meilleurs score au lancement de la fenêtre
 * @author Valentin PORCHET
 *
 */
public class AffichageMeilleursScores extends Affichage {
	private Texture textureBackground;
	private Sprite background;
	private Font scoresFont;
	private LinkedHashMap<String, Integer> scores; // A voir si on laisse éa lé
	private Vector<Text> scoreNames;
	private Vector<Text> scoreValues;
	
	/**
	 * Méthode de chargement des meilleurs scores à partir d'un fichier
	 * @param path Chemin vers le fichier contenant les meilleurs scores
	 * @return HashMap de la forme Joueur -> Score
	 */
	private static LinkedHashMap<String, Integer> loadScores(String path){
		LinkedHashMap<String,Integer> highscores = new LinkedHashMap<String,Integer>();
		String temp = new String(); // Chaine temporaire qui contiendra chaque ligne
		
		try{			
			/* On utilise deux Scanner : un pour extraire chaque ligne
			 * et un autre pour les analyser  
			 */
			
			// Scanner extracteur de lignes
			Scanner scannerLine = new Scanner(new File(path));
			
			// Scanner analyseur de lignes
			Scanner scannerTemp = new Scanner(temp);
			
			while (scannerLine.hasNextLine()){
				temp = scannerLine.nextLine();	// Récupération de la ligne	
				scannerTemp = new Scanner(temp); // Assignation au scanner
				highscores.put(scannerTemp.next(), scannerTemp.nextInt()); // Rajout des scores
			}
			
			// On ferme les Scanner
			scannerLine.close();
			scannerTemp.close();
		}
		catch (Exception e){
			e.printStackTrace(); // On catch l'exception en cas de probléme
		}
		
		return highscores;
	}
	
	/**
	 * Méthode de mise à jour des scores affichés en fonction des scores stockés
	 */
	private void updateVisibleScores(){		
		int compteur = 0;
		
		// A l'entrée dans cette méthode, tous les scores sont triés		
		for(Entry<String, Integer> entry : scores.entrySet()) {
			scoreNames.add(new Text(entry.getKey(), scoresFont, 29));
			scoreValues.add(new Text(entry.getValue().toString(), scoresFont, 29));
			
			scoreNames.lastElement().setPosition(new Vector2f(200,110+compteur*50));
			scoreValues.lastElement().setPosition(new Vector2f(525,110+compteur*50));
			compteur++;
		}
	}
	
	public AffichageMeilleursScores(){		
		scores = loadScores("highscores.txt");
		scoreNames = new Vector<Text>();
		scoreValues = new Vector<Text>();
		scoresFont = loadFont("font/Finalnew.ttf");
		updateVisibleScores(); // Ajoute les scores chargés dans les listes de Text
		
		textureBackground = loadTexture("img/scores.png");		
		background = new Sprite(textureBackground);	
	}
	
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		target.setView(target.getDefaultView());
		target.draw(this.background, states);
		
		for (Text t : scoreNames){
			target.draw(t);
		}
		
		for (Text t : scoreValues){
			target.draw(t);
		}
	}
}
