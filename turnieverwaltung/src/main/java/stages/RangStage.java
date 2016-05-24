package stages;

import java.util.ArrayList;

import backend.Mannschaft;
import interfaces.IMatch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RangStage extends Stage {

	private ArrayList<IMatch> matches = new ArrayList<IMatch>();
	private ObservableList<Mannschaft> mannschaften = FXCollections.observableArrayList();
	private TableView<Mannschaft> table = new TableView<Mannschaft>();

	TableColumn<Mannschaft, String> nameCol;
	TableColumn<Mannschaft, Integer> pntCol;
	TableColumn<Mannschaft, Integer> torCol;

	private Pane root = new Pane();
	private GridPane grid = new GridPane();

	@SuppressWarnings("unchecked")
	public RangStage(ArrayList<IMatch> matches) {
		super();
		this.setTitle("Rangliste");
		this.matches = matches;

		for (int i = 0; i < (this.matches.size() / 4 + 1); i++) {
			mannschaften.add(this.matches.get(i).getMannschaft1());
			mannschaften.add(this.matches.get(i).getMannschaft2());
		}

		for (int i = this.matches.size() / 2; i <= ((this.matches.size() / 4) * 3) + 1; i++) {
			mannschaften.add(this.matches.get(i).getMannschaft1());
			mannschaften.add(this.matches.get(i).getMannschaft2());
		}

		nameCol = new TableColumn<Mannschaft, String>("Mannschaftsname");
		nameCol.setMinWidth(150);
		nameCol.setCellValueFactory(new PropertyValueFactory<Mannschaft, String>("name"));

		pntCol = new TableColumn<Mannschaft, Integer>("Punkte");
		pntCol.setMinWidth(100);
		pntCol.setCellValueFactory(new PropertyValueFactory<Mannschaft, Integer>("punkte"));
		TableColumn<Mannschaft, Integer> torCol = new TableColumn<Mannschaft, Integer>("Tordifferenz");
		torCol.setMinWidth(100);
		torCol.setCellValueFactory(new PropertyValueFactory<Mannschaft, Integer>("tordifferenz"));

		table.setItems(mannschaften);
		table.getColumns().addAll(nameCol, pntCol, torCol);

		
		table.getSortOrder().add(pntCol);
		pntCol.setSortType(TableColumn.SortType.DESCENDING);
		table.getSortOrder().add(torCol);
		torCol.setSortType(TableColumn.SortType.DESCENDING);

		// pntCol.setSortable(false);
		// torCol.setSortable(false);
		// nameCol.setSortable(false);

		grid.add(table, 0, 0);
		root.getChildren().add(grid);

		grid.setMinWidth(400);
		grid.setMinHeight(500);

		grid.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root, 400, 500);
		this.setScene(scene);
	}

}
