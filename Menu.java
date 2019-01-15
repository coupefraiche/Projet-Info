package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Menu extends JMenuBar implements ActionListener {
	private GUI gui;
	private Grille grille;
	private Score myScore;
	private String score[][];
	private int choice;
	private static JMenuItem Débutant, Normal, Expert, Exit, Score;

	public Menu(GUI new_gui) {
		myScore = new Score();
		gui = new_gui;
		grille = gui.getGrille();
		score = myScore.getScore();

		JMenu menuGame = new JMenu("Game");
		Débutant = new JMenuItem("mode Débutant");
		Normal = new JMenuItem("mode Normal");
		Expert = new JMenuItem("mode Difficile");
		Score = new JMenuItem("Records");
		Exit = new JMenuItem("Quitter");
		
		Débutant.addActionListener(this);
		Normal.addActionListener(this);
		Expert.addActionListener(this);
		Score.addActionListener(this);
		Exit.addActionListener(this);
		
		menuGame.add(Débutant);
		menuGame.add(Normal);
		menuGame.add(Expert);		
		menuGame.addSeparator();
		menuGame.add(Score);
		menuGame.addSeparator();
		menuGame.add(Exit);
		
		this.add(menuGame);
	}

	public void actionPerformed(ActionEvent arg0) {
		Object objet = arg0.getSource();
		
		if(objet.equals(Débutant)) grille = gui.newGame(1);
		else if(objet.equals(Normal)) grille = gui.newGame(2);
		else if(objet.equals(Expert)) grille = gui.newGame(3);
		else if(objet.equals(Score)) JOptionPane.showMessageDialog(null, "Vos meilleurs scores sont : " + score[0][0] + " " + score[0][1] + "\n"
				+ score[1][0] + " " + score[1][1] + "\n"
				+ score[2][0] + " " + score[2][1] + "\n");
		else if(objet.equals(Exit)) System.exit(0);
	}
}
