package org.miprojekt.turnieverwaltung.gui.windows;
import org.miprojekt.turnieverwaltung.Main;
import org.miprojekt.turnieverwaltung.gui.SceneParent;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class Splashscreen extends SceneParent {

	public Splashscreen(Main main) {
		
		super(main);

		Button b = new Button("test");
		this.getChildren().add(b);

		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				
				main.getStage().setScene(main.getScreen("settings"));
				
			}
		});

	}
	
}
