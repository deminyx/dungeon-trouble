package fr.dungeontrouble.evenement;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

import fr.dungeontrouble.partie.entite.Personnage.TypePersonnage;
/**
 * Classe servant au choix du personnage [Jeu 1-Joueur]
 * @author Maxime BELLIER
 *
 */
public class ChoiceEvent extends Evenement {

	/**
	 * Fonction de capture du clic pour choix du TypePersonnage
	 * @param window Fen�tre courante
	 * @return TypePersonnage : Valeur d'enum sur le type du personnage voulu
	 */
	public static TypePersonnage getChoice(RenderWindow window) {
		for(Event e : window.pollEvents()) {
		    switch(e.type) {
		        case MOUSE_BUTTON_PRESSED : 								// Si on clique...
		            
		        	Vector2i positionClic = e.asMouseEvent().position;
		        															// .. on r�cup�re la position de ce clic...
		        	if ((positionClic.x<800)&&(positionClic.y<600)){
		            			
		        		// Analyse du perso cliqu�
		        		/*
		        		 * Coin sup�rieur gauche : Guerrier
		        		 * Coin sup�rieur droit : Elfe
		        		 * Coin inf�rieur gauche : Valkyrie
		        		 * Coin inf�rieur droit : Magicien 
		        		 */
		        		
		        		if ((positionClic.x<400)&&(positionClic.y<300)){
		        			return(TypePersonnage.guerrier);
		            	}		            
		        		if ((positionClic.x>400)&&(positionClic.y<300)){
		        			return(TypePersonnage.elfe);
		        		}
		        		if ((positionClic.x<400)&&(positionClic.y>300)){
		        			return(TypePersonnage.valkyrie);
		        		}
		        		if ((positionClic.x>400)&&(positionClic.y>300)){
		        			return(TypePersonnage.magicien);
		        		}
		        	}
		            break;
			
			default:
				break;
		    }
		
		}
		return null;
	}
}