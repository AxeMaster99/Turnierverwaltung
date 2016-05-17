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
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.control.ScrollPane;

public class SpielBaum extends SceneParent {

	private Canvas canvas = new Canvas(1400, 700);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private ObservableList<String> teams = FXCollections.observableArrayList();

	public SpielBaum(Main main, ObservableList<String> teams) throws Exception {
		super(main);
		
		
//		String style = getClass().getResource("style.css").toExternalForm();
//		this.getStylesheets().addAll(style);
		
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
	}

	private void zeichneLinienLinks() {
		for (int i = 0; i < this.steuerung.getMatches().size()/2; i++) {
			if (this.steuerung.getMatches().get(i) instanceof FolgeMatch && !(this.steuerung.getMatches().get(i) instanceof FinalMatch)) {
				
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

				gc.setFill(Color.BLACK);
				gc.setStroke(Color.BLACK);
				gc.setLineWidth(3);
				gc.strokeLine(fmX, fmY, m1X, m1Y);
				gc.strokeLine(fmX, fmY, m2X, m2Y);
			}
		}
	}
	
	private void zeichneLinienRechts() {
		for (int i = this.steuerung.getMatches().size()/2; i < this.steuerung.getMatches().size(); i++) {
			if (this.steuerung.getMatches().get(i) instanceof FolgeMatch && !(this.steuerung.getMatches().get(i) instanceof FinalMatch)) {
				
				MatchPane fm = this.steuerung.getMatches().get(i).getMatchPane();
				MatchPane m1 = ((FolgeMatch) this.steuerung.getMatches().get(i)).getPrevMatch1().getMatchPane();
				MatchPane m2 = ((FolgeMatch) this.steuerung.getMatches().get(i)).getPrevMatch2().getMatchPane();

				double fmX = fm.getTranslateX() +110;
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
		
		MatchPane finaleMatchPane = this.steuerung.getMatches().get(this.steuerung.getMatches().size()-1).getMatchPane();
		finaleMatchPane.getMatchStage().setSpielbaum(this);
		
		this.getChildren().add(finaleMatchPane);
		
		// ermittle Position finalMatchPane
		double x1 = this.steuerung.getMatches().get(this.steuerung.getMatches().size() / 2 - 1).getMatchPane().getTranslateX() + 110;
		double x2 = this.steuerung.getMatches().get(this.steuerung.getMatches().size()-2).getMatchPane().getTranslateX();
		double mitte = x1 + (x2 - x1) / 2;
		
		// finalMatchPane positionieren
		finaleMatchPane.setTranslateX(mitte - 55);
		finaleMatchPane.setTranslateY(this.steuerung.getMatches().get(this.steuerung.getMatches().size() / 2 - 1).getMatchPane().getTranslateY());
		
		// Striche zu finalMatchPane zeichnen
		double fmX = finaleMatchPane.getTranslateX();
		double fmY = finaleMatchPane.getTranslateY() + 20;
				
		double m1X = this.steuerung.getMatches().get(this.steuerung.getMatches().size() / 2 - 1).getMatchPane().getTranslateX() + 110;
		double m1Y = this.steuerung.getMatches().get(this.steuerung.getMatches().size() / 2 - 1).getMatchPane().getTranslateY() + 20;
		double m2X = this.steuerung.getMatches().get(this.steuerung.getMatches().size()-2).getMatchPane().getTranslateX();
		double m2Y = this.steuerung.getMatches().get(this.steuerung.getMatches().size()-2).getMatchPane().getTranslateY() + 20;
		
		gc.setFill(Color.BLACK);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(3);
		gc.strokeLine(fmX, fmY, m1X, m1Y);
		gc.strokeLine(fmX, fmY, m2X, m2Y);
	}
	
}
