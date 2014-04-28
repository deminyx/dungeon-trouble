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
	
	public enum Etat{
		arret,
		mouvement1,
		mouvement2
	};
	
	public enum Direction{
		haut,
		bas,
		bas_droit,
		bas_gauche,
		haut_gauche,
		haut_droit
	};
	
	private Texture textureArme;
	private ArrayList spriteArme;
	private int nbCles;
	private int score;
	private Texture texturePersonnage;
	private Sprite spritePersonnage;
	TypePersonnage perso;
	Direction direction;
	Etat etat;
	private ArrayList directionArme; //Tableau contenant les indices correspondant à chaque lancée d'armes
	private final Clock chrono;
	
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
		this.chrono = new Clock();
	}
	
	
	
	@Override
	public void faireAction() { //permet de balancer une arme lorsqu'il ny a pas dde porte
		// TODO Auto-generated method stub
		//if((!existporte) Methode qui verifie la presence de porte 
				{
			spriteArme.add(spriteArme);
			directionArme.add(direction);
		}
		
	}
	
	/**
	 * Méthode de déplacement d'un personnage selon une direction
	 * @param direction 0 = gauche, 1 = haut, 2 = droite, 3 = bas
	 */
	public void seDeplacer(int direction){ //deplace en fonction mv
		//sprite.move(arg0)
		//if(method=false) methde qui prend en parametre la positionSprite et la direction (retourne un boolean) verife la presence d'une collision
		{
			Time tmp =chrono.restart();
			float tempsEcoule = tmp.asSeconds();
			//sprite.move(float x, float y);
			
		}
		
	}
	
	/**
	 * Méthode de vérification de sortie du jeu
	 */
	public void verifierSortie(){
		
	}
}
