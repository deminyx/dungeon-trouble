package fr.dungeontrouble.evenement;

import org.jsfml.system.Time;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

import fr.dungeontrouble.partie.Partie;
import fr.dungeontrouble.partie.entite.Entite.Direction;
import fr.dungeontrouble.partie.entite.Entite.Etat;

public class MoveEvent extends Evenement {

	
	
	public static void getMove1J(Time t) {
		if (Keyboard.isKeyPressed(Key.UP)) {
			if (Keyboard.isKeyPressed(Key.RIGHT)) {
				Partie.getP1().seDeplacer(Direction.haut_droit, t);
			} else if (Keyboard.isKeyPressed(Key.LEFT)) {
				Partie.getP1().seDeplacer(Direction.haut_gauche, t);
			} else {
				Partie.getP1().seDeplacer(Direction.haut, t);
			}
		} else if (Keyboard.isKeyPressed(Key.DOWN)) {
			if (Keyboard.isKeyPressed(Key.RIGHT)) {
				Partie.getP1().seDeplacer(Direction.bas_droit, t);
			} else if (Keyboard.isKeyPressed(Key.LEFT)) {
				Partie.getP1().seDeplacer(Direction.bas_gauche, t);

			} else {
				Partie.getP1().seDeplacer(Direction.bas, t);
			}
		} else if (Keyboard.isKeyPressed(Key.RIGHT)) {
			Partie.getP1().seDeplacer(Direction.droit, t);
		} else if (Keyboard.isKeyPressed(Key.LEFT)) {
			Partie.getP1().seDeplacer(Direction.gauche, t);
		} else {
			Partie.getP1().updateSprite(Partie.getP1().getDirection(), Etat.arret);
		}
	}

	public static void getMove4J(Time t) {
		// JOUEUR  1 : Guerrier
		/*
		 * Z = HAUT
		 * D = DROITE
		 * Q = GAUCHE
		 * S = BAS
		 */
		if (Keyboard.isKeyPressed(Key.Z)) {
			if (Keyboard.isKeyPressed(Key.D)) {
				Partie.getP1().seDeplacer(Direction.haut_droit, t);
			} else if (Keyboard.isKeyPressed(Key.Q)) {
				Partie.getP1().seDeplacer(Direction.haut_gauche, t);
			} else {
				Partie.getP1().seDeplacer(Direction.haut, t);
			}
		} else if (Keyboard.isKeyPressed(Key.S)) {
			if (Keyboard.isKeyPressed(Key.D)) {
				Partie.getP1().seDeplacer(Direction.bas_droit, t);
			} else if (Keyboard.isKeyPressed(Key.Q)) {
				Partie.getP1().seDeplacer(Direction.bas_gauche, t);
			} else {
				Partie.getP1().seDeplacer(Direction.bas, t);
			}
		} else if (Keyboard.isKeyPressed(Key.D)) {
			Partie.getP1().seDeplacer(Direction.droit, t);
		} else if (Keyboard.isKeyPressed(Key.Q)) {
			Partie.getP1().seDeplacer(Direction.gauche, t);
		} else {
			Partie.getP1().updateSprite(Partie.getP1().getDirection(), Etat.arret);
		}
		// JOUEUR 2 :Elfe
				/*
				 * NUMPAD8 = HAUT
				 * NUMPAD6 = DROITE
				 * NUMPAD4 = GAUCHE
				 * NUMPAD5 = BAS
				 */
			if (Keyboard.isKeyPressed(Key.NUMPAD8)) {
					if (Keyboard.isKeyPressed(Key.NUMPAD6)) {
						Partie.getP2().seDeplacer(Direction.haut_droit, t);
					} else if (Keyboard.isKeyPressed(Key.NUMPAD4)) {
						Partie.getP2().seDeplacer(Direction.haut_gauche, t);
					} else {
						Partie.getP2().seDeplacer(Direction.haut, t);
					}
				} else if (Keyboard.isKeyPressed(Key.NUMPAD5)) {
					if (Keyboard.isKeyPressed(Key.NUMPAD6)) {
						Partie.getP2().seDeplacer(Direction.bas_droit, t);
					} else if (Keyboard.isKeyPressed(Key.NUMPAD4)) {
						Partie.getP2().seDeplacer(Direction.bas_gauche, t);
					} else {
						Partie.getP2().seDeplacer(Direction.bas, t);
					}
				} else if (Keyboard.isKeyPressed(Key.NUMPAD6)) {
					Partie.getP2().seDeplacer(Direction.droit, t);
				} else if (Keyboard.isKeyPressed(Key.NUMPAD4)) {
					Partie.getP2().seDeplacer(Direction.gauche, t);
				} else {
					Partie.getP2().updateSprite(Partie.getP2().getDirection(), Etat.arret);
				}
		
			// JOUEUR 3 : Magicien
			/*
			 * O = HAUT
			 * M = DROITE
			 * K = GAUCHE
			 * L = BAS
			 */
			if (Keyboard.isKeyPressed(Key.O)) {
				
				if (Keyboard.isKeyPressed(Key.M)) {
					Partie.getP3().seDeplacer(Direction.haut_droit, t);
				} else if (Keyboard.isKeyPressed(Key.K)) {
					Partie.getP3().seDeplacer(Direction.haut_gauche, t);
				} else {
					Partie.getP3().seDeplacer(Direction.haut, t);
				}
			} else if (Keyboard.isKeyPressed(Key.L)) {
				if (Keyboard.isKeyPressed(Key.M)) {
					Partie.getP3().seDeplacer(Direction.bas_droit, t);
				} else if (Keyboard.isKeyPressed(Key.K)) {
					Partie.getP3().seDeplacer(Direction.bas_gauche, t);

				} else {
					Partie.getP3().seDeplacer(Direction.bas, t);
				}
			} else if (Keyboard.isKeyPressed(Key.M)) {
				Partie.getP3().seDeplacer(Direction.droit, t);
			} else if (Keyboard.isKeyPressed(Key.K)) {
				Partie.getP3().seDeplacer(Direction.gauche, t);
			} else {
				Partie.getP3().updateSprite(Partie.getP3().getDirection(), Etat.arret);
			}
			
			
			// JOUEUR 4 : Valkyrie
		/*
		 * T = HAUT
		 * H = DROITE
		 * F = GAUCHE
		 * G = BAS
		 */
		if (Keyboard.isKeyPressed(Key.T)) {
			if (Keyboard.isKeyPressed(Key.H)) {
				Partie.getP4().seDeplacer(Direction.haut_droit, t);
			} else if (Keyboard.isKeyPressed(Key.F)) {
				Partie.getP4().seDeplacer(Direction.haut_gauche, t);

			} else {
				Partie.getP4().seDeplacer(Direction.haut, t);
			}
		} else if (Keyboard.isKeyPressed(Key.G)) {
			if (Keyboard.isKeyPressed(Key.H)) {
				Partie.getP4().seDeplacer(Direction.bas_droit, t);
			} else if (Keyboard.isKeyPressed(Key.F)) {
				Partie.getP4().seDeplacer(Direction.bas_gauche, t);
			} else {
				Partie.getP4().seDeplacer(Direction.bas, t);
			}
		} else if (Keyboard.isKeyPressed(Key.H)) {
			Partie.getP4().seDeplacer(Direction.droit, t);
		} else if (Keyboard.isKeyPressed(Key.F)) {
			Partie.getP4().seDeplacer(Direction.gauche, t);
		} else {
			Partie.getP4().updateSprite(Partie.getP4().getDirection(), Etat.arret);
		}
	}
}
