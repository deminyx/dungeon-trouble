package fr.dungeontrouble.affichage;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.Vertex;
import org.jsfml.graphics.VertexArray;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

import fr.dungeontrouble.partie.niveau.Niveau;

/**
 * Classe concernant l'affichage du terrain de jeu
 * @author Valentin PORCHET
 *
 */
public class AffichageJeu extends Affichage {
	
	private VertexArray levelArray;
	private Texture terrain;
	
	/**
	 * Fonction permettant d'initialiser les vertex pour l'affichage du niveau
	 * @param lvl Tableau d'entiers correspondant au niveau
	 * @return Tableau de Vertex du niveau chargè
	 */
	public static VertexArray loadVertex(int[][] lvl){
		// Instanciation de l'array à retourner
		VertexArray array = new VertexArray(PrimitiveType.QUADS);
		int tempX, tempY;
		
		// Pour chaque chaque case, on rajoute 4 vertex correspond aux 4 coins,
		// Avec les coordonnèes de la texture correspondante
		for (int i=0; i < Niveau.NBCOLONNES; i++)
		{
			for (int j=0; j < Niveau.NBLIGNES; j++)
			{
				tempX = lvl[j][i] % 14;
				tempY = lvl[j][i] / 14;
				array.add(new Vertex(new Vector2f(i*Niveau.SIZE,j*Niveau.SIZE), new Vector2f(tempX*Niveau.SIZE,tempY*Niveau.SIZE)));		
				array.add(new Vertex(new Vector2f((i+1)*Niveau.SIZE,j*Niveau.SIZE), new Vector2f((tempX+1)*Niveau.SIZE,tempY*Niveau.SIZE)));		
				array.add(new Vertex(new Vector2f((i+1)*Niveau.SIZE,(j+1)*Niveau.SIZE), new Vector2f((tempX+1)*Niveau.SIZE,(tempY+1)*Niveau.SIZE)));		
				array.add(new Vertex(new Vector2f(i*Niveau.SIZE,(j+1)*Niveau.SIZE), new Vector2f(tempX*Niveau.SIZE,(tempY+1)*Niveau.SIZE)));
			}
		}
		
		return array;
	}
	
	public void updateView(ArrayList<Sprite> persos){
		float x=0, y=0;
		
		for (Sprite p : persos){
			x += p.getPosition().x;
			y += p.getPosition().y;
		}
		
		x /= persos.size();
		y /= persos.size();
		
		this.vue.setCenter(x, y);
	}
	
	
	/**
	 * Constructeur par dèfaut de l'affichage du jeu
	 */
	public AffichageJeu(Niveau niv){
		levelArray = loadVertex(niv.getNiveau());
		terrain = loadTexture("img/terrain.png");
		
		vue = new View(new FloatRect(0, 0, 11*Niveau.SIZE, HAUTEUR)); // On dèfinit la taille de la vue
		vue.setViewport(new FloatRect(0, 0, 11/16.f, 1)); // On dèfinit la zone oè elle va s'afficher
	}	

	/**
	 * Fonction d'affichage du jeu
	 */
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		target.setView(vue); // On applique la vue
		RenderStates newStates = new RenderStates(this.terrain); // On crèe un nouvel état de rendu prenant en compte les textures
		target.draw(this.levelArray, newStates); // On affiche le tableau de vertex associé au nouvel état de rendu
	}	
}
