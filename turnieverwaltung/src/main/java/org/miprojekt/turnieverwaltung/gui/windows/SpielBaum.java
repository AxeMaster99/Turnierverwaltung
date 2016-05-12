package org.miprojekt.turnieverwaltung.gui.windows;

import java.util.ArrayList;

import org.miprojekt.turnieverwaltung.Main;
import org.miprojekt.turnieverwaltung.gui.MatchPane;
import org.miprojekt.turnieverwaltung.gui.SceneParent;

import backend.FolgeMatch;
import backend.Mannschaft;
import backend.Match;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SpielBaum extends SceneParent {

	private ObservableList<String> teams = FXCollections.observableArrayList();

	public SpielBaum(Main main, ObservableList<String> teams) {
		super(main);
		this.teams = teams;
		this.setStyle("-fx-background-color: #999999");
		steuerung.erstelleMatches(teams);
		this.zeichneSpielBaumLinks();
		this.zeichneFinale();
		this.zeichneSpielBaumRechts();
	}

	private void zeichneSpielBaumLinks() {
		int offset=0;
		int posX = 10;
		int posY = 10;
		int matchesInSpalte = teams.size() / 4;
		int actMatch = 0;
		int sprung=70;

		while (matchesInSpalte > 0) {
			for (int i = 0; i < matchesInSpalte; i++) {
				Pane pane = new Pane();
				pane = new MatchPane(steuerung.getMatches().get(actMatch).getMannschaft1().getName(),
						steuerung.getMatches().get(actMatch).getMannschaft2().getName());
				pane.setTranslateX(posX);
				pane.setTranslateY(posY);
				this.getChildren().add(pane);
				posY += sprung;
				actMatch++;
				
			}
			matchesInSpalte = matchesInSpalte/2;
			offset+=sprung/2;
			sprung=sprung*2;
			posY=10+offset;
			posX += 150;
		}

	}
	
	private void zeichneFinale(){
		
	};

	private void zeichneSpielBaumRechts() {
		int offset=0;
		int posX = (steuerung.getAnzahlSpalten()-2)*150;
		int posY = 10;
		int matchesInSpalte = teams.size() / 4;
		int actMatch = steuerung.getMatches().size()/2;
		int sprung=70;

		while (matchesInSpalte > 0) {
			for (int i = 0; i < matchesInSpalte; i++) {
				Pane pane = new Pane();
				pane = new MatchPane(steuerung.getMatches().get(actMatch).getMannschaft1().getName(),
						steuerung.getMatches().get(actMatch).getMannschaft2().getName());
				pane.setTranslateX(posX);
				pane.setTranslateY(posY);
				this.getChildren().add(pane);
				posY += sprung;
				actMatch++;
				
			}
			matchesInSpalte = matchesInSpalte/2;
			offset+=sprung/2;
			sprung=sprung*2;
			posY=10+offset;
			posX -= 150;
		}

	}
}
