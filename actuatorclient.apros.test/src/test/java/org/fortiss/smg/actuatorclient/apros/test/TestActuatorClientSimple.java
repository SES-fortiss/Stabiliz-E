package org.fortiss.smg.actuatorclient.apros.test;


import org.fortiss.smg.actuatorclient.apros.impl.ActuatorClientImpl;


import java.util.concurrent.TimeoutException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestActuatorClientSimple {


	private ActuatorClientImpl impl;

	@Before
	public void setUp() {
		impl = new ActuatorClientImpl(null, null, null, 0, null, null, null);
        }

	@After
	public void tearDown(){
            // TODO do some cleanup
        }

	@Test(timeout=5000)
	public void testYourMethod() throws TimeoutException{
		assertEquals("Hello smg",impl.doSomething("hi"));
	}
}
