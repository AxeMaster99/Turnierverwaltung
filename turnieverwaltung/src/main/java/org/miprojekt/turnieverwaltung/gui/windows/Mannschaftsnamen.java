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
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class Mannschaftsnamen extends SceneParent {

	private int anzahlMannschaften;
	private int cnt = 0; // Globale Laufvariable
	ProgressBar pb_fortschritt = new ProgressBar(0);
	
	public Mannschaftsnamen(Main main, int anzahlMannschaften) {
		super(main);
		this.anzahlMannschaften = anzahlMannschaften;

		GridPane grid = new GridPane();
		Button b_add = new Button("hinzufügen");
		Button b_delete = new Button("entfernen");
		Button b_edit = new Button("Name ändern");
		TextField t_teamnames = new TextField();
		
		ObservableList<String> teams = FXCollections.observableArrayList();
		ListView<String> lb_teams = new ListView<String>(teams);

		GridPane.setColumnSpan(lb_teams, 2);
		GridPane.setColumnSpan(pb_fortschritt, 2);
		pb_fortschritt.setMinWidth(400);

		grid.add(t_teamnames, 0, 0);
		grid.add(b_add, 1, 0);
		grid.add(b_delete, 0, 2);
		grid.add(b_edit, 1, 2);
		grid.setPadding(new Insets(25));
		grid.add(pb_fortschritt, 0, 3);

		GridPane.setMargin(t_teamnames, new Insets(0, 25, 0, 0));

		b_add.setOnAction((event) -> {
			if (cnt >= anzahlMannschaften) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setHeaderText("Es ist ein Fehler aufgetreten");
				alert.setContentText("Anzahl der Mannschaften erreicht");
				alert.showAndWait();
			} else if (t_teamnames.getText().equals("")) {
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
						alert.setContentText("Mannshaft ist schon vorhanden");
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
				alert.setHeaderText("Kein Mannschaft ausgewählt");
				alert.setContentText("!");
				alert.showAndWait();
			} else {
				TextInputDialog dialog = new TextInputDialog(lb_teams.getSelectionModel().getSelectedItem());
				dialog.setHeaderText("Bitte neuen Namen eingeben");
				dialog.setTitle("Mannschaft ändern");
				dialog.setContentText("Neuer Name:");
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					teams.set(lb_teams.getSelectionModel().getSelectedIndex(), result.get());
					System.out.println("Neuer Name: " + result.get());
				}
			}
		});

		t_teamnames.setOnKeyReleased((KeyEvent event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				b_add.fire();
			}
		});

		grid.add(lb_teams, 0, 1);
		GridPane.setMargin(lb_teams, new Insets(25, 0, 0, 0));
		this.getChildren().add(grid);
	}
	
	
	public void updateFortschritt() {
		double cnt_double = cnt;
		double anzMannschaftenDouble = anzahlMannschaften;
		this.pb_fortschritt.setProgress(1/anzMannschaftenDouble*cnt);
	}

}
