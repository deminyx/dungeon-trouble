package fr.dungeontrouble.partie.entite;

import java.util.ArrayList;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;

import fr.dungeontrouble.affichage.Affichage;

public class Personnage extends Entite {
	
	//enumeration des differents personnages
		public enum TypePersonnage
		{
			guerrier,
			elfe,
			magicien,
			valkyrie
		};
		
	private Texture textureArme;
	private ArrayList<Sprite> spriteArme;
	private int nbCles;
	private int score;
	private Texture texturePersonnage;
	private Sprite spritePersonnage;
	TypePersonnage perso;
	Direction direction;
	Etat etat;
	private ArrayList<Direction> directionArme; //Tableau contenant les indices correspondant à chaque lancée d'armes
	
	public Personnage(TypePersonnage perso) { // A modifier !!!
		// TODO Auto-generated constructor stub
		
		this.perso = perso;
		this.nbCles = 0;
		this.score = 5000;
		this.texturePersonnage = Affichage.loadTexture("sprite_"+perso.name()+".png");
		this.spritePersonnage = new Sprite(texturePersonnage);
		this.textureArme = Affichage.loadTexture("spriteArme_"+perso.name()+".png");
		this.spriteArme = new ArrayList<Sprite>();
		//this.spriteArme = new Sprite(textureArme);
		this.direction = Direction.bas;
		this.etat = Etat.arret;
		
	}
	
	
	
	@Override
	public void faireAction() { //permet de balancer une arme lorsqu'il ny a pas dde porte
		// TODO Auto-generated method stub
		//if((!existporte) Methode qui verifie la presence de porte 
				{
			
			spriteArme.add(sprite);
			directionArme.add(direction);
		}
		
	}
	
	/**
	 * Méthode de déplacement d'un personnage selon une direction
	 * @param direction 0 = gauche, 1 = haut, 2 = droite, 3 = bas
	 */
	
	public void seDeplacer(Direction direction){ //deplace en fonction mv
		//sprite.move(arg0)
		//if(method=false) methode qui prend en parametre la positionSprite et la direction (retourne un boolean) verifie la presence d'une collision
		{
			float vitesse=5;
			Time tmp =chrono.restart(); //permet de connaitre la vitesse
			float tempsEcoule = tmp.asSeconds();
			float distance= tempsEcoule * vitesse;
			float x = 0;
			float y = 0;
			
			if (tmp.equals(5000)){
				switch (direction){
				
				case haut :
					x = 0;
					y= -distance;
					sprite.move(x, y);
					break;
				
				case haut_droit :
					x=distance;
					y=-distance;
					sprite.move(x, y);
					break;
				
				case droit :
					x = distance;
					y = 0;
					break;
					
				case bas_droit :
					x = distance; 
					y = distance;
					break;
					
				case bas :
					x = 0;
					y = distance;
					sprite.move(x, y);
					break;
				
				case bas_gauche:
					x = -distance;
					y = distance;
					sprite.move(x, y);
					break;
				
				case gauche:
					x = -distance;
					y = 0;
					sprite.move(x, y);
					break;
				
				case haut_gauche:
					x = -distance;
					y = -distance;
					sprite.move(x, y);
					break;
					
				}
				
			}
			
		}
		
	}
	
	/**
	 * Méthode de vérification de sortie du jeu
	 */
	public void verifierSortie(){
		
	}
}
