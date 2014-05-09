package fr.dungeontrouble.etatmoteurjeu;

import org.jsfml.system.Clock;

import fr.dungeontrouble.evenement.Evenement;
import fr.dungeontrouble.partie.Partie;
import fr.dungeontrouble.partie.entite.Personnage.TypePersonnage;
import fr.dungeontrouble.partie.niveau.Niveau;

/**
 * Classe, point d'entrée du programme, initialisant une partie et contenant
 * l'ensemble des instructions du jeu (main).
 * @author Maxime BELLIER
 *
 */
public class EtatJeu{

	private static int nbJoueurs;
	private Clock clock;
	private Evenement event;
	private GestionScore gestionScores;
	
	/**
	 * Constructeur à l'origine de la création de toutes classes du programme
	 * @param nbJoueurs Nombre de joueurs jouant la partie (1 ou 4)
	 */
	 public EtatJeu(int nbJoueurs) {
		 this.nbJoueurs=nbJoueurs;
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
