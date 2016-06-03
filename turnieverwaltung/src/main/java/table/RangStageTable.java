package table;

import backend.Mannschaft;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RangStageTable extends TableView<Mannschaft> {
	
	public TableColumn<Mannschaft, String> nameCol = new TableColumn<Mannschaft, String>("Name");
	public TableColumn<Mannschaft, Integer> pntCol = new TableColumn<Mannschaft, Integer>("Pkt");
	public TableColumn<Mannschaft, Integer> torCol  = new TableColumn<Mannschaft, Integer>("Tordiff.");
	
	public RangStageTable(){
		super();
		
		nameCol.setMinWidth(100);
		nameCol.setCellValueFactory(new PropertyValueFactory<Mannschaft, String>("name"));
		pntCol.setCellValueFactory(new PropertyValueFactory<Mannschaft, Integer>("punkte"));
		pntCol.setMinWidth(100);
		torCol.setCellValueFactory(new PropertyValueFactory<Mannschaft, Integer>("tordifferenz"));
		torCol.setMinWidth(100);

	}
}
