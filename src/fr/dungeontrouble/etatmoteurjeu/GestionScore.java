package fr.dungeontrouble.etatmoteurjeu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.jsfml.system.Vector2i;

import fr.dungeontrouble.partie.entite.Personnage;
import fr.dungeontrouble.partie.entite.Personnage.TypePersonnage;

/**
 * Classe gérant le palmarès des scores en fin de partie
 * 
 * @author Maxime BELLIER
 * @author Valentin PORCHET
 */
public class GestionScore {

	public LinkedHashMap<String, Score> meilleursScores;
	public ArrayList<Score> scoresFinPartie;

	/**
	 * Constructeur de GestionScore
	 * 
	 * @param path
	 *            String: chemin vers le fichier .txt contenant les scores.
	 */
	public GestionScore() {
		this.meilleursScores = recupererScores("highscores.txt");
		scoresFinPartie = new ArrayList<Score>();
	}

	/**
	 * Méthode de chargement des meilleurs scores à partir d'un fichier
	 * 
	 * @param path
	 *            Chemin vers le fichier contenant les meilleurs scores
	 * @return HashMap de la forme Joueur -> Score
	 */
	public static LinkedHashMap<String, Score> recupererScores(String path){
		LinkedHashMap<String,Score> highscores = new LinkedHashMap<String,Score>();
		String temp = new String(); // Chaine temporaire qui contiendra chaque ligne
		
		try{			
			/* On utilise deux Scanner : un pour extraire chaque ligne
			 * et un autre pour les analyser  
			 */
			
			// Scanner extracteur de lignes
			Scanner scannerLine = new Scanner(new File("highscores.txt"));
			
			// Scanner analyseur de lignes
			Scanner scannerTemp = new Scanner(temp);
			
			while (scannerLine.hasNextLine()){
				temp = scannerLine.nextLine();	// Récupération de la ligne	
				scannerTemp = new Scanner(temp); // Assignation au scanner
				highscores.put(scannerTemp.next(), new Score(scannerTemp.next(),scannerTemp.nextInt())); // Rajout des scores
			}
			
			// On ferme les Scanner
			scannerLine.close();
			scannerTemp.close();
			
			System.out.println(highscores);
		}
		catch (Exception e){
			e.printStackTrace(); // On catch l'exception en cas de probléme
		}
		
		return highscores;
	}

	public void changerMeilleursScores() {
		String nomScoreMin = (String) (meilleursScores.keySet().toArray()[meilleursScores
				.size() - 1]);
		// pour chaque personnge ayant joué
		for (Score s : scoresFinPartie) {
			if (meilleursScores.get(nomScoreMin).scoreFinal < s.scoreFinal) {
				// Oui, il a un meilleur score, on récupère son nom
				System.out.println("Vous avez fait un nouveau meilleur score !");
				System.out.println("Votre classe : " + s.t.name() + " et votre score : " + s.scoreFinal);
				System.out.print("Rentrez votre pseudo : ");
				Scanner scan = new Scanner(System.in);
				String nomJoueur = scan.next();
				scan.close();
				// cas 1 : Il a déjà joué et son score doit être mis à jour
				if ((meilleursScores.containsKey(nomJoueur))
						&& (meilleursScores.get(nomJoueur).scoreFinal < s.scoreFinal)) {
					meilleursScores.put(nomJoueur, s);
				}
				// cas 2 : Il n'est pas dans les meilleurs scores
				else {
					meilleursScores.remove(nomScoreMin);
					meilleursScores.put(nomJoueur, s);

				}

			}
		}
		//Tri de la map des Scores
		List<Map.Entry<String, Score>> entries = new ArrayList<Map.Entry<String, Score>>(
				meilleursScores.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, Score>>() {
			public int compare(Map.Entry<String, Score> a,
					Map.Entry<String, Score> b) {
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

	public void enregistrerScores() {
		String temp = new String(); // Chaine temporaire qui contiendra chaque
									// ligne
		// on va chercher le chemin et le nom du fichier et on me tout ca dans
		// un String
		String adresseDuFichier = "highscores.txt";

		// on met try si jamais il y a une exception
		try {
			/*
			 * BufferedWriter a besoin d un FileWriter, les 2 vont ensemble, on
			 * donne comme argument le nom du fichier
			 */
			FileWriter fw = new FileWriter(adresseDuFichier, false);

			// le BufferedWriter output auquel on donne comme argument le
			// FileWriter fw cree juste au dessus
			BufferedWriter output = new BufferedWriter(fw);

			for (String s : meilleursScores.keySet()) {
				temp = "" + s + " " + meilleursScores.get(s).t.toString() + " "
						+ String.valueOf(meilleursScores.get(s).scoreFinal)
						+ "\n";
				output.write(temp);
				System.out.print(temp);
			}
			// on marque dans le fichier ou plutot dans le BufferedWriter qui
			// sert comme un tampon(stream)

			// on peut utiliser plusieurs fois methode write

			output.flush();
			// ensuite flush envoie dans le fichier, ne pas oublier cette
			// methode pour le BufferedWriter

			output.close();
			// et on le ferme
		} catch (IOException ioe) {
			System.out.print("Erreur : ");
			ioe.printStackTrace();
		}
	
	}
	public void declareScoresFinaux(HashMap<Vector2i,Personnage> personnages){
		for(Personnage p : personnages.values()){
			scoresFinPartie.add(new Score(p.getPerso().name(),p.getScore()));
		}
	}
	
}
