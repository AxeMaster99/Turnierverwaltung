package stages;

import java.util.Optional;

import com.sun.javafx.stage.StageHelper;

import interfaces.IMatch;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import panes.MatchPane;
import stages.MatchStage.Event;
import stages.MatchStage.Status;
import threads.GUIUpdateThread;
//import screens.SettingsScreen;

public class MatchStage extends Stage {

	public enum Status {
		clickable_white, unclickable_white, clickable_yellow, unclickable_yellow, clickable_orange, unclickable_orange, finished_green, hovered
	}

	public enum Event {
		start_stop, hide, close, click, timer_finished, hover
	}

	private Status currentState = Status.clickable_white;

	private MatchPane matchPane;
	private IMatch match;
	Thread t1;

	private Pane root = new Pane();
	private GridPane grid = new GridPane();

	private Timeline timeline;
	private int matchTimer = -1; // Every Game gets his individual timer,
									// initial value -1 to check if timer has
									// had runtime before on resuming a paused
									// Match
	// private final double SPIELMINUTEN = 2; TODO: Spielminuten enablen
	private static int timerdauer = 5; // bisher aus Testgründen noch sekunden,
										// kann bei "Release" auf Minuten
										// gesetzt werden

	private Label l_Spielstand = new Label();
	private Label l_Mannschaft1 = new Label();
	private Label l_Mannschaft2 = new Label();

	private Label l_timer = new Label("Verbleibende Zeit: ");
	private Label l_timerdauer = new Label(String.format("%02d:%02d", timerdauer / 60, timerdauer % 60));

	private Button b_TorMannschaft1 = new Button("Tor M1");
	private Button b_TorMannschaft2 = new Button("Tor M2");
	private Button b_Start_Stopp = new Button("Start");
	private Button b_hide = new Button("Hide");

	public MatchStage(IMatch match, MatchPane matchPane) {
		super();
		this.match = match;
		this.matchPane = matchPane;

		this.setOnCloseRequest((WindowEvent) -> {

			if (this.currentState == Status.unclickable_white || this.currentState == Status.unclickable_orange) {
				this.switchState(Event.close);
			} else {
				this.stoppeSpiel();
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Warnung");
				alert.setHeaderText("Wirklich schließen?");
				alert.setContentText(
						"Wollen sie das Fenster wirklich schließen? Der Timer wird dann gestoppt und das Spiel muss später fortgesetzt werden.");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					this.switchState(Event.close);
					this.close();
				} else {
					WindowEvent.consume();
					alert.close();
					this.starteSpiel();
				}
			}
		});

		grid.setMinWidth(350);
		grid.setMinHeight(200);
		grid.setAlignment(Pos.CENTER);

		l_Mannschaft1.setText(this.match.getMannschaft1().getName());
		l_Mannschaft2.setText(this.match.getMannschaft2().getName());

		b_TorMannschaft1.setDisable(true);
		b_TorMannschaft2.setDisable(true);

		Font font = new Font(25);

		l_timerdauer.setFont(font);

		l_Spielstand.setFont(font);
		l_Spielstand.setText(this.match.getToreM1() + ":" + this.match.getToreM2());

		ColumnConstraints col1 = new ColumnConstraints(100);
		ColumnConstraints col2 = new ColumnConstraints(100);
		ColumnConstraints col3 = new ColumnConstraints(100);
		grid.setHgap(10);
		grid.setVgap(10);

		grid.getColumnConstraints().addAll(col1, col2, col3);

		grid.add(l_Mannschaft1, 0, 0);
		GridPane.setHalignment(l_Mannschaft1, HPos.RIGHT);

		grid.add(l_Spielstand, 1, 0);
		GridPane.setHalignment(l_Spielstand, HPos.CENTER);

		grid.add(l_Mannschaft2, 2, 0);
		GridPane.setHalignment(l_Mannschaft2, HPos.LEFT);

		grid.add(b_TorMannschaft1, 0, 1);
		GridPane.setHalignment(b_TorMannschaft1, HPos.RIGHT);

		grid.add(b_Start_Stopp, 1, 1);
		GridPane.setHalignment(b_Start_Stopp, HPos.CENTER);

		grid.add(b_TorMannschaft2, 2, 1);
		GridPane.setHalignment(b_TorMannschaft2, HPos.LEFT);

		grid.add(l_timer, 0, 2);
		GridPane.setHalignment(l_timer, HPos.LEFT);

		grid.add(l_timerdauer, 1, 2);
		GridPane.setHalignment(l_timerdauer, HPos.CENTER);

		grid.add(b_hide, 2, 2);
		GridPane.setHalignment(b_hide, HPos.LEFT);

		// grid.setGridLinesVisible(true);
		root.getChildren().add(grid);

		Scene scene = new Scene(root, 350, 200);

		this.setTitle(this.match.getMannschaft1().getName() + " vs " + this.match.getMannschaft2().getName());
		this.setScene(scene);
		this.show();

		b_TorMannschaft1.setOnAction((event) -> {
			this.match.incrementToreM1();
			l_Spielstand.setText(this.match.getToreM1() + ":" + this.match.getToreM2());
		});

		b_TorMannschaft2.setOnAction((event) -> {
			this.match.incrementToreM2();
			l_Spielstand.setText(this.match.getToreM1() + ":" + this.match.getToreM2());
		});

		b_Start_Stopp.setOnAction((event) -> {
			this.switchState(Event.start_stop);
		});

		b_hide.setOnAction((event) -> {
			this.switchState(Event.hide);
		});

	}

	private void stoppeSpiel() {
		timeline.stop();
	}

	private void starteSpiel() {

		if (matchTimer == -1) {
			matchTimer = timerdauer;
		}
		this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			matchTimer--;
			l_timerdauer.setText(String.format("%02d:%02d", matchTimer / 60, matchTimer % 60));
		}));

		timeline.setOnFinished((e) -> {
			if (this.match.getToreM1() == this.match.getToreM2()) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information");
						alert.setHeaderText("Unentschieden");
						alert.setContentText("Spiel " + match.getIndex() + " endete Unentschieden. Das nächste Tor entscheidet.");
						alert.showAndWait();
						switchState(Event.click);
					}
				});
				new GUIUpdateThread(match, this).start();
			} else {
				new GUIUpdateThread(match, this).start();
			}
		});

		timeline.setCycleCount(matchTimer);
		timeline.play();
	}

	public void beendeSpiel() {
		this.close();
		this.match.setSieger();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText("Spiel Nr." + match.getIndex() + " ist beendet.\n(" + match.getSieger() + " vs "
						+ match.getVerlierer() + ")");
				alert.setContentText("Das Spiel wurde Beendet. Gewonnen hat: " + match.getSieger());
				alert.showAndWait();
			}
		});

		// update rangStage
		this.matchPane.getSteuerung().getRangStage().updateTable();

		this.match.getMatchPane().setDisable(false);
		this.matchPane.getSteuerung().updateSpielBaum();
	}

	public void setLabelM1(String label) {
		l_Mannschaft1.setText(label);
		if (!l_Mannschaft2.getText().equals("...")) {
			this.setTitle(l_Mannschaft1.getText() + " vs " + l_Mannschaft2.getText());
		}
	}

	public void setLabelM2(String label) {
		l_Mannschaft2.setText(label);
		if (!l_Mannschaft1.getText().equals("...")) {
			this.setTitle(l_Mannschaft1.getText() + " vs " + l_Mannschaft2.getText());
		}
	}

	public static void setTimerdauer(int duration) {
		timerdauer = duration;
	}

	public void switchState(Event e) {
		switch (currentState) {
		case clickable_orange:
			if (e == Event.click) {
				this.matchPane.setDisable(true);
				currentState = Status.unclickable_orange;
			}
			break;
		case clickable_white:
			if (e == Event.click) {
				this.matchPane.setDisable(true);
				currentState = Status.unclickable_white;
			}
			break;
		case clickable_yellow:
			if (e == Event.click) {
				this.matchPane.setDisable(true);
				currentState = Status.unclickable_yellow;
			} else if (e == Event.timer_finished) {
				this.matchPane.getGrid()
						.setStyle("-fx-background-color: rgba(127,255,0,1);"
								+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
								+ "-fx-border-color:black;" + "-fx-border-radius:5;");
				this.currentState = Status.finished_green;
			}
			break;
		case finished_green:
			break;
		case unclickable_orange:
			if (e == Event.start_stop) {
				this.starteSpiel();
				this.matchPane.getGrid()
						.setStyle("-fx-background-color: yellow;"
								+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
								+ "-fx-border-color:black;" + "-fx-border-radius:5;");
				b_TorMannschaft1.setDisable(false);
				b_TorMannschaft2.setDisable(false);
				b_Start_Stopp.setText("Stop");
				this.currentState = Status.unclickable_yellow;
			} else if (e == Event.close) {
				this.matchPane.setDisable(false);
				this.currentState = Status.clickable_orange;
			} else if (e == Event.hide) {
				this.hide();
				this.matchPane.setDisable(false);
				this.currentState = Status.clickable_orange;
			}
			break;
		case unclickable_white:
			if (e == Event.close) {
				this.close();
				this.matchPane.setDisable(false);
				this.currentState = Status.clickable_white;
			} else if (e == Event.start_stop) {
				this.starteSpiel();
				this.matchPane.getGrid()
						.setStyle("-fx-background-color: yellow;"
								+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
								+ "-fx-border-color:black;" + "-fx-border-radius:5;");
				b_TorMannschaft1.setDisable(false);
				b_TorMannschaft2.setDisable(false);
				b_Start_Stopp.setText("Stop");
				this.currentState = Status.unclickable_yellow;
			} else if (e == Event.hide) {
				this.hide();
				this.matchPane.setDisable(false);
				this.currentState = Status.clickable_white;
			}
			break;
		case unclickable_yellow:
			if (e == Event.close) {
				this.matchPane.getGrid()
						.setStyle("-fx-background-color: orange;"
								+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
								+ "-fx-border-color:black;" + "-fx-border-radius:5;");
				b_Start_Stopp.setText("Start");
				this.matchPane.setDisable(false);
				b_TorMannschaft1.setDisable(true);
				b_TorMannschaft2.setDisable(true);
				this.currentState = Status.clickable_orange;
			} else if (e == Event.start_stop) {
				this.matchPane.getGrid()
						.setStyle("-fx-background-color: orange;"
								+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
								+ "-fx-border-color:black;" + "-fx-border-radius:5;");
				this.stoppeSpiel();
				b_Start_Stopp.setText("Start");
				b_TorMannschaft1.setDisable(true);
				b_TorMannschaft2.setDisable(true);
				this.currentState = Status.unclickable_orange;
			} else if (e == Event.timer_finished) {
				this.matchPane.getGrid()
						.setStyle("-fx-background-color: rgba(127,255,0,1);"
								+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
								+ "-fx-border-color:black;" + "-fx-border-radius:5;");
				this.currentState = Status.finished_green;
			} else if (e == Event.hide) {
				this.hide();
				this.matchPane.setDisable(false);
				this.currentState = Status.clickable_yellow;
			}
			break;
		default:
			break;
		}
	}
}
