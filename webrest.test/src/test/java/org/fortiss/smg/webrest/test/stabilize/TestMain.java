package org.fortiss.smg.webrest.test.stabilize;

import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.fortiss.smg.informationbroker.api.InformationBrokerInterface;
import org.fortiss.smg.usermanager.api.KeyManagerInterface;
import org.fortiss.smg.usermanager.impl.key.KeyManagerImpl;
import org.fortiss.smg.webrest.impl.BundleFactory;
import org.fortiss.smg.webrest.test.TestLogin;
import org.fortiss.smg.webrest.test.server.Const;
import org.fortiss.smg.webrest.test.server.MockWrapperServerControl;
import org.fortiss.smg.webrest.test.util.AuthenticationHelper;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMain {

	private static final Logger logger = LoggerFactory.getLogger(TestMain.class);

	private static int port = 8091;
    private static MockWrapperServerControl server;
    
    @BeforeClass
    public static void startServer() {
    	KeyManagerInterface keyManager = BundleFactory.getIKeyManager();
    	AuthenticationHelper.setKeyManager(keyManager);
    }

    @AfterClass
    public static void stopServer() {
        
    }


	@Test
	public void doSomething() {

		makeCall("stabilize/doSomething");
		
		long stop = new Date().getTime();
		long start = stop - 3600000;
		makeCall("stabilize/106/second/" + start + "/" + stop);
//		// important: timestamp must be set including miliseconds
//		Calendar from = Calendar.getInstance(TimeZone.getTimeZone("GMT+1"));
//		from.set(2017, Calendar.FEBRUARY, 7, 14, 00, 00);
//		new Date().getTime();
//		Calendar to = Calendar.getInstance(TimeZone.getTimeZone("GMT+1"));
//		to.set(2017, Calendar.FEBRUARY, 7, 16, 30, 00);
//
//		System.out.println("from and to " + from.getTime() + " " + to.getTime());
//		// call url
//		System.out.println("Now: " + new Date().getTime());
//		makeCall("stabilize/getlatest/dummy.DAPO_ENRG_l1_Volt/" + new Date().getTime());
//		//makeCall("stabilize/getAverage/dummy.DAPO_ENRG_l1_Volt/" + from.getTimeInMillis() + "/" + to.getTimeInMillis());
//		//makeCall("stabilize/getAverage/123456789.DAPO_ENRG_l1_Volt/" + 1486474661147L + "/" + 1486474665866L);
	}

	public void makeCall(String s) {
		Const.setAccessKey("68a45057-5734-4dad-9f86-ab9e32c4506e");
    	Const.setSecretKey("jg9e65dui5272c45uds3qrf3b8gc71crjq4raq43");
		String url = "";
		try {
			url = AuthenticationHelper.generateValidURL(s, port);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			Assert.fail("Unsupported url");
		}
		String out = "";
		try {
			System.out.println("URL: " + url);
			out = AuthenticationHelper.testResourceAtUrl(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug("baa", e.fillInStackTrace());
			Assert.fail("something falied");
		}
		if (out.length() < 1) {
			Assert.fail("no response");

		}
	}
}
