package fr.dungeontrouble.evenement;

import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

import fr.dungeontrouble.partie.Partie;

public class ActionEvent extends Evenement {

	public static void getAction1J() {
		if (Keyboard.isKeyPressed(Key.SPACE)) {
			Partie.getP1().faireAction();
		}
	}

	public static void getAction4J() {
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
