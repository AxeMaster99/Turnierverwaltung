package panes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import backend.FolgeMatch;
import interfaces.IMatch;
import interfaces.IMatchStage;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import stages.MatchStage;
import stages.MatchStage.Event;
import stages.TreeMatchStage;
import verwaltung.Steuerung;
import javafx.scene.input.MouseEvent;

public class MatchPane extends Pane {

	private IMatch match;
	private GridPane grid = new GridPane();
	private String savedStyle;

	private Label l0_matchnummer;
	
	private Label l1_mannschaft;
	private Label l2_mannschaft;

	private Label l3_toreM1;
	private Label l4_toreM2;
	private final double minHeight=60;
	
	private IMatchStage treeMatchStage;
	private Steuerung steuerung;

	private static final Logger logger = (Logger) LogManager.getLogger("MatchPane");
	
	public MatchPane(Steuerung steuerung, IMatch match) {
		this.steuerung = steuerung;
		this.match = match;
		this.setMinHeight(minHeight);
		
		
		grid.setMinSize(110, minHeight);
		grid.setStyle("-fx-background-color: white;"
				+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
				+ "-fx-border-color:black;" + "-fx-border-radius:5;");

		this.gridInitMouseEnteredListener();
		this.gridInitMouseExitedListener();
		this.gridInitMouseClickListener(match);	
		this.gridInitComponents();
	}

	private void gridInitComponents() {
		Font font = new Font(14);
		l0_matchnummer = new Label("Match ID " + Integer.toString(this.match.getIndex()));
		l0_matchnummer.setFont(font);
		l1_mannschaft = new Label(this.match.getMannschaft1().getName());
		l2_mannschaft = new Label(this.match.getMannschaft2().getName());
		l3_toreM1 = new Label("");
		l4_toreM2 = new Label("");
		l3_toreM1.setFont(font);
		l4_toreM2.setFont(font);

		grid.add(l0_matchnummer, 0, 0);
		grid.add(l1_mannschaft, 0, 1);
		grid.add(l2_mannschaft, 0, 2);

		grid.add(l3_toreM1, 1, 1);
		grid.add(l4_toreM2, 1, 2);

		grid.setHgap(15);
		this.getChildren().add(grid);		
	}

	private void gridInitMouseClickListener(IMatch match) {
		grid.setOnMouseReleased((event) -> {	
			// Überprüfen, ob die davorigen Spiele bereits beendet
			if (this.match instanceof FolgeMatch && (!((FolgeMatch) match).getPrevMatch1().isGameFinished()
					|| !((FolgeMatch) match).getPrevMatch2().isGameFinished())) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Info");
				alert.setHeaderText("Das Spiel kann nicht gestartet werden");
				alert.setContentText("Die voherigen Spiele müssen erst beendet werden.");
				alert.showAndWait();
			} else if (this.match.isGameFinished()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Info");
				alert.setHeaderText("Das Spiel wurde schon beendet");
				alert.setContentText("Ergebnis: " + this.match.getToreM1() + ":" + this.match.getToreM2());
				alert.showAndWait();
			} else {
				if (this.treeMatchStage == null) {
					this.treeMatchStage = new TreeMatchStage(this.steuerung, this.match);
				}
				this.treeMatchStage.show();
				
				//wozu ?
				logger.info(this.match.getMannschaft1().getName() + " gegen " + this.match.getMannschaft2().getName());
				logger.info(this.getTranslateX());
				logger.info(this.getTranslateY());
				
				
				
				this.treeMatchStage.switchState(Event.click);
			}
		});
	}

	private void gridInitMouseExitedListener() {
		grid.setOnMouseExited((MouseEvent e) -> {
			grid.setStyle(savedStyle);
		});
	}

	private void gridInitMouseEnteredListener() {
		grid.setOnMouseEntered((MouseEvent e) -> {
			this.savedStyle=grid.getStyle();
			grid.setStyle("-fx-background-color: #CCCCCC;"
					+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
					+ "-fx-border-color:black;" + "-fx-border-radius:5;");
		});
	}

	public void setLabelErgebnis(int toreM1, int toreM2) {
		l3_toreM1.setText(Integer.toString(toreM1));
		l4_toreM2.setText(Integer.toString(toreM2));
		this.treeMatchStage.switchState(Event.timer_finished);
	}

	public IMatchStage getMatchStage() {
		return this.treeMatchStage;
	}

	public void updateMannschaftsLabelM1() {
		l1_mannschaft.setText(this.match.getMannschaft1().getName());
	}

	public void updateMannschaftsLabelM2() {
		l2_mannschaft.setText(this.match.getMannschaft2().getName());
	}

	public GridPane getGrid() {
		return this.grid;
	}

}
