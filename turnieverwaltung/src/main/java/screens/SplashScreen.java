package screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import verwaltung.Steuerung;

public class SplashScreen extends Pane {

	private Timeline timer;
	private static final Logger logger = (Logger) LogManager.getLogger("SplashScreen");
	
	public SplashScreen(Steuerung steuerung) {
				
		logger.warn("SplashScreen started");
		
		GridPane grid = new GridPane();
		
		Image bgImage = new Image("images/splashscreen.jpg");
		ImageView bgImageView = new ImageView();
		bgImageView.setImage(bgImage);
		
		bgImageView.fitHeightProperty().bind(steuerung.getMain().getStage().heightProperty());
		bgImageView.fitWidthProperty().bind(steuerung.getMain().getStage().widthProperty());
		grid.add(bgImageView, 0, 0);
		
		this.getChildren().add(grid);
		
		timer = new Timeline(new KeyFrame(Duration.seconds(2.1), new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	steuerung.setSettingsScreen();
		    	timer.stop();
		    }
		}));
		timer.setCycleCount(Timeline.INDEFINITE);
		timer.play();
		
		/*
		Button b = new Button("test");
		this.getChildren().add(b);
		
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				
				main.getStage().setScene(main.getScreen("settings"));
				
			}
		});
		*/

	}
	
}
