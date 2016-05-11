package org.miprojekt.turnieverwaltung.gui.windows;

import java.util.Optional;

import org.miprojekt.turnieverwaltung.Main;
import org.miprojekt.turnieverwaltung.gui.SceneParent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;


public class Mannschaftsnamen extends SceneParent {

	private int anzahlMannschaften;
	private int cnt = 0;
	private ObservableList<String> teams = FXCollections.observableArrayList();
	private ListView<String> lb_teams = new ListView<String>(teams);
	private ProgressBar pb_fortschritt = new ProgressBar(0);
	private ProgressIndicator pi = new ProgressIndicator(0);
	private Button b_submit = new Button("Bestätigen");
	private GridPane grid = new GridPane();
	private Button b_add = new Button("hinzufügen");
	private Button b_delete = new Button("entfernen");
	private Button b_edit = new Button("Name ändern");
	private Button b_back = new Button("zurück");
	private Button b_autofill = new Button("aut. füllen");
	private TextField t_teamnames = new TextField();

	public Mannschaftsnamen(Main main, int anzahlMannschaften) {
		super(main);
		this.anzahlMannschaften = anzahlMannschaften;

		grid.setPadding(new Insets(20));
		
		grid.add(new Label("Mannschaftsname:"), 0,0 );

		grid.add(t_teamnames, 0, 1, 2, 1);
		grid.add(b_add, 2, 1);
		grid.add(b_autofill, 3, 1);

		grid.add(lb_teams, 0, 2);
		GridPane.setColumnSpan(lb_teams, 4);

		grid.add(b_edit, 0, 3);
		grid.add(b_delete, 1, 3);

		grid.add(pb_fortschritt, 0, 4, 4, 1);
		pb_fortschritt.setPrefWidth(250);

		grid.add(pi, 3, 4);

		grid.add(b_submit, 3, 5);
		b_submit.setDisable(true);

		grid.add(b_back, 0, 5);

		GridPane.setMargin(lb_teams, new Insets(15, 0, 15, 0));
		GridPane.setMargin(b_add, new Insets(0, 15, 0, 15));
		GridPane.setMargin(b_edit, new Insets(0, 15, 0, 0));
		GridPane.setMargin(pb_fortschritt, new Insets(20));
		GridPane.setMargin(pi, new Insets(0,0,20,0));

		b_add.setOnAction((event) -> {
			if (cnt >= anzahlMannschaften) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setHeaderText("Es ist ein Fehler aufgetreten");
				alert.setContentText("Anzahl der Mannschaften erreicht");
				alert.showAndWait();
			} else if (t_teamnames.getText().replace(" ", "").length() == 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setHeaderText("Es ist ein Fehler aufgetreten");
				alert.setContentText("Kein Mannschaftsname eingegeben");
				alert.showAndWait();
			} else {
				Boolean teamSchonVorhanden = false;
				for (int i = 0; i < teams.size(); i++) {
					if (t_teamnames.getText().equals(teams.get(i))) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Fehler");
						alert.setHeaderText("Es ist ein Fehler aufgetreten");
						alert.setContentText("Mannschaft ist schon vorhanden");
						alert.showAndWait();
						teamSchonVorhanden = true;
						break;
					}
				}
				if (!teamSchonVorhanden) {
					teams.add(t_teamnames.getText());
					t_teamnames.requestFocus();
					cnt++;
					updateFortschritt();
				}
				t_teamnames.setText("");
			}
		});

		b_delete.setOnAction((event) -> {
			if (lb_teams.getSelectionModel().getSelectedItem() == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setHeaderText("Es ist ein Fehler aufgetreten");
				alert.setContentText("Kein Mannschaft ausgewählt");
				alert.showAndWait();
			} else {
				teams.remove(lb_teams.getSelectionModel().getSelectedIndex());
				cnt--;
				updateFortschritt();
			}
		});

		b_edit.setOnAction((event) -> {
			if (lb_teams.getSelectionModel().getSelectedItem() == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setHeaderText("Es ist ein Fehler aufgetreten");
				alert.setContentText("Keine Mannschaft ausgewählt");
				alert.showAndWait();
			} else {
				TextInputDialog dialog = new TextInputDialog(lb_teams.getSelectionModel().getSelectedItem());
				dialog.setHeaderText("Bitte neuen Namen eingeben");
				dialog.setTitle("Mannschaft ändern");
				dialog.setContentText("Neuer Name:");
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					if (result.get().equals("")) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Fehler");
						alert.setHeaderText("Es ist ein Fehler aufgetreten");
						alert.setContentText("Name darf nicht leer sein");
						alert.showAndWait();
					} else {
						teams.set(lb_teams.getSelectionModel().getSelectedIndex(), result.get());
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information");
						alert.setHeaderText("Die Mannschaft wurde umbenannt");
						alert.setContentText("Die Mannschaft wurde zu " +result.get()+" umbenannt.");
						alert.showAndWait();
					}
				}
			}
		});

		t_teamnames.setOnKeyReleased((KeyEvent event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				b_add.fire();
			}
		});

		b_back.setOnAction((event) -> {
			main.getStage().setScene(main.getScreen("settings"));
		});

		b_autofill.setOnAction((event) -> {
			teams.clear();
			for (int i = 1; i <= anzahlMannschaften; i++) {
				teams.add("Mannschaft " + i);
			}
			cnt = anzahlMannschaften;
			updateFortschritt();
		});
		
		b_submit.setOnAction((event)->{
			steuerung.setTeams(teams);
			main.setSpielBaumScreen(steuerung.getMatches(), "spielbaum");
		});
		

		this.getChildren().add(grid);
	}
	

	public void updateFortschritt() {
		double cnt_double = cnt;
		double anzMannschaftenDouble = anzahlMannschaften;
		this.pb_fortschritt.setProgress(1 / anzMannschaftenDouble * cnt_double);
		this.pi.setProgress(this.pb_fortschritt.getProgress());
		if (cnt == anzahlMannschaften) {
			b_submit.setDisable(false);
			b_add.setDisable(true);
			t_teamnames.setDisable(true);
		} else {
			b_submit.setDisable(true);
			b_add.setDisable(false);
			t_teamnames.setDisable(false);
		}
	}

}
