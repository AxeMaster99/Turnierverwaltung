package GUI.GroupScreen;

import java.util.ArrayList;

import GUI.RangStageTable;
import backend.turnier.Mannschaft;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GroupRangStage extends Stage {

	private GridPane grid = new GridPane();
	private Pane root = new Pane();

	private ObservableList<Mannschaft> mannschaften = FXCollections.observableArrayList();

	private ArrayList<RangStageTable> tables = new ArrayList<RangStageTable>();

	public GroupRangStage() {
		super();
		this.setTitle("Rangliste");

		root.getChildren().add(grid);

		grid.setMinWidth(400);
		grid.setMinHeight(650);
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setPadding(new Insets(10));

		Scene scene = new Scene(root, 650, 650);
		this.setScene(scene);
	}

	@SuppressWarnings("unchecked")
	public void initializeTable(ArrayList<Mannschaft> teams) {
		for (int i = 0; i < teams.size(); i++) {
			mannschaften.add(teams.get(i));
		}
		int rowLeft = 0;
		int rowRight = 0;
		int numberOfGroups = mannschaften.size() / 4;
		int start = 0;
		int ende = 4;

		for (int i = 0; i < numberOfGroups; i++) {
			this.tables.add(new RangStageTable());
			ObservableList<Mannschaft> sublist = FXCollections.observableArrayList();
			for (; start < ende; start++) {
				sublist.add(mannschaften.get(start));
			}
			ende += 4;
			this.tables.get(i).setItems(sublist);
			this.tables.get(i).getColumns().addAll(tables.get(i).nameCol, tables.get(i).pntCol, tables.get(i).torCol);
			this.tables.get(i).setMaxHeight(135);
			GridPane.setMargin(tables.get(i), new Insets(0, 15, 5, 0));

			tables.get(i).getSortOrder().add(tables.get(i).pntCol);
			tables.get(i).pntCol.setSortType(TableColumn.SortType.DESCENDING);
			tables.get(i).getSortOrder().add(tables.get(i).torCol);
			tables.get(i).torCol.setSortType(TableColumn.SortType.DESCENDING);

			if (rowLeft < 8) {
				grid.add(new Label("Gruppe " + this.getCharForNumber(i)), 0, rowLeft);
				grid.add(tables.get(i), 0, rowLeft + 1);
				rowLeft += 2;
			} else {
				grid.add(new Label("Gruppe " + this.getCharForNumber(i)), 1, rowRight);
				grid.add(tables.get(i), 1, rowRight + 1);
				rowRight += 2;
			}
		}
	}

	public final void updateTables() {
		for (int i = 0; i < tables.size(); i++) {
			tables.get(i).sort();
			tables.get(i).refresh();
		}
	}

	private String getCharForNumber(int i) {
		return i > -1 && i < 26 ? String.valueOf((char) (i + 65)) : null;
	}

}
