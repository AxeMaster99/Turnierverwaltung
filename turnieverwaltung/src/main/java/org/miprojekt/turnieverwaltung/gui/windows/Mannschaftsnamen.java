package org.miprojekt.turnieverwaltung.gui.windows;


import org.miprojekt.turnieverwaltung.Main;
import org.miprojekt.turnieverwaltung.gui.SceneParent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Mannschaftsnamen extends SceneParent {

	private int anzahlMannschaften;
	private int cnt=0; //Globale Laufvariable

	public Mannschaftsnamen(Main main, int anzahlMannschaften) {
		super(main);
		this.anzahlMannschaften = anzahlMannschaften;

		GridPane grid = new GridPane();
		ObservableList<String> teams = FXCollections.observableArrayList();
		
		TextField t_teamnames = new TextField();
		Button b_add = new Button("Hinzufügen");
		grid.add(t_teamnames, 0, 0);
		grid.add(b_add, 1, 0);
		grid.setPadding(new Insets(25));
		GridPane.setMargin(t_teamnames, new Insets(0, 25, 0, 0));

		
		b_add.setOnAction((event)->{
			if(cnt<anzahlMannschaften && t_teamnames.getText()!=null){
				teams.add(t_teamnames.getText());
				cnt++;
			}
		});

		ListView<String> lb_teams = new ListView<String>(teams);
		grid.add(lb_teams, 0, 1);
		GridPane.setMargin(lb_teams, new Insets(25,0,0,0));
		this.getChildren().add(grid);
	}

}
