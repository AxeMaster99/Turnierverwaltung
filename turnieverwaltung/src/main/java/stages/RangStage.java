package stages;

import java.util.ArrayList;

import backend.Mannschaft;
import interfaces.IMatch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import verwaltung.Steuerung;

public class RangStage extends Stage {

	private Steuerung steuerung;
	private ArrayList<IMatch> matches = new ArrayList<IMatch>();
	private ObservableList<Mannschaft> mannschaften = FXCollections.observableArrayList();
	private TableView<Mannschaft> table = new TableView<Mannschaft>();
	
	TableColumn<Mannschaft, String> nameCol = new TableColumn<Mannschaft, String>("Name");
	TableColumn<Mannschaft, Integer> pntCol = new TableColumn<Mannschaft, Integer>("Punkte");
	TableColumn<Mannschaft, Integer> torCol  = new TableColumn<Mannschaft, Integer>("Tordifferenz");

	private Pane root = new Pane();
	private GridPane grid = new GridPane();

	@SuppressWarnings("unchecked")
	public RangStage(Steuerung steuerung) {
		super();
		this.setTitle("Rangliste");
		this.steuerung = steuerung;

		nameCol.setMinWidth(150);
		nameCol.setCellValueFactory(new PropertyValueFactory<Mannschaft, String>("name"));

		pntCol.setMinWidth(100);
		pntCol.setCellValueFactory(new PropertyValueFactory<Mannschaft, Integer>("punkte"));
		torCol.setMinWidth(100);
		torCol.setCellValueFactory(new PropertyValueFactory<Mannschaft, Integer>("tordifferenz"));

		table.setItems(mannschaften);
		table.getColumns().addAll(nameCol, pntCol, torCol);

		


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
		
		// Tabelle updaten, wenn RangStage angezeigt wird
		this.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent window)
            {
                updateTable();
            }
        });
		
	}
	
	public final void updateTable() {
		this.matches = this.steuerung.getMatches();
		this.mannschaften.clear();
		
		for (int i = 0; i < (this.matches.size() / 4 + 1); i++) {
			mannschaften.add(this.matches.get(i).getMannschaft1());
			mannschaften.add(this.matches.get(i).getMannschaft2());
		}

		for (int i = this.matches.size() / 2; i <= ((this.matches.size() / 4) * 3) + 1; i++) {
			mannschaften.add(this.matches.get(i).getMannschaft1());
			mannschaften.add(this.matches.get(i).getMannschaft2());
		}
		
		table.getSortOrder().add(pntCol);
		pntCol.setSortType(TableColumn.SortType.DESCENDING);
		table.getSortOrder().add(torCol);
		torCol.setSortType(TableColumn.SortType.DESCENDING);
	}

}
