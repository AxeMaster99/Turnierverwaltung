package verwaltung;

import java.util.HashMap;

import com.sun.javafx.application.PlatformImpl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;



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
	
	public void close() {
		this.close();
		Platform.setImplicitExit(false);
		stage.close();
		
        Platform.exit();
        System.exit(0);
   		PlatformImpl.exit();

	}


	
}
