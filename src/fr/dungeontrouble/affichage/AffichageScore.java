package fr.dungeontrouble.affichage;

import java.util.HashMap;
import java.util.Map.Entry;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * Classe concernant l'affichage des scores pendant une partie
 * @author Valentin PORCHET
 *
 */
public class AffichageScore extends Affichage {
	private static Vector2i TAILLE = new Vector2i(250,600); // Taille de la vue
	private Texture background; // Texture contenant le fond des scores (logo, nom des classes)
	private Texture clef; // Texture pour les clefs que poss�dent les joueurs
	private Font scoresFont; // Police utilis�e pour l'affichage des scores
	
	private Sprite sprite; // Sprite utilisant la texture "background"
	private HashMap<String, Text> scoresText; // Text � afficher pour les scores	
	private HashMap<String, Sprite> clefJoueur; // Tableau de sprites des clefs pour chaque joueur
	
	// Future fonction de mise � jour des donn�es d'affichage prenant en param�tre des joueurs
	public void updateData(){
		// Maj du nombre de cl�s et des scores...
	}
	
	/**
	 * Constructeur par d�faut de l'affichage des scores
	 */
	public AffichageScore(){
		background = loadTexture("bgscore.png");
		sprite = new Sprite(background);
		sprite.setPosition(0, 0);
		scoresFont = loadFont("Finalnew.ttf");
		
		// Initialisation des textes des scores
		scoresText = new HashMap<String, Text>();
		
		scoresText.put("Guerrier", new Text("999",scoresFont,22));
		scoresText.put("Magicien", new Text("1000",scoresFont,22));
		scoresText.put("Valkyrie", new Text("1000",scoresFont,22));
		scoresText.put("Elfe", new Text("1000",scoresFont,22));
		
		// On applique � chaque score une position et une couleur
		scoresText.get("Guerrier").setPosition(new Vector2f(190,300));
		scoresText.get("Guerrier").setColor(Color.RED);
		
		scoresText.get("Magicien").setPosition(new Vector2f(190,341));
		scoresText.get("Magicien").setColor(Color.YELLOW);
		
		scoresText.get("Valkyrie").setPosition(new Vector2f(190,382));
		scoresText.get("Valkyrie").setColor(Color.CYAN);
		
		scoresText.get("Elfe").setPosition(new Vector2f(190,423));
		scoresText.get("Elfe").setColor(Color.GREEN);
		
		// Initialisation des sprites contenant des clefs
		clef = loadTexture("clef.png");
		clef.setRepeated(true);
		
		clefJoueur = new HashMap<String, Sprite>();
		
		clefJoueur.put("Guerrier", new Sprite(clef));
		clefJoueur.get("Guerrier").setPosition(new Vector2f(5,300));
		clefJoueur.get("Guerrier").setTextureRect(new IntRect(0,0,4*12,25)); // 4 clefs
		
		clefJoueur.put("Magicien", new Sprite(clef));
		clefJoueur.get("Magicien").setPosition(new Vector2f(5,340));
		
		clefJoueur.put("Valkyrie", new Sprite(clef));
		clefJoueur.get("Valkyrie").setPosition(new Vector2f(5,380));
		clefJoueur.get("Valkyrie").setTextureRect(new IntRect(0,0,0,0)); // 0 clef
		
		clefJoueur.put("Elfe", new Sprite(clef));
		clefJoueur.get("Elfe").setPosition(new Vector2f(5,420));		
		
		vue = new View(new FloatRect(0, 0, TAILLE.x, TAILLE.y)); // On d�finit la taille de la vue
		vue.setViewport(new FloatRect(11/16.f, 0, 5/16.f, 1)); // On d�finit la zone o� elle va s'afficher (coordonn�es puis taille entre 0 et 1)
	}
	
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		target.setView(vue); // On applique la vue
		target.draw(this.sprite, states); // On affiche le fond avec le logo du jeu et les noms de classes
		
		// On affiche les clefs
		for(Entry<String, Sprite> entry : clefJoueur.entrySet()) {
			target.draw(entry.getValue());
		}
		
		// On affiche les scores
		for(Entry<String, Text> entry : scoresText.entrySet()) {
		    target.draw(entry.getValue());
		}
	}
}

