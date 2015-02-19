package tests;

import static org.junit.Assert.*;

import java.util.Calendar;

import models.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MessageTest {

	Message one;
	
	@Before
	public void setup() {
		String time = Calendar.HOUR_OF_DAY + ":" + Calendar.MINUTE + ":" + Calendar.SECOND;
		one = new Message("jake", "test", time);
	}
	
	@After
	public void teardown() {
		
	}
	
	@Test
	public void toJson1() {
		String expected = "{\"author\":\"" + one.getAuthor() + "\",\"message\":\""+ one.getMessage() +"\",\"time\":"+ one.getTime() +"}";
		assertEquals(one.toJson(), expected);
	}
	
	@Test
	public void fromJson1() {
		Message expected = one.fromJson(one.toJson());
		System.out.println(one.fromJson(one.toJson()).toString());
		assertEquals(one, expected);
	}
}
