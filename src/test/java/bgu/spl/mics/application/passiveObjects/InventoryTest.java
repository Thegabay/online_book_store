package bgu.spl.mics.application.passiveObjects;

import static org.junit.Assert.*;
import java.io.*;
import java.util.HashMap;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InventoryTest {
	
	private Inventory classUnderTest;

	@Before
	public void setUp() throws Exception {
		classUnderTest = Inventory.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	//if we have one instance of Inventory it is not null, if we have more they all the same.
	public void testGetInstance() {
		assertTrue(classUnderTest != null);
		Inventory classUnderTest2 = Inventory.getInstance();
		assertEquals(classUnderTest,classUnderTest2);
	}

	@Test
	public void testLoad() {
		BookInventoryInfo book1 = new BookInventoryInfo("TestBook",1,100);
		BookInventoryInfo[] inventory = {book1};
		classUnderTest.load(inventory);
		OrderResult result = classUnderTest.take("TestBook");
		assertEquals(result,OrderResult.SUCCESSFULLY_TAKEN);
		
	}

	@Test
	public void testTake() {
		BookInventoryInfo book1 = new BookInventoryInfo("TestBook",2,100);
		BookInventoryInfo[] inventory = {book1};
		classUnderTest.load(inventory);
		//Check if the book is not in inventory it return NOT_IN_STOCK
		OrderResult result = classUnderTest.take("notTheRightBook");
		Assert.assertEquals(result,OrderResult.NOT_IN_STOCK);
		//Check if the book is available in inventory it return SUCCESSFULLY_TAKEN
		result = classUnderTest.take("TestBook");
		Assert.assertEquals(result,OrderResult.SUCCESSFULLY_TAKEN);
		//Check if the book is available in inventory it reduce by one the number of books of the desired type.
		result = classUnderTest.take("TestBook");
		Assert.assertEquals(result,OrderResult.SUCCESSFULLY_TAKEN);
		result = classUnderTest.take("TestBook");
		Assert.assertEquals(result,OrderResult.NOT_IN_STOCK);
		
	}

	@Test
	public void testCheckAvailabiltyAndGetPrice() {
		BookInventoryInfo book1 = new BookInventoryInfo("TestBook",2,100);
		BookInventoryInfo[] inventory = {book1};
		classUnderTest.load(inventory);
		Assert.assertEquals(-1, classUnderTest.checkAvailabiltyAndGetPrice("notTheRightBook"));
		Assert.assertEquals(100, classUnderTest.checkAvailabiltyAndGetPrice("TestBook"));
	}

	
	@Test
	public void testPrintInventoryToFile() {
		BookInventoryInfo book1 = new BookInventoryInfo("TestBook",2,100);
		BookInventoryInfo[] inventory = {book1};
		classUnderTest.load(inventory);
		classUnderTest.printInventoryToFile("testPrint");
		HashMap<String, Integer> hm = null;
		try {
			FileInputStream fileIn = new FileInputStream("testPrint");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			hm = (HashMap<String, Integer>) in.readObject();
			in.close();
			fileIn.close();
		}
		catch(IOException i) {
			i.printStackTrace();
			return;
		}
		catch(ClassNotFoundException c) {
			System.out.println("HashMap class not found");
			c.printStackTrace();
			return;
		}
		Assert.assertTrue(hm.get("TestBook")==100);
				
	}

}
