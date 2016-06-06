package screens;

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
import stages.MatchStage;
import verwaltung.Steuerung;

public class SettingsScreen extends Pane {

	// private Label l_turnierTypes = new Label("Turnierart");
	// private Label l_teams = new Label("Teamanzahl");
	// private Label l_matchDauer = new Label("Match dauer");

	private final ObservableList<String> types = FXCollections.observableArrayList("KO-Turnier", "Gruppen + KO");
	private final ObservableList<String> options = FXCollections.observableArrayList("4 Teams (Nur KO)!", "8 Teams",
			"16 Teams", "32 Teams");
	private final ObservableList<String> durations = FXCollections.observableArrayList("5 Minuten", "30 Minuten",
			"45 Minuten", "60 Minuten", "75 Minuten", "90 Minuten");
	private ComboBox<String> c_types = new ComboBox<String>(types);
	private ComboBox<String> c_teams = new ComboBox<String>(options);
	private ComboBox<String> c_duration = new ComboBox<String>(durations);
	private GridPane grid = new GridPane();
	private Button b_teams = new Button("Bestätigen");
	private static final Logger logger = (Logger) LogManager.getLogger("SettingsScreen");

	public SettingsScreen(Steuerung steuerung) {

		c_types.setValue("Turnierart");
		c_duration.setValue("Matchdauer");
		c_teams.setValue("Anzahl Teams");

		b_teams.setOnAction((event) -> {
			if (c_types.getValue().equals("Turnierart") || c_duration.getValue().equals("Matchdauer")
					|| c_teams.getValue().equals("Anzahl Teams")) {
				Alert missingInput = new Alert(AlertType.INFORMATION);
				missingInput.setTitle("Fehler");
				missingInput.setHeaderText("Eingaben fehlen!");
				missingInput.setContentText("Zum fortfahren alle Werte angeben.");
				missingInput.showAndWait();
			}

			else if (!c_types.getValue().equals("KO-Turnier") && c_teams.getValue().equals("4 Teams (Nur KO)!")) {
				Alert wrongInput = new Alert(AlertType.INFORMATION);
				wrongInput.setTitle("Fehler");
				wrongInput.setHeaderText("4 Teams sind nur für den Typ 'KO' gestattet!");
				wrongInput.setContentText("Zum fortfahren andere Teamanzahl angeben.");
				wrongInput.showAndWait();
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

		// grid.add(l_turnierTypes, 0, 0);
		// grid.add(l_teams, 0, 1);
		// grid.add(l_matchDauer, 0, 2);
		// GridPane.setHalignment(l_teams, HPos.RIGHT);

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
