package project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class InterfaceGraphique extends JPanel implements MouseListener {
	private Chronometre chrono;
	private Grille grille;
	private GUI gui;
	private int nbreFlags;
	private final int bombe = 666;
	private Image bombe_img, drapeau_img, temps_img, perdu_img, clicked_img, gagne_img, en_cours_img, smiley_en_cours,
			gameover_img;
	private Color[] couleurs = new Color[8];

	public InterfaceGraphique(GUI myGui) {

		// Pré-chargement des images.
		// But : éviter la latence pour afficher une image
		MediaTracker trk = new MediaTracker(myGui);

		drapeau_img = Toolkit.getDefaultToolkit().getImage("img/drapeau.png");
		bombe_img = Toolkit.getDefaultToolkit().getImage("img/bombe.png");
		temps_img = Toolkit.getDefaultToolkit().getImage("img/temps.png");
		perdu_img = Toolkit.getDefaultToolkit().getImage("img/perdu.png");
		gagne_img = Toolkit.getDefaultToolkit().getImage("img/gagne.png");
		clicked_img = Toolkit.getDefaultToolkit().getImage("img/clicked.png");
		en_cours_img = Toolkit.getDefaultToolkit().getImage("img/en_cours.png");
		smiley_en_cours = Toolkit.getDefaultToolkit().getImage("img/en_cours.png");
		gameover_img = Toolkit.getDefaultToolkit().getImage("img/gameover.png");

		trk.addImage(drapeau_img, 0);
		trk.addImage(bombe_img, 1);
		trk.addImage(temps_img, 2);
		trk.addImage(perdu_img, 3);
		trk.addImage(gagne_img, 4);
		trk.addImage(clicked_img, 5);
		trk.addImage(en_cours_img, 6);
		trk.addImage(smiley_en_cours, 7);
		trk.addImage(gameover_img, 8);

		try {
			trk.waitForAll();
		} catch (InterruptedException e) {
			// Traitement de l'exception.
			System.out.println("Une erreur est survenue lors du chargement des images...");
		}
		// les couleurs des chiffres
		couleurs[0] = new Color(0, 0, 255);
		couleurs[1] = new Color(0, 128, 0);
		couleurs[2] = new Color(255, 0, 0);
		couleurs[3] = new Color(0, 0, 128);
		couleurs[4] = new Color(128, 0, 0);
		couleurs[5] = new Color(0, 128, 128);
		couleurs[6] = new Color(128, 0, 128);
		couleurs[7] = new Color(0, 0, 0);

		gui = myGui;
		grille = gui.getGrille();
		nbreFlags = grille.getNbreBombes();
		chrono = new Chronometre(this);
		chrono.start();
		chrono.suspend();
		chrono.reset();

		addMouseListener(this);
	}

	public void paint(Graphics g) {

		super.paintComponent(g); // on demande de repeindre correctement ce composant
		super.revalidate(); // on revalide le composant

		Graphics2D g2D = (Graphics2D) g;

		grille = gui.getGrille();

		// le compteur de drapeau
		g.setFont(new java.awt.Font("Monospaced", 1, 15));
		g.setColor(Color.black);
		g.fillRect(1, 10, 39, 20);
		g.setColor(Color.red);
		g.drawRect(1, 10, 39, 20);

		g.setColor(Color.red);
		g.drawString(Integer.toString(nbreFlags), 4, 25);
		g.drawImage(bombe_img, 42, 11, this);

		// le bouton du milieu
		g.setColor(Color.black);
		g.draw3DRect(grille.getNbreColonnes() * 20 / 2 - 13, 7, 25, 25, true);
		g.drawImage(smiley_en_cours, grille.getNbreColonnes() * 20 / 2 - 11, 9, this);

		// le chrono
		g.drawImage(temps_img, grille.getNbreColonnes() * 20 - 63, 9, this);
		g.fillRect(grille.getNbreColonnes() * 20 - 40, 10, 39, 20);
		g.setColor(Color.red);
		g.drawRect(grille.getNbreColonnes() * 20 - 40, 10, 39, 20);

		g.setColor(Color.red);
		g.drawString(Integer.toString(chrono.getCompteur()), grille.getNbreColonnes() * 20 - 34, 25);

		// la grille
		dessiner_grille(g2D);
	}

	private void dessiner_grille(Graphics2D g)
	// procedure d'affichage de la grille (cela inclue aussi les bombes et drapeaux)
	{
		int x_pos = 0, y_pos = 40;
		grille = gui.getGrille();

		for (int i = 0; i < grille.getNbreLignes(); i++) {
			for (int j = 0; j < grille.getNbreColonnes(); j++) {
				if (grille.getState(i, j) == 0)
					g.setColor(Color.gray);
				else
					g.setColor(Color.lightGray);
				g.fillRect(x_pos, y_pos, 20, 20);
				g.setColor(Color.black);
				g.drawRect(x_pos, y_pos, 20, 20);
				g.setFont(new java.awt.Font("Monospaced", 1, 15));
				if (grille.getState(i, j) == 1) {
					switch (grille.getValue(i, j)) {
					case bombe:
						g.drawImage(bombe_img, x_pos + 1, y_pos + 1, this);
						break;

					case 0:
						break;

					default:
						g.setColor(couleurs[grille.getValue(i, j) - 1]);
						g.drawString(Integer.toString(grille.getValue(i, j)), x_pos + 5, y_pos + 15);
						break;
					}
				} else if (grille.getState(i, j) == 2)
					g.drawImage(drapeau_img, x_pos + 1, y_pos + 1, this);

				x_pos += 20;
			}
			y_pos += 20;
			x_pos = 0;
		}
		if (grille.getGameOver())
			g.drawImage(gameover_img, grille.getNbreColonnes() * 20 / 2 - 75, grille.getNbreLignes() * 20 / 2 - 54 + 40,
					this);
	}

	public void mousePressed(MouseEvent e) {
		if (!grille.getGameOver()) {
			gui.setFocusActif(true);
			if (chrono.isSuspended())
				chrono.resume();
			smiley_en_cours = clicked_img;
			repaint();
		}
	}

	public void mouseReleased(MouseEvent e) {
		int check_x, check_y;
		String nom_record;

		if (!grille.getGameOver())
			smiley_en_cours = en_cours_img;
		grille = gui.getGrille();

		if (e.getY() >= 40) {
			if (chrono.isSuspended() && !grille.getGameOver())
				chrono.resume();
			gui.setFocusActif(true);

			// protection contre effets de bords
			check_x = e.getX() / 20;
			if (check_x >= grille.getNbreColonnes())
				check_x = grille.getNbreColonnes() - 1;

			check_y = e.getY() / 20 - 2;
			if (check_y >= grille.getNbreLignes())
				check_y = grille.getNbreLignes() - 1;

			// Choix du bouton
			switch (e.getButton()) {
			// bouton gauche de la souris --> traitement des cases
			case 1:
				if (!grille.getGameOver() && grille.getState(check_y, check_x) == 0)
					if (grille.isBomb(check_y, check_x)) {
						chrono.suspend();
						grille.decouverteBombes();
						grille.gameIsOver();
						smiley_en_cours = perdu_img;
					} else {
						grille.decouverteCase(check_y, check_x);
						if (grille.victory()) {
							chrono.suspend();
							grille.decouverteBombes();
							grille.gameIsOver();
							smiley_en_cours = gagne_img;

							nom_record = JOptionPane.showInputDialog(null, "Veuillez saisir votre nom",
									"Nouveau Record!!!", JOptionPane.INFORMATION_MESSAGE);
							if (nom_record == null || nom_record.length() == 0)
								nom_record = "N/A";
						}
					}
				break;

			// bouton droit de la souris --> placement ou retrait d'un drapeau
			case 3:
				if (!grille.getGameOver() && grille.getState(check_y, check_x) != 1)
					if (grille.getState(check_y, check_x) == 2) {
						nbreFlags++;
						grille.setState(check_y, check_x, 0);
					} else {
						nbreFlags--;
						grille.setState(check_y, check_x, 2);
					}
				break;
			}

		} else // ici on a cliqué sur le smiley du mileu --> on lance une nouvelle partie du
				// niveau courant
		{
			if (e.getX() >= (grille.getNbreColonnes() * 20 / 2 - 11)
					&& e.getX() <= (grille.getNbreColonnes() * 20 / 2 + 11) && e.getY() >= 9 && e.getY() <= 50) {
				grille = gui.newGame(grille.getLevel());
				smiley_en_cours = en_cours_img;
			}
		}

		// Le traitement est terminé, on rafraîchit
		repaint();
	}

	public Chronometre getChrono()
	/* retourne l'objet chrono de type chrono */
	{
		return chrono;
	}

	public void setNbreFlags(int value)
	/* assigne une valeur donnée au compteur de drapeaux */
	{
		nbreFlags = value;
	}

	/* procédures nécessaires pour le MouseListener mais non utilisées */
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	// fin ZoneGraphique.java
}
