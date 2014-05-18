package fr.dungeontrouble.etatmoteurjeu;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
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
 * Classe, point d'entr�e du programme, initialisant une partie et contenant
 * l'ensemble des instructions du jeu (main).
 * @author Maxime BELLIER
 * @author Valentin PORCHET
 */
public class EtatJeu{
	
	public static int nbJoueurs;
	public static int numNiveau;
	
	/**
	 * Constructeur � l'origine de la cr�ation de toutes classes du programme
	 * @param p Type du personnage (guerrier, elfe, magicien ou valkyrie)
	 * @param numNiveau num�ro du niveau voulu (1 � 3)
	 */
	 public EtatJeu(TypePersonnage p, int numNiveau) {
		 // On r�cup�re l'id du niveau
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
	  * M�thode de r�cup�ration des arguments de lancement
	  * @param args arguments de lancement du jeu
	  */
	public static void recupererArguments(String[] args){
		// Si aucun argument n'a �t� donn� en param�tre
		if (args.length == 0){
			System.out.println("Erreur : aucun argument fourni");
			System.out.println("Lancement : java -jar dungeontrouble.jar nbJoueurs numNiveau");
			System.exit(0);
		} else if (args.length == 1){
			System.out.println("Erreur : num�ro du niveau manquant");
			System.out.println("Lancement : java -jar dungeontrouble.jar nbJoueurs numNiveau");
			System.exit(0);
		}
				
		// Sinon, on stocke les donnes fournies
		nbJoueurs = Integer.parseInt(args[0]);
		numNiveau = Integer.parseInt(args[1]);
				
		// V�rification de la validit� des donn�es
		if (nbJoueurs != 1 && nbJoueurs !=4){
			System.out.println("Erreur, le nombre de joueurs doit �tre 1 ou 4");
			System.out.println("Lancement : java -jar dungeontrouble.jar nbJoueurs numNiveau");
			System.exit(0);
		}
		else if (numNiveau < 1 || numNiveau > 3){
			System.out.println("Erreur, le niveau doit �tre compris entre 1 et 3");
			System.out.println("Lancement : java -jar dungeontrouble.jar nbJoueurs numNiveau");
			System.exit(0);
		}
	}
	
	/**
	 * M�thode d'affichage des meilleurs scores � l'initialisation du jeu
	 * @param window Fen�tre de rendu
	 * @return bool�en qui pour quitter le programme ou non
	 */
	public static boolean drawMeilleursScores(RenderWindow window){
		
		boolean gameIsRunning = true;
		
		// On affiche les meilleurs scores
		Affichage affBestScores = new AffichageMeilleursScores("highscores_"+numNiveau+".txt");
		window.clear();
		window.draw(affBestScores);
		window.display();
					
		// Affichage des meilleurs scores tant que la touche Entre n'est pas press�e
		boolean affichageMeilleursScores = true;
		while (affichageMeilleursScores){
			for(Event event : window.pollEvents()) {
				switch(event.type)
				{
					case CLOSED:
						affichageMeilleursScores = false;
						gameIsRunning = false;
						window.close();
						break;		
								
					case KEY_PRESSED:
						affichageMeilleursScores = !Keyboard.isKeyPressed(Key.RETURN);									
						break;
								
					default:break;
				}
			}
		}
		
		return gameIsRunning;
	}
	
	/**
	 * M�thode d'affichage de fin du jeu
	 * @param window Fen�tre de rendu
	 * @param gameIsRunning bool�en indiquant si le jeu tourne encore
	 * @param affJeu Affichage du terrain de jeu
	 * @param affScores Affichage des scores en jeu
	 */
	public static void drawFinJeu(RenderWindow window, boolean gameIsRunning, AffichageJeu affJeu, AffichageScore affScores){
		
		Texture textPartieTerminee = Affichage.loadTexture("partie_terminee.png");
		Sprite partieTerminee = new Sprite(textPartieTerminee);
		
		window.clear();
		window.draw(affJeu); // Dessin de la map
		window.draw(affScores); // Dessin des scores
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
	 * M�thode d'affichage du choix de personnage
	 * @param window Fen�tre dans laquelle afficher
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
	 * Point d'entr�e du programme lancant le jeu
	 * @param args Arguments du programme : nombre de joueurs et num�ro du niveau
	 */
	public static void main(String[] args){
		
		// On lance une m�thode si on est sur un syst�me Unix
		String osName = System.getProperty("os.name");
		if (!osName.contains("Windows")) // Si ce n'est pas un syst�me Windows
			System.loadLibrary("xx");
		
		// On r�cup�re les arguments dans des variables globales
		recupererArguments(args);
		
		// On initialise la fen�tre
		RenderWindow window = new RenderWindow(new VideoMode(Affichage.LARGEUR,Affichage.HAUTEUR), "Dungeon Trouble",RenderWindow.CLOSE | RenderWindow.TITLEBAR);
		window.setVerticalSyncEnabled(true); // Activation de la synchronisation verticale
		window.setKeyRepeatEnabled(false); // D�sactivation de la r�p�tition des touches
		
		while (window.isOpen()){
			// Bool�ens
			boolean gameIsRunning = true;
			boolean gameWon = false;
			
			// On affiche les meilleurs scores
			gameIsRunning = drawMeilleursScores(window);
			
			// On r�cup�re le choix du joueur
			if (!gameIsRunning){
				System.exit(0);
			}
			
			TypePersonnage choixDuJoueur = drawChoice(window);
			
			// Initialisation de l'etat du jeu et de la partie
			@SuppressWarnings("unused")
			EtatJeu etat = new EtatJeu(choixDuJoueur, numNiveau);
			
			// Initialisation des affichages en jeu
			Affichage affJeu = new AffichageJeu();
			Affichage affScores = new AffichageScore(nbJoueurs);
			Partie.setCentreVue(((AffichageJeu)affJeu).getCenter());
			
			// Initialisation des chronom�tres de mesure de temps
			// et des personnages
			Clock gameClock = new Clock();
			Clock clock = new Clock();
			Personnage.init();
			
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
				
				// Gestion des �v�nements de mouvement
				if (nbJoueurs == 1)
					MoveEvent.getMove1J(timeElapsed);
				else
					MoveEvent.getMove4J(timeElapsed);
							
				//---- Mises � jours (d�placements, g�n�ration de monstres) ----//
				
				// Mise � jour des positions des personnages dans le hashmap
				Personnage.majPos();
				
				// G�n�ration de nouveaux monstres
				// Limite de g�n�ration : 50
				if (Partie.getMonstres().size() < 50){
					for (Objet o : Niveau.getObjets().values()){
						if ((o instanceof Generateur)&&(Partie.getMonstres().keySet().size() < 50)){
							((Generateur)o).genererMonstres(((AffichageJeu)affJeu).getCenter());
						}
					}
				}
				
				// D�placement des monstres
				if (timeElapsed.asSeconds() < 1){
					Vector2i oldPosition = null;
					
					for (int i=0; i < Partie.getMonstres().size(); i++){
						Monstre m = (Monstre)(Partie.getMonstres().values().toArray()[i]);
						oldPosition = m.getPosition();
						
						// Si le d�placement s'est effectu�, on met � jour la position dans la map
						if (m.seDeplacerVersCible(timeElapsed)){
							// On met bien � jour dans le hashmap
							Partie.getMonstres().remove(oldPosition);
							Partie.getMonstres().put(m.getPosition(), m);
						}
					}
				}
				
				// Elimination des monstres trop loins des joueurs //
				eliminerMonstresTropLoin(Partie.getCentreVue());
				
				//---- Mise � jour des scores et fin de jeu ----//
				
				// Pertes de points de vies
				if (gameClock.getElapsedTime().asSeconds() > 1){
					for (Personnage p : Partie.getPersonnages().values()){
						p.setScore(p.getScore()-1);
					}
					gameClock.restart();
				}
				
				// On v�rifie si les personnages sont tous en vie
				Personnage.verifierSiVivant((AffichageScore)affScores);
							
				// On v�rifie qu'il reste un personnage en vie
				// Si tout le monde est mort, on ferme la fen�tre
				if (Partie.getPersonnages().isEmpty()){
					drawFinJeu(window, gameIsRunning, (AffichageJeu)affJeu, (AffichageScore)affScores);
					System.out.println("Game Over");
					gameIsRunning = false;
					window.close();
				}
				
				// On v�rifie si les personnages sont arriv�s � la sortie
				for (Personnage p : Partie.getPersonnages().values()){
					if (p.verifierSortie()){
						gameWon = true;
						gameIsRunning = false;
						window.close();
					}
				}
				
				//---- Mise � jour des armes ----//
				
				// Mise � jour des positions des armes et v�rification des collisions
				for (Personnage p : Partie.getPersonnages().values()){
					p.bougerArmes(timeElapsed, ((AffichageJeu)affJeu).getCenter());
					p.verifierCollisionArmes(timeElapsed);
				}
				
				//---- Mise � jour de l'affichage ----//
				
				//System.out.println(Generateur.nbMonstres);
				
				// Mise � jour de la vue
				((AffichageJeu) affJeu).updateView();
				
				// On met � jour le centre de vue dans la partie
				Partie.setCentreVue(((AffichageJeu)affJeu).getCenter());
				
				// Mise � jour des clefs et des scores
				((AffichageScore) affScores).updateScores();		
				
				// On efface l'affichage
				window.clear();
				
				window.draw(affJeu); // Dessin de la map
				window.draw(affScores); // Dessin des scores				
				
				// On affiche
				window.display();
			}
			
			// Si on a bien gagn�
			if (gameWon){
				// On v�rifie s'il reste des personnages vivants
				if (!Partie.getPersonnages().isEmpty()){
					System.out.println("Niveau termin� !");
					
					GestionScore g = new GestionScore("highscores_"+numNiveau+".txt");
					g.declareScoresFinaux(Partie.getPersonnages());
					g.changerMeilleursScores();
					g.enregistrerScores();
				}
			}
			
			// On ferme proprement
			System.exit(0);			
		}		
	}

	/**
	 * M�thode qui permet d'�liminer les monstres trop loins des joueurs
	 * @param centreVue centre de la vue actuelle
	 */
	private static void eliminerMonstresTropLoin(Vector2f centreVue) {
		Vector2f firstLimitPos = new Vector2f(centreVue.x - 500, centreVue.y - 500);
		Vector2f secondLimitPos = new Vector2f(centreVue.x + 500, centreVue.y + 500);
		Vector2f pos = null;
		
		for (int i=0; i < Partie.getMonstres().size(); i++){
			Monstre m = (Monstre)(Partie.getMonstres().values().toArray()[i]);
			pos = m.getSprite().getPosition();
			
			// Si le monstre est en dehors des limites, on l'efface
			if ((pos.x < firstLimitPos.x)||
				(pos.x > secondLimitPos.x)||
				(pos.y < firstLimitPos.y)||
				(pos.y > secondLimitPos.y)){
				Partie.getMonstres().remove(m.getPosition());
				Generateur.nbMonstres--;
			}
		}
	}
}
