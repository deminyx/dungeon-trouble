package fr.dungeontrouble.partie.entite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import fr.dungeontrouble.affichage.Affichage;
import fr.dungeontrouble.partie.Partie;
import fr.dungeontrouble.partie.niveau.Niveau;
import fr.dungeontrouble.partie.niveau.Objet;
import fr.dungeontrouble.partie.niveau.Porte;
/**
 * 
 * @author Awa CAMARA
 * permet de gerer les personnages (position, score,deplacement..)
 */

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
	
	TypePersonnage perso;
	
	private ArrayList<Direction> directionArme; //Tableau contenant les indices correspondant à chaque lancée d'armes
	
	/**
	 * Constructeur de personnage
	 * @param perso Le type du personnage
	 * @param position Position à laquelle faire apparaître le personnage
	 */
	public Personnage(TypePersonnage perso, Vector2i position) { 
		// TODO Auto-generated constructor stub
		super(position);
		this.perso = perso;
		this.nbCles = 0;
		this.score = 1000;
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
	
	
	/**
	 * permet de lancer une arme lorsqu'il n'y a pas de porte
	 */
	@Override
	public void faireAction() { 
		// TODO Auto-generated method stub
		if(!regardeUnePorte()) //Methode qui verifie la presence de porte 
		{			
			spriteArme.add(new Sprite(textureArme,new IntRect(perso.ordinal()*50,0,50,50)));
			spriteArme.get(spriteArme.size()-1).setPosition(this.sprite.getPosition().x + 25, this.sprite.getPosition().y + 25);
			directionArme.add(direction);
			
			spriteArme.get(spriteArme.size()-1).setOrigin(new Vector2f(25,25));
			switch(direction){//lancement de l'arme en fonction de la direction
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
		else{
			if (this.nbCles > 0){ 	// On ouvre la porte si on a au moins une clef
				switch(this.direction){
				case bas:
					detruirePorte(this.position.x, this.position.y+1);
					break;
				case bas_droit:
					detruirePorte(this.position.x+1, this.position.y+1);
					break;
				case bas_gauche:
					detruirePorte(this.position.x-1, this.position.y+1);
					break;
				case droit:
					detruirePorte(this.position.x+1, this.position.y);
					break;
				case gauche:
					detruirePorte(this.position.x-1, this.position.y);
					break;
				case haut:
					detruirePorte(this.position.x, this.position.y-1);
					break;
				case haut_droit:
					detruirePorte(this.position.x+1, this.position.y-1);
					break;
				case haut_gauche:
					detruirePorte(this.position.x-1, this.position.y-1);
					break;
				default:
					break;
				
				}
				this.nbCles--;
			}
		}
	}
	

	/**
	 * Methode de verification de collision entre une arme et un monstre
	 * @param m Monstre avec lequel vérifier la collision
	 * @param arme Arme avec laquelle vérifier la collision
	 * @return true s'il y a collision, false sinon
	 */
	public boolean collisionMonstre(Monstre m, Sprite arme){
		Vector2f armePos = new Vector2f(arme.getPosition().x + 25, arme.getPosition().y + 25);
		return (armePos.x >= m.getSprite().getPosition().x &&
				armePos.x <= m.getSprite().getPosition().x + 50 &&
				armePos.y >= m.getSprite().getPosition().y &&
				armePos.y <= m.getSprite().getPosition().y + 50);				
	}
	
	/**
	 * Méthode de vérification de collision des armes
	 */
	public void verifierCollisionArmes(){
		Iterator<Entry<Vector2i, Monstre>> i = Partie.getMonstres().entrySet().iterator();
		for (Sprite s : this.spriteArme){
			while (i.hasNext()){
				Monstre m = i.next().getValue();
				if (collisionMonstre(m,s)){
					m.setPdv(m.getPdv()-1);
					if (m.getPdv() == 0){
						i.remove();
					}
				}
			}			
		}
	}
	
	/**
	 * Méthode de destruction d'une porte sur la case ciblée
	 * @param posX Position en X de la porte dans la matrice du niveau
	 * @param posY Position en Y de la porte dans la matrice du niveau
	 */
	void detruirePorte(int posX, int posY){
		if (Niveau.getObjets().containsKey(new Vector2i(posX, posY))){
			if ((Niveau.getObjets().get(new Vector2i(posX,posY))) instanceof Porte){
				int idASupprimer = ((Porte)(Niveau.getObjets().get(new Vector2i(posX, posY)))).getIdPorteCourante();
				Iterator<Entry<Vector2i, Objet>> i = Niveau.getObjets().entrySet().iterator();
				while (i.hasNext()){
					Objet o = i.next().getValue();
					if (o.toString().equals("Porte")){
						if(((Porte)o).getIdPorteCourante() == idASupprimer){
							i.remove();
						}								
					}
				}
			}
		}
	}
	
	/**
	 * Méthode de vérification de sortie du jeu
	 * @return true si on est sorti du jeu, false sinon
	 */
	public boolean verifierSortie(){//verifier si position perso = niveau.getNiveaude la pos du perso = val = 14 (correspoond à la sortie)
		
		if(Niveau.getNiveau()[position.y][position.x]==14 ){
			return true;
		}
		
		else
			return false;
	}
	
	/**
	 * Méthode qui vérifie si une arme est toujours visible
	 * @param arme Arme à vérifier
	 * @param centre Centre de la vue
	 * @return Booléen à vrai si l'arme est dans la vue
	 */
	public boolean armeDansVue(Sprite arme, Vector2f centre){
		Vector2f pos = new Vector2f(arme.getPosition().x, arme.getPosition().y);
		return ((pos.x >= centre.x - 275)&&
				(pos.x <= centre.x + 275)&&
				(pos.y >= centre.y - 300)&&
				(pos.y <= centre.y + 300));
	}
	
	/**
	 * Méthode pour mettre à jour les positions des armes jetées
	 * @param t Temps écoulé depuis le dernier passage dans la boucle principale
	 * @param centre Coordonnées du centre de la vue de jeu
	 */
	public void bougerArmes(Time t, Vector2f centre){
		float distance = this.vitesse*3*t.asSeconds();
		
		for (int i=0; i < spriteArme.size(); i++){
			if (!armeDansVue(spriteArme.get(i), centre)){
				spriteArme.remove(i);
				directionArme.remove(i);
			}
			else{
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
	}
	
	/**methode d'affichage du personnage
	 *
	 */
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		target.draw(this.sprite);
		for (Sprite s : spriteArme){
			target.draw(s);
		}		
	}

	public static Texture getTextureArme() {
		return textureArme;
	}

	public static void setTextureArme(Texture textureArme) {
		Personnage.textureArme = textureArme;
	}

	public ArrayList<Sprite> getSpriteArme() {
		return spriteArme;
	}

	public void setSpriteArme(ArrayList<Sprite> spriteArme) {
		this.spriteArme = spriteArme;
	}

	public int getNbCles() {
		return nbCles;
	}

	public void setNbCles(int nbCles) {
		this.nbCles = nbCles;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	public TypePersonnage getPerso() {
		return perso;
	}

	public void setPerso(TypePersonnage perso) {
		this.perso = perso;
	}

	public ArrayList<Direction> getDirectionArme() {
		return directionArme;
	}

	public void setDirectionArme(ArrayList<Direction> directionArme) {
		this.directionArme = directionArme;
	}
	
	
}
