package GUI;

import java.util.Optional;

import main.Steuerung;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class TeamScreen extends Pane {

	private int anzahlMannschaften;
	private int cnt = 0;
	private ObservableList<String> teams = FXCollections.observableArrayList();
	private ListView<String> lb_teams = new ListView<String>(teams);
	private ProgressBar pb_fortschritt = new ProgressBar(0);
	private ProgressIndicator pi = new ProgressIndicator(0);
	private Label l_mannschatsname = new Label("Mannschaftsname:");
	private Button b_submit = new Button("Bestätigen");
	private GridPane grid = new GridPane();
	private Button b_add = new Button("hinzufügen");
	private Button b_delete = new Button("entfernen");
	private Button b_edit = new Button("Name ändern");
	private Button b_back = new Button("zurück");
	private Button b_autofill = new Button("aut. füllen");
	private TextField t_teamnames = new TextField();
	private static final Logger logger = (Logger) LogManager.getLogger("TeamScreen");
	
	public TeamScreen(Steuerung steuerung, int anzahlMannschaften) {
		this.anzahlMannschaften = anzahlMannschaften;
	    
		ColumnConstraints col1 = new ColumnConstraints(100);
		ColumnConstraints col2 = new ColumnConstraints(100);
		ColumnConstraints col3 = new ColumnConstraints(100);
		ColumnConstraints col4 = new ColumnConstraints(100);
		grid.setHgap(10);
		grid.setVgap(10);
		
		grid.getColumnConstraints().addAll(col1,col2,col3,col4);
		
		grid.setPadding(new Insets(20));
		
		grid.add(l_mannschatsname, 0,0 );
		l_mannschatsname.setMinWidth(100);

		grid.add(t_teamnames, 0, 1, 2, 1);
		t_teamnames.setMinWidth(100);
		
		grid.add(b_add, 2, 1);
		b_add.setMinWidth(100);

		grid.add(b_autofill, 3, 1);
		b_autofill.setMinWidth(100);

		grid.add(lb_teams, 0, 2);
		GridPane.setColumnSpan(lb_teams, 4);

		grid.add(b_edit, 0, 3);
		b_edit.setMinWidth(100);

		grid.add(b_delete, 1, 3);
		b_delete.setMinWidth(100);

		grid.add(pb_fortschritt, 0, 4, 3, 1);
		pb_fortschritt.setMinWidth(270);
		GridPane.setHalignment(pb_fortschritt, HPos.CENTER);

		grid.add(pi, 3, 4);

		grid.add(b_submit, 3, 5);
		b_submit.setMinWidth(100);
		
		b_submit.setDisable(true);

		grid.add(b_back, 0, 5);
		b_back.setMinWidth(100);

	//	grid.setGridLinesVisible(true);
		
		GridPane.setHalignment(b_add, HPos.CENTER);
		grid.setMinSize(500,630);
		grid.setAlignment(Pos.CENTER);
		
		this.addListener(anzahlMannschaften);
		this.deleteListener();
		this.editListener();
		this.teamnamesEnterListener();
		this.backListener(steuerung);
		this.autoFillListener(anzahlMannschaften);
		this.submitListener(steuerung);
		
		this.getChildren().add(grid);
	}

	private void addListener(int anzahlMannschaften) {
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
	}

	private void deleteListener() {
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
	}

	private void editListener() {
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
	}

	private void teamnamesEnterListener() {
		t_teamnames.setOnKeyReleased((KeyEvent event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				b_add.fire();
			}
		});
	}

	private void backListener(Steuerung steuerung) {
		b_back.setOnAction((event) -> {
			steuerung.setSettingsScreen();
		});
	}

	private void submitListener(Steuerung steuerung) {
		b_submit.setOnAction((event)->{
				logger.info("Turnier-Type gesetzt auf: "+steuerung.getTurnierType());
				if(steuerung.getTurnierType() == "KO-Turnier") {
					steuerung.setTreeScreen("spielBaum",teams);
				} else {
					steuerung.setGroupScreen("groupscreen", teams);
				}	
		});
	}

	private void autoFillListener(int anzahlMannschaften) {
		b_autofill.setOnAction((event) -> {
			teams.clear();
			for (int i = 1; i <= anzahlMannschaften; i++) {

				teams.add("Mannschaft " + i);
			}
			cnt = anzahlMannschaften;
			updateFortschritt();
		});
	}
	
	/**
	 * updates the progress bar and the progress indicator. If the value of cnt
	 * equals the value of anzahlMannschaften, the button which adds a team is disabled 
	 * and the button "submit" is enabled.
	 * If a team is deleted, the button "add" is enabled again.
	 */

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
