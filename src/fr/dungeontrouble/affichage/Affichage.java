package fr.dungeontrouble.affichage;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.Mouse.Button;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

/**
 * Classe principale d'affichage héritée par les différents affichages
 * @author Valentin PORCHET
 *
 */
public abstract class Affichage implements Drawable {
	public static int LARGEUR = 800;
	public static int HAUTEUR = 600;
	
	protected View vue; // Vue de l'affichage en question

	// Fonction temporaire pour les tests
	public View getVue() {
		return vue;
	}

	/**
	 * Fonction permettant de charger une texture, renvoie une exception si problème
	 * @param path Chemin vers le fichier contenant la texture
	 * @return La texture chargée
	 */
	public static Texture loadTexture(String path){
		Texture texture = new Texture();
		try {
			texture.loadFromFile(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texture;
	}
	
	/**
	 * Fonction permettant de charger une police et de gérer les exceptions
	 * @param path Chemin vers le fichier de police
	 * @return Police sous le format objet Font
	 */
	public static Font loadFont(String path){
		Font font = new Font();
		try {
		    font.loadFromFile(Paths.get(path));
		} catch(IOException ex) {
		    ex.printStackTrace();
		}
		return font;
	}
	
	/**
	 * Méthode pour déplacer la vue de x en abscisse et y en ordonnée
	 * @param x Déplacement en abscisse
	 * @param y Déplacement en ordonnée
	 */
	public void deplacerVue(float x, float y) {
		vue.move(x,y);		
	}	
	
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		
	}
	
	// Classe de test de l'affichage
	public static class Test {

		public static void main(String[] args) {
			
			Affichage affJeu = new AffichageJeu();
			Affichage affScores = new AffichageScore();
			Affichage affBestScores = new AffichageMeilleursScores();
			//Affichage affChoix = new AffichageChoix();
			
			System.out.println("Chargement terminé !");
			
			RenderWindow window = new RenderWindow(new VideoMode(Affichage.LARGEUR,Affichage.HAUTEUR), "Dungeon Trouble",RenderWindow.CLOSE | RenderWindow.TITLEBAR);
			window.setVerticalSyncEnabled(true); // Activation de la synchronisation verticale
			window.setKeyRepeatEnabled(false); // Désactivation de la répétition des touches
			
			Vector2i mousePosition = Mouse.getPosition(window);
		
			while (window.isOpen()) {				
				for(Event event : window.pollEvents()) {
					switch(event.type)
					{
						case CLOSED:
							window.close();
							break;
						
						 //Test de mouvement de la vue
						case MOUSE_MOVED: // Souris bougée
							if (Mouse.isButtonPressed(Button.MIDDLE)) // Déplacement sur la carte
							{
								Vector2f mousePos = window.mapPixelToCoords(mousePosition);
								Vector2f eventPosition = window.mapPixelToCoords(new Vector2i(event.asMouseEvent().position.x, event.asMouseEvent().position.y));

								Vector2f movement = new Vector2f(mousePos.x - eventPosition.x, mousePos.y - eventPosition.y);
								affJeu.deplacerVue(movement.x, movement.y);
							}
							break;
						default:break;
					}
				}
				
				mousePosition = Mouse.getPosition(window); // Sauvegarde de la position de la souris
				
				window.clear();
				
				window.draw(affJeu); // Dessin de la map
				window.draw(affScores); // Dessin des scores
				window.draw(affBestScores); // Dessin des meilleurs scores
				//window.draw(affChoix);

				window.display();
			}
		}

	}
}
