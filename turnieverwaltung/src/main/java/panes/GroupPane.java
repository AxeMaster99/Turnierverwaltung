package panes;

import java.util.ArrayList;

import backend.Mannschaft;
import interfaces.IMatch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import stages.GroupMatchStage;
import stages.MatchStage;
import stages.MatchStage.Event;
import verwaltung.Steuerung;

public class GroupPane extends Pane {

	private ObservableList<IMatch> matches = FXCollections.observableArrayList();
	private TableView<IMatch> table = new TableView<IMatch>();

	TableColumn<IMatch, Mannschaft> team1Col = new TableColumn<IMatch, Mannschaft>("Mannschaft 1");
	TableColumn<IMatch, Mannschaft> team2Col = new TableColumn<IMatch, Mannschaft>("Mannschaft 2");
	TableColumn<IMatch, Integer> tor1Col = new TableColumn<IMatch, Integer>("T - M1");
	TableColumn<IMatch, Integer> tor2Col = new TableColumn<IMatch, Integer>("T - M2");

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

		table.setItems(this.matches);
		table.getColumns().addAll(team1Col, tor1Col, tor2Col, team2Col);

		table.setMaxWidth(295);
		table.setMaxHeight(250);

		this.getChildren().add(table);

		table.setOnMouseReleased((event) -> {
			IMatch match = table.getSelectionModel().getSelectedItem();
			
			GroupMatchStage gms = new GroupMatchStage(match);
			gms.switchState(Event.click);
			
		});
	}

	public TableView<IMatch> getTable() {
		return this.table;
	}

}
