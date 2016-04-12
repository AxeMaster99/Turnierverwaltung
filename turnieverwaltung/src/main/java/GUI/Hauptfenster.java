package GUI;

import java.util.Stack;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Hauptfenster extends Application {

	private Stage stage;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		System.out.println("ok");
		
		this.stage = stage;
		
		stage.setTitle("Rocket Ligue Turnierverwaltung");
		
		StackPane root = new StackPane();
		Scene scene = new Scene(root, 800, 600);
		
		stage.setScene(scene);
		stage.show();
		
	}	
	
}
