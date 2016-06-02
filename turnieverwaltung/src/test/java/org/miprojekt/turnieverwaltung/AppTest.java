package org.miprojekt.turnieverwaltung;

import org.junit.Assert;
import org.junit.Test;

import backend.Group;
import backend.Mannschaft;
import backend.Match;
import backend.MatchFactory;
import interfaces.IMatch;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import verwaltung.Main;
import verwaltung.Steuerung;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testApp() {
        Assert.assertTrue( true );
    }
    
    
    @Test
    public void testeGruppenSieger() {

    	Steuerung s = new Steuerung();
    	s.setTurnierType("KO-wadawdaawd");
    	
    	Mannschaft m1 = new Mannschaft("Mannschaft 1");
    	Mannschaft m2 = new Mannschaft("Mannschaft 2");
    	Mannschaft m3 = new Mannschaft("Mannschaft 3");
    	Mannschaft m4 = new Mannschaft("Mannschaft 4");
    	
    	Group gruppe = new Group(m1, m2, m3, m4);
    	
    	IMatch matches[] = new Match[6];
    	
    	matches[0] = MatchFactory.build(s, m1, m2);
    	matches[1] = MatchFactory.build(s, m2, m3);
    	matches[2] = MatchFactory.build(s, m3, m4);
    	matches[3] = MatchFactory.build(s, m1, m3);
    	matches[4] = MatchFactory.build(s, m2, m4);
    	matches[5] = MatchFactory.build(s, m1, m4);

    	matches[0].setToreM1(2);
       	matches[0].setToreM2(2);
    	
    	matches[1].setToreM1(0);
    	matches[1].setToreM2(10);
    	
    	matches[2].setToreM1(7);
    	matches[2].setToreM2(2);
    	
    	matches[3].setToreM1(1);
    	matches[3].setToreM2(4);

    	matches[4].setToreM1(1);
    	matches[4].setToreM2(2);
    	
    	matches[5].setToreM1(0);
    	matches[5].setToreM2(2);

    	matches[0].setSieger();
    	matches[1].setSieger();
    	matches[2].setSieger();
    	matches[3].setSieger();
    	matches[4].setSieger(); 
    	matches[5].setSieger();

    	Assert.assertEquals(m3.getName(), gruppe.getGruppenSieger().get(0));
    	
    	Platform.exit();
    }    
    
}
