package org.fortiss.smg.stabilize.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeoutException;

import org.fortiss.smg.actuatormaster.api.ActuatorMasterQueueNames;
import org.fortiss.smg.actuatormaster.api.IActuatorMaster;
import org.fortiss.smg.actuatormaster.api.events.DeviceEvent;
import org.fortiss.smg.analyzer.api.AnalyzerInterface;
import org.fortiss.smg.analyzer.api.AnalyzerQueueNames;
import org.fortiss.smg.containermanager.api.devices.DeviceContainer;
import org.fortiss.smg.containermanager.api.devices.DeviceId;
import org.fortiss.smg.informationbroker.api.InformationBrokerInterface;
import org.fortiss.smg.informationbroker.api.InformationBrokerQueueNames;
import org.fortiss.smg.remoteframework.lib.DefaultProxy;
import org.fortiss.smg.stabilize.impl.KPIGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class TestKPIGenerator {
	private static org.slf4j.Logger logger = LoggerFactory
			.getLogger(TestKPIGenerator.class);

	public org.fortiss.smg.testing.MockOtherBundles mocker;
	public KPIGenerator gen;

	@Before
	public void setUp() throws ClassNotFoundException, IOException,
			TimeoutException {
		ArrayList<String> bundles = new ArrayList<String>();

		bundles.add("Ambulance");
		bundles.add("InformationBroker");
		bundles.add("ActuatorMaster");
		bundles.add("ContainerManager");
		bundles.add("Analyzer");

		mocker = new org.fortiss.smg.testing.MockOtherBundles(bundles);
		gen = new KPIGenerator();

	}

	@After
	public void tearDown() {
	}

	@Test(timeout = 5000)
	public void testdoSomething() throws TimeoutException {
		assertEquals("Hello smg", gen.doSomething("hi"));
	}

	@Test
	public void testgenerateKPI() {
		IActuatorMaster master = null;
		DefaultProxy<IActuatorMaster> proxyMaster = new DefaultProxy<IActuatorMaster>(
				IActuatorMaster.class,
				ActuatorMasterQueueNames.getActuatorMasterInterfaceQueue(),
				5000);
		try {
			master = proxyMaster.init();
			logger.info("Master intiated");

		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		DeviceContainer devCon = new DeviceContainer(new DeviceId(
				"test.wrapper", "testdevid"), "anyparent");
//		devCon.setValue(10);

		if (master != null) {
			try {
				master.sendDeviceEvent(new DeviceEvent(devCon), "");
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("Testing the generateKPI method");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//			gen.generateKPIs();

	}

	@Test
		public void testgenerateKPI() {
			IActuatorMaster master = null;
			DefaultProxy<IActuatorMaster> proxyMaster = new DefaultProxy<IActuatorMaster>(
					IActuatorMaster.class,
					ActuatorMasterQueueNames.getActuatorMasterInterfaceQueue(),
					5000);
			try {
				master = proxyMaster.init();
				logger.info("Master intiated");
	
			} catch (TimeoutException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			DeviceContainer devCon = new DeviceContainer(new DeviceId(
					"test.wrapper", "testdevid"), "anyparent");
	//		devCon.setValue(10);
	
			if (master != null) {
				try {
					master.sendDeviceEvent(new DeviceEvent(devCon), "");
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
			System.out.println("Testing the generateKPI method");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	//			gen.generateKPIs();
	
		}

	@Test
	public void testgetValues() throws TimeoutException, IOException {
		System.out.println("Testing the voltage distortion");

		System.out.println("searching for informationbroker");
		DefaultProxy<InformationBrokerInterface> clientInfo = new DefaultProxy<InformationBrokerInterface>(
				InformationBrokerInterface.class,
				InformationBrokerQueueNames.getQueryQueue(), 300);

		InformationBrokerInterface broker = clientInfo.init();
		System.out.println("found informationbroker");

		String id = "dummy.DAPO_ENRG_l1_EnergyVA";
		DeviceId dev = new DeviceId(id, "dummy.wrapper");

		// DateFormat df = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		int startDate = cal.get(Calendar.DATE);
		while (cal.get(Calendar.DATE) == startDate) {
			long lowerbound = cal.getTimeInMillis();
			cal.add(Calendar.HOUR, 1);
			long upperbound = cal.getTimeInMillis();
			System.out.println("[" + lowerbound + " , " + upperbound + "]");

			// List<DoublePoint> result = broker.getDoubleValue(dev, lowerbound,
			// upperbound);
			// System.out.println (result);
		}
	}

	@Test
	public void TestAnalyzerMethod() throws IOException, TimeoutException {

		DefaultProxy<AnalyzerInterface> analyzerInfo = new DefaultProxy<AnalyzerInterface>(
				AnalyzerInterface.class,
				AnalyzerQueueNames.getAnalyzerInterfaceQueue(), 5000);

		AnalyzerInterface analyzer = analyzerInfo.init();
		System.out.println("found analyzer");

		String id = "dummy.DAPO_ENRG_l1_EnergyVA";
		DeviceId dev = new DeviceId(id, "dummy.wrapper");

		double hourlyAverage = 0.0;

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		int startDate = cal.get(Calendar.DATE);
		while (cal.get(Calendar.DATE) == startDate) {
			long lowerbound = cal.getTimeInMillis();
			cal.add(Calendar.HOUR, 1);
			long upperbound = cal.getTimeInMillis();
			// System.out.println("["+ lowerbound+ " , "+ upperbound+ "]");
			/*
			 * Calculating hourly average
			 */
			try {
				hourlyAverage = 0; // analyzer.getArithmeticMean(lowerbound,
									// upperbound, dev);
				System.out.println("hourlyAverage: " + hourlyAverage);

				double nominalVal = 230;
				double distortion = ((hourlyAverage / nominalVal) - 1) * 100;
				System.out.println("Voltage distortion % at each hour: "
						+ distortion);

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// double hourlyAvg = impl.calculateVoltageDistortion();
		// System.out.println(hourlyAvg);
	}
}
