package verwaltung;

import java.util.HashMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {
	
	private Stage stage;
	private Steuerung steuerung;
	private HashMap<String, Scene> scenes = new HashMap<String, Scene>();
	
	public static void main(String args[]) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.steuerung = new Steuerung(this);
		this.stage = stage;	
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


	
}
