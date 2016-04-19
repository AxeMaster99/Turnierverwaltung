package org.miprojekt.turnieverwaltung.gui.windows;

import org.miprojekt.turnieverwaltung.Main;
import org.miprojekt.turnieverwaltung.gui.SceneParent;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class Splashscreen extends SceneParent {

	private Timeline timer;
	
	public Splashscreen(Main main) {
		
		super(main);
		
		BorderPane bp = new BorderPane();
		
		Image bgImage = new Image("images/splashscreen.jpg");
		ImageView bgImageView = new ImageView();
		bgImageView.setImage(bgImage);
		
		bgImageView.fitHeightProperty().bind(main.getStage().heightProperty());
		bgImageView.fitWidthProperty().bind(main.getStage().widthProperty());
		
		bp.setCenter(bgImageView);
		
		this.getChildren().add(bp);
		
		timer = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	main.getStage().setScene(main.getScreen("settings"));
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
