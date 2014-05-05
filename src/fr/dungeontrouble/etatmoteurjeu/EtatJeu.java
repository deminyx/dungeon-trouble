package fr.dungeontrouble.etatmoteurjeu;

import org.jsfml.system.Clock;

import fr.dungeontrouble.evenement.Evenement;
import fr.dungeontrouble.partie.Partie;
import fr.dungeontrouble.partie.entite.Personnage.TypePersonnage;
import fr.dungeontrouble.partie.niveau.Niveau;

public class EtatJeu {

	private Clock clock;
	private Evenement event;
	
	 public EtatJeu(int nbJoueurs) {

		 this.clock=new Clock();
		 this.event=null;
		 Niveau.IDNiveau n=Niveau.IDNiveau.map1;
		 TypePersonnage p = TypePersonnage.guerrier;
		 if (nbJoueurs==1){
			 Partie.InitPartie(n,p); // ajout de l'id map + 1 joueurs
		 }
		 if (nbJoueurs==4){
			 Partie.InitPartie(n); // ajout de l'id mp + 4 joueurs
		 }

	}
	
	
}
