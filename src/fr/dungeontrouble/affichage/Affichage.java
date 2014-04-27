package fr.dungeontrouble.affichage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.Mouse.Button;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import fr.dungeontrouble.partie.niveau.Niveau;

/**
 * Classe principale d'affichage h�rit�e par les diff�rents affichages
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
	
	public Vector2f getCentreVue(){
		return vue.getCenter();
	}

	/**
	 * Fonction permettant de charger une texture, renvoie une exception si probl�me
	 * @param path Chemin vers le fichier contenant la texture
	 * @return La texture charg�e
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
	 * Fonction permettant de charger une police et de g�rer les exceptions
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
	 * M�thode pour d�placer la vue de x en abscisse et y en ordonn�e
	 * @param x D�placement en abscisse
	 * @param y D�placement en ordonn�e
	 */
	public void deplacerVue(float x, float y) {
		vue.move(x,y);		
	}	
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		
	}
	
	// Classe de test de l'affichage
	public static class Test {

		public static void main(String[] args) {
			
			Niveau niveau = new Niveau("maps/map1.txt");
			Affichage affJeu = new AffichageJeu(niveau);
			
			Affichage affScores = new AffichageScore();
			//Affichage affBestScores = new AffichageMeilleursScores();
			Affichage affChoix = new AffichageChoix();
			
			System.out.println("Chargement termin� !");
				
			RenderWindow window = new RenderWindow(new VideoMode(Affichage.LARGEUR,Affichage.HAUTEUR), "Dungeon Trouble",RenderWindow.CLOSE | RenderWindow.TITLEBAR);
			window.setVerticalSyncEnabled(true); // Activation de la synchronisation verticale
			window.setKeyRepeatEnabled(false); // D�sactivation de la r�p�tition des touches			
			Vector2i mousePosition = Mouse.getPosition(window); // A effacer apr�s les tests
		
			Texture text = loadTexture("img/spritestest.png");
			ArrayList<Sprite> persos = new ArrayList<Sprite>(4);
			persos.add(new Sprite());
			persos.add(new Sprite());
			persos.add(new Sprite());
			persos.add(new Sprite());
			
			for (Sprite p : persos){
				p.setTexture(text);
				p.setTextureRect(new IntRect(28,0,28,40));
				p.setPosition(new Vector2f((float)Math.random()*200, (float)Math.random()*200));
			}
			
			while (window.isOpen()) {			
				for(Event event : window.pollEvents()) {
					switch(event.type)
					{
						case CLOSED:
							window.close();
							break;
						
						 //Test de mouvement de la vue
						case MOUSE_MOVED: // Souris boug�e
							if (Mouse.isButtonPressed(Button.LEFT)||(Mouse.isButtonPressed(Button.RIGHT))) // D�placement sur la carte
							{
								Vector2f mousePos = window.mapPixelToCoords(mousePosition);
								Vector2f eventPosition = window.mapPixelToCoords(new Vector2i(event.asMouseEvent().position.x, event.asMouseEvent().position.y));

								Vector2f movement = new Vector2f(mousePos.x - eventPosition.x, mousePos.y - eventPosition.y);
								affJeu.deplacerVue(movement.x, movement.y);
							}
							break;
						
						case KEY_PRESSED:
							persos.get(0).move(-10,0);
							break;
						default:break;
					}
				}
				
				mousePosition = Mouse.getPosition(window); // Sauvegarde de la position de la souris
				
				// Mouvement des mariiiiios
				for (Sprite p : persos){
					p.move((int)(Math.random() * 3),(int)(Math.random() * 3));
				}
				
				// Mise � jour de la vue en cons�quence
				//((AffichageJeu) affJeu).updateView(persos);
				
				window.clear();
				
				window.draw(affJeu); // Dessin de la map
				
				for (Sprite p : persos){
					window.draw(p);
				}
				
				window.draw(affScores); // Dessin des scores
				
				//window.draw(affChoix);

				window.display();
			}
		}

	}
}
