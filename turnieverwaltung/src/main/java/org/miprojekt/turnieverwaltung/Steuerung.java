package org.miprojekt.turnieverwaltung;

public class Steuerung {

	int anzahl_teams ;
	backend.Mannschaft[] manschaften ;
	
	
	public Steuerung (){
		
		
	}
	
	void set_anzahl_teams(int teams){
		this.anzahl_teams = teams;
		manschaften = new backend.Mannschaft[teams];
	}
	
	int get_anzahl_teams(){
		return this.anzahl_teams;
	}
	
	void create_tunier(){
		
	}
	
	void spiel_starten(){
		
	}
	
	void set_tor(){
		
	}
	
	void set_team_name(String team_name, int team_id){
		manschaften[team_id].set_name(team_name);	
	}
	
	
	
}
