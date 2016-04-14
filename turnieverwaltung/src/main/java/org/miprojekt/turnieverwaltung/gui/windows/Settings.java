package org.miprojekt.turnieverwaltung.gui.windows;
import org.miprojekt.turnieverwaltung.Main;
import org.miprojekt.turnieverwaltung.gui.SceneParent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class Settings extends SceneParent {

	public Settings(Main main) {
		super(main);
		
		
		Label l_teams = new Label("Anzahl der Teams eingeben: ");
		final ObservableList<Integer> options = 
			    FXCollections.observableArrayList(
			        2,
			        4,
			        8,
			        16,
			        32
			    );
		ComboBox<Integer> c_teams = new ComboBox<Integer>(options);
		c_teams.setValue(2);
		
		Button b = new Button("OKAY");
		
		
		GridPane grid = new GridPane();
		
		
		grid.add(l_teams, 0, 0);
		grid.add(c_teams, 1, 0);
		grid.add(b, 2,0);
		
		this.getChildren().add(grid);
		
	}
	
}
