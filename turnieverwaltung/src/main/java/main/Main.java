package main;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import backend.turnier.Steuerung;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static final Logger logger = (Logger) LogManager.getLogger("Main");
	
	private Stage stage;
	private Steuerung steuerung;
	private HashMap<String, Scene> scenes = new HashMap<String, Scene>();
	
	public static void main(String args[]) {
		logger.info("Programm start");
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		this.steuerung = new Steuerung(this);
		this.stage = stage;	
		this.stage.setOnCloseRequest(e -> Platform.exit());
		stage.setTitle("Turnierverwaltung");
		steuerung.setSplashScreen();
		stage.show();
	}
	/**
	 * 
	 * @return
	 * the stage
	 */
	public Stage getStage() {
		return this.stage;
	}
	
	/**
	 * Depending on the key, the HashMap returns the scene.
	 * @param key 
	 *  the key of type "String"
	 * @return the scene 
	 */
	public Scene getScene(String key) {
		return this.scenes.get(key);
	}

	public HashMap<String,Scene> getScenes() {
		return scenes;
	}
	
	public Steuerung getSteuerung() {
		return this.steuerung;
	}
	
	
	
	// exit on close für gui 
	public void close() {
		logger.info("Programm close");
		this.close();
		Platform.setImplicitExit(false);
		stage.close();
	}
	
}
