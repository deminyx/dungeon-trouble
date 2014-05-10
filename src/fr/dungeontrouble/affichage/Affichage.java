package fr.dungeontrouble.affichage;

import java.io.IOException;
import java.io.InputStream;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.View;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import fr.dungeontrouble.etatmoteurjeu.EtatJeu;
import fr.dungeontrouble.evenement.ActionEvent;
import fr.dungeontrouble.evenement.MoveEvent;
import fr.dungeontrouble.partie.Partie;
import fr.dungeontrouble.partie.entite.Monstre;
import fr.dungeontrouble.partie.entite.Personnage;
import fr.dungeontrouble.partie.niveau.Generateur;
import fr.dungeontrouble.partie.niveau.Niveau;
import fr.dungeontrouble.partie.niveau.Objet;

/**
 * Classe principale d'affichage héritée par les différents affichages
 * @author Valentin PORCHET
 *
 */
public abstract class Affichage implements Drawable {
	public static int LARGEUR = 800;
	public static int HAUTEUR = 600;
	
	protected View vue; // Vue de l'affichage en question

	/**
	 * Fonction permettant de charger une texture, renvoie une exception si probléme
	 * @param path Chemin vers le fichier contenant la texture
	 * @return La texture chargée
	 */
	public static Texture loadTexture(String path){
		Texture texture = new Texture(); // Instanciation d'une nouvelle texture
		        
		try {
			// On stocke dans un input stream l'image que l'on veut charger
			// (la méthode utilisée permet de charger depuis le .jar final)
			InputStream inputStream = texture.getClass().getResourceAsStream("/"+path);
			texture.loadFromStream(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texture; // Retour de la texture
	}
	
	/**
	 * Fonction permettant de charger une police et de gérer les exceptions
	 * @param path Chemin vers le fichier de police
	 * @return Police sous le format objet Font
	 */
	public static Font loadFont(String path){
		Font font = new Font();
		try {
			// On stocke dans un input stream la police que l'on veut charger
			// (la méthode utilisée permet de charger depuis le .jar final)
			InputStream inputStream = font.getClass().getResourceAsStream("/"+path);
		    font.loadFromStream(inputStream);
		} catch(IOException ex) {
		    ex.printStackTrace();
		}
		return font;
	}
	
	// Méthode d'affichage de l'affichage, qui sera redéfinie dans les classes filles
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		
	}
	
	// Classe de test de l'affichage
		public static class Test {

			public static void main(String[] args) {
				
				EtatJeu etat = new EtatJeu(1);
				Affichage affJeu = new AffichageJeu();
				Affichage affScores = new AffichageScore(1);
				Clock clock = new Clock();
				Personnage.init();
				//Affichage affBestScores = new AffichageMeilleursScores();
				//Affichage affChoix = new AffichageChoix();
				
				//System.out.println("Chargement terminé !");
					
				RenderWindow window = new RenderWindow(new VideoMode(Affichage.LARGEUR,Affichage.HAUTEUR), "Dungeon Trouble",RenderWindow.CLOSE | RenderWindow.TITLEBAR);
				window.setVerticalSyncEnabled(true); // Activation de la synchronisation verticale
				window.setKeyRepeatEnabled(false); // Désactivation de la répétition des touches			
				
				while (window.isOpen()) {			
					Time timeElapsed = clock.restart();
					
					for(Event event : window.pollEvents()) {
						switch(event.type)
						{
							case CLOSED:
								window.close();
								break;		
							
							case KEY_PRESSED:
								ActionEvent.getAction1J();
//								if (Keyboard.isKeyPressed(Key.B))
//									Partie.getP1().setNbCles(Partie.getP1().getNbCles()+1);
//								if (Keyboard.isKeyPressed(Key.A))
//									Partie.getP1().setScore(Partie.getP1().getScore()-1);
								
								break;
							
							default:break;
						}
					}
					
					MoveEvent.getMove1J(timeElapsed);
					
//					if (timeElapsed.asSeconds() < 1){
//						for (Monstre m : Partie.getMonstres().values()){					
//							m.seDeplacerVersCible(timeElapsed);
//						}
//					}
					
					if (Generateur.nbMonstres < 100){
						for (Objet o : Niveau.getObjets().values()){
							if ((o instanceof Generateur)&&(Generateur.nbMonstres < 100)){
								((Generateur)o).genererMonstres();
							}
						}
					}	
					
					// Mise à jour de la vue en conséquence
					for (Personnage p : Partie.getPersonnages().values()){
						p.bougerArmes(timeElapsed, ((AffichageJeu)affJeu).getCenter());
						p.verifierCollisionArmes(timeElapsed);
					}
					((AffichageJeu) affJeu).updateView();
					
					// A appeler après chaque modif de score et de cles plutôt que tout le temps
					((AffichageScore) affScores).updateScores();				
					window.clear();
					
					window.draw(affJeu); // Dessin de la map
					window.draw(affScores); // Dessin des scores				

					window.display();
				}
			}
		}
}
