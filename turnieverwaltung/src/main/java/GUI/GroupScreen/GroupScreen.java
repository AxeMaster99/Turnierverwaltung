package GUI.GroupScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import backend.exception.GameUnentschiedenException;
import backend.interfaces.IMatch;
import backend.turnier.Group;
import backend.turnier.Mannschaft;
import backend.turnier.MatchFactory;
import backend.turnier.Steuerung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GroupScreen extends Pane {

	private GridPane grid = new GridPane();
	private ObservableList<String> teamnamen = FXCollections.observableArrayList();
	private ArrayList<Mannschaft> teams = new ArrayList<Mannschaft>();
	private ArrayList<Group> groups = new ArrayList<Group>();
	private ObservableList<IMatch> matches = FXCollections.observableArrayList();
	private ArrayList<GroupPane> groupPanes = new ArrayList<GroupPane>();

	private Font font = Font.font("Arial", FontWeight.BOLD, 16);

	private static final Logger logger = (Logger) LogManager.getLogger("GroupScreen");

	public GroupScreen(Steuerung steuerung, ObservableList<String> teamnamen) {

		this.setStyle("-fx-background-color:black;");

		// ImageView bgImageView = this.setImage();

		this.zeichneLegende(teamnamen.size());

		Image bgImage = new Image("images/boden_wiese.jpg");
		ImageView bgImageView = new ImageView();
		bgImageView.setImage(bgImage);
		bgImageView.setOpacity(0.7);
		bgImageView.fitHeightProperty().bind(steuerung.getMain().getStage().heightProperty());
		bgImageView.fitWidthProperty().bind(steuerung.getMain().getStage().widthProperty());
		this.getChildren().add(bgImageView);

		MenuBar menubar = new MenuBar();
		Menu toolMenu = new Menu("Tools");
		MenuItem ranglisteMenuItem = new MenuItem("Rangliste");
		MenuItem simulateErgebnisse = new MenuItem("Ergebnisse simulieren");

		toolMenu.getItems().add(ranglisteMenuItem);
		toolMenu.getItems().add(simulateErgebnisse);

		ranglisteMenuItem.setOnAction((WindowEvent) -> {
			steuerung.getgRangStage().updateTables();
			steuerung.getgRangStage().show();
		});

		simulateErgebnisse.setOnAction((WindowEvent) -> {

			for (int i = 0; i < matches.size(); i++) {

				Random random = new Random();
				int ergM1 = random.nextInt(6);
				int ergM2 = random.nextInt(6);

				if (matches.get(i).isGameFinished() == false) {
					matches.get(i).setToreM1(ergM1);
					matches.get(i).setToreM2(ergM2);
					matches.get(i).setState("F");
					try {
						matches.get(i).setSieger();
					} catch(GameUnentschiedenException e) {
						logger.error(e.getMessage());
					}
				}
			}

			/*
			 * for (int i = 0; i < groupPanes.size(); i++) {
			 * groupPanes.get(i).getTable().refresh();
			 * groupPanes.get(i).setDisable(); }
			 */

			groupPanes.stream().parallel().forEach(pane -> {
				pane.getTable().refresh();
				pane.setDisable();
			});

		});

		menubar.getMenus().addAll(toolMenu);
		menubar.prefWidthProperty().bind(this.widthProperty());

		grid.add(menubar, 0, 0, 5, 1);
		GridPane.setMargin(menubar, new Insets(0, 0, 20, 0));

		this.teamnamen = teamnamen;
		Collections.shuffle(this.teamnamen);
		
		/*
		 * for (int i = 0; i < this.teamnamen.size(); i++) { teams.add(new
		 * Mannschaft(this.teamnamen.get(i))); }
		 */
		teamnamen.stream().forEach(team -> teams.add(new Mannschaft(team)));

		steuerung.getgRangStage().initializeTable(teams);

		for (int i = 0; i < this.teamnamen.size(); i += 4) {
			groups.add(new Group(teams.get(i), teams.get(i + 1), teams.get(i + 2), teams.get(i + 3)));
		}

		for (int i = 0; i < this.groups.size(); i++) {
			matches.add(MatchFactory.build(steuerung, groups.get(i).getMannschaft(1), groups.get(i).getMannschaft(2)));
			matches.add(MatchFactory.build(steuerung, groups.get(i).getMannschaft(3), groups.get(i).getMannschaft(4)));
			matches.add(MatchFactory.build(steuerung, groups.get(i).getMannschaft(1), groups.get(i).getMannschaft(3)));
			matches.add(MatchFactory.build(steuerung, groups.get(i).getMannschaft(2), groups.get(i).getMannschaft(4)));
			matches.add(MatchFactory.build(steuerung, groups.get(i).getMannschaft(1), groups.get(i).getMannschaft(4)));
			matches.add(MatchFactory.build(steuerung, groups.get(i).getMannschaft(2), groups.get(i).getMannschaft(3)));
		}

		/*
		 * for (int i = 0; i < this.matches.size(); i++) {
		 * logger.info(this.matches.get(i).toString()); }
		 */
		
		matches.stream().forEach(match -> logger.info(match.toString()));

		int colBot = 0;
		for (int i = 0; i < this.groups.size(); i++) {
			GroupPane pane = new GroupPane(steuerung, this.matches, i, teams);
			GridPane.setMargin(pane, new Insets(5, 0, 10, 15));
			this.groupPanes.add(pane);
			if (i < 4) {
				Label groupLabel = new Label("Gruppe " + this.getCharForNumber(i));
				this.grid.add(groupLabel, i, 1);
				groupLabel.setFont(font);
				groupLabel.setTextFill(Color.WHITE);

				GridPane.setMargin(groupLabel, new Insets(0, 0, 0, 15));
				this.grid.add(this.groupPanes.get(i), i, 2);
			} else {
				Label groupLabel = new Label("Gruppe " + this.getCharForNumber(i));
				this.grid.add(groupLabel, colBot, 3);
				groupLabel.setFont(font);
				groupLabel.setTextFill(Color.WHITE);

				GridPane.setMargin(groupLabel, new Insets(0, 0, 0, 15));
				this.grid.add(this.groupPanes.get(i), colBot, 4);
				colBot++;
			}
		}

		Button weiterBtn = new Button("weiter");
		weiterBtn.setMinWidth(80);

		int pos;
		if (groups.size() == 4) {
			pos = 3;
		} else {
			pos = (groups.size() / 2) - 1;
		}
		grid.add(weiterBtn, (pos > 0 ? pos : ++pos), 5);
		GridPane.setHalignment(weiterBtn, HPos.RIGHT);
		GridPane.setMargin(weiterBtn, new Insets(0,0,5,0));
		// grid.setGridLinesVisible(true);
		this.getChildren().add(grid);

		weiterBtn.setOnAction((WindowEvent) -> {

			boolean alleFertig = true;
			for (int i = 0; i < this.matches.size(); i++) {
				if (!this.matches.get(i).isGameFinished()) {
					alleFertig = false;
					break;
				}
			}

			if (!alleFertig) {
				Alert missingInput = new Alert(AlertType.INFORMATION);
				missingInput.setTitle("Fehler");
				missingInput.setHeaderText("Nicht alle Matches gespielt");
				missingInput.setContentText("Für die Endrunde müssen erst alle Spiele beendet sein!");
				missingInput.showAndWait();
			} else {

				ObservableList<String> alleSieger = FXCollections.observableArrayList();

				for (int i = 0; i < groups.size(); i++) {
					logger.info("Sieger Gruppe " + (i + 1) + " " + this.groups.get(i).getGruppenSieger().get(0) + "," + this.groups.get(i).getGruppenSieger().get(1) );
					alleSieger.add(this.groups.get(i).getGruppenSieger().get(0));
					alleSieger.add(this.groups.get(i).getGruppenSieger().get(1));
				}

				
					for(int i = 0; i < matches.size(); i++) {
						this.matches.get(i).setTurnierType("KO-Turnier");
					}
					steuerung.setTreeScreen("spielBaum", alleSieger);
			}
		});
	}

	private void zeichneLegende(int numberOfTeams) {
		Label l_Legende = new Label("Legende: 'NYO' = noch nicht geöffnet, "
				+ "'O' = offen, "
				+ "'R' = laufend, "
				+ "'S' = gestoppt, "
				+ "'H' = versteckt (laufend), "
				+ "'C' = geschlossen/unterbrochen, "
				+ "'F' = beendet");
		
		l_Legende.setFont(font);
		l_Legende.setTextFill(Color.WHITE);
		GridPane.setMargin(l_Legende, new Insets(0, 0, 0, 15));
		
		if(numberOfTeams>10){
		grid.add(l_Legende, 0, 5,4,1);
		}
		else {
			grid.add(l_Legende, 0, 6,4,1);
		}
	}

	private String getCharForNumber(int i) {
		return i > -1 && i < 26 ? String.valueOf((char) (i + 65)) : null;
	}

}
