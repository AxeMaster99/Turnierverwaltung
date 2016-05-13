package org.miprojekt.turnieverwaltung.gui;

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

	private Label l1_mannschaft;
	private Label l2_mannschaft;
	private GridPane grid = new GridPane();
	
	public MatchPane(String nameMannschaft1, String nameMannschaft2) {
		super();
		
		grid.setMinSize(110, 40);
		grid.setStyle("-fx-background-color: white;");
		
		grid.setOnMouseEntered((MouseEvent e)->{
			grid.setStyle("-fx-background-color: CCCCCC;");
		});
		
		grid.setOnMouseExited((MouseEvent e)->{
			grid.setStyle("-fx-background-color: white;");
		});
		
		grid.setOnMouseReleased((event)->{
			new MatchStage(nameMannschaft1, nameMannschaft2);
			
			System.out.println(l1_mannschaft.getText()+" gegen " + l2_mannschaft.getText());
			System.out.println(this.getTranslateX());
			System.out.println(this.getTranslateY());
		});
		
		l1_mannschaft = new Label(nameMannschaft1);
		l2_mannschaft = new Label(nameMannschaft2);
		
		grid.add(l1_mannschaft, 0, 0);
		grid.add(l2_mannschaft, 0, 1);
		
		this.getChildren().add(grid);
	}
	


}
