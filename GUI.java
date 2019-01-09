package project;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GUI extends JFrame implements FocusListener {
	private int hauteur, largeur;
	private InterfaceGraphique interfaceGraph;
	private Dimension taille;
	private Chronometre chrono;
	private Grille grille;
	private boolean focus;

	public GUI() {
		setTitle("Démineur"); // Permet de donner un titre à la fenêtre
		this.setFocusActif(false);
		grille = new Grille(1); // On initialise une première grille au niveau 1
		hauteur = 20 * grille.getNbreLignes(); // On donne une hauteur et une largeur à la fenêtre
		largeur = 20 * grille.getNbreColonnes(); // selon le nombre de lignes et colonnes

		// Centrer la fenêtre au milieu de l'écran
		taille = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds((taille.width - largeur) / 2, (taille.height - hauteur) / 2, largeur, hauteur);
		// this.setIconImage(new ImageIcon("img/icon/icon.png").getImage());
		
		setJMenuBar(new Menu(this)); // Ajoute une barre de menu à la fenêtre

		interfaceGraph = new InterfaceGraphique(this); // On initialise l'interface graphique du GUI
		chrono = interfaceGraph.getChrono(); // On renvoie le timer initial de la partie
		this.getContentPane().add(interfaceGraph); // On rajoute l'interface graph au GUI

		this.setResizable(false); // On ne peut pas redimensionner la fenêtre
		setVisible(true); // On la rend visible
		setDefaultCloseOperation(EXIT_ON_CLOSE); // La seule action permettant de fermer la fenêtre est la croix
		addFocusListener(this); // Ajoute un focus au niveau du GUI
	}

	private void setFocusActif(boolean b) { // Accesseur pour set le focus
		focus = b;
	}

	private boolean getFocusActif() { // Accesseur pour renvoyer l'état du focus
		return focus;
	}

	public Grille getGrille() { // Accesseur pour renvoyer la grille
		return grille;
	}

	public Grille newGame(int niveau) {
		this.setFocusActif(false);
		grille = null;
		grille = new Grille(niveau);

		hauteur = 20 * grille.getNbreLignes();
		largeur = 20 * grille.getNbreColonnes();

		interfaceGraph.setNbreFlags(grille.getNbreBombes());

		chrono.suspend();
		chrono.reset();

		this.setSize(hauteur, largeur);
		setVisible(true);

		interfaceGraph.repaint();

		return grille;
	}

	public void focusGained(FocusEvent arg0) {
		if (!grille.getGameOver() && this.getFocusActif())
			chrono.resume();
	}

	public void focusLost(FocusEvent arg0) {
		if (!grille.getGameOver() && !chrono.isSuspended() && this.getFocusActif())
			chrono.suspend();
	}

}
