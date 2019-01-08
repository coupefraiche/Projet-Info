package project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class InterfaceGraphique extends JPanel implements MouseListener {
	private Chronometre chrono;
	private Grille grille;
	private GUI gui;
	private int nbreFlags;
	private final int bombe = 666;
	private Image imgBombe, imgFlag, imgTemps, imgPerdu, imgClicked, imgGagne, imgEnCours, smileyEnCours, imgGameOver;
	private Color[] colors = new Color[8];

	public InterfaceGraphique(GUI gui) {

	}

	public void paint(Graphics g) {

	}

	private void draw(Graphics2D g) {

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public Chronometre getChrono() {
		return timer;
	}

	public void setNbreFlags(int valeur) {
		nbreFlags = valeur;
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}
}
