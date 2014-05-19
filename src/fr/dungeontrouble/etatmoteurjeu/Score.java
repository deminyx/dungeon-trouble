package fr.dungeontrouble.etatmoteurjeu;

import fr.dungeontrouble.partie.entite.Personnage.TypePersonnage;

/**
 * Classe implémentant un score
 * @author Maxime BELLIER
 *
 */
public class Score {
	public TypePersonnage t;
	public int scoreFinal;
	
	
	public Score(String type, int scoreFinal) {
		super();
		
		switch(type){
			case "guerrier":
				this.t = TypePersonnage.guerrier;
				break;
			case "valkyrie":
				this.t = TypePersonnage.valkyrie;
				break;
			case "elfe":
				this.t = TypePersonnage.elfe;
				break;
			case "magicien":
				this.t = TypePersonnage.magicien;
				break;	
		}
		this.scoreFinal = scoreFinal;
	}


	@Override
	public String toString() {
		return "Score [t=" + t + ", scoreFinal=" + scoreFinal + "]\n";
	}
	
	
}
