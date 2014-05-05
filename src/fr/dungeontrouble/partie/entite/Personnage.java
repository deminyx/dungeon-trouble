package fr.dungeontrouble.partie.entite;

import java.util.ArrayList;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import fr.dungeontrouble.affichage.Affichage;
import fr.dungeontrouble.partie.niveau.Niveau;

public class Personnage extends Entite {
	
	//enumeration des differents personnages
		public enum TypePersonnage
		{
			guerrier,
			elfe,
			magicien,
			valkyrie
		};
		
	private static Texture textureArme;
	private ArrayList<Sprite> spriteArme;
	private int nbCles;
	private int score;
	private int vitesse;
	TypePersonnage perso;
	
	private ArrayList<Direction> directionArme; //Tableau contenant les indices correspondant à chaque lancée d'armes
	
	public Personnage(TypePersonnage perso, Vector2i position) { 
		// TODO Auto-generated constructor stub
		super(position);
		this.perso = perso;
		this.nbCles = 0;
		this.score = 1000;
		this.vitesse = 100;
		this.texture = Affichage.loadTexture("sprite_"+perso.name()+".png");
		this.sprite = new Sprite(texture);
		this.spriteArme = new ArrayList<Sprite>();
		this.directionArme = new ArrayList<Direction>();
		this.sprite.setPosition(this.position.x*50,this.position.y*50);//initialise la position
		updateSprite(this.direction, this.etat);
		
		
	}
	
	//une fonction dinitialisation
	public static void init(){
		textureArme = Affichage.loadTexture("sprite_armes.png");
	}
	
	@Override
	public void faireAction() { //permet de lancer une arme lorsqu'il ny a pas de porte
		// TODO Auto-generated method stub
		if(regardeUnePorte()==false) //Methode qui verifie la presence de porte 
				{
			
			spriteArme.add(new Sprite(textureArme,new IntRect(perso.ordinal()*50,0,50,50)));
			spriteArme.get(spriteArme.size()-1).setPosition(this.sprite.getPosition());
			directionArme.add(direction);
			
			spriteArme.get(spriteArme.size()-1).setOrigin(new Vector2f(25,25));
			switch(direction){
			case bas:
				spriteArme.get(spriteArme.size()-1).rotate(90);
				break;
			case bas_droit:
				spriteArme.get(spriteArme.size()-1).rotate(45);
				break;
			case bas_gauche:
				spriteArme.get(spriteArme.size()-1).rotate(135);
				break;
			case droit:
				spriteArme.get(spriteArme.size()-1).rotate(0);
				break;
			case gauche:
				spriteArme.get(spriteArme.size()-1).rotate(180);
				break;
			case haut:
				spriteArme.get(spriteArme.size()-1).rotate(-90);
				break;
			case haut_droit:
				spriteArme.get(spriteArme.size()-1).rotate(-45);
				break;
			case haut_gauche:
				spriteArme.get(spriteArme.size()-1).rotate(-135);
				break;
			default:
				break;
			
			}
		}
		//else retirerPorte()
	}
	
	public void updateSprite(Direction direction, Etat etat){ //permet de faire la mise à jour du sprite actuel
		this.sprite.setTextureRect(new IntRect(etat.ordinal()*50, direction.ordinal()*50,50,50));
	}
	
	/**
	 * Méthode de déplacement d'un personnage selon une direction
	 * @param direction 0 = gauche, 1 = haut, 2 = droite, 3 = bas
	 */
	
	public void seDeplacer(Direction direction, Time tempsEcoule){ //deplace en fonction mv
		this.direction = direction;
		//if(method=false) methode qui prend en parametre la positionSprite et la direction (retourne un boolean) verifie la presence d'une collision
		{			
			float tpsEcoule = tempsEcoule.asSeconds(); //temps ecoulé our changemet d'image
			float distance= tpsEcoule * vitesse;
			float x = 0;
			float y = 0;
			
			switch (direction){
				
				case haut :
					x = 0;
					y= -distance;
					//mise à jour position du sprite
					break;
				
				case haut_droit :
					x=distance;
					y=-distance;
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
					
					break;
				
				case bas_gauche:
					x = -distance;
					y = distance;
					
					break;
				
				case gauche:
					x = -distance;
					y = 0;
					
					break;
				
				case haut_gauche:
					x = -distance;
					y = -distance;
					
					break;
					
				}
				sprite.move(x,y);
				
				//updatePosition (met à jour la variable de position)
				this.position=new Vector2i((int)this.sprite.getPosition().x/Niveau.SIZE,(int)this.sprite.getPosition().y/Niveau.SIZE);
				
				if(direction !=this.direction){ //test pour voir si la direction choisie est différente de la diection actuelle
					updateSprite(direction, Etat.mouvement1);
					chrono.restart();
				}
				
				else if(chrono.getElapsedTime().asMilliseconds()>200)
				{
					chrono.restart(); 
					if(this.etat==Etat.mouvement1)
						this.etat = Etat.mouvement2;
					else
						this.etat = Etat.mouvement1;
					
					updateSprite(this.direction, this.etat);
				}			
		}
		
	}
	
	/**
	 * Méthode de vérification de sortie du jeu
	 */
	public boolean verifierSortie(){//verifier si position perso = niveau.getNiveaude la pos du perso = val = 14 (correspoond à la sortie)
		
		if(Niveau.getNiveau()[position.y][position.x]==14 ){
			
			System.out.println("on est sorti weaaaaaah");
			return true;
		}
		
		else
			return false;
	}
	
	/**
	 * Méthode pour mettre à jour les positions des armes jetées
	 */
	public void bougerArmes(Time t){
		float distance = this.vitesse*3*t.asSeconds();
		
		for (int i=0; i < spriteArme.size(); i++){
			switch(directionArme.get(i)){
			case bas:
				spriteArme.get(i).move(new Vector2f(0,distance));
				break;
			case bas_droit:
				spriteArme.get(i).move(new Vector2f(distance,distance));
				break;
			case bas_gauche:
				spriteArme.get(i).move(new Vector2f(-distance,distance));
				break;
			case droit:
				spriteArme.get(i).move(new Vector2f(distance,0));
				break;
			case gauche:
				spriteArme.get(i).move(new Vector2f(-distance,0));
				break;
			case haut:
				spriteArme.get(i).move(new Vector2f(0,-distance));
				break;
			case haut_droit:
				spriteArme.get(i).move(new Vector2f(distance,-distance));
				break;
			case haut_gauche:
				spriteArme.get(i).move(new Vector2f(-distance,-distance));
				break;
			default:
				break;
				
			}
		}
	}
	
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		target.draw(this.sprite);
		for (Sprite s : spriteArme){
			target.draw(s);
		}		
	}
}
