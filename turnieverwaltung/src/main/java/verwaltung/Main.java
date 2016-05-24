package verwaltung;

import java.util.HashMap;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import screens.SettingsScreen;
import screens.SplashScreen;
import screens.TeamScreen;
import screens.TreeScreen;


public class Main extends Application {
	
	private Stage stage;
	private HashMap<String, Scene> scenes = new HashMap<String, Scene>();
	
	public static void main(String args[]) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;	
		stage.setTitle("Turnierverwaltung");
		this.setSplashScreen();
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

	/**
	 * sets the TeamScreen with width=500 and height=630. The screen isn't maximized and its
	 * size is set to scene.
	 * @param anzahlMannschaften
	 * 		number of teams that were chosen in the last screen.
	 * @param screenName
	 * 		the key of the screen which will be set.
	 */
	public void setTeamScreen(int anzahlMannschaften, String screenName) {
		this.scenes.put(screenName, new Scene(new TeamScreen(this,anzahlMannschaften),500,630));
		this.stage.setScene(this.getScene(screenName));
		stage.setMaximized(false);
		stage.sizeToScene();
		stage.centerOnScreen();
	}
	/**
	 * sets the TreeScreen with width and height maximized. 
	 * @param screenName 
	 * 		the key of the screen which will be set.
	 * @param teams	
	 * 		an observable list of the teams.
	 * @throws Exception
	 */
	public void setTreeScreen(String screenName, ObservableList<String> teams) throws Exception {
		this.scenes.put(screenName, new Scene(new TreeScreen(this,teams)));
		this.stage.setScene(this.getScene(screenName));
		stage.setMaximized(true);
	}
	
	/** 
	 * sets the SettingsScreen with width=500 and height=200. The stage's size is 
	 * set to the scene-size and centered to the screen.
	 */
	public void setSettingsScreen(){
		this.scenes.put("settings", new Scene(new SettingsScreen(this),500,200));
		this.stage.setScene(this.getScene("settings"));
		stage.setMaximized(false);
		stage.sizeToScene();
		stage.centerOnScreen();
	}
	/**
	 * sets the SplashScreen with Maximized width and height.
	 */
	private void setSplashScreen(){
		this.scenes.put("splashscreen", new Scene(new SplashScreen(this)));
		this.stage.setScene(this.getScene("splashscreen"));
		stage.setMaximized(true);
	}
	
}
