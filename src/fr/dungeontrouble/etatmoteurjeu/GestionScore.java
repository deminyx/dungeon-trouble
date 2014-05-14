package fr.dungeontrouble.etatmoteurjeu;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import fr.dungeontrouble.partie.entite.Personnage.TypePersonnage;

/**
 * Classe gérant le palmarès des scores en fin de partie
 * 
 * @author Maxime BELLIER
 * @author Valentin PORCHET
 */
public class GestionScore {

	public LinkedHashMap<String, Score> meilleursScores;
	public LinkedHashMap<String, Score> scoresFinPartie;

	/**
	 * Constructeur de GestionScore
	 * 
	 * @param path
	 *            String: chemin vers le fichier .txt contenant les scores.
	 */
	public GestionScore(String path) {
		this.meilleursScores = recupererScores("highscores.txt");
		scoresFinPartie = new LinkedHashMap<>();
	}

	/**
	 * Méthode de chargement des meilleurs scores à partir d'un fichier
	 * 
	 * @param path
	 *            Chemin vers le fichier contenant les meilleurs scores
	 * @return HashMap de la forme Joueur -> Score
	 */
	public static LinkedHashMap<String, Score> recupererScores(String path) {
		LinkedHashMap<String, Integer> highscores = new LinkedHashMap<String, Integer>();
		String temp = new String(); // Chaine temporaire qui contiendra chaque
									// ligne

		try {
			/*
			 * On utilise deux Scanner : un pour extraire chaque ligne et un
			 * autre pour les analyser
			 */

			// Scanner extracteur de lignes
			Scanner scannerLine = new Scanner(temp.getClass()
					.getResourceAsStream("/" + path));

			// Scanner analyseur de lignes
			Scanner scannerTemp = new Scanner(temp);

			while (scannerLine.hasNextLine()) {
				temp = scannerLine.nextLine(); // Récupération de la ligne
				scannerTemp = new Scanner(temp); // Assignation au scanner
				highscores.put(scannerTemp.next(), scannerTemp.nextInt()); // Rajout
																			// des
																			// scores
			}

			// On ferme les Scanner
			scannerLine.close();
			scannerTemp.close();
		} catch (Exception e) {
			e.printStackTrace(); // On catch l'exception en cas de probléme
		}

		return highscores;
	}

	public void changerMeilleursScores() {
		String nomScoreMin = (String) (meilleursScores.keySet().toArray()[meilleursScores
				.size() - 1]);
		// pour chaque personnge ayant joué
		for (Score s : scoresFinPartie.values()) {
			if (meilleursScores.get(nomScoreMin).scoreFinal < s.scoreFinal) {
				// Oui, il a un meilleur score, on récupère son nom
				Scanner scan = new Scanner(System.in);
				String nomJoueur = scan.next();
				scan.close();
				// cas 1 : Il a déjà joué et son score doit être mis à jour
				if ((meilleursScores.containsKey(nomJoueur))
						&& (meilleursScores.get(nomJoueur).scoreFinal<s.scoreFinal)) {
					meilleursScores.put(nomJoueur, s);
				}
				// cas 2 : Il n'est pas dans les meilleurs scores
				else {
					meilleursScores.remove(nomScoreMin);
					meilleursScores.put(nomJoueur, s);

				}
				
				// de la map des Scores
				List<Map.Entry<String, Score>> entries =
						  new ArrayList<Map.Entry<String, Score>>(meilleursScores.entrySet());
						Collections.sort(entries, new Comparator<Map.Entry<String, Score>>() {
						  public int compare(Map.Entry<String, Score> a, Map.Entry<String, Score> b){
							  Integer scoreA = new Integer(a.getValue().scoreFinal);
							  Integer scoreB = new Integer(b.getValue().scoreFinal);
						    return scoreA.compareTo(scoreB);
						  }
						});
						Map<String, Score> sortedMap = new LinkedHashMap<String, Score>();
						for (Map.Entry<String, Score> entry : entries) {
						  sortedMap.put(entry.getKey(), entry.getValue());
						}
			}
		}
	}

	public void enregistrerScores() {
	}

}