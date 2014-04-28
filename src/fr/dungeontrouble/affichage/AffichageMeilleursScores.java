package fr.dungeontrouble.affichage;

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

/**
 * Classe concernant l'affichage des meilleurs score au lancement de la fen�tre
 * @author Valentin PORCHET
 *
 */
public class AffichageMeilleursScores extends Affichage {
	private Texture textureBackground;
	private Sprite background;
	private Font scoresFont;
	private LinkedHashMap<String, Integer> scores; // A voir si on laisse �a l�
	private Vector<Text> scoreNames;
	private Vector<Text> scoreValues;
	
	
	/**
	 * M�thode de mise � jour des scores affich�s en fonction des scores stock�s
	 */
	private void updateVisibleScores(){		
		int compteur = 0;
		
		// A l'entr�e dans cette m�thode, tous les scores sont tri�s		
		for(Entry<String, Integer> entry : scores.entrySet()) {
			scoreNames.add(new Text(entry.getKey(), scoresFont, 29));
			scoreValues.add(new Text(entry.getValue().toString(), scoresFont, 29));
			
			scoreNames.lastElement().setPosition(new Vector2f(200,110+compteur*50));
			scoreValues.lastElement().setPosition(new Vector2f(525,110+compteur*50));
			compteur++;
		}
	}
	
	public AffichageMeilleursScores(){		
		//scores = loadScores("highscores.txt"); A REVOIR DEPUIS MODIF MOTEUR JEU
		scoreNames = new Vector<Text>();
		scoreValues = new Vector<Text>();
		scoresFont = loadFont("Finalnew.ttf");
		updateVisibleScores(); // Ajoute les scores charg�s dans les listes de Text
		
		textureBackground = loadTexture("scores.png");		
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
