package fr.dungeontrouble.affichage;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import fr.dungeontrouble.partie.Partie;
import fr.dungeontrouble.partie.entite.Monstre;
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
				// On r�cup�re les coordonn�es du bloc � rajouter
				tempX = lvl[j][i] % 14;
				tempY = lvl[j][i] / 14;
				
				// On rajoute les quatres vertex correspondant aux coins de la case en cours,
				// associ�s � la texture correspondante
				array.add(new Vertex(new Vector2f(i*Niveau.SIZE,j*Niveau.SIZE), new Vector2f(tempX*Niveau.SIZE,tempY*Niveau.SIZE)));		
				array.add(new Vertex(new Vector2f((i+1)*Niveau.SIZE,j*Niveau.SIZE), new Vector2f((tempX+1)*Niveau.SIZE,tempY*Niveau.SIZE)));		
				array.add(new Vertex(new Vector2f((i+1)*Niveau.SIZE,(j+1)*Niveau.SIZE), new Vector2f((tempX+1)*Niveau.SIZE,(tempY+1)*Niveau.SIZE)));		
				array.add(new Vertex(new Vector2f(i*Niveau.SIZE,(j+1)*Niveau.SIZE), new Vector2f(tempX*Niveau.SIZE,(tempY+1)*Niveau.SIZE)));
			}
		}
		
		return array;
	}
	
	/**
	 * M�thode de mise � jour du centre de la vue
	 */
	public void updateView(){
		
		float x=0, y=0;
		
		// On calcule l'abscisse et l'ordonn�e moyenne des personnages
		// Et on stocke dans deux floats x et y
		for (Personnage p : Partie.getPersonnages().values()){
			x += p.getSprite().getPosition().x;
			y += p.getSprite().getPosition().y;
		}
		
		x /= Partie.getPersonnages().values().size();
		y /= Partie.getPersonnages().values().size();
		
		// Si la vue est proche du bord gauche, on fixe l'abscisse de la vue
		if (x < 275){
			x = 275;
		}
		// Sinon si elle est proche du bord droit, on fait la m�me chose
		else if (x > Niveau.NBCOLONNES*50 - 275){
			x = Niveau.NBCOLONNES*50 - 275;
		}
		
		// Si la vue est proche du bord haut, on fait la m�me chose pour l'ordonn�e
		if (y < 300){
			y = 300;
		}
		// Sinon si la vue est proche du bord bas, encore la m�me chose
		else if (y > Niveau.NBLIGNES*50 - 300){
			y = Niveau.NBLIGNES*50 - 300;
		}
	
		// On applique le nouveau centre
		this.vue.setCenter(x, y);	
	}
	
	
	/**
	 * Constructeur par d�faut de l'affichage du jeu (niveau+entit�s)
	 */
	@SuppressWarnings("static-access")
	public AffichageJeu(){
		// On charge le niveau dans un tableau de vertex
		this.levelArray = loadVertex(Partie.getNiveau().getNiveau());
		// On charge les textures du terrain
		this.terrain = loadTexture("terrain.png");
		
		// On cr�e une vue de 550px*600px pour l'affichage du jeu
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
		
		// Affichage des objets (porte, g�n�rateurs,...)
		for (Objet s : Partie.getNiveau().getObjets().values()){
			target.draw(s);
		}
		// Affichage des personnages
		for (Personnage p : Partie.getPersonnages().values()){
			target.draw(p);
		}
		// Affichage des monstres
		for (Monstre m : Partie.getMonstres().values()){
			target.draw(m);
		}
	}	
	
	/**
	 * M�thode qui permet d'obtenir le centre de la vue
	 * @return Coordonn�es du centre de la vue sous forme de deux float
	 */
	public Vector2f getCenter(){
		return this.vue.getCenter();
	}
}
