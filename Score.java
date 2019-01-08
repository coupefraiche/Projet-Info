package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JOptionPane;

public class Score {
	private String score[][] = null;

	public Score() {
		score = new String[3][2]; // 3 records enregistrés par nom + temps
		score = this.getScore();
	}

	public String[][] getScore() {
		String temp;
		int ligne = 0, colonne = 0;

		if (!this.fichierExiste("score.dem")) {
			for (int i = 0; i < 3; i++) {
				score[i][0] = "N/A";
				score[i][1] = "999";
			}

			this.saveScore();
		} else {
			try {
				BufferedReader tampon = new BufferedReader(new FileReader("score.dem"));
				do {
					temp = tampon.readLine();
					if (temp != null) {
						score[ligne][colonne] = temp;

						if (colonne == 1) {
							colonne = 0;
							ligne++;
						} else
							colonne++;
					}
				} while (temp != null);

				tampon.close();
			}

			catch (Exception e) {
				if (e != null)
					showError(e.getMessage());
			}
		}

		return score;
	}

	public void showError(String error) {
		JOptionPane.showMessageDialog(null, error, "Erreur :", JOptionPane.INFORMATION_MESSAGE);
	}

	public void saveScore() {
		try {
			BufferedWriter tampon = new BufferedWriter(new FileWriter("score.dem"));

			for (int ligne = 0; ligne < 3; ligne++) {
				tampon.write(this.score[ligne][0], 0, score[ligne][0].length());
				tampon.newLine();
				tampon.write(this.score[ligne][1], 0, score[ligne][1].length());
				tampon.newLine();
			}

			tampon.close();
		}

		catch (Exception e) {
			if (e != null)
				showError(e.getMessage());
		}
	}

	private boolean fichierExiste(String nomFichier) {
		File file = new File(nomFichier);

		if (file.exists()) {
			return true;
		} else
			return false;
	}

	public void setScore(int pos, String nom, int temps) {
		if (pos % 3 == 0) {
			score[pos + 2][0] = score[pos + 1][0];
			score[pos + 2][1] = score[pos + 1][1];
			score[pos + 1][0] = score[pos][0];
			score[pos + 1][1] = score[pos][1];
		} else if (pos % 3 == 1) {
			score[pos + 1][0] = score[pos][0];
			score[pos + 1][1] = score[pos][1];
		}

		score[pos][0] = nom;
		score[pos][1] = Integer.toString(temps);

		this.saveScore();
	}
}
