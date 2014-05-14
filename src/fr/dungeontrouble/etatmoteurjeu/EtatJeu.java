package fr.dungeontrouble.etatmoteurjeu;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import fr.dungeontrouble.affichage.Affichage;
import fr.dungeontrouble.affichage.AffichageChoix;
import fr.dungeontrouble.affichage.AffichageJeu;
import fr.dungeontrouble.affichage.AffichageMeilleursScores;
import fr.dungeontrouble.affichage.AffichageScore;
import fr.dungeontrouble.evenement.ActionEvent;
import fr.dungeontrouble.evenement.ChoiceEvent;
import fr.dungeontrouble.evenement.MoveEvent;
import fr.dungeontrouble.partie.Partie;
import fr.dungeontrouble.partie.entite.Monstre;
import fr.dungeontrouble.partie.entite.Personnage;
import fr.dungeontrouble.partie.entite.Personnage.TypePersonnage;
import fr.dungeontrouble.partie.niveau.Generateur;
import fr.dungeontrouble.partie.niveau.Niveau;
import fr.dungeontrouble.partie.niveau.Objet;

/**
 * Classe, point d'entrée du programme, initialisant une partie et contenant
 * l'ensemble des instructions du jeu (main).
 * @author Maxime BELLIER
 * @author Valentin PORCHET
 */
public class EtatJeu{
	
	public static int nbJoueurs;
	public static int numNiveau;
	
	/**
	 * Constructeur à l'origine de la création de toutes classes du programme
	 * @param p Type du personnage (guerrier, elfe, magicien ou valkyrie)
	 * @param numNiveau numéro du niveau voulu (1 à 3)
	 */
	 public EtatJeu(TypePersonnage p, int numNiveau) {
		 // On récupère l'id du niveau
		 Niveau.IDNiveau n = Niveau.IDNiveau.map1;	 
		 switch(numNiveau){
			 case 1:
				 n = Niveau.IDNiveau.map1;
				 break;
			 case 2:
				 n = Niveau.IDNiveau.map2;
				 break;
			 case 3:
				 n = Niveau.IDNiveau.map3;
				 break;		
		 }
		 
		 if (nbJoueurs==1){
			 Partie.InitPartie(n,p); // ajout de l'id map + 1 joueurs
		 }
		 if (nbJoueurs==4){
			 Partie.InitPartie(n); // ajout de l'id mp + 4 joueurs
		 }
	}
	
	 /**
	  * Méthode de récupération des arguments de lancement
	  * @param args arguments de lancement du jeu
	  */
	public static void recupererArguments(String[] args){
		// Si aucun argument n'a été donné en paramètre
		if (args.length == 0){
			System.out.println("Erreur : aucun argument fourni");
			System.out.println("Lancement : java -jar dungeontrouble.jar nbJoueurs numNiveau");
			System.exit(0);
		} else if (args.length == 1){
			System.out.println("Erreur : numéro du niveau manquant");
			System.out.println("Lancement : java -jar dungeontrouble.jar nbJoueurs numNiveau");
			System.exit(0);
		}
				
		// Sinon, on stocke les donnes fournies
		nbJoueurs = Integer.parseInt(args[0]);
		numNiveau = Integer.parseInt(args[1]);
				
		// Vérification de la validité des données
		if (nbJoueurs != 1 && nbJoueurs !=4){
			System.out.println("Erreur, le nombre de joueurs doit être 1 ou 4");
			System.out.println("Lancement : java -jar dungeontrouble.jar nbJoueurs numNiveau");
			System.exit(0);
		}
		else if (numNiveau < 1 || numNiveau > 3){
			System.out.println("Erreur, le niveau doit être compris entre 1 et 3");
			System.out.println("Lancement : java -jar dungeontrouble.jar nbJoueurs numNiveau");
			System.exit(0);
		}
	}
	
	/**
	 * Méthode d'affichage des meilleurs scores à l'initialisation du jeu
	 * @param window Fenêtre de rendu
	 */
	public static void drawMeilleursScores(RenderWindow window){
		// On affiche les meilleurs scores
		Affichage affBestScores = new AffichageMeilleursScores();
		window.clear();
		window.draw(affBestScores);
		window.display();
					
		// Affichage des meilleurs scores tant que la touche Entre n'est pas pressée
		boolean affichageMeilleursScores = true;
		while (affichageMeilleursScores){
			for(Event event : window.pollEvents()) {
				switch(event.type)
				{
					case CLOSED:
						affichageMeilleursScores = false;
						window.close();
						break;		
								
					case KEY_PRESSED:
						affichageMeilleursScores = !Keyboard.isKeyPressed(Key.RETURN);									
						break;
								
					default:break;
				}
			}
		}
	}
	
	public static void drawFinJeu(RenderWindow window, boolean gameIsRunning){
		
		Texture textPartieTerminee = Affichage.loadTexture("partie_terminee.png");
		Sprite partieTerminee = new Sprite(textPartieTerminee);
		
		window.setView(window.getDefaultView());	
		window.draw(partieTerminee);
		window.display();
		
		Clock c = new Clock();
		while (c.getElapsedTime().asSeconds() < 2) {
			for (Event event : window.pollEvents()) {
				switch (event.type) {
					case CLOSED:
						gameIsRunning = false;
						window.close();
					break;
					
					default:break;
		       }
		    }
		}
	}
	
	/**
	 * Méthode d'affichage du choix de personnage
	 * @param window Fenêtre dans laquelle afficher
	 * @return Le type de personnage choisi, null si 4 joueurs
	 */
	public static TypePersonnage drawChoice(RenderWindow window){
		// Variable dans laquelle on va stocker le
		// type du personnage choisi pour un joueur
		TypePersonnage choix = null;
					
		// Si le mode de jeu est un jour, on fait le choix
		if (nbJoueurs == 1){
			// On affiche les choix
			Affichage affChoix = new AffichageChoix();
			window.clear();
			window.draw(affChoix);
			window.display();
			while (choix == null){
				choix = ChoiceEvent.getChoice(window);
			}
		}				
		
		// On retourne le choix
		return choix;
	}
	
	/**
	 * Point d'entrée du programme lancant le jeu
	 * @param args Arguments du programme : nombre de joueurs et numéro du niveau
	 */
	public static void main(String[] args){
		
		// On lance une méthode si on est sur un système Unix
		String osName = System.getProperty("os.name");
		System.out.println(osName);
		if (!osName.contains("Windows")) // Si ce n'est pas un système Windows
			System.loadLibrary("xx");
		
		// On récupère les arguments dans des variables globales
		recupererArguments(args);
		
		// On initialise la fenêtre
		RenderWindow window = new RenderWindow(new VideoMode(Affichage.LARGEUR,Affichage.HAUTEUR), "Dungeon Trouble",RenderWindow.CLOSE | RenderWindow.TITLEBAR);
		window.setVerticalSyncEnabled(true); // Activation de la synchronisation verticale
		window.setKeyRepeatEnabled(false); // Désactivation de la répétition des touches
		
		while (window.isOpen()){
			
			// On affiche les meilleurs scores
			drawMeilleursScores(window);
			
			// On récupère le choix du joueur
			TypePersonnage choixDuJoueur = drawChoice(window);
			
			// Initialisation de l'etat du jeu et de la partie
			@SuppressWarnings("unused")
			EtatJeu etat = new EtatJeu(choixDuJoueur, numNiveau);
			
			// Initialisation des affichages en jeu
			Affichage affJeu = new AffichageJeu();
			Affichage affScores = new AffichageScore(nbJoueurs);
			
			// Initialisation des chronomètres de mesure de temps
			// et des personnages
			Clock gameClock = new Clock();
			Clock clock = new Clock();
			Personnage.init();
			
			boolean gameIsRunning = true;
			boolean gameWon = false;
			
			while (gameIsRunning){
				Time timeElapsed = clock.restart();
				
				for(Event event : window.pollEvents()) {
					switch(event.type)
					{
						case CLOSED:
							gameIsRunning = false;
							window.close();
							break;		
						
						case KEY_PRESSED:
							if (nbJoueurs == 1)
								ActionEvent.getAction1J();	
							else
								ActionEvent.getAction4J();
							break;
						
						default:break;
					}
				}
				
				// Gestion des événements de mouvement
				if (nbJoueurs == 1)
					MoveEvent.getMove1J(timeElapsed);
				else
					MoveEvent.getMove4J(timeElapsed);
								
				// Mise à jour des positions des personnages dans le hashmap
				Personnage.majPos();
				
				// Génération de nouveaux monstres
				// Limite de génération : 50
				if (Partie.getMonstres().size() < 50){
					for (Objet o : Niveau.getObjets().values()){
						if ((o instanceof Generateur)&&(Partie.getMonstres().keySet().size() < 50)){
							((Generateur)o).genererMonstres(((AffichageJeu)affJeu).getCenter());
						}
					}
				}
				
				// Déplacement des monstres
				if (timeElapsed.asSeconds() < 1){
					Vector2i oldPosition = null;
					
					for (int i=0; i < Partie.getMonstres().size(); i++){
						Monstre m = (Monstre)(Partie.getMonstres().values().toArray()[i]);
						oldPosition = m.getPosition();
						
						// Si le déplacement s'est effectué, on met à jour la position dans la map
						if (m.seDeplacerVersCible(timeElapsed)){
							// On met bien à jour dans le hashmap
							Partie.getMonstres().remove(oldPosition);
							Partie.getMonstres().put(m.getPosition(), m);
						}
					}
				}
				
				// Pertes de points de vies
				if (gameClock.getElapsedTime().asSeconds() > 1){
					for (Personnage p : Partie.getPersonnages().values()){
						p.setScore(p.getScore()-1);
					}
					gameClock.restart();
				}
				
				// On vérifie si les personnages sont tous en vie
				Personnage.verifierSiVivant((AffichageScore)affScores);
							
				// On vérifie qu'il reste un personnage en vie
				// Si tout le monde est mort, on ferme la fenêtre
				if (Partie.getPersonnages().isEmpty()){
					drawFinJeu(window, gameIsRunning);
					System.out.println("Game Over");
					gameIsRunning = false;
					window.close();
				}
				
				// On vérifie si les personnages sont arrivés à la sortie
				for (Personnage p : Partie.getPersonnages().values()){
					if (p.verifierSortie()){
						gameWon = true;
						gameIsRunning = false;
						window.close();
					}
				}
							
				// Mise à jour des positions des armes et vérification des collisions
				for (Personnage p : Partie.getPersonnages().values()){
					p.bougerArmes(timeElapsed, ((AffichageJeu)affJeu).getCenter());
					p.verifierCollisionArmes(timeElapsed);
				}
				
				// Mise à jour de la vue
				((AffichageJeu) affJeu).updateView();
				
				// Mise à jour des clefs et des scores
				((AffichageScore) affScores).updateScores();		
				
				// On efface l'affichage
				window.clear();
				
				window.draw(affJeu); // Dessin de la map
				window.draw(affScores); // Dessin des scores				
				
				// On affiche
				window.display();
			}
			
			// Si on a bien gagné
			if (gameWon){
				// On vérifie s'il reste des personnages vivants
				if (!Partie.getPersonnages().isEmpty()){
					System.out.println("Niveau terminé !");
					
					GestionScore g = new GestionScore();
					g.declareScoresFinaux(Partie.getPersonnages());
					g.changerMeilleursScores();
					g.enregistrerScores();
				}
			}
			
			// On ferme proprement
			System.exit(0);			
		}		
	}
}
