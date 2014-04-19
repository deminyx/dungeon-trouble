package fr.dungeontrouble.affichage;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public class AffichageChoix extends Affichage {
	private Texture background;
	private Sprite sprite;
	
	public AffichageChoix(){
		background = loadTexture("img/bgchoix.png");
		sprite = new Sprite(background);
	}
	
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		target.draw(this.sprite, states);
	}
}
