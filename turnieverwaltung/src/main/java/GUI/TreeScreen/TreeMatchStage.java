package GUI.TreeScreen;

import java.util.Optional;

import main.Steuerung;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import GUI.MatchStage;
import GUI.WaitForButtonThread;
import GUI.MatchStage.Event;
import backend.exception.GameNotFinishedException;
import backend.exception.GameUnentschiedenException;
import backend.interfaces.IMatch;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;

public class TreeMatchStage extends MatchStage {

	private static final Logger logger = (Logger) LogManager.getLogger("TreeMatchStage");
	
	public enum Status {
		clickable_white, unclickable_white, clickable_yellow, unclickable_yellow, clickable_orange, unclickable_orange, finished_green, hovered
	}

	private Status currentState = Status.clickable_white;
	private Steuerung steuerung; 
	
	public TreeMatchStage(Steuerung steuerung, IMatch match) {
		super(match);
		this.steuerung = steuerung;

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

		b_Start_Stopp.setOnAction((event) -> {
			this.switchState(Event.start_stop);
		});

		b_hide.setOnAction((event) -> {
			this.switchState(Event.hide);
		});
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
						alert.setContentText(
								"Spiel " + match.getIndex() + " endete Unentschieden. Das nächste Tor entscheidet.");
						alert.showAndWait();
						switchState(Event.click);
					}
				});
				new WaitForButtonThread(match, this).start();
			} else {
				new WaitForButtonThread(match, this).start();
			}
		});

		timeline.setCycleCount(matchTimer);
		timeline.play();
	}
	
	public synchronized void beendeSpiel() {
		this.close();
		try {
			this.match.setSieger();
		} catch(GameUnentschiedenException e) {
			logger.error(e.getMessage());
		}
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				try {
					alert.setHeaderText("Spiel Nr." + match.getIndex() + " ist beendet.\n(" + match.getSieger() + " vs "
							+ match.getVerlierer() + ")");
					alert.setContentText("Das Spiel wurde Beendet. Gewonnen hat: " + match.getSieger());

				} catch (GameNotFinishedException e) {
					logger.error(e.getMessage());
				}
				alert.showAndWait();
			}
		});
		this.steuerung.updateSpielBaum();
		this.steuerung.getTRangStage().updateTable();
		this.match.getMatchPane().setDisable(false);
	}

	public void switchState(Event e) {
		switch (currentState) {
		case clickable_orange:
			if (e == Event.click) {
				this.match.getMatchPane().setDisable(true);
				currentState = Status.unclickable_orange;
			}
			break;
		case clickable_white:
			if (e == Event.click) {
				this.match.getMatchPane().setDisable(true);
				currentState = Status.unclickable_white;
			}
			break;
		case clickable_yellow:
			if (e == Event.click) {
				this.show();
				this.match.getMatchPane().setDisable(true);
				currentState = Status.unclickable_yellow;
			} else if (e == Event.timer_finished) {
				this.match.getMatchPane().getGrid()
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
				this.match.getMatchPane().getGrid()
						.setStyle("-fx-background-color: yellow;"
								+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
								+ "-fx-border-color:black;" + "-fx-border-radius:5;");
				b_TorMannschaft1.setDisable(false);
				b_TorMannschaft2.setDisable(false);
				b_Start_Stopp.setText("Stop");
				this.currentState = Status.unclickable_yellow;
			} else if (e == Event.close) {
				this.match.getMatchPane().setDisable(false);
				this.currentState = Status.clickable_orange;
			} else if (e == Event.hide) {
				this.hide();
				this.match.getMatchPane().setDisable(false);
				this.currentState = Status.clickable_orange;
			}
			break;
		case unclickable_white:
			if (e == Event.close) {
				this.close();
				this.match.getMatchPane().setDisable(false);
				this.currentState = Status.clickable_white;
			} else if (e == Event.start_stop) {
				this.starteSpiel();
				this.match.getMatchPane().getGrid()
						.setStyle("-fx-background-color: yellow;"
								+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
								+ "-fx-border-color:black;" + "-fx-border-radius:5;");
				b_TorMannschaft1.setDisable(false);
				b_TorMannschaft2.setDisable(false);
				b_Start_Stopp.setText("Stop");
				this.currentState = Status.unclickable_yellow;
			} else if (e == Event.hide) {
				this.hide();
				this.match.getMatchPane().setDisable(false);
				this.currentState = Status.clickable_white;
			}
			break;
		case unclickable_yellow:
			if (e == Event.close) {
				this.match.getMatchPane().getGrid()
						.setStyle("-fx-background-color: orange;"
								+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
								+ "-fx-border-color:black;" + "-fx-border-radius:5;");
				b_Start_Stopp.setText("Start");
				this.match.getMatchPane().setDisable(false);
				b_TorMannschaft1.setDisable(true);
				b_TorMannschaft2.setDisable(true);
				this.currentState = Status.clickable_orange;
			} else if (e == Event.start_stop) {
				this.match.getMatchPane().getGrid()
						.setStyle("-fx-background-color: orange;"
								+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
								+ "-fx-border-color:black;" + "-fx-border-radius:5;");
				this.stoppeSpiel();
				b_Start_Stopp.setText("Start");
				b_TorMannschaft1.setDisable(true);
				b_TorMannschaft2.setDisable(true);
				this.currentState = Status.unclickable_orange;
			} else if (e == Event.timer_finished) {
				this.match.getMatchPane().getGrid()
						.setStyle("-fx-background-color: rgba(127,255,0,1);"
								+ "-fx-background-radius: 5;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
								+ "-fx-border-color:black;" + "-fx-border-radius:5;");
				this.currentState = Status.finished_green;
			} else if (e == Event.hide) {
				this.hide();
				this.match.getMatchPane().setDisable(false);
				this.currentState = Status.clickable_yellow;
			}
			break;
		default:
			break;
		}
	}
}
