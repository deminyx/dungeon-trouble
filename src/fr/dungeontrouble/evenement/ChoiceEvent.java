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
		        case MOUSE_BUTTON_PRESSED :
		            
		        	Vector2i positionClic = e.asMouseEvent().position;
		        	
		        	if ((positionClic.x<800)&&(positionClic.y<600)){
		            			
		        		System.out.println("Analyse du perso cliqu� !");
		           
		           
		        		if ((positionClic.x<400)&&(positionClic.y<300)){
		        			System.out.println("Choix Guerrier");
		        			return(TypePersonnage.guerrier);
		        			
		            			}
		            
		        		if ((positionClic.x>400)&&(positionClic.y<300)){
		        			System.out.println("Choix Elfe");
		        			return(TypePersonnage.elfe);
		        			
		            			}

		        		if ((positionClic.x<400)&&(positionClic.y>300)){
		        			System.out.println("Choix Valkyrie");
		        			return(TypePersonnage.valkyrie);
		        			
		            			}
		        		if ((positionClic.x>400)&&(positionClic.y>300)){
		        			System.out.println("Choix Magicien !");
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