package org.fortiss.smg.actuatorclient.dimis.test;


import org.fortiss.smg.actuatorclient.dimis.impl.ActuatorClientImpl;

import org.fortiss.smg.config.lib.Ops4JTestTime;
import java.util.concurrent.TimeoutException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

public class TestActuatorClientSimple {


	private ActuatorClientImpl impl;

	@Before
	public void setUp() {
		impl = new ActuatorClientImpl(null, null, null, 0, null, null);
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
