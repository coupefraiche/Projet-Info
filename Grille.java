package project;

import java.util.Random;

public class Grille {
	// Attributs
	private int nbreLignes, nbreColonnes, nbreBombes;
	private int grille[][][] = null;
	private final int bombe = 666;
	private boolean gameOver;
	private int level;

	/* Constructeur */
	public Grille(int niveau) {
		niveau = level;

		switch (niveau) {
		case 1:
			nbreLignes = 10;
			nbreColonnes = 10;
			nbreBombes = 10;
			break;

		case 2:
			nbreLignes = 20;
			nbreColonnes = 20;
			nbreBombes = 42;
			break;

		case 3:
			nbreLignes = 20;
			nbreColonnes = 30;
			nbreBombes = 99;
			break;
		}

		// Initialiser la grille
		grille = new int[nbreLignes][nbreColonnes][2];

		this.init(); // Initialiser à 0 les cases de la grille
		this.placerBombes(nbreBombes); // Affectation bombes
		gameOver = false;
	}

	/* Accessors */
	// Permet de donner un état voulu à la case
	public void setState(int i, int j, int k) {
		grille[i][j][1] = k;
	}

	// Permet de donner une valeur voulue à la case
	public void setValue(int i, int j, int k) {
		grille[i][j][0] = k;
	}

	// Renvoie le nombre de bombes initialisé
	public int getNbreBombes() {
		return nbreBombes;
	}

	// Renvoie la valeur de la case
	public int getValue(int ligne, int colonne) {
		return grille[ligne][colonne][0];
	}

	// Renvoie l'état de la case : 0 -> non découverte, 1 -> découverte, 2 ->
	// drapeau placé
	public int getState(int ligne, int colonne) {
		return grille[ligne][colonne][1];
	}

	// Vérifie si la case est une bombe
	public boolean isBomb(int ligne, int colonne) {
		return (this.getValue(ligne, colonne) == bombe);
	}

	// Renvoie le nombre de lignes de la grille
	public int getNbreLignes() {
		return nbreLignes;
	}

	// Renvoie le nombre de colonnes de la grille
	public int getNbreColonnes() {
		return nbreColonnes;
	}

	// La partie est terminée
	public void gameIsOver() {
		gameOver = true;
	}

	// Permet de traquer l'état de la partie
	public boolean getGameOver() {
		return gameOver;
	}

	// Retourne le niveau sélectionné
	public int getLevel() {
		return level;
	}

	private void init() {
		// Procédure qui remplit les cases de 0 pour ne pas avoir de valeurs inattendues
		for (int i = 0; i < nbreLignes; i++) {
			for (int j = 0; j < nbreColonnes; j++) {
				this.setValue(i, j, 0); // Mettre à 0 la valeur
				this.setState(i, j, 0); // Mettre à 0 l'état
			}
		}
	}

	private void placerBombes(int nbreBombes) {
		int i, j, compteur = 0;
		Random random = new Random();

		// On génère une ligne et une colonne aléatoire pour la bombe
		do {
			i = random.nextInt(nbreLignes);
			j = random.nextInt(nbreColonnes);

			// Si la bombe n'existe pas au niveau de cette case, on place la bombe
			if (!this.isBomb(i, j)) {
				this.setValue(i, j, bombe);
			}
			compteur++;
		} while (compteur < nbreBombes);
	}

	private int bombesProches(int i, int j) {
		int bombeProche = 0;

		/*
		 * Si la case même ne contient pas de bombe, on cherche les 8 cases autour pour
		 * vérifier la présence de bombes
		 */
			if (this.getValue(i, j) != bombe) {
			if ((i - 1 >= 0) && (this.getValue(i - 1, j) == bombe))
				bombeProche++;
			if ((j - 1 >= 0) && (this.getValue(i, j - 1) == bombe))
				bombeProche++;
			if ((i + 1 >= 0) && (i+1<nbreLignes) && (this.getValue(i + 1, j) == bombe))
				bombeProche++;
			if ((j + 1 >= 0) && (j+1<nbreColonnes) && (this.getValue(i, j + 1) == bombe))
				bombeProche++;
			if ((i - 1 >= 0) && (j - 1 >= 0) && (this.getValue(i - 1, j - 1) == bombe))
				bombeProche++;
			if ((i + 1 >= 0) && (i+1<nbreLignes) && (j + 1 >= 0) && (j+1<nbreColonnes) && (this.getValue(i + 1, j + 1) == bombe))
				bombeProche++;
			if ((i + 1 >= 0) && (i+1<nbreLignes) && (j - 1 >= 0) && (this.getValue(i + 1, j - 1) == bombe))
				bombeProche++;
			if ((i - 1 >= 0) && (j + 1 >= 0) && (j+1<nbreColonnes) && (this.getValue(i - 1, j + 1) == bombe))
				bombeProche++;
		} else
			bombeProche = bombe;

		return bombeProche;
	}

	private void decouverteCase(int i, int j) {
		int temp = 0;

		// Si la case n'est pas découverte
		if (this.getState(i, j) == 0) {
			this.setState(i, j, 1); // On la passe à l'état 1 donc découverte

			temp = bombesProches(i, j); // On récupère le nombre de bombes sur les 8 cases qui l'entourent
			this.setValue(i, j, temp); // On donne la valeur à la case en fonction des bombes proches

			// On découvre les cases de manière récursive
			if (temp == 0) {
				if (i > 0)
					this.decouverteCase(i - 1, j);
				if (j > 0)
					this.decouverteCase(i, j - 1);
				if (i > 0 && j > 0)
					this.decouverteCase(i - 1, j - 1);
				if (i < nbreColonnes - 1)
					this.decouverteCase(i + 1, j);
				if (j < nbreColonnes - 1)
					this.decouverteCase(i, j + 1);
				if (i > 0 && j < nbreColonnes - 1)
					this.decouverteCase(i - 1, j + 1);
				if (i < nbreColonnes - 1 && j > 0)
					this.decouverteCase(i + 1, j - 1);
				if (i < nbreColonnes - 1 && j < nbreColonnes - 1)
					this.decouverteCase(i + 1, j + 1);
			}
		}
	}

	public void decouverteBombes() { // Seulement si la partie est finie
		for (int i = 0; i < nbreLignes; i++) {
			for (int j = 0; j < nbreColonnes; j++) {
				if (this.isBomb(i, j))
					this.setState(i, j, 1);
			}
		}
	}

	public boolean victory() {
		//boolean drapeau = false;
		int compteur = 0;

		for (int i = 0; i < nbreLignes; i++) {
			for (int j = 0; j < nbreColonnes; j++) {
				if(this.getState(i, j) == 1) compteur++;
			}
		}
		
		return ((nbreLignes*nbreColonnes)-compteur == this.getNbreBombes());
	}
}
