package fr.dungeontrouble.etatmoteurjeu;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Scanner;

import fr.dungeontrouble.evenement.Evenement;

public class MoteurJeu {

	private static int nbJoueurs;
	private LinkedHashMap<String, Integer> meilleursScores;

	
	
	public MoteurJeu(int nbJoueurs,String path) {
		this.nbJoueurs=nbJoueurs;
		this.meilleursScores=recupererScores("highscores.txt");
	}
	
	/**
	 * M�thode de chargement des meilleurs scores � partir d'un fichier
	 * @param path Chemin vers le fichier contenant les meilleurs scores
	 * @return HashMap de la forme Joueur -> Score
	 */
	private static LinkedHashMap<String, Integer> recupererScores(String path){
		LinkedHashMap<String,Integer> highscores = new LinkedHashMap<String,Integer>();
		String temp = new String(); // Chaine temporaire qui contiendra chaque ligne
		
		try{			
			/* On utilise deux Scanner : un pour extraire chaque ligne
			 * et un autre pour les analyser  
			 */
			
			// Scanner extracteur de lignes
			Scanner scannerLine = new Scanner(new File(path));
			
			// Scanner analyseur de lignes
			Scanner scannerTemp = new Scanner(temp);
			
			while (scannerLine.hasNextLine()){
				temp = scannerLine.nextLine();	// R�cup�ration de la ligne	
				scannerTemp = new Scanner(temp); // Assignation au scanner
				highscores.put(scannerTemp.next(), scannerTemp.nextInt()); // Rajout des scores
			}
			
			// On ferme les Scanner
			scannerLine.close();
			scannerTemp.close();
		}
		catch (Exception e){
			e.printStackTrace(); // On catch l'exception en cas de probl�me
		}
		
		return highscores;
	}
	
	
	public static int getNbJoueurs() {
		return nbJoueurs;
	}

	public void setNbJoueurs(int nbJoueurs) {
		this.nbJoueurs = nbJoueurs;
	}

	public void analyserEvent(Evenement e) {
	}

	public void changerPalmares() {
	}

	public void enregistrerScores() {
	}

	
}