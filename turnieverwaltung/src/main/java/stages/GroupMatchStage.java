package stages;

import java.util.Optional;

import interfaces.IMatch;
import interfaces.IMatchStage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;
import panes.GroupPane;

public class GroupMatchStage extends MatchStage implements IMatchStage {

	public enum Status {
		closed, opened, hidden, started, stopped, finished
	}

	private GroupPane groupPane;
	private Status currentState = Status.closed;

	public GroupMatchStage(GroupPane groupPane, IMatch match) {
		super(match);
		this.groupPane=groupPane;

		this.setOnCloseRequest((WindowEvent) -> {

			if (this.currentState == Status.opened) {
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
			this.switchState(Event.timer_finished);
			this.groupPane.getTable().refresh();
		});

		timeline.setCycleCount(matchTimer);
		timeline.play();
	}

	public synchronized void beendeSpiel() {
		this.close();
		this.match.setSieger();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText("Spiel Nr." + match.getIndex() + " ist beendet.\n(" + match.getMannschaft1()
						+ " vs " + match.getMannschaft2() + ")");
				alert.setContentText("Das Spiel wurde Beendet. "
						+ (match.getUnentschieden() ? "Es endete Unentschieden, die Punkte werden verteilt."
								: ("Gewonnen hat: ") + match.getSieger()));
				alert.showAndWait();
			}
		});
	}

	public void switchState(Event e) {
		switch (currentState) {
		case closed:
			if (e == Event.click) {
				this.show();
				this.currentState = Status.opened;
			}
			break;
		case finished:
			if (e == Event.click) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Info");
				alert.setHeaderText("Das Spiel wurde schon beendet");
				alert.setContentText("Ergebnis: " + this.match.getToreM1() + ":" + this.match.getToreM2());
				alert.showAndWait();
			}
			break;
		case hidden:
			if (e == Event.click) {
				this.show();
				this.currentState = Status.opened;
			}
			break;
		case opened:
			if (e == Event.start_stop) {
				this.starteSpiel();
				b_Start_Stopp.setText("Stop");
				b_TorMannschaft1.setDisable(false);
				b_TorMannschaft2.setDisable(false);
				this.currentState = Status.started;
			} else if (e == Event.hide) {
				this.hide();
				this.currentState = Status.hidden;
			} else if (e == Event.close) {
				this.currentState = Status.closed;
				this.close();
			}
			break;
		case started:
			if (e == Event.start_stop) {
				this.stoppeSpiel();
				b_Start_Stopp.setText("Start");
				b_TorMannschaft1.setDisable(true);
				b_TorMannschaft2.setDisable(true);
				this.currentState = Status.stopped;
			} else if (e == Event.timer_finished) {
				this.beendeSpiel();
				this.close();
				this.currentState = Status.finished;
			} else if (e == Event.hide) {
				this.hide();
				this.currentState = Status.hidden;
			} else if (e == Event.close) {
				this.currentState = Status.closed;
				this.close();
			}
			break;
		case stopped:
			if (e == Event.start_stop) {
				this.starteSpiel();
				b_Start_Stopp.setText("Stop");
				b_TorMannschaft1.setDisable(false);
				b_TorMannschaft2.setDisable(false);
				this.currentState = Status.started;
			} else if (e == Event.hide) {
				this.hide();
				this.currentState = Status.hidden;
			} else if (e == Event.close) {
				this.currentState = Status.closed;
				this.close();
			}
			break;
		default:
			break;
		}
	}

	public IMatch getMatch() {
		return this.match;
	}

}
