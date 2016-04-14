package org.miprojekt.turnieverwaltung.gui.windows;

import org.miprojekt.turnieverwaltung.Main;
import org.miprojekt.turnieverwaltung.gui.SceneParent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class Settings extends SceneParent {

	public Settings(Main main) {
		super(main);

		GridPane grid = new GridPane();
		
		
		Label l_teams = new Label("Anzahl der Teams eingeben: ");
		
		final ObservableList<Integer> options = FXCollections.observableArrayList(2, 4, 8, 16, 32);
		ComboBox<Integer> c_teams = new ComboBox<Integer>(options);
		c_teams.setValue(2);

		Button b_teams = new Button("OKAY");
		b_teams.setOnAction(
			event -> 
				System.out.println("Sie haben ein Turnier mit "
						+c_teams.getValue()+" Spielern angelegt."));

		grid.add(l_teams, 0, 0);
		grid.add(c_teams, 1, 0);
		grid.add(b_teams, 2, 0);
		grid.setPadding(new Insets(25));
		GridPane.setMargin(l_teams, new Insets(0, 25, 0, 0));
		GridPane.setMargin(c_teams, new Insets(0, 25, 0, 0));

		this.getChildren().add(grid);

	}

}
