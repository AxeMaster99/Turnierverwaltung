package interfaces;

import backend.Mannschaft;
import exception.GameUnentschiedenException;
import panes.MatchPane;
import stages.MatchStage;
import verwaltung.Steuerung;

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
	
	public Mannschaft getSieger() throws Exception;
	
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
