package bgu.spl.mics;
import bgu.spl.mics.example.messages.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class MessageBusImplTest {
	
	private MessageBusImpl classUnderTest;
	private MicroServiceForTest m;
	private MicroServiceForTest m2;
	@Before
	public void setUp() throws Exception {
		classUnderTest = MessageBusImpl.getInstance();
		 m = new MicroServiceForTest("tester");
		 m2 = new MicroServiceForTest("tester2");
		classUnderTest.register(m);
		classUnderTest.register(m2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetInstance() {
		Assert.assertTrue(classUnderTest != null);
		MessageBusImpl classUnderTest2 = MessageBusImpl.getInstance();
		Assert.assertEquals(classUnderTest,classUnderTest2);
	}
	
	@Test
	public void testSubscribeEvent() {
		ExampleEvent e = new ExampleEvent("sender");
		classUnderTest.subscribeEvent(e.getClass(), m);
		classUnderTest.sendEvent(e);
		try {
			classUnderTest.awaitMessage(m);
			Assert.assertTrue(true);
		} catch (InterruptedException e1) {
			Assert.assertTrue(false);
			e1.printStackTrace();
		}
	}

	@Test
	public void testSubscribeBroadcast() {;
		ExampleBroadcast e = new ExampleBroadcast("sender");
		classUnderTest.subscribeBroadcast(e.getClass(), m);
		classUnderTest.sendBroadcast(e);
		try {
			classUnderTest.awaitMessage(m);
			Assert.assertTrue(true);
		} catch (InterruptedException e1) {
			Assert.assertTrue(false);
			e1.printStackTrace();
		}
	}

	@Test
	public void testComplete() {
		ExampleEvent e = new ExampleEvent("sender");
		classUnderTest.subscribeEvent(e.getClass(), m);
		Future f = new Future<>();
		f = classUnderTest.sendEvent(e);
		try {
			classUnderTest.awaitMessage(m);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		classUnderTest.complete(e, "result");
		Assert.assertTrue((String)f.get()=="result");
		
	}

	@Test
	public void testSendBroadcast() {
		ExampleBroadcast e = new ExampleBroadcast("sender");
		classUnderTest.subscribeBroadcast(e.getClass(), m);
		classUnderTest.sendBroadcast(e);
		try {
			classUnderTest.awaitMessage(m);
			Assert.assertTrue(true);
		} catch (InterruptedException e1) {
			Assert.assertTrue(false);
			e1.printStackTrace();
		}
		try {
			classUnderTest.awaitMessage(m2);
			Assert.assertTrue(false);
		} catch (InterruptedException e1) {
			Assert.assertTrue(true);
			e1.printStackTrace();
		}
		classUnderTest.subscribeBroadcast(e.getClass(), m2);
		classUnderTest.sendBroadcast(e);
		try {
			classUnderTest.awaitMessage(m);
			Assert.assertTrue(true);
		} catch (InterruptedException e1) {
			Assert.assertTrue(false);
			e1.printStackTrace();
		}
		try {
			classUnderTest.awaitMessage(m2);
			Assert.assertTrue(true);
		} catch (InterruptedException e1) {
			Assert.assertTrue(false);
			e1.printStackTrace();
		}
	}

	@Test
	public void testSendEvent() {
		ExampleEvent e = new ExampleEvent("sender");
		ExampleEvent e2 = new ExampleEvent("sender2");
		classUnderTest.subscribeEvent(e.getClass(), m);
		classUnderTest.sendEvent(e);
		classUnderTest.subscribeEvent(e.getClass(), m2);
		classUnderTest.sendEvent(e2);
		try {
			classUnderTest.awaitMessage(m2);
			Assert.assertTrue(true);
		} catch (InterruptedException e1) {
			Assert.assertTrue(false);
			e1.printStackTrace();
		}
		try {
			classUnderTest.awaitMessage(m);
			Assert.assertTrue(false);
		} catch (InterruptedException e1) {
			Assert.assertTrue(true);
			e1.printStackTrace();
		}
	}

	@Test
	public void testRegister() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnregister() {
		fail("Not yet implemented");
	}

	@Test
	public void testAwaitMessage() {
		fail("Not yet implemented");
	}

}
