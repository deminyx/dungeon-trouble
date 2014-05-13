package fr.dungeontrouble.affichage;

import java.io.IOException;
import java.io.InputStream;

import org.jsfml.graphics.*;

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
}
