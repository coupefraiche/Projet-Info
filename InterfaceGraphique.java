package application;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Main extends Application implements FocusListener {
	private int hauteur, largeur;
	private Chronometre chrono;
	private boolean focus;
	private Dimension taille;
	private Grille grille;
	private Score score;
	private int nbreFlags;
	private final int bombe = 666;
	//private BufferedImage imgBombe, imgFlag, imgTemps, imgPerdu, imgClicked, imgGagne, imgEnCours, smileyEnCours, imgGameOver;
	private Color[] colors = new Color[8];

	Image imgBombe = new Image(getClass().getResourceAsStream("bombe2.png"),20, 20, false, false);
	ImageView imageView = new ImageView(imgBombe);
	public void start(Stage primaryStage) {
		try {
			int x,y;
			colors[1] = Color.RED;
			grille = new Grille(1);
			Group root = new Group();
			 Label text1 = new Label();
		        text1.setAlignment(Pos.TOP_LEFT);
		    //    text1.setText(Integer.toString(score.getScore().));
		        text1.setPadding(new Insets(0, 0, 0, 0));
		        HBox Score = new HBox();
		        Score.setAlignment(Pos.TOP_LEFT);
		        VBox vertBox = new VBox();
		        vertBox.setPrefSize(400, 400);
		        vertBox.setAlignment(Pos.TOP_LEFT);
			
			
			
			
			
			
			GridPane grid = new GridPane();
			//VBox vboxForButtons = new VBox();
			for(int j=0; j<grille.getNbreLignes(); j++) {	
			for (int i = 0; i <grille.getNbreColonnes(); i++) {	
				Button btnNumber = new Button();
int carre=25;
				btnNumber.setMaxHeight(carre);
				btnNumber.setMinHeight(carre);
				btnNumber.setMaxWidth(carre);
				btnNumber.setMinWidth(carre);
				btnNumber.setStyle(" -fx-background-color: #D3D3D3; -fx-border-color: black;-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0)");
                //-fx-background-color: LIGHT_GRAY; -fx-background-radius: 8px; -fx-border-radius: 8px; -fx-border-width: 5px;-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0)
				btnNumber.setOnAction(new EventHandler<ActionEvent>() {
						    public void handle(ActionEvent event) {
						    	grille.decouverteCase(GridPane.getColumnIndex(btnNumber),GridPane.getRowIndex(btnNumber));
						    	couleur(GridPane.getColumnIndex(btnNumber), GridPane.getRowIndex(btnNumber), btnNumber);
						    	//verif_voisin(GridPane.getColumnIndex(btnNumber), GridPane.getRowIndex(btnNumber), btnNumber);
						    	//btnNumber.setStyle("-fx-background-color: white ");	
								//btnNumber.setStyle("-fx-text-fill: Color=red");
						    }
						  //  public void 
					});	
						
				grid.add(btnNumber, i, j);
				}
			}
				Score.getChildren().addAll(text1);
			//Scene scene2 = new Scene(root, 250,250);
			 vertBox.getChildren().addAll( Score, grid);
			 Scene scene = new Scene(vertBox);
			//xscene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Demineur");
			//primaryStage.setScene(scene2);
			primaryStage.setScene(scene);
			
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Grille newGame(int niveau) {
		this.setFocusActif(false);
		grille = null;
		grille = new Grille(niveau);

		hauteur = 20 * grille.getNbreLignes();
		largeur = 20 * grille.getNbreColonnes();

		//interfaceGraph.setNbreFlags(grille.getNbreBombes());

		chrono.suspend();
		chrono.reset();

		
		//interfaceGraph.repaint();

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
	
	
	private void setFocusActif(boolean b) { // Accesseur pour set le focus
		focus = b;
	}

	private boolean getFocusActif() { // Accesseur pour renvoyer l'état du focus
		return focus;
	}

	public Grille getGrille() { // Accesseur pour renvoyer la grille
		return grille;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void couleur(int i, int j, Button btnNumber) {
		if(grille.getState(i, j)==1 && 1<=grille.getValue(i, j) && 8>=grille.getValue(i, j)) { //Chiffre
		if((grille.getValue(i, j)%3)==1) btnNumber.setStyle("-fx-background-color: white;-fx-border-color: black; -fx-text-fill: blue;");
		if((grille.getValue(i, j)%3)==2) btnNumber.setStyle("-fx-background-color: white;-fx-border-color: black; -fx-text-fill: green;");	
		if((grille.getValue(i, j)%3)==0) btnNumber.setStyle("-fx-background-color: white;-fx-border-color: black; -fx-text-fill: red;");
			btnNumber.setText(Integer.toString(grille.getValue(i, j)));


		}else if(grille.getState(i, j)==1 && grille.getValue(i, j)==666) {
			//btnNumber.setText("B");
			//btnNumber.setStyle("-fx-background-color: white;");
			btnNumber.setGraphic(new ImageView(imgBombe));
			btnNumber.setStyle("-fx-background-color: white;-fx-border-color: black;");
		}else {
			btnNumber.setStyle("-fx-background-color: white;-fx-border-color: black;");
		}
		
	}/*
	public void verif_voisin(int i, int j, Button btnNumber ) {
		if ((i - 1 >= 0) && (grille.getValue(i - 1, j) == 0)) 
			verif(i - 1,j,btnNumber );
		if ((j - 1 >= 0) && (grille.getValue(i, j - 1) == 0)) {
			verif(i, j - 1,btnNumber );
		}
		if ((i + 1 >= 0) && (i+1<grille.getNbreLignes()) && (grille.getValue(i + 1, j) == 0)) {
			verif(i + 1, j,btnNumber );
		}
		if ((j + 1 >= 0) && (j+1<grille.getNbreColonnes()) && (grille.getValue(i, j + 1) == 0)) {
			verif(i, j + 1,btnNumber );
		}
		if ((i - 1 >= 0) && (j - 1 >= 0) && (grille.getValue(i - 1, j - 1) == 0)) {
			verif(i - 1, j - 1,btnNumber );
		}
		if ((i + 1 >= 0) && (i+1<grille.getNbreLignes()) && (j + 1 >= 0) && (j+1<grille.getNbreColonnes()) && (grille.getValue(i + 1, j + 1) == 0)) {
			verif(i + 1, j + 1,btnNumber );
		}	
		if ((i + 1 >= 0) && (i+1<grille.getNbreLignes()) && (j - 1 >= 0) && (grille.getValue(i + 1, j - 1) == 0)) {
			verif(i + 1, j - 1,btnNumber );
		}
		if ((i - 1 >= 0) && (j + 1 >= 0) && (j+1<grille.getNbreColonnes()) && (grille.getValue(i - 1, j + 1) == 0)) {
			verif(i - 1, j + 1,btnNumber );
		}	
		}*/
	
			
}
