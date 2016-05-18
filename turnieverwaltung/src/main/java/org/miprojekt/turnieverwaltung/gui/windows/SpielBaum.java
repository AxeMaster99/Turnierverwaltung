package org.miprojekt.turnieverwaltung.gui.windows;

import java.util.ArrayList;

import org.miprojekt.turnieverwaltung.Main;
import org.miprojekt.turnieverwaltung.gui.MatchPane;
import org.miprojekt.turnieverwaltung.gui.SceneParent;

import backend.FinalMatch;
import backend.FolgeMatch;
import backend.Mannschaft;
import backend.Match;
import interfaces.IMatch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;

public class SpielBaum extends SceneParent {

	private Canvas canvas = new Canvas(1400, 700);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	
	private ObservableList<String> teams = FXCollections.observableArrayList();

	public SpielBaum(Main main, ObservableList<String> teams) throws Exception {
		super(main);

		this.teams = teams;
		this.setStyle("-fx-background-color: #999999");
		steuerung.erstelleMatches(teams);

		canvas.setMouseTransparent(true);
		this.getChildren().add(canvas);

		this.zeichneSpielBaumLinks();
		this.zeichneSpielBaumRechts();
		this.zeichneLinienLinks();
		this.zeichneLinienRechts();
		this.zeichneFinale();
		this.zeichneLegende();
	}

	private void zeichneLegende() {
		MatchPane mp = this.steuerung.getMatches().get(this.teams.size()/4-1).getMatchPane();
		double mpY = mp.getTranslateY()+70;
		
		Font font = new Font(16);
		
		Label l0_legende = new Label("Legende:");
		l0_legende.setFont(font);
		l0_legende.setTranslateX(10);
		l0_legende.setTranslateY(mpY+3);
		Label l1_orange = new Label("Spiel Unterbrochen");
		l1_orange.setTranslateX(140);
		l1_orange.setTranslateY(mpY+7);
		Label l2_green= new Label("Spiel Beendet");
		l2_green.setTranslateX(340);
		l2_green.setTranslateY(mpY+7);
		Label l3_yellow= new Label("Spiel Laufend");
		l3_yellow.setTranslateX(540);
		l3_yellow.setTranslateY(mpY+7);
		Label l4_white= new Label("Spiel noch nicht gestartet");
		l4_white.setTranslateX(740);
		l4_white.setTranslateY(mpY+7);
		
		Rectangle rectangleOrange = new Rectangle(30,30,Color.ORANGE);
		rectangleOrange.relocate(100, mpY);
		this.getChildren().add(rectangleOrange);
		
		Rectangle rectangleGreen = new Rectangle(30,30,Color.GREEN);
		rectangleGreen.relocate(300, mpY);
		this.getChildren().add(rectangleGreen);
		
		Rectangle rectangleYellow = new Rectangle(30,30,Color.YELLOW);
		rectangleYellow.relocate(500, mpY);
		this.getChildren().add(rectangleYellow);
		
		Rectangle rectangleWhite = new Rectangle(30,30,Color.WHITE);
		rectangleWhite.relocate(700, mpY);
		this.getChildren().addAll(rectangleWhite,l1_orange,l2_green,l3_yellow,l4_white,l0_legende);
	}

	private void zeichneLinienLinks() {
		for (int i = 0; i < this.steuerung.getMatches().size() / 2; i++) {
			if (this.steuerung.getMatches().get(i) instanceof FolgeMatch
					&& !(this.steuerung.getMatches().get(i) instanceof FinalMatch)) {

				MatchPane fm = this.steuerung.getMatches().get(i).getMatchPane();
				MatchPane m1 = ((FolgeMatch) this.steuerung.getMatches().get(i)).getPrevMatch1().getMatchPane();
				MatchPane m2 = ((FolgeMatch) this.steuerung.getMatches().get(i)).getPrevMatch2().getMatchPane();

				double fmX = fm.getTranslateX();
				double fmY = fm.getTranslateY() + 20;
				double m1X = m1.getTranslateX() + 110;
				double m1Y = m1.getTranslateY() + 20;
				double m2X = m2.getTranslateX() + 110;
				double m2Y = m2.getTranslateY() + 20;

				// System.out.println(fmBoundsX + " | "+fmBoundsY);

				//gc.setFill(Color.BLACK);
				//gc.setStroke(Color.BLACK);
				gc.setLineWidth(3);
				gc.strokeLine(fmX, fmY, m1X, m1Y);
				gc.strokeLine(fmX, fmY, m2X, m2Y);
			}
		}
	}

	private void zeichneLinienRechts() {
		for (int i = this.steuerung.getMatches().size() / 2; i < this.steuerung.getMatches().size(); i++) {
			if (this.steuerung.getMatches().get(i) instanceof FolgeMatch
					&& !(this.steuerung.getMatches().get(i) instanceof FinalMatch)) {

				MatchPane fm = this.steuerung.getMatches().get(i).getMatchPane();
				MatchPane m1 = ((FolgeMatch) this.steuerung.getMatches().get(i)).getPrevMatch1().getMatchPane();
				MatchPane m2 = ((FolgeMatch) this.steuerung.getMatches().get(i)).getPrevMatch2().getMatchPane();

				double fmX = fm.getTranslateX() + 110;
				double fmY = fm.getTranslateY() + 20;
				double m1X = m1.getTranslateX();
				double m1Y = m1.getTranslateY() + 20;
				double m2X = m2.getTranslateX();
				double m2Y = m2.getTranslateY() + 20;

				// System.out.println(fmBoundsX + " | "+fmBoundsY);

				gc.setFill(Color.BLACK);
				gc.setStroke(Color.BLACK);
				gc.setLineWidth(3);
				gc.strokeLine(fmX, fmY, m1X, m1Y);
				gc.strokeLine(fmX, fmY, m2X, m2Y);
			}
		}
	}

	private void zeichneSpielBaumLinks() {
		int offset = 0;
		int posX = 10;
		int posY = 10;
		int matchesInSpalte = teams.size() / 4;
		int actMatch = 0;
		int sprungY = 70;
		int sprungX = 140;

		while (matchesInSpalte > 0) {
			for (int i = 0; i < matchesInSpalte; i++) {
				MatchPane pane = this.steuerung.getMatches().get(actMatch).getMatchPane();
				pane.getMatchStage().setSpielbaum(this);
				pane.setTranslateX(posX);
				pane.setTranslateY(posY);
				this.getChildren().add(pane);
				posY += sprungY;
				actMatch++;
			}
			matchesInSpalte = matchesInSpalte / 2;
			offset += sprungY / 2;
			sprungY = sprungY * 2;
			posY = 10 + offset;
			posX += sprungX;
		}

	}

	private void zeichneSpielBaumRechts() {
		int offset = 0;
		int posX = (this.steuerung.getAnzahlSpalten() * 130) - 20;
		int posY = 10;
		int matchesInSpalte = teams.size() / 4;
		int actMatch = this.steuerung.getMatches().size() / 2;
		int sprungY = 70;
		int sprungX = 140;

		while (matchesInSpalte > 0) {
			for (int i = 0; i < matchesInSpalte; i++) {
				MatchPane pane = this.steuerung.getMatches().get(actMatch).getMatchPane();
				pane.getMatchStage().setSpielbaum(this);
				pane.setTranslateX(posX);
				pane.setTranslateY(posY);
				this.getChildren().add(pane);
				posY += sprungY;
				actMatch++;
			}
			matchesInSpalte = matchesInSpalte / 2;
			offset += sprungY / 2;
			sprungY = sprungY * 2;
			posY = 10 + offset;
			posX -= sprungX;
		}

	}

	private void zeichneFinale() {

		MatchPane finaleMatchPane = this.steuerung.getMatches().get(this.steuerung.getMatches().size() - 1)
				.getMatchPane();
		finaleMatchPane.getMatchStage().setSpielbaum(this);

		this.getChildren().add(finaleMatchPane);

		// ermittle Position finalMatchPane
		double x1 = this.steuerung.getMatches().get(this.steuerung.getMatches().size() / 2 - 1).getMatchPane()
				.getTranslateX() + 110;
		double x2 = this.steuerung.getMatches().get(this.steuerung.getMatches().size() - 2).getMatchPane()
				.getTranslateX();
		double mitte = x1 + (x2 - x1) / 2;

		// finalMatchPane positionieren
		finaleMatchPane.setTranslateX(mitte - 55);
		finaleMatchPane.setTranslateY(this.steuerung.getMatches().get(this.steuerung.getMatches().size() / 2 - 1)
				.getMatchPane().getTranslateY());

		// Striche zu finalMatchPane zeichnen
		double fmX = finaleMatchPane.getTranslateX();
		double fmY = finaleMatchPane.getTranslateY() + 20;

		double m1X = this.steuerung.getMatches().get(this.steuerung.getMatches().size() / 2 - 1).getMatchPane()
				.getTranslateX() + 110;
		double m1Y = this.steuerung.getMatches().get(this.steuerung.getMatches().size() / 2 - 1).getMatchPane()
				.getTranslateY() + 20;
		double m2X = this.steuerung.getMatches().get(this.steuerung.getMatches().size() - 2).getMatchPane()
				.getTranslateX();
		double m2Y = this.steuerung.getMatches().get(this.steuerung.getMatches().size() - 2).getMatchPane()
				.getTranslateY() + 20;

		gc.setFill(Color.BLACK);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(3);
		gc.strokeLine(fmX, fmY, m1X, m1Y);
		gc.strokeLine(fmX, fmY, m2X, m2Y);
	}

	public void updateSpielBaum() {

		System.out.println("update spielbaum");

		for (int i = 0; i < this.steuerung.getMatches().size(); i++) {
			if (this.steuerung.getMatches().get(i) instanceof FolgeMatch) {

				FolgeMatch actMatch = (FolgeMatch) this.steuerung.getMatches().get(i);

				if (((FolgeMatch) this.steuerung.getMatches().get(i)).getPrevMatch1().isGameFinished()) {
					IMatch prevMatch1 = actMatch.getPrevMatch1();
					this.steuerung.getMatches().get(i).setMannschaft1(prevMatch1.getSieger());
					this.steuerung.getMatches().get(i).getMatchPane().updateMannschaftsLabelM1();

				}

				if (((FolgeMatch) this.steuerung.getMatches().get(i)).getPrevMatch2().isGameFinished()) {
					IMatch prevMatch2 = actMatch.getPrevMatch2();
					this.steuerung.getMatches().get(i).setMannschaft2(prevMatch2.getSieger());
					this.steuerung.getMatches().get(i).getMatchPane().updateMannschaftsLabelM2();
				}
				
			}
			
			if (this.steuerung.getMatches().get(i) instanceof FinalMatch && this.steuerung.getMatches().get(i).isGameFinished()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText("Sieger des Turniers");
				alert.setContentText("Der Sieger des Turniers ist: "+ this.steuerung.getMatches().get(i).getSieger());
				alert.showAndWait();
			}
		}
	}

}
