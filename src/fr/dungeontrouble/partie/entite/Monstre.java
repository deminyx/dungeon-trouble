package fr.dungeontrouble.partie.entite;

import java.util.HashMap;
import java.util.Random;

import org.jsfml.system.Time;
import org.jsfml.system.Vector2i;

import fr.dungeontrouble.partie.Partie;

/**
 * 
 * @author Awa CAMARA
 * permet de gerer les monstres (position, points de vie, attaque, deplacement)
 */

public class Monstre extends Entite {
	
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
		
		switch(type){
		case Fantome:
			this.pdv = 1;
			break;
		
		case Gobelin:
			this.pdv = 2;
		break;
		}
		
		Random rand = new Random();
		cible = (Personnage) Partie.getPersonnages().values().toArray()[rand.nextInt(4)]; //permet de prendre un personnage aleatoirement
			
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
	
	
	public void seDeplacerVersCible(Time tempsEcoule){
		
		int diffAbs=this.getPosition().x-cible.getPosition().x;
		int diffOrd=this.getPosition().y-cible.getPosition().y;
		
		
		if(diffAbs==0){
			if(diffOrd>0){ //va vers le haut ou le bas
				this.seDeplacer(Direction.bas, tempsEcoule);
			}
			else {
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
			else
				this.seDeplacer(Direction.gauche, tempsEcoule);
		}
			
	}
	
	
}
