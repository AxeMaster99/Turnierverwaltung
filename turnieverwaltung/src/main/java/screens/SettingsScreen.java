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

	private Label l_teams = new Label("Teamanzahl");
	private Label l_matchDauer = new Label("Match dauer");
	private final ObservableList<Integer> options = FXCollections.observableArrayList(4, 8, 16, 32);
	private final ObservableList<Integer> durations = FXCollections.observableArrayList(5, 30, 45, 60,75, 90);
	private ComboBox<Integer> c_teams = new ComboBox<Integer>(options);
	private ComboBox<Integer> setMatchDuration = new ComboBox<Integer>(durations);
	private GridPane grid = new GridPane();
	private Button b_teams = new Button("Bestätigen");

	public SettingsScreen(Steuerung steuerung) {
		super(steuerung);
		
		setMatchDuration.setValue(90);		
			
		
		c_teams.setValue(4);

		b_teams.setOnAction((event) -> {
			steuerung.setTeamScreen(c_teams.getValue(), "mannschaftsnamen");
			MatchStage.setDuration(setMatchDuration.getValue());
		});

		grid.add(l_teams, 0, 0);
		grid.add(l_matchDauer, 0, 1);
		//GridPane.setHalignment(l_teams, HPos.RIGHT);

		grid.add(c_teams, 1, 0);
		c_teams.setMinWidth(100);
		grid.add(b_teams, 2, 1);
		b_teams.setMinWidth(100);
		grid.add(setMatchDuration, 1, 1);
		setMatchDuration.setMinWidth(100);
		
		
		grid.setPadding(new Insets(25));

		ColumnConstraints col1 = new ColumnConstraints(100);
		ColumnConstraints col2 = new ColumnConstraints(100);
		ColumnConstraints col3 = new ColumnConstraints(100);
		grid.getColumnConstraints().addAll(col1, col2,col3);

		grid.setHgap(10);
		grid.setVgap(10);

		grid.setMinSize(500,300);
		grid.setAlignment(Pos.CENTER);

		this.getChildren().add(grid);
		
	}

}
