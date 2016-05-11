package org.miprojekt.turnieverwaltung.gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MatchPane extends Pane {

	private Label l1_mannschaft;
	private Label l2_mannschaft;
	private Button b1_torMannschaft1;
	private Button b2_torMannschaft2;
	private Button b3_start_stop;
	
	public MatchPane(String nameMannschaft1, String nameMannschaft2) {
		super();
		GridPane grid = new GridPane();
		l1_mannschaft = new Label(nameMannschaft1);
		l2_mannschaft = new Label(nameMannschaft2);
		b1_torMannschaft1 = new Button("TOR M1");
		b2_torMannschaft2 = new Button("TOR M2");
		b3_start_stop= new Button("Start/Stopp");
		grid.add(l1_mannschaft, 0, 0);
		grid.add(l2_mannschaft, 0, 1);
		grid.add(b1_torMannschaft1, 1, 0);
		grid.add(b2_torMannschaft2, 1, 1);
		grid.add(b3_start_stop, 0, 2);
		this.getChildren().add(grid);
	}

}
