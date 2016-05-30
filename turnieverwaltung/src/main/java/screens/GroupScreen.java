package screens;

import java.util.ArrayList;

import backend.Group;
import backend.Mannschaft;
import backend.MatchFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import panes.GroupPane;
import panes.SceneParent;
import stages.MatchStage;
import stages.RangStage;
import verwaltung.Steuerung;
import interfaces.IMatch;

public class GroupScreen extends SceneParent {

	private ObservableList<String> teamnamen = FXCollections.observableArrayList();
	private ArrayList<Mannschaft> teams = new ArrayList<Mannschaft>();
	private ArrayList<Group> groups = new ArrayList<Group>();
	private ObservableList<IMatch> matches = FXCollections.observableArrayList();

	public GroupScreen(Steuerung steuerung, ObservableList<String> teamnamen) {
		super(steuerung);
		
		MenuBar menubar = new MenuBar();
		Menu toolMenu = new Menu("Tools");
		MenuItem ranglisteMenuItem = new MenuItem("Rangliste");
		toolMenu.getItems().add(ranglisteMenuItem);
		
		ranglisteMenuItem.setOnAction((WindowEvent) -> {	
			// new RangStage(steuerung, teams).show();
		});
		
		menubar.getMenus().addAll(toolMenu);
		menubar.prefWidthProperty().bind(this.widthProperty());
		this.getChildren().add(menubar);
		
		this.teamnamen = teamnamen;
		// Collections.shuffle(this.teamnamen);
		for (int i = 0; i < this.teamnamen.size(); i++) {
			teams.add(new Mannschaft(this.teamnamen.get(i)));
		}

		for (int i = 0; i < this.teamnamen.size(); i += 4) {
			groups.add(new Group(teams.get(i), teams.get(i + 1), teams.get(i + 2), teams.get(i + 3)));
		}

		for (int i = 0; i < this.groups.size(); i++) {
			matches.add(
					MatchFactory.build(steuerung, groups.get(i).getM1().getName(), groups.get(i).getM2().getName()));
			matches.add(
					MatchFactory.build(steuerung, groups.get(i).getM3().getName(), groups.get(i).getM4().getName()));
			matches.add(
					MatchFactory.build(steuerung, groups.get(i).getM1().getName(), groups.get(i).getM3().getName()));
			matches.add(
					MatchFactory.build(steuerung, groups.get(i).getM2().getName(), groups.get(i).getM4().getName()));
			matches.add(
					MatchFactory.build(steuerung, groups.get(i).getM1().getName(), groups.get(i).getM4().getName()));
			matches.add(
					MatchFactory.build(steuerung, groups.get(i).getM2().getName(), groups.get(i).getM3().getName()));
		}

		for (int i = 0; i < this.matches.size(); i++) {
			System.out.println(this.matches.get(i).toString());
		}

		int x = 25;
		int y = 50;
		for (int i = 0; i < this.groups.size(); i++) {
			GroupPane pane = new GroupPane(steuerung, this.matches, i, teams);
			pane.setTranslateX(x);
			pane.setTranslateY(y);
			this.getChildren().add(pane);
			x+=310;
			if(x>1000){
				y+=300;
				x=25;
			}
		}

	}

}
