package fr.dungeontrouble.affichage;

import java.io.IOException;
import java.io.InputStream;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.View;

/**
 * Classe principale d'affichage h�rit�e par les diff�rents affichages
 * @author Valentin PORCHET
 *
 */
public abstract class Affichage implements Drawable {
	public static int LARGEUR = 800;
	public static int HAUTEUR = 600;
	
	protected View vue; // Vue de l'affichage en question

	/**
	 * Fonction permettant de charger une texture, renvoie une exception si probl�me
	 * @param path Chemin vers le fichier contenant la texture
	 * @return La texture charg�e
	 */
	public static Texture loadTexture(String path){
		Texture texture = new Texture(); // Instanciation d'une nouvelle texture
		        
		try {
			// On stocke dans un input stream l'image que l'on veut charger
			// (la m�thode utilis�e permet de charger depuis le .jar final)
			InputStream inputStream = texture.getClass().getResourceAsStream("/"+path);
			texture.loadFromStream(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texture; // Retour de la texture
	}
	
	/**
	 * Fonction permettant de charger une police et de g�rer les exceptions
	 * @param path Chemin vers le fichier de police
	 * @return Police sous le format objet Font
	 */
	public static Font loadFont(String path){
		Font font = new Font();
		try {
			// On stocke dans un input stream la police que l'on veut charger
			// (la m�thode utilis�e permet de charger depuis le .jar final)
			InputStream inputStream = font.getClass().getResourceAsStream("/"+path);
		    font.loadFromStream(inputStream);
		} catch(IOException ex) {
		    ex.printStackTrace();
		}
		return font;
	}
	
	// M�thode d'affichage de l'affichage, qui sera red�finie dans les classes filles
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		
	}
}
