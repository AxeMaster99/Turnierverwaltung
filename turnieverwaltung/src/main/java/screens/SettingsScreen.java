package screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import panes.SceneParent;
import stages.MatchStage;
import verwaltung.Steuerung;

public class SettingsScreen extends SceneParent {

	private Label l_turnierTypes = new Label("Turnierart");
	private Label l_teams = new Label("Teamanzahl");
	private Label l_matchDauer = new Label("Match dauer");
	private final ObservableList<String> types = FXCollections.observableArrayList("KO-Turnier", "Gruppen + KO");
	private final ObservableList<Integer> options = FXCollections.observableArrayList(4, 8, 16, 32);
	private final ObservableList<Integer> durations = FXCollections.observableArrayList(5, 30, 45, 60, 75, 90);
	private ComboBox<String> c_types = new ComboBox<String>(types);
	private ComboBox<Integer> c_teams = new ComboBox<Integer>(options);
	private ComboBox<Integer> c_duration = new ComboBox<Integer>(durations);
	private GridPane grid = new GridPane();
	private Button b_teams = new Button("Bestätigen");

	public SettingsScreen(Steuerung steuerung) {
		super(steuerung);

		c_types.setValue("KO Turnier");
		c_duration.setValue(5);
		c_teams.setValue(16);

		b_teams.setOnAction((event) -> {
			this.steuerung.setTurnierType(c_types.getValue());
			steuerung.setTeamScreen(c_teams.getValue(), "mannschaftsnamen");
			MatchStage.setTimerdauer(c_duration.getValue());
		});

		grid.add(l_turnierTypes, 0, 0);
		grid.add(l_teams, 0, 1);
		grid.add(l_matchDauer, 0, 2);
		// GridPane.setHalignment(l_teams, HPos.RIGHT);

		grid.add(c_types, 1, 0);
		grid.add(c_teams, 1, 1);
		grid.add(c_duration, 1, 2);
		grid.add(b_teams, 1, 3);
		c_types.setMinWidth(200);
		c_duration.setMinWidth(200);
		c_types.setMinWidth(200);
		c_teams.setMinWidth(200);

		grid.setPadding(new Insets(25));

		ColumnConstraints col1 = new ColumnConstraints(100);
		ColumnConstraints col2 = new ColumnConstraints(100);
		ColumnConstraints col3 = new ColumnConstraints(100);
		grid.getColumnConstraints().addAll(col1, col2, col3);

		grid.setHgap(10);
		grid.setVgap(10);

		grid.setMinSize(500, 200);
		grid.setAlignment(Pos.CENTER);

		this.getChildren().add(grid);

	}

}
