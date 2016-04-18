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

		Label l_teamsMade = new Label("Teams: 2. Bestätigen Drücken.");
		Label l_teams = new Label("Anzahl der Teams eingeben: ");

		final ObservableList<Integer> options = FXCollections.observableArrayList(2, 4, 8, 16, 32);
		ComboBox<Integer> c_teams = new ComboBox<Integer>(options);
		c_teams.setValue(2);

		Button b_teams = new Button("Bestätigen");
		c_teams.setOnAction((event) -> {
			l_teamsMade.setText("Teams: " + c_teams.getValue() + ". Bestätigen Drücken.");
		});
		b_teams.setOnAction((event) -> {
			main.getStage().setScene(main.getScreen("mannschaftsnamen"));
		});

		grid.add(l_teams, 0, 0);
		grid.add(c_teams, 1, 0);
		grid.add(b_teams, 2, 0);
		grid.add(l_teamsMade, 0, 1);
		grid.setPadding(new Insets(25));
		GridPane.setMargin(l_teams, new Insets(0, 25, 0, 0));
		GridPane.setMargin(c_teams, new Insets(0, 25, 0, 0));

		this.getChildren().add(grid);
	}


}
