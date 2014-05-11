package fr.dungeontrouble.partie.entite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2i;

import fr.dungeontrouble.affichage.Affichage;
import fr.dungeontrouble.partie.Partie;

/**
 * 
 * @author Awa CAMARA
 * permet de gerer les monstres (position, points de vie, attaque, deplacement)
 */

public class Monstre extends Entite implements Cloneable{
	
	//enumeration des differents monstres
	public enum TypeMonstre{
		Fantome,
		Gobelin
	};
	
	
	private TypeMonstre type;
	protected int pdv; //points de vie
	private Personnage cible;
		
	public Monstre(TypeMonstre monstre,Vector2i position){
		super(position);
		type=monstre;
		//en fonction du type de personnage les points de vie varient
		switch(type){
		case Fantome:
			this.pdv = 1;
			this.texture = Affichage.loadTexture("sprite_fantome.png");
			this.sprite = new Sprite(this.texture);
			break;
		
		case Gobelin:
			this.pdv = 2;
			this.texture = Affichage.loadTexture("sprite_gobelin.png");
			this.sprite = new Sprite(this.texture);
		break;
		}
		
		this.sprite.setPosition(this.position.y*50,this.position.x*50);//initialise la position
		updateSprite(this.direction, this.etat);
		
		Random rand = new Random();
		cible = Partie.getP1();
		//cible = (Personnage) Partie.getPersonnages().values().toArray()[rand.nextInt(0)]; //permet de prendre un personnage aleatoirement
			
	}
	
	public int getPdv() {
		return pdv;
	}
	
	
	public void setPdv(int pdv) {
		this.pdv = pdv;
	}
	
	@Override
	public void faireAction() {
		// TODO Auto-generated method stub
		switch (type) {
		case Fantome:
			cible.setScore(cible.getScore()-10);
		break;
		
		case Gobelin:
			cible.setScore(cible.getScore()-20);
		break;
		}
			
	}
	
	
	/**
	 * permet au monstre de se d�placer vers une cible pr�cise(un personnage)
	 * @param tempsEcoule Temps �coul� depuis le dernier passage dans la boucle principale
	 */
	public void seDeplacerVersCible(Time tempsEcoule){
		
		int diffAbs=this.getPosition().x-cible.getPosition().x;//difference entre les abscisses du monstre et celui du personnage
		int diffOrd=this.getPosition().y-cible.getPosition().y;//difference entre les ordonn�es du monstre et celui du personnage
		
		if(diffAbs==0){//en fonction de cette difference le monstre se d�placera vers sa cible 
			if(diffOrd<0){ //va vers le haut ou le bas
				this.seDeplacer(Direction.bas, tempsEcoule);
			}
			else if (diffOrd>0){
				this.seDeplacer(Direction.haut, tempsEcoule);
			}
		}
		
		else if (diffAbs<0){ //va a droite
			if(diffOrd>0){
				this.seDeplacer(Direction.haut_droit,tempsEcoule);
			}
			else if(diffOrd<0){
				this.seDeplacer(Direction.bas_droit, tempsEcoule);
			}
			else 
				this.seDeplacer(Direction.droit, tempsEcoule);
		}
		
		else { //va a gauche
			if(diffOrd>0){ 
				this.seDeplacer(Direction.haut_gauche, tempsEcoule);
			}
			else if(diffOrd<0){
				this.seDeplacer(Direction.bas_gauche, tempsEcoule);
			}
			else{
				this.seDeplacer(Direction.gauche, tempsEcoule);					
			}				
		}			
	}

	public static void majPos(){
		ArrayList<Monstre> actualMonstres = new ArrayList<Monstre>();
		for (Monstre m : Partie.getMonstres().values()){
			try {
				actualMonstres.add((Monstre) m.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		
		Partie.getMonstres().clear();
		for (Monstre m : actualMonstres){
			Partie.getMonstres().put(m.getPosition(),m);			
		}
	}
	
	
}
