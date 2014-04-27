package fr.dungeontrouble.partie.niveau;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 * Classe mère de tout objet du jeu.
 * 
 * @author Maxime BELLIER
 * 
 */
public abstract class Objet implements Drawable {

	protected Sprite sprite;
	protected static Texture texture;
	protected Vector2f position;

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public static Texture getTexture() {
		return texture;
	}

	public static void setTexture(Texture texture) {
		Objet.texture = texture;
	}

	@Override
	public void draw(RenderTarget target, RenderStates states) {
		target.draw(this.sprite);
	}
}
