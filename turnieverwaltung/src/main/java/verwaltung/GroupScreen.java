package verwaltung;

import java.util.ArrayList;

import backend.Group;
import backend.Mannschaft;
import backend.MatchFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import panes.SceneParent;
import stages.MatchStage;
import interfaces.IMatch;

public class GroupScreen extends SceneParent {

	private ObservableList<String> teamnamen = FXCollections.observableArrayList();
	private ArrayList<Mannschaft>  teams = new ArrayList<Mannschaft>();
	private ArrayList<Group> groups = new ArrayList<Group>();
	private ObservableList<IMatch> matches = FXCollections.observableArrayList();
	
	private TableView<IMatch> table = new TableView<IMatch>();
	
	TableColumn<IMatch, Mannschaft> team1Col = new TableColumn<IMatch, Mannschaft>("Mannschaft 1 vs");
	TableColumn<IMatch, Mannschaft> team2Col = new TableColumn<IMatch, Mannschaft>("Mannschaft 2");
	TableColumn<IMatch, Integer> tor1Col = new TableColumn<IMatch, Integer>("Tore M1");
	TableColumn<IMatch, Integer> tor2Col = new TableColumn<IMatch, Integer>("Tore M2");
	
	@SuppressWarnings("unchecked")
	public GroupScreen(Steuerung steuerung, ObservableList<String> teamnamen) {
		super(steuerung);
		this.teamnamen = teamnamen;
//		Collections.shuffle(this.teamnamen);
		for (int i=0; i<this.teamnamen.size();i++){
			teams.add(new Mannschaft(this.teamnamen.get(i)));
		}
		
		for (int i=0;i<this.teamnamen.size();i+=4){
			groups.add(new Group(teams.get(i),teams.get(i+1),teams.get(i+2),teams.get(i+3)));
		}
		
		for (int i=0; i<this.groups.size();i++){
			matches.add(MatchFactory.build(steuerung, groups.get(i).getM1().getName(), groups.get(i).getM2().getName()));
			matches.add(MatchFactory.build(steuerung, groups.get(i).getM3().getName(), groups.get(i).getM4().getName()));
			matches.add(MatchFactory.build(steuerung, groups.get(i).getM1().getName(), groups.get(i).getM3().getName()));
			matches.add(MatchFactory.build(steuerung, groups.get(i).getM2().getName(), groups.get(i).getM4().getName()));
			matches.add(MatchFactory.build(steuerung, groups.get(i).getM1().getName(), groups.get(i).getM4().getName()));
			matches.add(MatchFactory.build(steuerung, groups.get(i).getM2().getName(), groups.get(i).getM3().getName()));
		}
		
		for (int i=0; i<this.matches.size();i++){
			System.out.println(this.matches.get(i).toString());
		}
		
		team1Col.setMinWidth(150);
		team1Col.setCellValueFactory(new PropertyValueFactory<IMatch, Mannschaft>("mannschaft1"));

		team2Col.setMinWidth(100);
		team2Col.setCellValueFactory(new PropertyValueFactory<IMatch, Mannschaft>("mannschaft2"));
		
		tor1Col.setMinWidth(150);
		tor1Col.setCellValueFactory(new PropertyValueFactory<IMatch, Integer>("toreM1"));

		tor2Col.setMinWidth(100);
		tor2Col.setCellValueFactory(new PropertyValueFactory<IMatch, Integer>("toreM2"));
	
		table.setItems(matches);
		table.getColumns().addAll(team1Col, team2Col,tor1Col,tor2Col);
		
		this.getChildren().add(table);
		
		table.setOnMouseReleased((event)->{
			 IMatch match = table.getSelectionModel().getSelectedItem();     
			 new MatchStage(match);
		});
	}

}
