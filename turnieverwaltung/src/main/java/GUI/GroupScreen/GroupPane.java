package GUI.GroupScreen;

import java.util.ArrayList;

import main.Steuerung;
import GUI.MatchStage.Event;
import backend.interfaces.IMatch;
import backend.interfaces.IMatchStage;
import backend.turnier.Mannschaft;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GroupPane extends Pane {

	private boolean randomThusDisable = false;

	private ArrayList<GroupMatchStage> gms = new ArrayList<GroupMatchStage>();
	private ObservableList<IMatch> matches = FXCollections.observableArrayList();
	
	private TableView<IMatch> table = new TableView<IMatch>();

	TableColumn<IMatch, Mannschaft> team1Col = new TableColumn<IMatch, Mannschaft>("Team");
	TableColumn<IMatch, Mannschaft> team2Col = new TableColumn<IMatch, Mannschaft>("Team");
	TableColumn<IMatch, Integer> tor1Col = new TableColumn<IMatch, Integer>("T1");
	
	TableColumn<IMatch, Integer> tor2Col = new TableColumn<IMatch, Integer>("T2");
	TableColumn<IMatch, String> stateCol = new TableColumn<IMatch, String>("State");


	@SuppressWarnings("unchecked")
	public GroupPane(Steuerung steuerung, ObservableList<IMatch> matches, int i, ArrayList<Mannschaft> teams) {
		int start = 6 * i;
		int ende = start + 6;
		while (start < ende) {
			this.matches.add(matches.get(start));
			start++;
		}
		
		team1Col.setCellValueFactory(new PropertyValueFactory<IMatch, Mannschaft>("mannschaft1"));
		team2Col.setCellValueFactory(new PropertyValueFactory<IMatch, Mannschaft>("mannschaft2"));
		tor1Col.setCellValueFactory(new PropertyValueFactory<IMatch, Integer>("toreM1"));
		tor2Col.setCellValueFactory(new PropertyValueFactory<IMatch, Integer>("toreM2"));
		stateCol.setCellValueFactory(new PropertyValueFactory<IMatch, String>("state"));
		
		table.setItems(this.matches);
		table.getColumns().addAll(team1Col, tor1Col, tor2Col, team2Col,stateCol);
				
		
		tor1Col.setMaxWidth(25);
		tor2Col.setMaxWidth(25);
		table.setMaxWidth(300);
		table.setMaxHeight(250);
		table.setStyle("-fx-font-size: 12;");
		this.getChildren().add(table);

		table.setOnMouseReleased((event) -> {
			if (this.randomThusDisable == true) {
				IMatch match = table.getSelectionModel().getSelectedItem();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Info");
				alert.setHeaderText("Das Spiel wurde schon beendet");
				alert.setContentText("Ergebnis: " + match.getToreM1() + ":" + match.getToreM2());
				alert.showAndWait();
			} else {
				boolean gefunden = false;
				for (int index = 0; index < gms.size(); index++) {
					if (gms.get(index).getMatch() == table.getSelectionModel().getSelectedItem()) {
						gms.get(index).switchState(Event.click);
						gefunden = true;
					}
				}
				if (gefunden == false) {
					IMatchStage neueStage = new GroupMatchStage(this, table.getSelectionModel().getSelectedItem());
					gms.add((GroupMatchStage) neueStage);
					neueStage.switchState(Event.click);
				}
			}
		});
	}

	public TableView<IMatch> getTable() {
		return this.table;
	}

	public void setDisable() {
		this.randomThusDisable = true;
	}

}
