package fr.dungeontrouble.partie.entite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import fr.dungeontrouble.affichage.Affichage;
import fr.dungeontrouble.affichage.AffichageScore;
import fr.dungeontrouble.partie.Partie;
import fr.dungeontrouble.partie.niveau.Generateur;
import fr.dungeontrouble.partie.niveau.Niveau;
import fr.dungeontrouble.partie.niveau.Objet;
import fr.dungeontrouble.partie.niveau.Porte;

/**
 * Classe qui permet de gerer les personnages (position, score,deplacement..)
 * ainsi que les armes de ces personnages
 * @author Awa CAMARA
 * @author Valentin PORCHET
 */
public class Personnage extends Entite{
	
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
	private TypePersonnage perso;
	
	private ArrayList<Direction> directionArme; //Tableau contenant les indices correspondant � chaque lanc�e d'armes
	
	/**
	 * Constructeur de personnage
	 * @param perso Le type du personnage
	 * @param position Position � laquelle faire appara�tre le personnage
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
		// Si on ne regarde pas de porte, alors on peut lancer une arme
		
		if(!regardeUnePorte()) //Methode qui verifie la presence de porte 
		{			
			Partie.getBip().play();
			spriteArme.add(new Sprite(textureArme,new IntRect(perso.ordinal()*50,0,50,50)));
			spriteArme.get(spriteArme.size()-1).setPosition(this.sprite.getPosition().x + 25, this.sprite.getPosition().y + 25);
			directionArme.add(direction);
			
			// On modifie l'origine de l'arme pour effectuer une rotation sur elle-m�me
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
	}
	
	/**
	 * M�thode de v�rification de collision des armes
	 * @param timeElapsed temps �coul� � prendre en compte
	 */
	public void verifierCollisionArmes(Time timeElapsed){
		// It�rateur pour parcourir les armes
		Iterator<Sprite> iArmes = this.spriteArme.iterator(); 
		int compteur = 0; // Compteur pour pouvoir supprimer les directions des armes
		Vector2i key = null; // Vecteur qui contiendra la position de l'arme courante dans la matrice
		Vector2f pos = null; // Vecteur qui contiendra la position de l'arme courante sur le terrain
		float move = timeElapsed.asSeconds()*this.vitesse*2; // Le mouvement effectu�
		
		// Tant qu'on a des armes
		while (iArmes.hasNext()){
			Sprite s = iArmes.next(); // On r�cup�re l'arme
			pos = new Vector2f(s.getPosition().x,s.getPosition().y); // On r�cup�re sa position
			
			// On r�cup�re la future position de l'arme dans la matrice
			switch (this.directionArme.get(compteur)){
			case bas:
				key = new Vector2i((int)pos.x / Niveau.SIZE,(int)(pos.y+move) / Niveau.SIZE);
				break;
			case bas_droit:
				key = new Vector2i((int)(pos.x+move) / Niveau.SIZE,(int)(pos.y+move) / Niveau.SIZE);
				break;
			case bas_gauche:
				key = new Vector2i((int)(pos.x-move) / Niveau.SIZE,(int)(pos.y+move) / Niveau.SIZE);
				break;
			case droit:
				key = new Vector2i((int)(pos.x+move) / Niveau.SIZE,(int)pos.y / Niveau.SIZE);
				break;
			case gauche:
				key = new Vector2i((int)(pos.x-move) / Niveau.SIZE,(int)pos.y / Niveau.SIZE);
				break;
			case haut:
				key = new Vector2i((int)pos.x / Niveau.SIZE,(int)(pos.y-move) / Niveau.SIZE);
				break;
			case haut_droit:
				key = new Vector2i((int)(pos.x+move) / Niveau.SIZE,(int)(pos.y-move) / Niveau.SIZE);
				break;
			case haut_gauche:
				key = new Vector2i((int)(pos.x-move) / Niveau.SIZE,(int)(pos.y-move) / Niveau.SIZE);
				break;
			default:
				break;
			}
						
			// On v�rifie s'il y a collision avec un mur
			if ((Niveau.getNiveau()[key.y][key.x] > 0)&&
				(Niveau.getNiveau()[key.y][key.x] != 14)){
				iArmes.remove();
				this.directionArme.remove(compteur);
				compteur--;
			} // Sinon s'il y a un objet, on v�rifie lequel c'est
			else if (Niveau.getObjets().containsKey(key)){
				Objet o = Niveau.getObjets().get(key);
				if (o instanceof Porte){
					iArmes.remove();
					this.directionArme.remove(compteur);
					compteur--;
				}
				else if (o instanceof Generateur){
					Niveau.getObjets().remove(key); // On supprime le g�n�rateur
					iArmes.remove(); // On supprime �galement l'arme
					this.directionArme.remove(compteur);
					compteur--;
				}
				// Sinon, que ce soit un tr�sor ou une clef, pas de collision
			} // On v�rifie les collisions avec les monstres
			else if (Partie.getMonstres().containsKey(key)){ 
				Monstre m = Partie.getMonstres().get(key);
				m.setPdv(m.getPdv()-1);
				Partie.getBip().play(); // On joue le bip
				// Si le monstre n'a plus de PV
				if (m.getPdv() <= 0){
					Partie.getMonstres().remove(key);
					Generateur.nbMonstres--;
				}
				// On supprime l'arme
				iArmes.remove();
				this.directionArme.remove(compteur);
				compteur--;
			}
			else if (Partie.getPersonnages().containsKey(key) && this != Partie.getPersonnages().get(key)){
				// On supprime l'arme
				iArmes.remove();
				this.directionArme.remove(compteur);
				compteur--;
			}
			compteur++;
		}
	}
	
	/**
	 * M�thode de v�rification de sortie du jeu
	 * @return true si on est sorti du jeu, false sinon
	 */
	public boolean verifierSortie(){//verifier si position perso = niveau.getNiveaude la pos du perso = val = 14 (correspoond � la sortie)
		
		if(Niveau.getNiveau()[position.y][position.x]==14 ){
			return true;
		}
		
		else
			return false;
	}
	
	/**
	 * M�thode qui v�rifie si une arme est toujours visible
	 * @param arme Arme � v�rifier
	 * @param centre Centre de la vue
	 * @return Bool�en � vrai si l'arme est dans la vue
	 */
	public boolean armeDansVue(Sprite arme, Vector2f centre){
		Vector2f pos = new Vector2f(arme.getPosition().x, arme.getPosition().y);
		return ((pos.x >= centre.x - 275)&&
				(pos.x <= centre.x + 275)&&
				(pos.y >= centre.y - 300)&&
				(pos.y <= centre.y + 300));
	}
	
	/**
	 * M�thode pour mettre � jour les positions des armes jet�es
	 * @param t Temps �coul� depuis le dernier passage dans la boucle principale
	 * @param centre Coordonn�es du centre de la vue de jeu
	 */
	public void bougerArmes(Time t, Vector2f centre){
		float distance = this.vitesse*2*t.asSeconds();
		
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
	
	public static void majPos(){
		ArrayList<Personnage> actualPersonnages = new ArrayList<Personnage>();
		for (Personnage p : Partie.getPersonnages().values()){
			actualPersonnages.add((Personnage) p);
		}
		
		Partie.getPersonnages().clear();
		for (Personnage p : actualPersonnages){
			Partie.getPersonnages().put(p.getPosition(),p);			
		}
	}
	
	public static void verifierSiVivant(AffichageScore affScore){
		Iterator<Personnage> i = Partie.getPersonnages().values().iterator();
		
		while (i.hasNext()){
			Personnage p = i.next();
			// Si le personnage vient de mourir
			if (p.getScore() <= 0){
				
				// On supprime le personnage du HashMap
				i.remove();
				
				// S'il reste au moins un personnage, on cherche les monstres qui ont 
				// pour cible le personnage, qui meurt et on change leur cible
				if (!Partie.getPersonnages().isEmpty()){
					for (Monstre m : Partie.getMonstres().values()){
						if (m.getCible() == p){
							m.setCible((Personnage)Partie.getPersonnages().values().toArray()
									[(new Random().nextInt(Partie.getPersonnages().size()))]);
						}
					}		
				}
			
				// On enl�ve les clefs de l'affichage
				affScore.getClefJoueur().get(p.getPerso().name()).setTextureRect(new IntRect(0,0,0,0));
				// On met le score affich� � 0 et en rouge
				affScore.getScoresText().get(p.getPerso().name()).setString("0");
				affScore.getScoresText().get(p.getPerso().name()).setColor(Color.RED);
				
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

	// M�thodes get() et set()
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
