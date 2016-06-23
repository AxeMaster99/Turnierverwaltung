package GUI;

import main.Steuerung;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SettingsScreen extends Pane {

	private static final Logger logger = (Logger) LogManager.getLogger("SettingsScreen");
	
	private final ObservableList<String> types = FXCollections.observableArrayList("KO-Turnier", "Gruppen + KO");
	private final ObservableList<String> options = FXCollections.observableArrayList("8 Teams", "16 Teams", "32 Teams");
	private final ObservableList<String> durations = FXCollections.observableArrayList("5 Minuten", "30 Minuten","45 Minuten", "60 Minuten", "75 Minuten", "90 Minuten");
	
	private ComboBox<String> c_types = new ComboBox<String>(types);
	private ComboBox<String> c_teams = new ComboBox<String>(options);
	private ComboBox<String> c_duration = new ComboBox<String>(durations);
	private GridPane grid = new GridPane();
	private Button b_teams = new Button("Bestätigen");
	

	public SettingsScreen(Steuerung steuerung) {

		c_types.setValue("Turnierart");
		c_teams.setValue("Anzahl Teams");
		c_teams.setDisable(true);
		c_duration.setValue("Matchdauer");
		c_duration.setDisable(true);

		c_types.setOnAction((event) -> {
			if (c_types.getValue().equals("KO-Turnier")) {
				c_teams.getItems().add(0, "4 Teams");
				c_teams.setValue("Anzahl Teams");
			} else if (c_types.getValue().equals("Gruppen + KO") && c_teams.getItems().get(0).equals("4 Teams")) {
				c_teams.getItems().remove(0);
				c_teams.setValue("Anzahl Teams");
				c_teams.autosize();
			}
			c_teams.setDisable(false);
		});

		c_teams.setOnAction((event) -> {
			c_duration.setDisable(false);
		});

		b_teams.setOnAction((event) -> {

			if (c_types.getValue().equals("Turnierart") || c_teams.getValue().equals("Anzahl Teams") || c_duration.getValue().equals("Matchdauer")) {
				Alert missingInput = new Alert(AlertType.INFORMATION);
				missingInput.setTitle("Fehler");
				missingInput.setHeaderText("Es fehlt mindestens eine Eingabe");
				missingInput.setContentText("Zum fortfahren alle Felder ausfüllen.");
				missingInput.showAndWait();
			}

			else {
				
				steuerung.setTurnierType(c_types.getValue());

				String teamsCut = c_teams.getValue().substring(0, 2);
				if (teamsCut.charAt(1) == ' ') {
					teamsCut = teamsCut.substring(0, 1);
				}
				steuerung.setTeamScreen(Integer.parseInt(teamsCut), "mannschaftsnamen");
				String durationCut = c_duration.getValue().substring(0, 2);
				if (durationCut.charAt(1) == ' ') {
					durationCut = durationCut.substring(0, 1);
				}
				MatchStage.setTimerdauer(Integer.parseInt(durationCut));
				logger.info("Anzahl Teams: " + teamsCut + ", Matchdauer: " + durationCut);
			}
		});

		grid.add(c_types, 0, 0);
		grid.add(c_teams, 0, 1);
		grid.add(c_duration, 0, 2);
		grid.add(b_teams, 0, 3);
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
