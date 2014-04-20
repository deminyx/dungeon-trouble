package fr.dungeontrouble.partie.niveau;

/**
 * Classe associée aux portes du jeu.
 * @author Maxime BELLIER
 *
 */
public class Porte extends Objet {

	private boolean isOpen ;

	public boolean getIsOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
}
