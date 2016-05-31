package interfaces;

import backend.Mannschaft;
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
	
	public void setSieger();
	
	public Mannschaft getSieger();
	
	public Mannschaft getVerlierer();
	
	public void setToreM1(int toreM1);

	public void setToreM2(int toreM2);
	
	public boolean isGameFinished();
	
	public void setMannschaft1(Mannschaft m1);
	
	public void setMannschaft2(Mannschaft m2);

	public Steuerung getSteuerung();

	public boolean getUnentschieden();
}
