package panes;

import backend.FolgeMatch;
import interfaces.IMatch;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import screens.TreeScreen;
import stages.MatchStage;
import stages.MatchStage.Status;
import javafx.scene.input.MouseEvent;

public class MatchPane extends SceneParent {

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

		grid.setMinSize(110, 40);
		grid.setStyle("-fx-background-color: white;"
				+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
				+ "-fx-border-color:black;" + "-fx-border-radius:5;");

		grid.setOnMouseEntered((MouseEvent e) -> {
			currentStyle = grid.getStyle();
			if (this.match.isGameFinished()) {
				grid.setStyle("-fx-background-color: rgba(0,170,0,1);"
						+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
						+ "-fx-border-color:black;" + "-fx-border-radius:5;");
			} else {
				grid.setStyle("-fx-background-color: CCCCCC;"
						+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
						+ "-fx-border-color:black;" + "-fx-border-radius:5;");
			}
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
			} else if (this.match.isGameFinished()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Info");
				alert.setHeaderText("Das Spiel wurde schon beendet");
				alert.setContentText("Ergebnis: " + this.match.getToreM1() + ":" + this.match.getToreM2());
				alert.showAndWait();
			} else {
				this.matchStage = new MatchStage(this.match, this);
				this.matchStage.setSpielbaum((TreeScreen) this.getParent());

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
		grid.setStyle("-fx-background-color: rgba(127,255,0,1);"
				+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
				+ "-fx-border-color:black;" + "-fx-border-radius:5;");
	}

	public MatchStage getMatchStage() {
		return this.matchStage;
	}

	public void updateMannschaftsLabelM1() {
		l1_mannschaft.setText(this.match.getMannschaft1().getName());
		// matchStage.setLabelM1(this.match.getMannschaft1().getName());
	}

	public void updateMannschaftsLabelM2() {
		l2_mannschaft.setText(this.match.getMannschaft2().getName());
		// matchStage.setLabelM2(this.match.getMannschaft2().getName());
	}

	public void statusFarbeAendern(Status state) {
		switch (state) {
		case clickable:
			grid.setStyle("-fx-background-color: white;"
					+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
					+ "-fx-border-color:black;" + "-fx-border-radius:5;");
			break;
		case closed:
			grid.setStyle("-fx-background-color: orange;"
					+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
					+ "-fx-border-color:black;" + "-fx-border-radius:5;");
			break;
		case running:
			grid.setStyle("-fx-background-color: yellow;"
					+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
					+ "-fx-border-color:black;" + "-fx-border-radius:5;");
			break;
		default:
			break;
		}
	}

	public String getCurrentStyle() {
		return currentStyle;

	}

}
