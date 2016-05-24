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
	
	public Stage getStage() {
		return this.stage;
	}
	
	public Scene getScreen(String key) {
		return this.scenes.get(key);
	}

	public void setTeamScreen(int anzahlMannschaften, String screenName) {
		this.scenes.put(screenName, new Scene(new TeamScreen(this,anzahlMannschaften),500,630));
		this.stage.setScene(this.getScreen(screenName));
		stage.centerOnScreen();
		stage.setMaximized(false);
	}
	
	public void setTreeScreen(String screenName, ObservableList<String> teams) throws Exception {
		this.scenes.put(screenName, new Scene(new TreeScreen(this,teams)));
		this.stage.setScene(this.getScreen(screenName));
		stage.setMaximized(true);
	}
	
	public void setSettingsScreen(){
		this.scenes.put("settings", new Scene(new SettingsScreen(this), 500, 200));
		this.stage.setScene(this.getScreen("settings"));
		stage.centerOnScreen();
		stage.setMaximized(false);
	}
	
	private void setSplashScreen(){
		this.scenes.put("splashscreen", new Scene(new SplashScreen(this), 500, 200));
		this.stage.setScene(this.getScreen("splashscreen"));
		stage.setMaximized(true);
	}
	
}
