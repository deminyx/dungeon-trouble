package fr.dungeontrouble.affichage;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

/**
 * Classe concernant l'affichage des meilleurs score au lancement de la fenêtre
 * @author Valentin PORCHET
 *
 */
public class AffichageMeilleursScores extends Affichage {
	private Texture textureBackground;
	private Sprite background;
	
	public AffichageMeilleursScores(){		
		textureBackground = loadTexture("img/scores.png");		
		background = new Sprite(textureBackground);	
	}
	
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		target.draw(this.background, states);
	}
}
