package fr.dungeontrouble.etatmoteurjeu;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * Classe gérant le palmarès des scores en fin de partie
 * @author Maxime BELLIER
 * @author Valentin PORCHET
 */
public class GestionScore {

	private LinkedHashMap<String, Integer> meilleursScores;
	
	/**
	 * Constructeur de GestionScore
	 * @param path String: chemin vers le fichier .txt contenant les scores.
	 */
	public GestionScore(String path) {
		this.setMeilleursScores(recupererScores("highscores.txt"));
	}
	
	/**
	 * Méthode de chargement des meilleurs scores à partir d'un fichier
	 * @param path Chemin vers le fichier contenant les meilleurs scores
	 * @return HashMap de la forme Joueur -> Score
	 */
	public static LinkedHashMap<String, Integer> recupererScores(String path){
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
				temp = scannerLine.nextLine();	// Récupération de la ligne	
				scannerTemp = new Scanner(temp); // Assignation au scanner
				highscores.put(scannerTemp.next(), scannerTemp.nextInt()); // Rajout des scores
			}
			
			// On ferme les Scanner
			scannerLine.close();
			scannerTemp.close();
		}
		catch (Exception e){
			e.printStackTrace(); // On catch l'exception en cas de probléme
		}
		
		return highscores;
	}
	
	public void changerPalmares() {
	}

	public void enregistrerScores() {
	}

	public LinkedHashMap<String, Integer> getMeilleursScores() {
		return meilleursScores;
	}

	public void setMeilleursScores(LinkedHashMap<String, Integer> meilleursScores) {
		this.meilleursScores = meilleursScores;
	}

	
}
