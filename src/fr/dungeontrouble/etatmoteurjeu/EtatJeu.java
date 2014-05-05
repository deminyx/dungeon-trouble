package fr.dungeontrouble.etatmoteurjeu;

import org.jsfml.system.Clock;

import fr.dungeontrouble.evenement.Evenement;
import fr.dungeontrouble.partie.Partie;

public class EtatJeu {

	private Clock clock;
	private Partie partie;
	private Evenement event;
	
	 public EtatJeu(int nbJoueurs) {

		 this.clock=new Clock();
		 this.event=null;
		 if (nbJoueurs==1){
			 this.partie=new Partie(null,null); // ajout de l'id map + 1 joueurs
		 }
		 if (nbJoueurs==4){
			 this.partie=new Partie(null, null, null, null, null); // ajout de l'id mp + 4 joueurs
		 }

	}
	
	
}
