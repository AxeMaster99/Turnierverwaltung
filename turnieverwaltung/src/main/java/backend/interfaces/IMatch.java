package backend.interfaces;

import GUI.TreeScreen.MatchPane;
import backend.exception.GameNotFinishedException;
import backend.exception.GameUnentschiedenException;
import backend.turnier.Mannschaft;

public interface IMatch {

	public Mannschaft getMannschaft1();

	public Mannschaft getMannschaft2();

	public MatchPane getMatchPane();

	public void incrementToreM1();

	public void incrementToreM2();

	public int getToreM1();

	public int getToreM2();

	public int getIndex();
	
	public void setSieger() throws GameUnentschiedenException;
	
	public Mannschaft getSieger() throws GameNotFinishedException;
	
	public Mannschaft getVerlierer();
	
	public void setToreM1(int toreM1);

	public void setToreM2(int toreM2);
	
	public boolean isGameFinished();
	
	public void setMannschaft1(Mannschaft m1);
	
	public void setMannschaft2(Mannschaft m2);

	public boolean getUnentschieden();
	
	public void setState(String state);
	
	public String getState();
	
	public void setTurnierType(String turnierType);
}
