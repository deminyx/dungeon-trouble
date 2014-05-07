package fr.dungeontrouble.partie.entite;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import fr.dungeontrouble.partie.Partie;
import fr.dungeontrouble.partie.niveau.Niveau;
import fr.dungeontrouble.partie.niveau.Objet;
import fr.dungeontrouble.partie.niveau.Porte;

/**
 * 
 * @author Awa CAMARA
 *
 */
public abstract class Entite implements Drawable {

	protected Vector2i position;
	protected Sprite sprite;
	protected Texture texture;
	protected int attaque;
	protected final Clock chrono;
	protected int vitesse;
	Direction direction;
	Etat etat;

	public enum Etat{
		arret,
		mouvement1,
		mouvement2
	};
	
	
	public Entite(Vector2i position){
		this.chrono = new Clock();
		this.direction = Direction.bas;
		this.etat = Etat.arret;
		this.position = position;
		this.vitesse = 150;
	}
	
	
	
	public enum Direction{
		haut,
		haut_droit,
		droit,
		bas_droit,
		bas,
		bas_gauche,
		gauche,
		haut_gauche,
		
	};
	
	public void updateSprite(Direction direction, Etat etat){ //permet de faire la mise � jour du sprite actuel
		this.sprite.setTextureRect(new IntRect(etat.ordinal()*50, direction.ordinal()*50,50,50));
	}
	
	public boolean collision(Time timeElapsed){
		float move = this.vitesse * timeElapsed.asSeconds();		
		Vector2f nextPos = null;
		Vector2i nextCoord = null;
		
		switch(direction){
			case bas:
				nextPos = new Vector2f(this.sprite.getPosition().x ,this.sprite.getPosition().y + move);			
				break;
			case bas_droit:
				nextPos = new Vector2f(this.sprite.getPosition().x + move,this.sprite.getPosition().y + move);
				break;
			case bas_gauche:
				nextPos = new Vector2f(this.sprite.getPosition().x - move,this.sprite.getPosition().y + move);
				break;
			case droit:
				nextPos = new Vector2f(this.sprite.getPosition().x + move,this.sprite.getPosition().y);
				break;
			case gauche:
				nextPos = new Vector2f(this.sprite.getPosition().x - move,this.sprite.getPosition().y);
				break;
			case haut:
				nextPos = new Vector2f(this.sprite.getPosition().x,this.sprite.getPosition().y - move);
				break;
			case haut_droit:
				nextPos = new Vector2f(this.sprite.getPosition().x + move,this.sprite.getPosition().y - move);
				break;
			case haut_gauche:
				nextPos = new Vector2f(this.sprite.getPosition().x - move,this.sprite.getPosition().y - move);
				break;
			default:
				break;
		}
		
		nextPos = new Vector2f(nextPos.x + 20, nextPos.y + 35);
		nextCoord = new Vector2i((int)nextPos.x/Niveau.SIZE, (int)nextPos.y/Niveau.SIZE);
	
		boolean returnValue = Niveau.getNiveau()[nextCoord.y][nextCoord.x] > 0;
		
		// V�rification si un objet sur la case cible
		if (Niveau.getObjets().containsKey(nextCoord)){
			switch (Niveau.getObjets().get(nextCoord).getClass().getSimpleName()){
				case "Tresor":
					if (this instanceof Personnage){
						((Personnage)(this)).setScore(((Personnage)(this)).getScore()+100);
						Niveau.getObjets().remove(nextCoord);
						returnValue = false;
					}
					
					break;
				
				case "Cle":
					if (this instanceof Personnage){
						((Personnage)(this)).setNbCles(((Personnage)(this)).getNbCles()+1);
						Niveau.getObjets().remove(nextCoord);
						returnValue = false;
					}
					
					break;
				
				case "Generateur":
					returnValue = true;
					break;
				
				case "Porte":
					returnValue = true;
					break;
			}
		}
//		else if (Partie.getMonstres().containsKey(nextCoord)){
//			returnValue = true;
//		}
		
		return returnValue;
	}

	public void seDeplacer(Direction direction, Time tempsEcoule){ //deplace en fonction mv
		this.direction = direction;
		if (!collision(tempsEcoule)) // S'il y a collision, alors on ne fait pas le d�placement
		{			
			float tpsEcoule = tempsEcoule.asSeconds(); //temps ecoul� our changemet d'image
			float distance= tpsEcoule * vitesse;
			float x = 0;
			float y = 0;
			
			switch (direction){
				
				case haut :
					x = 0;
					y= -distance;
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
				sprite.move(x,y); //mise � jour position du sprite
				
				//updatePosition (met � jour la variable de position)
				this.position=new Vector2i(((int)this.sprite.getPosition().x+25)/Niveau.SIZE,
						((int)this.sprite.getPosition().y+25)/Niveau.SIZE);
				
				if(direction !=this.direction){ //test pour voir si la direction choisie est diff�rente de la diection actuelle
					updateSprite(direction, Etat.mouvement1);
					chrono.restart();
				}
				
				else if(chrono.getElapsedTime().asMilliseconds()>150)
				{
					chrono.restart(); 
					if(this.etat==Etat.mouvement1)
						this.etat = Etat.mouvement2;
					else
						this.etat = Etat.mouvement1;
					
					updateSprite(this.direction, this.etat);
				}			
		}
		updateSprite(this.direction, this.etat);
		
	}
	
	public boolean regardeUnePorte(){
        Vector2i pos = this.position;
        boolean value = false;
       
        switch(this.direction){
                case bas:
                        value = (Niveau.getObjets().containsKey(new Vector2i(pos.x, pos.y+1))&&
                                        (Niveau.getObjets().get(new Vector2i(pos.x, pos.y+1)) instanceof Porte));
                        break;
                case bas_droit:
                        value = (Niveau.getObjets().containsKey(new Vector2i(pos.x+1, pos.y+1))&&
                                        (Niveau.getObjets().get(new Vector2i(pos.x+1, pos.y+1)) instanceof Porte));
                        break;
                case bas_gauche:
                        value = (Niveau.getObjets().containsKey(new Vector2i(pos.x-1, pos.y+1))&&
                                        (Niveau.getObjets().get(new Vector2i(pos.x-1, pos.y+1)) instanceof Porte));
                        break;
                case droit:
                        value = (Niveau.getObjets().containsKey(new Vector2i(pos.x+1, pos.y))&&
                                        (Niveau.getObjets().get(new Vector2i(pos.x+1, pos.y)) instanceof Porte));
                        break;
                case gauche:
                        value = (Niveau.getObjets().containsKey(new Vector2i(pos.x-1, pos.y))&&
                                        (Niveau.getObjets().get(new Vector2i(pos.x-1, pos.y)) instanceof Porte));
                        break;
                case haut:
                        value = (Niveau.getObjets().containsKey(new Vector2i(pos.x, pos.y-1))&&
                                        (Niveau.getObjets().get(new Vector2i(pos.x, pos.y-1)) instanceof Porte));
                        break;
                case haut_droit:
                        value = (Niveau.getObjets().containsKey(new Vector2i(pos.x+1, pos.y-1))&&
                                        (Niveau.getObjets().get(new Vector2i(pos.x+1, pos.y-1)) instanceof Porte));
                        break;
                case haut_gauche:
                        value = (Niveau.getObjets().containsKey(new Vector2i(pos.x-1, pos.y-1))&&
                                        (Niveau.getObjets().get(new Vector2i(pos.x-1, pos.y-1)) instanceof Porte));
                        break;
                default:
                        break;         
        }
       
        return value;
}
	
	public int getAttaque() {
		return attaque;
	}

	public void setAttaque(int attaque) {
		this.attaque = attaque;
	}

	public Vector2i getPosition() {
		return position;
	}

	public void setPosition(Vector2i position) {
		this.position = position;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	

	public int getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	public Clock getChrono() {
		return chrono;
	}

	/**
	 * M�thode abstraite permettant � l'entit� de faire son action
	 */
	public abstract void faireAction();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		target.draw(this.sprite);
	}
}
