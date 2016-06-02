package screens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import backend.Group;
import backend.Mannschaft;
import backend.MatchFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;
import panes.GroupPane;
import stages.MatchStage;
import stages.RangStage;
import verwaltung.Steuerung;
import interfaces.IMatch;

public class GroupScreen extends Pane {

	private ObservableList<String> teamnamen = FXCollections.observableArrayList();
	private ArrayList<Mannschaft> teams = new ArrayList<Mannschaft>();
	private ArrayList<Group> groups = new ArrayList<Group>();
	private ObservableList<IMatch> matches = FXCollections.observableArrayList();
	private ArrayList<GroupPane> groupPanes = new ArrayList<GroupPane>();
	
	private static final Logger logger = (Logger) LogManager.getLogger("GroupScreen");
	
	public GroupScreen(Steuerung steuerung, ObservableList<String> teamnamen) {
		
		MenuBar menubar = new MenuBar();
		Menu toolMenu = new Menu("Tools");
		MenuItem ranglisteMenuItem = new MenuItem("Rangliste");
		MenuItem simulateErgebnisse = new MenuItem("Ergebnisse simulieren");
		
		toolMenu.getItems().add(ranglisteMenuItem);
		toolMenu.getItems().add(simulateErgebnisse);
		
		ranglisteMenuItem.setOnAction((WindowEvent) -> {	
			//new RangStage(steuerung);
		});
	
		simulateErgebnisse.setOnAction((WindowEvent) -> {
			
			for(int i = 0; i < matches.size(); i++) {
				
				Random random = new Random();
				int ergM1 = random.nextInt(6);
				int ergM2 = random.nextInt(6);
				
				matches.get(i).setToreM1(ergM1);
				matches.get(i).setToreM2(ergM2);
			
				matches.get(i).setSieger();	
			}
			
			for(int i = 0; i < groupPanes.size(); i++) {
				groupPanes.get(i).getTable().refresh();
				groupPanes.get(i).setDisable();
			}
			
		});
		
		menubar.getMenus().addAll(toolMenu);
		menubar.prefWidthProperty().bind(this.widthProperty());
		this.getChildren().add(menubar);
		
		this.teamnamen = teamnamen;
		Collections.shuffle(this.teamnamen);
		
		for (int i = 0; i < this.teamnamen.size(); i++) {
			teams.add(new Mannschaft(this.teamnamen.get(i)));
		}

		for (int i = 0; i < this.teamnamen.size(); i += 4) {
			groups.add(new Group(teams.get(i), teams.get(i + 1), teams.get(i + 2), teams.get(i + 3)));
		}

		for (int i = 0; i < this.groups.size(); i++) {
			matches.add(
					MatchFactory.build(steuerung, groups.get(i).getMannschaft(1), groups.get(i).getMannschaft(2)));
			matches.add(
					MatchFactory.build(steuerung, groups.get(i).getMannschaft(3), groups.get(i).getMannschaft(4)));
			matches.add(
					MatchFactory.build(steuerung, groups.get(i).getMannschaft(1), groups.get(i).getMannschaft(3)));
			matches.add(
					MatchFactory.build(steuerung, groups.get(i).getMannschaft(2), groups.get(i).getMannschaft(4)));
			matches.add(
					MatchFactory.build(steuerung, groups.get(i).getMannschaft(1), groups.get(i).getMannschaft(4)));
			matches.add(
					MatchFactory.build(steuerung, groups.get(i).getMannschaft(2), groups.get(i).getMannschaft(3)));
		}

		for (int i = 0; i < this.matches.size(); i++) {
			logger.info(this.matches.get(i).toString());
		}

		int x = 25;
		int y = 50;
		for (int i = 0; i < this.groups.size(); i++) {
			GroupPane pane = new GroupPane(steuerung, this.matches, i, teams);
			this.groupPanes.add(pane);
			pane.setTranslateX(x);
			pane.setTranslateY(y);
			this.getChildren().add(pane);
			x+=310;
			if(x>1000){
				y+=300;
				x=25;
			}
		}
	
		Button weiterBtn = new Button("weiter");
		weiterBtn.setMinWidth(80);
		weiterBtn.setTranslateX(this.groupPanes.get(this.groupPanes.size()-1).getTranslateX() + 
				this.groupPanes.get(this.groupPanes.size()-1).getTable().getMaxWidth() - weiterBtn.getMinWidth());
		weiterBtn.setTranslateY(this.groupPanes.get(this.groupPanes.size()-1).getTranslateY() + 
				this.groupPanes.get(this.groupPanes.size()-1).getTable().getMaxHeight() + 25);
		this.getChildren().add(weiterBtn);
		
		
		weiterBtn.setOnAction((WindowEvent) -> {	
			
			boolean alleFertig = true;
			for(int i = 0; i < this.matches.size(); i++) {
				if(!this.matches.get(i).isGameFinished()) {
					alleFertig = false;
					break;
				}
			}
			
			if(!alleFertig) {
				Alert missingInput = new Alert(AlertType.INFORMATION);
				missingInput.setTitle("Fehler");
				missingInput.setHeaderText("Nicht alle Matches gespielt");
				missingInput.setContentText("Für die Endrunde müssen erst alle Spiele beendet sein!");
				missingInput.showAndWait();
			} else {
				
				ObservableList<String> alleSieger = FXCollections.observableArrayList();
				
				for(int i = 0; i < groups.size(); i++) {
					logger.info("Sieger Gruppe "+(i+1));
					logger.info(this.groups.get(i).getGruppenSieger().get(0));
					logger.info(this.groups.get(i).getGruppenSieger().get(1));
					alleSieger.add(this.groups.get(i).getGruppenSieger().get(0));
					alleSieger.add(this.groups.get(i).getGruppenSieger().get(1));
				}
				
				try {
					steuerung.setTreeScreen("spielBaum", alleSieger);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	

	}

}
