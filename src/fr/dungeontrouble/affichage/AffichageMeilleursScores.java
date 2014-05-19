package fr.dungeontrouble.affichage;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Vector;

import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import fr.dungeontrouble.etatmoteurjeu.GestionScore;
import fr.dungeontrouble.etatmoteurjeu.Score;

/**
 * Classe concernant l'affichage des meilleurs score au lancement de la fenêtre
 * @author Valentin PORCHET
 *
 */
public class AffichageMeilleursScores extends Affichage {
	private Texture textureBackground; // Texture de background des meilleurs scores
	private Sprite background; // Sprite sur lequel on applique la texture
	private Font scoresFont; // Police pour les scores
	private LinkedHashMap<String, Score> scores; // Hashmap des scores (si présent)
	boolean scoreExists;
	private Text pasDeScore; // Texte qui s'affichera s'il n'y a aucun meilleur score
	private Vector<Text> scoreNames; // Vecteur des textes des noms des joueurs
	private Vector<Text> scoreClass; // Vecteur des textes des classes des joueurs
	private Vector<Text> scoreValues; // Vecteur des textes des scores des joueurs
	
	
	/**
	 * Méthode de mise à jour des scores affichés en fonction des scores stockés
	 */
	private void updateVisibleScores(){		
		int compteur = 0;
		
		// A l'entrée dans cette méthode, tous les scores sont triés		
		for(Entry<String, Score> entry : scores.entrySet()) {
			scoreNames.add(new Text(entry.getKey(), scoresFont, 29)); // On rajoute le pseudo
			scoreClass.add(new Text(entry.getValue().t.name(), scoresFont, 29));
			scoreValues.add(new Text(String.valueOf(entry.getValue().scoreFinal), scoresFont, 29)); // On rajoute le score
			
			// On leur donne leur proposition
			scoreNames.lastElement().setPosition(new Vector2f(100,110+compteur*50));
			scoreClass.lastElement().setPosition(new Vector2f(380,110+compteur*50));
			scoreValues.lastElement().setPosition(new Vector2f(580,110+compteur*50));
			compteur++;
		}
	}
	
	/**
	 * Constructeur de l'affichage des meilleurs scores
	 * @param filename Nom du fichier contenant les meilleurs scores
	 */
	public AffichageMeilleursScores(String filename){
		scoresFont = loadFont("Finalnew.ttf");
		
		// Si le fichier des scores existe, on le charge
		if (scoreExists = new File(filename).exists()){
			// Cette méthode sera déplacée dans la gestion des scores
			scores = GestionScore.recupererScores(filename);
			scoreNames = new Vector<Text>();
			scoreValues = new Vector<Text>();
			scoreClass = new Vector<Text>();
			updateVisibleScores(); // Ajoute les scores chargés dans les listes de Text
		} else {
			pasDeScore = new Text("Aucun meilleur score !", scoresFont, 50);
			pasDeScore.setPosition(new Vector2f(150,300));
		}
		
		textureBackground = loadTexture("scores.png");		
		background = new Sprite(textureBackground);	
	}
	
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		target.setView(target.getDefaultView()); // On remet la vue par défaut
		target.draw(this.background, states); // On affiche le background
		
		if (scoreExists){
			// Affichage des pseudonymes
			for (Text t : scoreNames){
				target.draw(t);
			}
			
			// Affichage des classes
			for (Text t : scoreClass){
				target.draw(t);
			}
			
			// Affichage des scores		
			for (Text t : scoreValues){
				target.draw(t);
			}
		} else {
			target.draw(pasDeScore);
		}
		
	}
}
