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
import fr.dungeontrouble.partie.niveau.Porte;

/**
 * Classe Entite dont h�rite Monstre et Personnage
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

	//Enum pour determiner les differents �tats des personnages lors d'un mouvement
	public enum Etat{
		arret,
		mouvement1,
		mouvement2
	};
	
	//Enumeration pour d�terminer les diff�rentes dirction que peut suivre un personnage
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
	
	/**
	 * Constructeur de Entite
	 * @param position Position � laquelle positionner l'entit�
	 */
	public Entite(Vector2i position){
		this.chrono = new Clock();
		this.direction = Direction.bas;
		this.etat = Etat.arret;
		this.position = position;
		this.vitesse = 150;
	}
	
	
	/**
	 * permet de faire la mise � jour du sprite actuel
	 * @param direction : direction qu'une entit� va prendre
	 * @param etat: etat de l'entit�
	 */
	public void updateSprite(Direction direction, Etat etat){ 
		this.sprite.setTextureRect(new IntRect(etat.ordinal()*50, direction.ordinal()*50,50,50));
	}
	
	/**
	 * Permet de determiner la presence d'un obstacle 
	 * selon la direction on calcule la position � laquelle le personnage devrais �tre et on verifie s'il y a un objet l� o�
	 * il se dirige
	 * si c'est une cl� incr�mentation du nombre de cl�s du personnage
	 * si c'est un tr�sor incrementation du score du personnage
	 * si c'est un mur d�clenchement de la collision 
	 * @param timeElapsed temps �coul� lors du dernier passage dans la boucle principale
	 * @return true s'il y a collision
	 */
	
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
		
		// V�rification si un objet est sur la case cible
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
		
		// Si on a toujours rien trouv�, on v�rifie s'il y a une autre entit�
		if (!returnValue){
			for (Monstre m : Partie.getMonstres().values()){
				if ((m != this) && (collisionEntite(this,m))){
					returnValue = true;
					if (this instanceof Personnage){
						((Personnage)this).setScore(((Personnage)this).getScore()-1);
					}
				}
			}
			
			if (!returnValue){
				for (Personnage p : Partie.getPersonnages().values()){
					if ((p != this) && (collisionEntite(this,p))){
						returnValue = true;
					}
				}
			}
		}
	
		return returnValue;
	}
	

	private boolean collisionEntite(Entite e, Entite e2) {
//		Vector2f pos1 = e.getSprite().getPosition();
//		Vector2f pos2 = e2.getSprite().getPosition();
//		
//		return (!((pos2.x >= pos1.x + 35)      
//		|| (pos2.x + 35 <= pos1.x)
//		|| (pos2.y >= pos1.y + 35)
//		|| (pos2.y + 35 <= pos1.y)));
		return e.getPosition().equals(e2.getPosition());
	}


	/**
	 * Permet � l'entit� de se d�placer en fonction de la direction et du temps ecoul�
	 * @param direction Direction vers laquelle se d�placer
	 * @param tempsEcoule Temps �coul� lors du dernier passage dans la boucle principale
	 */
	public void seDeplacer(Direction direction, Time tempsEcoule){ 
		this.direction = direction;
		if (!collision(tempsEcoule)) // S'il y a collision, alors on ne fait pas le d�placement
		{
			float tpsEcoule = tempsEcoule.asSeconds(); //temps ecoul� pour chargement d'image
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
	
	/**
	 * permet de verifier la pr�sence d'une porte
	 * @return true s'il y a une porte en face du personnage, false sinon
	 */
	public boolean regardeUnePorte(){
        Vector2i pos = this.position;
        boolean value = false;
       
        switch(this.direction){ //verification en fonction de la direction
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
        
        // Si on n'a pas trouv� de porte, on v�rifie �galement qu'on n'est pas
        // Sur la m�me case qu'une porte
        
        if (!value){
        	value = (Niveau.getObjets().containsKey(pos)&&
                    (Niveau.getObjets().get(pos) instanceof Porte));
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
	 * M�thode abstraite permettant � l'entit� de faire une action
	 */
	public abstract void faireAction();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		target.draw(this.sprite);
	}


	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" ("+this.position.x+","+this.position.y+")";
	}
	
	
}
