package org.miprojekt.turnieverwaltung.gui;

import backend.Match;
import interfaces.IMatch;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class MatchPane extends Pane {

	private IMatch match;
	private GridPane grid = new GridPane();
	
	public MatchPane(IMatch match) {
		super();
		this.match = match;

		grid.setMinSize(110, 40);
		grid.setStyle("-fx-background-color: white;");

		grid.setOnMouseEntered((MouseEvent e) -> {
			grid.setStyle("-fx-background-color: CCCCCC;");
		});

		grid.setOnMouseExited((MouseEvent e) -> {
			grid.setStyle("-fx-background-color: white;");
		});

		grid.setOnMouseReleased((event) -> {
			System.out.println(this);
			new MatchStage(this.match);
			System.out.println(this.match.getMannschaft1().getName() + " gegen " + this.match.getMannschaft2().getName());
			System.out.println(this.getTranslateX());
			System.out.println(this.getTranslateY());
		});
		
		Label l1_mannschaft = new Label(this.match.getMannschaft1().getName());
		Label l2_mannschaft = new Label(this.match.getMannschaft2().getName());
		
		grid.add(l1_mannschaft, 0, 0);
		grid.add(l2_mannschaft, 0, 1);

		this.getChildren().add(grid);
	}

}
