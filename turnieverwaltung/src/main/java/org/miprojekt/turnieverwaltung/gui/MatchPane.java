package org.miprojekt.turnieverwaltung.gui;

import org.miprojekt.turnieverwaltung.Steuerung;
import org.miprojekt.turnieverwaltung.gui.MatchStage.Status;

import backend.FolgeMatch;
import backend.Match;
import interfaces.IMatch;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class MatchPane extends Pane {

	private IMatch match;
	private GridPane grid = new GridPane();
	private String currentStyle;

	private Label l1_mannschaft;
	private Label l2_mannschaft;

	private Label l3_toreM1;
	private Label l4_toreM2;

	private MatchStage matchStage;

	public MatchPane(IMatch match) {
		super();
		this.match = match;
		this.matchStage = new MatchStage(this.match, this);
		this.matchStage.hide();

		grid.setMinSize(110, 40);
		grid.setStyle("-fx-background-color: white;");

		grid.setOnMouseEntered((MouseEvent e) -> {
			currentStyle = grid.getStyle();
			grid.setStyle("-fx-background-color: CCCCCC;");
		});

		grid.setOnMouseExited((MouseEvent e) -> {
			grid.setStyle(currentStyle);
		});

		grid.setOnMouseReleased((event) -> {
			// Überprüfen, ob die davorigen Spiele bereits beendet
			if (this.match instanceof FolgeMatch && (!((FolgeMatch) match).getPrevMatch1().isGameFinished()
					|| !((FolgeMatch) match).getPrevMatch2().isGameFinished())) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Info");
				alert.setHeaderText("Das Spiel kann nicht gestartet werden");
				alert.setContentText("Die voherigen Spiele müssen erst beendet werden.");
				alert.showAndWait();
			} else {
				this.matchStage.show();
				System.out.println(
						this.match.getMannschaft1().getName() + " gegen " + this.match.getMannschaft2().getName());
				System.out.println(this.getTranslateX());
				System.out.println(this.getTranslateY());
				this.setDisable(true);
			}
		});

		l1_mannschaft = new Label(this.match.getMannschaft1().getName());
		l2_mannschaft = new Label(this.match.getMannschaft2().getName());
		l3_toreM1 = new Label("");
		l4_toreM2 = new Label("");
		Font font = new Font(14);
		l3_toreM1.setFont(font);
		l4_toreM2.setFont(font);

		grid.add(l1_mannschaft, 0, 0);
		grid.add(l2_mannschaft, 0, 1);

		grid.add(l3_toreM1, 1, 0);
		grid.add(l4_toreM2, 1, 1);

		grid.setHgap(15);
		this.getChildren().add(grid);
	}

	public void setLabelErgebnis(int toreM1, int toreM2) {
		l3_toreM1.setText(Integer.toString(toreM1));
		l4_toreM2.setText(Integer.toString(toreM2));
		grid.setStyle("-fx-background-color: green;");
	}

	public MatchStage getMatchStage() {
		return this.matchStage;
	}

	public void updateMannschaftsLabelM1() {
		l1_mannschaft.setText(this.match.getMannschaft1().getName());
		matchStage.setLabelM1(this.match.getMannschaft1().getName());
	}

	public void updateMannschaftsLabelM2() {
		l2_mannschaft.setText(this.match.getMannschaft2().getName());
		matchStage.setLabelM2(this.match.getMannschaft2().getName());
	}

	public void statusFarbeAendern(Status state) {
		switch (state) {
		case clickable:
			grid.setStyle("-fx-background-color: white;");
			break;
		case closed:
			grid.setStyle("-fx-background-color: orange;");
			break;
		case running:
			grid.setStyle("-fx-background-color: yellow;");
			break;
		default:
			break;
		}
	}

	public String getCurrentStyle() {
		return currentStyle;

	}

}
