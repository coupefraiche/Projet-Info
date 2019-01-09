package demineur;

import java.lang.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class InterfaceGraphique extends JPanel {
	private Chronometre chrono;
	private Grille grille;
	private GUI gui;
	private int nbreFlags;
	private final int bombe = 666;
	private BufferedImage imgBombe, imgFlag, imgTemps, imgPerdu, imgClicked, imgGagne, imgEnCours, smileyEnCours, imgGameOver;
	private Color[] colors = new Color[8];
		
	public InterfaceGraphique(GUI mon_gui) throws IOException {
	imgBombe = ImageIO.read(new File("image/mine.png"));
	imgFlag = ImageIO.read(new File("image/flag.png"));
	imgTemps= ImageIO.read(new File("image/chrono.png"));
	imgPerdu= ImageIO.read(new File("image/flag.png"));
	imgClicked= ImageIO.read(new File("image/case_appuye.png"));
	imgGagne= ImageIO.read(new File("image/flag.png"));
	imgEnCours= ImageIO.read(new File("image/case_non_appuye.png"));
	smileyEnCours= ImageIO.read(new File("image/flag.png"));
	imgGameOver= ImageIO.read(new File("image/flag.png"));
	
	gui = mon_gui;
	grille = gui.getGrille();
	}

	public void paint(Graphics g) {
		super.paint(g);
		for (int i = 0; i < grille.getNbreLignes(); i++) {
			for (int j = 0; j < grille.getNbreColonnes(); j++) {
				if(grille.getState(i, j)==0) {
					g.setColor(Color.gray);
				}else {
					g.setColor(Color.LIGHT_GRAY);
				}
				g.fillRect(20*i,20*j,20, 20);
				g.setColor(Color.BLACK);
				g.drawRect(20*i, 20*j, 20, 20);
				if(grille.getState(i, j)==1) {
					if( 1<=grille.getValue(i, j) && 8>=grille.getValue(i, j)) {
												
					}else if(grille.getValue(i, j)==666){
						g.drawImage(imgBombe, 20*i,20*j, null);
					}
				}
				}
		}
	}

	private void draw(Graphics2D g) {
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
/*
	public Chronometre getChrono() {
		//return timer;
	}*/

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
