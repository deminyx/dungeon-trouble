package fr.dungeontrouble.evenement;

import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

import fr.dungeontrouble.partie.Partie;

/**
 * Classe contenant la méthode de capture de la touche "Action" des Personnages
 * 
 * @author Maxime BELLIER
 * 
 */
public class ActionEvent extends Evenement {
	/**
	 * 
	 * Fonction de la capture de la touche Actoin pour le jeu en mode 1 joueur.
	 */
	public static void getAction1J() {
		if (Keyboard.isKeyPressed(Key.SPACE)) {
			Partie.getP1().faireAction();
		}
	}

	/**
	 * Fonction de la capture de la touche Actoin pour le jeu en mode 4 joueurs.
	 */
	public static void getAction4J() {
		/*
		 * J1 : LSHIFT
		 * J2 : NUMPAD0
		 * J3 : RSHIFT
		 * J4 : SPACE
		 */
		if (Keyboard.isKeyPressed(Key.LSHIFT)) {
			Partie.getP1().faireAction();
		}
		if (Keyboard.isKeyPressed(Key.NUMPAD0)) {
			Partie.getP2().faireAction();
		}
		if (Keyboard.isKeyPressed(Key.RSHIFT)) {
			Partie.getP3().faireAction();
		}
		if (Keyboard.isKeyPressed(Key.SPACE)) {
			Partie.getP4().faireAction();
		}
	}
}
