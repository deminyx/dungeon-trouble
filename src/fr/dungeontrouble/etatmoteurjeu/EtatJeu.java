package fr.dungeontrouble.etatmoteurjeu;

import org.jsfml.system.Clock;

import fr.dungeontrouble.affichage.Affichage;
import fr.dungeontrouble.evenement.Evenement;
import fr.dungeontrouble.partie.Partie;

public class EtatJeu {

	private Clock clock;
	private Partie partie;
	private Affichage affichage;
	private Evenement event;
	
	 public EtatJeu() {

		 this.clock=new Clock();
		 this.partie=new Partie(MoteurJeu.getNbJoueurs());
		 this.event=null;
		 this.affichage=null;
	}
	
	
}
