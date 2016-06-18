package org.miprojekt.turnieverwaltung;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.sun.javafx.application.PlatformImpl;

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


public class AppTest extends Application {

	
    @Test
    public void testApp() {
        Assert.assertTrue( true );
    }
    
   	@Test
    public void testeGruppenSieger() {
   		
    	Mannschaft m1 = new Mannschaft("Mannschaft 1");
    	Mannschaft m2 = new Mannschaft("Mannschaft 2");
    	Mannschaft m3 = new Mannschaft("Mannschaft 3");
    	Mannschaft m4 = new Mannschaft("Mannschaft 4");
    	
    	Group gruppe = new Group(m1, m2, m3, m4);
    	
    	IMatch matches[] = new Match[6];
    	
    	matches[0] = MatchFactory.build(m1, m2);
    	matches[1] = MatchFactory.build(m2, m3);
    	matches[2] = MatchFactory.build(m3, m4);
    	matches[3] = MatchFactory.build(m1, m3);
    	matches[4] = MatchFactory.build(m2, m4);
    	matches[5] = MatchFactory.build(m1, m4);

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
    	
   		Platform.setImplicitExit(true);
    	Platform.exit();
   		PlatformImpl.exit();
    }

//	Tests fuer Match (Sieger, Verlierer)
   	@Test
    public void testeMatchSiegerM1() throws Exception {
   		Mannschaft m1 = new Mannschaft("Mannschaft 1");
    	Mannschaft m2 = new Mannschaft("Mannschaft 2");
    	IMatch matches[] = new Match[1];
    	matches[0] = MatchFactory.build(m1, m2);
    	
    	matches[0].setToreM1(3);
    	matches[0].setToreM2(2);
       	
    	matches[0].setSieger();
       	Assert.assertEquals(matches[0].getSieger(), m1);
    	Assert.assertEquals(matches[0].getVerlierer(), m2);
   	}
   	@Test
   	public void testeMatchSiegerM2() throws Exception {
   		IMatch matches[] = new Match[1];
   		Mannschaft m1 = new Mannschaft("Mannschaft 1");
    	Mannschaft m2 = new Mannschaft("Mannschaft 2");
    	matches[0] = MatchFactory.build(m1, m2);
   		
   		matches[0].setToreM1(2);
       	matches[0].setToreM2(3);
       	
    	matches[0].setSieger();
       	Assert.assertEquals(matches[0].getSieger(), m2);
       	Assert.assertEquals(matches[0].getVerlierer(), m1);

   	}
   	@Test
    public void testeMatchUnentschieden() throws Exception {
   		Mannschaft m1 = new Mannschaft("Mannschaft 1");
    	Mannschaft m2 = new Mannschaft("Mannschaft 2");
    	IMatch matches[] = new Match[1];
    	matches[0] = MatchFactory.build(m1, m2);
    	
    	matches[0].setToreM1(1);
    	matches[0].setToreM2(1);
       	
    	matches[0].setSieger();
    	Assert.assertTrue(matches[0].getUnentschieden());
   	}
 	@Test
    public void testeMatchUnentschiedenKO() throws Exception {
   		Mannschaft m1 = new Mannschaft("Mannschaft 1");
    	Mannschaft m2 = new Mannschaft("Mannschaft 2");
    	IMatch matches[] = new Match[1];
    	matches[0] = MatchFactory.build(m1, m2);
    	matches[0].setTurnierType("KO-Turnier");
    	
    	matches[0].setToreM1(1);
    	matches[0].setToreM2(1);
       	
    	matches[0].setSieger();
    	Assert.assertFalse(matches[0].getUnentschieden());
    	Assert.assertTrue(matches[0].isGameFinished());
   	}
 	
 	@Test
    public void incrementTore() throws Exception {
   		Mannschaft m1 = new Mannschaft("Mannschaft 1");
    	Mannschaft m2 = new Mannschaft("Mannschaft 2");
    	IMatch matches[] = new Match[1];
    	matches[0] = MatchFactory.build(m1, m2);
    	
    	matches[0].incrementToreM1();
    	matches[0].incrementToreM2();
    	
    	Assert.assertEquals(matches[0].getToreM1(), 1);
    	Assert.assertEquals(matches[0].getToreM2(), 1);
   	}
 	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}


    
    
}
