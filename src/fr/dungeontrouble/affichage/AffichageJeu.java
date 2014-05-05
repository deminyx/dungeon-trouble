package fr.dungeontrouble.affichage;

import java.util.ArrayList;

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

import fr.dungeontrouble.partie.Partie;
import fr.dungeontrouble.partie.entite.Personnage;
import fr.dungeontrouble.partie.niveau.Niveau;
import fr.dungeontrouble.partie.niveau.Objet;

/**
 * Classe concernant l'affichage du terrain de jeu
 * @author Valentin PORCHET
 *
 */
public class AffichageJeu extends Affichage {
	
	private VertexArray levelArray; // VertexArray du terrain
	private Texture terrain; // Texture du terrain
	private Niveau niveau; // R�f�rence vers le niveau actuel
	
	/**
	 * Fonction permettant d'initialiser les vertex pour l'affichage du niveau
	 * @param lvl Tableau d'entiers correspondant au niveau
	 * @return Tableau de Vertex du niveau charg�
	 */
	public static VertexArray loadVertex(int[][] lvl){
		// Instanciation de l'array � retourner
		VertexArray array = new VertexArray(PrimitiveType.QUADS);
		int tempX, tempY;
		
		// Pour chaque chaque case, on rajoute 4 vertex correspond aux 4 coins,
		// Avec les coordonn�es de la texture correspondante
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
	
	// Fonction test pour mise � jour de la place de la vue
	public void updateView(){
		float x=0, y=0;
		
		for (Personnage p : Partie.getPersonnages().values()){
			x += p.getSprite().getPosition().x;
			y += p.getSprite().getPosition().y;
		}
		
		x /= Partie.getPersonnages().values().size();
		y /= Partie.getPersonnages().values().size();
		
		this.vue.setCenter(x, y);
	}
	
	
	/**
	 * Constructeur par d�faut de l'affichage du jeu
	 * @param niv Le niveau courant
	 */
	@SuppressWarnings("static-access")
	public AffichageJeu(){
		this.levelArray = loadVertex(Partie.getNiveau().getNiveau());
		this.terrain = loadTexture("terrain.png");
		
		this.vue = new View(new FloatRect(0, 0, 11*Niveau.SIZE, HAUTEUR)); // On d�finit la taille de la vue
		this.vue.setViewport(new FloatRect(0, 0, 11/16.f, 1)); // On d�finit la zone o� elle va s'afficher
	}	

	/**
	 * Fonction d'affichage du jeu
	 */
	@SuppressWarnings("static-access")
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		target.setView(vue); // On applique la vue
		RenderStates newStates = new RenderStates(this.terrain); // On cr�e un nouvel �tat de rendu prenant en compte les textures
		target.draw(this.levelArray, newStates); // On affiche le tableau de vertex associ� au nouvel �tat de rendu
		for (Objet s : Partie.getNiveau().getObjets().values()){
			target.draw(s);
		}
		for (Personnage p : Partie.getPersonnages().values()){
			target.draw(p);
		}
	}	
}
