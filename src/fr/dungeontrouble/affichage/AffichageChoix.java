package fr.dungeontrouble.affichage;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

/**
 * Classe d'affichage de choix d'un personnage en mode 1 joueur
 * @author Valentin PORCHET
 *
 */
public class AffichageChoix extends Affichage {
	private Texture background;
	private Sprite sprite;
	
	/**
	 * Constructeur par défaut de l'affichage de choix de personnage en mode 1J
	 */
	public AffichageChoix(){
		background = loadTexture("bgchoix.png");
		sprite = new Sprite(background);
	}
	
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		target.setView(target.getDefaultView()); // Remise de la vue par défaut
		target.draw(this.sprite, states); // Affichage de l'image
	}
}
