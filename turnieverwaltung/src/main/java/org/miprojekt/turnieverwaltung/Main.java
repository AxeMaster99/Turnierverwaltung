package org.miprojekt.turnieverwaltung;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	public static void main(String args[]) {
		new Steuerung();
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Rocket League Turnierverwaltung");
		
		StackPane root = new StackPane();
		
		Scene scene = new Scene(root, 800, 600);
		
		stage.setScene(scene);
		stage.show();
		
	}
	
}
