package fr.dungeontrouble.evenement;

import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

public class MoveEvent extends Evenement {

	public void getMove1J() {
		if (Keyboard.isKeyPressed(Key.UP)) {
			if (Keyboard.isKeyPressed(Key.RIGHT)) {
			} else if (Keyboard.isKeyPressed(Key.LEFT)) {

			} else {

			}
		} else if (Keyboard.isKeyPressed(Key.DOWN)) {
			if (Keyboard.isKeyPressed(Key.RIGHT)) {
			} else if (Keyboard.isKeyPressed(Key.LEFT)) {

			} else {

			}
		} else if (Keyboard.isKeyPressed(Key.RIGHT)) {
		} else if (Keyboard.isKeyPressed(Key.LEFT)) {
		}
	}

	public void getMove4J() {
		// JOUEUR 1
		/*
		 * Z = HAUT
		 * D = DROITE
		 * Q = GAUCHE
		 * S = BAS
		 */
		if (Keyboard.isKeyPressed(Key.Z)) {
			if (Keyboard.isKeyPressed(Key.D)) {
			} else if (Keyboard.isKeyPressed(Key.Q)) {

			} else {

			}
		} else if (Keyboard.isKeyPressed(Key.S)) {
			if (Keyboard.isKeyPressed(Key.D)) {
			} else if (Keyboard.isKeyPressed(Key.Q)) {

			} else {

			}
		} else if (Keyboard.isKeyPressed(Key.D)) {
		} else if (Keyboard.isKeyPressed(Key.Q)) {
		}

		// JOUEUR 2
		/*
		 * T = HAUT
		 * H = DROITE
		 * F = GAUCHE
		 * G = BAS
		 */
		else if (Keyboard.isKeyPressed(Key.T)) {
			if (Keyboard.isKeyPressed(Key.H)) {
			} else if (Keyboard.isKeyPressed(Key.F)) {

			} else {

			}
		} else if (Keyboard.isKeyPressed(Key.G)) {
			if (Keyboard.isKeyPressed(Key.H)) {
			} else if (Keyboard.isKeyPressed(Key.F)) {

			} else {

			}
		} else if (Keyboard.isKeyPressed(Key.H)) {
		} else if (Keyboard.isKeyPressed(Key.F)) {
		}

		// JOUEUR 3
		/*
		 * O = HAUT
		 * M = DROITE
		 * K = GAUCHE
		 * L = BAS
		 */
		else if (Keyboard.isKeyPressed(Key.O)) {
			if (Keyboard.isKeyPressed(Key.M)) {
			} else if (Keyboard.isKeyPressed(Key.K)) {

			} else {

			}
		} else if (Keyboard.isKeyPressed(Key.L)) {
			if (Keyboard.isKeyPressed(Key.M)) {
			} else if (Keyboard.isKeyPressed(Key.K)) {

			} else {

			}
		} else if (Keyboard.isKeyPressed(Key.M)) {
		} else if (Keyboard.isKeyPressed(Key.K)) {
		}
		// JOUEUR 4
		/*
		 * NUMPAD8 = HAUT
		 * NUMPAD6 = DROITE
		 * NUMPAD4 = GAUCHE
		 * NUMPAD5 = BAS
		 */
		else if (Keyboard.isKeyPressed(Key.NUMPAD8)) {
			if (Keyboard.isKeyPressed(Key.NUMPAD6)) {
			} else if (Keyboard.isKeyPressed(Key.NUMPAD4)) {

			} else {

			}
		} else if (Keyboard.isKeyPressed(Key.NUMPAD5)) {
			if (Keyboard.isKeyPressed(Key.NUMPAD6)) {
			} else if (Keyboard.isKeyPressed(Key.NUMPAD4)) {

			} else {

			}
		} else if (Keyboard.isKeyPressed(Key.NUMPAD6)) {
		} else if (Keyboard.isKeyPressed(Key.NUMPAD4)) {
		}
	}
}
