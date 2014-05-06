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

import fr.dungeontrouble.partie.Partie;
import fr.dungeontrouble.partie.entite.Personnage;
import fr.dungeontrouble.partie.entite.Personnage.TypePersonnage;

/**
 * Classe concernant l'affichage des scores pendant une partie
 * @author Valentin PORCHET
 *
 */
public class AffichageScore extends Affichage {
	private static Vector2i TAILLE = new Vector2i(250,600); // Taille de la vue
	private Texture background; // Texture contenant le fond des scores (logo, nom des classes)
	private Texture clef; // Texture pour les clefs que possèdent les joueurs
	private Font scoresFont; // Police utilisée pour l'affichage des scores
	
	private Sprite sprite; // Sprite utilisant la texture "background"
	private HashMap<String, Text> scoresText; // Text à afficher pour les scores	
	private HashMap<String, Sprite> clefJoueur; // Tableau de sprites des clefs pour chaque joueur
	
	// Future fonction de mise à jour des données d'affichage prenant en paramètre des joueurs
	public void updateData(){
		// Maj du nombre de clés et des scores...
	}
	
	/**
	 * Constructeur par défaut de l'affichage des scores
	 */
	public AffichageScore(int nbJoueurs){
		
		if (nbJoueurs == 1){
			background = loadTexture("bgscore_"+Partie.getP1().getPerso().name()+".png");
		} else {
			background = loadTexture("bgscore.png");
		}
		
		sprite = new Sprite(background);
		sprite.setPosition(0, 0);
		scoresFont = loadFont("Finalnew.ttf");
		
		// Initialisation des textes des scores
		scoresText = new HashMap<String, Text>();
		
		if (nbJoueurs == 1){
			String characterName = Partie.getP1().getPerso().name();
			scoresText.put(characterName, new Text("1000",scoresFont,22));
			scoresText.get(characterName).setPosition(new Vector2f(190,300));
			switch(Partie.getP1().getPerso()){
				case elfe:
					scoresText.get(characterName).setColor(Color.GREEN);
					break;
				case guerrier:
					scoresText.get(characterName).setColor(Color.RED);
					break;
				case magicien:
					scoresText.get(characterName).setColor(Color.YELLOW);
					break;
				case valkyrie:
					scoresText.get(characterName).setColor(Color.CYAN);
					break;
				default:
					break;
			}
		} else {
			scoresText.put("guerrier", new Text("1000",scoresFont,22));
			scoresText.put("magicien", new Text("1000",scoresFont,22));
			scoresText.put("valkyrie", new Text("1000",scoresFont,22));
			scoresText.put("elfe", new Text("1000",scoresFont,22));
			
			// On applique à chaque score une position et une couleur
			scoresText.get("guerrier").setPosition(new Vector2f(190,300));
			scoresText.get("guerrier").setColor(Color.RED);
			
			scoresText.get("magicien").setPosition(new Vector2f(190,341));
			scoresText.get("magicien").setColor(Color.YELLOW);
			
			scoresText.get("valkyrie").setPosition(new Vector2f(190,382));
			scoresText.get("valkyrie").setColor(Color.CYAN);
			
			scoresText.get("elfe").setPosition(new Vector2f(190,423));
			scoresText.get("elfe").setColor(Color.GREEN);
		}
			
		
		// Initialisation des sprites contenant des clefs
		clef = loadTexture("clef.png");
		clef.setRepeated(true);
		
		clefJoueur = new HashMap<String, Sprite>();
		
		if (nbJoueurs == 1){
			String characterName = Partie.getP1().getPerso().name();
			clefJoueur.put(characterName, new Sprite(clef));
			clefJoueur.get(characterName).setPosition(new Vector2f(5,300));
			clefJoueur.get(characterName).setTextureRect(new IntRect(0,0,0,0)); // 0 clef
		} else {
			clefJoueur.put("guerrier", new Sprite(clef));
			clefJoueur.get("guerrier").setPosition(new Vector2f(5,300));
			clefJoueur.get("guerrier").setTextureRect(new IntRect(0,0,0,0)); // 0 clef
			
			clefJoueur.put("magicien", new Sprite(clef));
			clefJoueur.get("magicien").setPosition(new Vector2f(5,340));
			clefJoueur.get("magicien").setTextureRect(new IntRect(0,0,0,0)); // 0 clef
			
			clefJoueur.put("valkyrie", new Sprite(clef));
			clefJoueur.get("valkyrie").setPosition(new Vector2f(5,380));
			clefJoueur.get("valkyrie").setTextureRect(new IntRect(0,0,0,0)); // 0 clef
			
			clefJoueur.put("elfe", new Sprite(clef));
			clefJoueur.get("elfe").setPosition(new Vector2f(5,420));		
			clefJoueur.get("elfe").setTextureRect(new IntRect(0,0,0,0)); // 0 clef
		}
		
		vue = new View(new FloatRect(0, 0, TAILLE.x, TAILLE.y)); // On définit la taille de la vue
		vue.setViewport(new FloatRect(11/16.f, 0, 5/16.f, 1)); // On définit la zone où elle va s'afficher (coordonnées puis taille entre 0 et 1)
	}
	
	/**
	 * Méthode de mise à jour de l'affichage des scores et des clefs
	 */
	public void updateScores(){
		for (Personnage p : Partie.getPersonnages().values()){
			// Mise à jour des images de clefs
			clefJoueur.get(p.getPerso().name()).setTextureRect(new IntRect(0,0,12*p.getNbCles(),25));
			// Mise à jour des scores
			scoresText.get(p.getPerso().name()).setString(p.getScore()+"");
		}
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

