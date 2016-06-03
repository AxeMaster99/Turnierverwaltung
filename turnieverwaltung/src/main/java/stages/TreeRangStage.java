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
import table.RangStageTable;
import verwaltung.Steuerung;

// Turnierrangliste: Fenster kann während dem Turnier offen gelassen werden. Aktualisiert sich automatisch und zeigt Mannschaftsname, Punkte und Tordifferenz an. 
// Standardmäßig wird nach Punktestand, bei gleichem Punktestand nach Tordifferenz absteigend sortiert.

public class TreeRangStage extends Stage {

	private GridPane grid = new GridPane();
	private Pane root = new Pane();
	
	private ObservableList<Mannschaft> mannschaften = FXCollections.observableArrayList();
	
	private RangStageTable table = new RangStageTable();
	
	@SuppressWarnings("unchecked")
	public TreeRangStage() {
		super();
		this.setTitle("Rangliste");

		table.setItems(mannschaften);
		table.getColumns().addAll(table.nameCol, table.pntCol, table.torCol);		

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
	
	public void initializeTable(ArrayList<IMatch> matches) {		
		for (int i = 0; i < (matches.size() / 4 + 1); i++) {
			mannschaften.add(matches.get(i).getMannschaft1());
			mannschaften.add(matches.get(i).getMannschaft2());
		}

		for (int i = matches.size() / 2; i <= ((matches.size() / 4) * 3) + 1; i++) {
			mannschaften.add(matches.get(i).getMannschaft1());
			mannschaften.add(matches.get(i).getMannschaft2());
		}
		
		table.getSortOrder().add(table.pntCol);
		table.pntCol.setSortType(TableColumn.SortType.DESCENDING);
		table.getSortOrder().add(table.torCol);
		table.torCol.setSortType(TableColumn.SortType.DESCENDING);		
	}

	public final void updateTable() {
		table.sort();
		table.refresh();
	}

}
