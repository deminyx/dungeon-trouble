package fr.dungeontrouble.evenement;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.Event;

/**
 * Classe traitant l'arr�t du programme en cas de QuitEvent
 * 
 * @author Maxime BELLIER
 * 
 */
public class QuitEvent extends Evenement {

	/**
	 * Fonction de capture du clic pour arr�t du programme
	 * 
	 * @param window
	 *            Fen�tre courante
	 * 
	 */
	
	public static void getChoice(RenderWindow window) {
		for (Event e : window.pollEvents()) {
			switch (e.type) {
			case CLOSED:
				window.close();
				System.out.println("Arr�t du programme ...");
				System.exit(0);
			}

		}

	}
}
