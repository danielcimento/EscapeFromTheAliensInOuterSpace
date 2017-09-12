package model;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import model.cards.DangerousSectorCard;
import org.junit.Before;
import org.junit.Test;

public class DangerousSectorCardTest {
	@Before
	public void reset(){
		try{
			Method reset = DangerousSectorCard.class.getDeclaredMethod("reset", null);
			reset.setAccessible(true);
			reset.invoke(null, null);
		}catch(NoSuchMethodException e){
			System.out.println(e.getStackTrace());
			System.out.println("No such method!");
		}catch(NullPointerException e){
			System.out.println(e.getStackTrace());
			System.out.println("Null pointer exception");
		}catch(Exception e){
			System.out.println(e.getStackTrace());
		}
	}
	
	@Test
	public void testInitializeDeck(){
		assertEquals(25, DangerousSectorCard.getDeckInstance().size());
	}
	
	@Test
	public void testRefillDeck(){
		for(int i = 0; i < 25; i++){
			DangerousSectorCard.draw();
		}
		assertEquals(0, DangerousSectorCard.getDeckInstance().size());
		DangerousSectorCard.draw();
		assertEquals(24, DangerousSectorCard.getDeckInstance().size());
	}
}
