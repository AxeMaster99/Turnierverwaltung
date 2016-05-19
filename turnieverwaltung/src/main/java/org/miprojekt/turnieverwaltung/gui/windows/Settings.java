package org.miprojekt.turnieverwaltung.gui.windows;

import javax.swing.JLabel;

import org.miprojekt.turnieverwaltung.Main;
import org.miprojekt.turnieverwaltung.gui.SceneParent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class Settings extends SceneParent {

	Label l_teams = new Label("Teamanzahl");
	final ObservableList<Integer> options = FXCollections.observableArrayList(4, 8, 16, 32);
	ComboBox<Integer> c_teams = new ComboBox<Integer>(options);
	GridPane grid = new GridPane();
	Button b_teams = new Button("Bestätigen");

	public Settings(Main main) {
		super(main);

		c_teams.setValue(4);

		b_teams.setOnAction((event) -> {
			main.setMannschaftsnamenScreen(c_teams.getValue(), "mannschaftsnamen");
		});

		grid.add(l_teams, 0, 0);
		//GridPane.setHalignment(l_teams, HPos.RIGHT);

		grid.add(c_teams, 1, 0);
		c_teams.setMinWidth(100);
		grid.add(b_teams, 2, 0);
		b_teams.setMinWidth(100);

		grid.setPadding(new Insets(25));

		ColumnConstraints col1 = new ColumnConstraints(100);
		ColumnConstraints col2 = new ColumnConstraints(100);
		ColumnConstraints col3 = new ColumnConstraints(100);
		grid.getColumnConstraints().addAll(col1, col2,col3);

		grid.setHgap(10);
		grid.setVgap(10);

		grid.setPrefSize(500,200);
		grid.setAlignment(Pos.CENTER);
		grid.setGridLinesVisible(true);

		this.getChildren().add(grid);
		
	}

}
