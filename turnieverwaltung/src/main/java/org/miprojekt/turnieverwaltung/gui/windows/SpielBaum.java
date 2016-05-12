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
		steuerung.erstelleMatches(teams);
		this.zeichneSpielBaumLinks();
		// this.zeichneSpielBaumRechts();
	}

	private void zeichneSpielBaumLinks() {

		int posX = 10;
		int posY = 10;
		int matchesInSpalte = teams.size() / 4;
		int actMatch = 0;

		while (matchesInSpalte > 0) {
			for (int i = 0; i < matchesInSpalte; i++) {
				Pane pane = new Pane();
				pane = new MatchPane(steuerung.getMatches().get(actMatch).getMannschaft1().getName(),
						steuerung.getMatches().get(actMatch).getMannschaft2().getName());
				pane.setTranslateX(posX);
				pane.setTranslateY(posY);
				this.getChildren().add(pane);
				posY += 70;
				actMatch++;
				
			}
			matchesInSpalte = matchesInSpalte/2;
			posY=10;
			posX += 160;
		}

	}

	private void zeichneSpielBaumRechts() {
		int posY = 10;
		for (int i = this.teams.size() / 2; i < this.teams.size(); i++) {
			Pane pane = new Pane();
			pane = new MatchPane(steuerung.getMatches().get(i).getMannschaft1().getName(),
					steuerung.getMatches().get(i).getMannschaft2().getName());
			pane.setTranslateX(500);
			pane.setTranslateY(posY);
			this.getChildren().add(pane);
			posY += 70;
		}
	}
}
