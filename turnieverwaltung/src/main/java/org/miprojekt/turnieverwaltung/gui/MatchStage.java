package org.miprojekt.turnieverwaltung.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MatchStage extends Stage{

	private Label l_Spielstand = new Label();
	private GridPane grid = new GridPane();
	
	public MatchStage(){
		super();
		this.setWidth(500);
		this.setHeight(300);
		this.show();
		
		Font font1 = new Font(20);
		l_Spielstand.setText("0:0");
		l_Spielstand.setFont(font1);
		
		grid.add(l_Spielstand, 0, 0);
	}

}
