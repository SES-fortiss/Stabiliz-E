package org.fortiss.smg.stabilize.test;


import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.fortiss.smg.actuatormaster.api.ActuatorMasterQueueNames;
import org.fortiss.smg.actuatormaster.api.IActuatorMaster;
import org.fortiss.smg.actuatormaster.api.events.DeviceEvent;
import org.fortiss.smg.containermanager.api.ContainerManagerInterface;
import org.fortiss.smg.containermanager.api.devices.DeviceContainer;
import org.fortiss.smg.containermanager.api.devices.DeviceId;
import org.fortiss.smg.containermanager.api.devices.SIDeviceType;
import org.fortiss.smg.informationbroker.api.InformationBrokerInterface;
import org.fortiss.smg.informationbroker.api.InformationBrokerQueueNames;
import org.fortiss.smg.remoteframework.lib.DefaultProxy;
import org.fortiss.smg.stabilize.impl.InterfaceFactory;
import org.fortiss.smg.stabilize.impl.Pair;
import org.fortiss.smg.stabilize.impl.StabilizeImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class TestStabilizeSimple {

//	private static org.slf4j.Logger logger = LoggerFactory.getLogger(TestKPIGenerator.class);
	public StabilizeImpl impl;
	public org.fortiss.smg.testing.MockOtherBundles mocker;
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(TestStabilizeSimple.class);

	
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
		impl = new StabilizeImpl();
		
		
	}

	@After
	public void tearDown(){
            // TODO do some cleanup
        }

	@Test(timeout=5000)
	public void testYourMethod() throws TimeoutException{
//		impl.doSomething("hi");
		long timestamp = 1487026800000L;
		
		Calendar cal_start = Calendar.getInstance();
		cal_start.setTimeInMillis(timestamp);
		cal_start.set(Calendar.HOUR, 0);
		cal_start.set(Calendar.MINUTE, 0);
		cal_start.set(Calendar.SECOND, 0);
		long start = cal_start.getTimeInMillis();
		
		cal_start.add(Calendar.DATE, 1);
		cal_start.set(Calendar.HOUR_OF_DAY, 0);
		cal_start.set(Calendar.MINUTE, 0);
		cal_start.set(Calendar.SECOND, 0);
		long stop = cal_start.getTimeInMillis();
	
		
		
		
		
//		cal_start.setTimeInMillis(timestamp);
//		cal_stop.setTimeInMillis(stop);
		System.out.println(start + " , "+ stop);
	}
	
	@Test(timeout=5000)
	public void testdoSomething() throws TimeoutException{
		assertEquals("Hello smg",impl.doSomething("hi"));
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
//			logger.info("Master intiated");

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
			
		impl.generateKPIs();

	}
	
	@Test
	public void testCalculateVoltageDistortion() {
		
		// insert data into database
		 /*
		  * 
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 53.60171131314223, 0 , 1487028794000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 38.56756531314223, 0 , 1487033157000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 68.60171131314223, 0 , 1487035643000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 52.31422655656573, 0 , 1487039298000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 23.11313142287433, 0 , 1487042898000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 55.60171131314223, 0 , 1487046736000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 30.56756531314223, 0 , 1487050076000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 68.60171131314223, 0 , 1487054072000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 53.31422655656573, 0 , 1487057012000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 22.11313142287433, 0 , 1487062401000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 53.60171131314223, 0 , 1487066241000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 30.56756531314223, 0 , 1487067134000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 69.60171131314223, 0 , 1487070914000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 53.31422655656573, 0 , 1487075178000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 23.11313142287433, 0 , 1487077965000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 53.60171131314223, 0 , 1487083965000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 37.56756531314223, 0 , 1487087340000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 68.60171131314223, 0 , 1487090257000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 53.31422655656573, 0 , 1487093862000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 53.11313142287433, 0 , 1487097667000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 53.60171131314223, 0 , 1487100967000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 80.56756531314223, 0 , 1487104567000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 68.60171131314223, 0 , 1487107047000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 73.31422655656573, 0 , 1487110647000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 23.11313142287433, 0 , 1487111854000);


INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 53.60171131314223, 0 , 1487028794000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 38.56756531314223, 0 , 1487033157000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 68.60171131314223, 0 , 1487035643000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 52.31422655656573, 0 , 1487039298000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 23.11313142287433, 0 , 1487042898000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 55.60171131314223, 0 , 1487046736000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 30.56756531314223, 0 , 1487050076000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 68.60171131314223, 0 , 1487054072000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 53.31422655656573, 0 , 1487057012000);
INSERT INTO `DoubleEvents`(`ContainerID`,`value`, `maxAbsError`, `timestamp`) VALUES (19, 22.11313142287433, 0 , 1487062401000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 53.60171131314223, 0 , 1487066241000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 30.56756531314223, 0 , 1487067134000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 69.60171131314223, 0 , 1487070914000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 53.31422655656573, 0 , 1487075178000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 23.11313142287433, 0 , 1487077965000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 53.60171131314223, 0 , 1487083965000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 37.56756531314223, 0 , 1487087340000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 68.60171131314223, 0 , 1487090257000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 53.31422655656573, 0 , 1487093862000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 53.11313142287433, 0 , 1487097667000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 53.60171131314223, 0 , 1487100967000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 80.56756531314223, 0 , 1487104567000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 68.60171131314223, 0 , 1487107047000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 73.31422655656573, 0 , 1487110647000);
INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (19, 23.11313142287433, 0 , 1487111854000);


		  */
		String insertQuery = "";
		
		InformationBrokerInterface infoB = null;
		DefaultProxy<InformationBrokerInterface> proxy = new DefaultProxy<InformationBrokerInterface>(
				InformationBrokerInterface.class,
				InformationBrokerQueueNames.getQueryQueue(),
				5000);
		try {
			infoB = proxy.init();
//			infoB.executeQuery(insertQuery);
//			logger.info("Inserted DoubleEvents");
			
			// clear kpi table
			infoB.executeQuery("Delete from KPI;");
			// set interval
			List<Pair> intervals = impl.getIntervals(1487026800000L, 1487113200000L, Calendar.HOUR, 1);

			

			for(Pair p : intervals) {
				logger.info("Start: " + new Date(p.getStart()) + " Stop: " + new Date(p.getStop()));
				logger.info("Start: " + p.getStart() + " Stop: " + p.getStop());
			}
			
			// test for two container ids
			List<String> containerIds = Arrays.asList("18", "19");
			
			impl.calculateVoltageDistortion(intervals, containerIds);
			
			logger.info("after calculateVoltageDistortion");
			List<Map<String, Object>> result18 = infoB.getSQLResults("SELECT * FROM `KPI` WHERE containerid=18;");
			List<Map<String, Object>> result19 = infoB.getSQLResults("SELECT * FROM `KPI` WHERE containerid=19;");
			if(result18 == null && result19 == null) {
				logger.info("result == null");
			} else {
				System.out.println("Result size for container id = 18: " + result18.size());
				assertEquals(24, result18.size());
				assertEquals(24, result19.size());
			}
			
			
		} catch (TimeoutException e) {
			logger.error("Error when inserting DoubleEvents");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Error when inserting DoubleEvents");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCalculateVoltageDistortionNoData() {
		InformationBrokerInterface infoB = null;
		DefaultProxy<InformationBrokerInterface> proxy = new DefaultProxy<InformationBrokerInterface>(
				InformationBrokerInterface.class, InformationBrokerQueueNames.getQueryQueue(), 5000);

		try {
			infoB = proxy.init();

			infoB.executeQuery("Delete from KPI;");

			List<Pair> intervals = impl.getIntervals(-627267600000L, -627181200000L, Calendar.HOUR, 1);
			List<String> containerIds = Arrays.asList("18", "19");

			impl.calculateVoltageDistortion(intervals, containerIds);

			List<Map<String, Object>> result = infoB.getSQLResults("SELECT * FROM `KPI`;");

			assertEquals(0, result.size());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testGetLatest() {
		System.out.println("start testGetLatest");
		// add containers to containerMapping
		impl.containerMappingDeviceId.put(new DeviceId("devid1", "wrapperid"), "1");
		impl.containerMappingDeviceId.put(new DeviceId("devid2", "wrapperid"), "2");
		
		try {
			DeviceContainer dev1 = new DeviceContainer(new DeviceId("devid1", "wrapperid"), "");
			DeviceContainer dev2 = new DeviceContainer(new DeviceId("devid2", "wrapperid"), "");
			dev1.setDeviceType(SIDeviceType.Acceleration);
			dev2.setDeviceType(SIDeviceType.Acceleration);

			dev1.setValue(991);
			dev2.setValue(992);
			
			ContainerManagerInterface containerMgr = InterfaceFactory.getContainerManager();
			if (containerMgr != null) {
				containerMgr.getRegisteredDevices();
//				containerMgr.updateContainer(18, dev1);
				containerMgr.addDevContainer(dev2);
			} else {
				System.out.println("Error");
			}
		} catch (TimeoutException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			List<Map<String, Object>> result = impl.getLatest();
			
			for(Map<String, Object> r : result) {
//				r.get();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void testGetValues() {
		
		/*
		 * * INSERT INTO `containermanager_containers`(`ContainerID`, `wrapperid`, `devid`, `containerhrname`, `containertype`, `containerfunction`, `virtualcontainer`) VALUES (18, 'test.wrapper', 'DAPO_ENRG_l1_Volt', 'test.Volt', 'DEVICE', 'NONE', 0);
		 * INSERT INTO `containermanager_containers`(`ContainerID`, `wrapperid`, `devid`, `containerhrname`, `containertype`, `containerfunction`, `virtualcontainer`) VALUES (18, 'test.wrapper', 'DAPO_ENRG_l2_Volt', 'test.Volt', 'DEVICE', 'NONE', 0)
		 * INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 53.60171131314223, 0 , 1487028794000);
		 * INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 38.56756531314223, 0 , 1487033157000);
		 * INSERT INTO `DoubleEvents`(`ContainerID`, `value`, `maxAbsError`, `timestamp`) VALUES (18, 68.60171131314223, 0 , 1487035643000);
		 */
		
//		DefaultProxy<IActuatorMaster> masterProxy = new DefaultProxy<IActuatorMaster>(
//				IActuatorMaster.class,
//				ActuatorMasterQueueNames.getActuatorMasterInterfaceQueue(), 5000);
//
//		IActuatorMaster master = masterProxy.init();
//		
//		DeviceContainer devCon = new DeviceContainer(new DeviceId(
//				"test.wrapper", "testdevid"), "anyparent");
//
//		master.sendDeviceEvent(new DeviceEvent(devCon), "");
//		
		
		try {
			String containerid = InterfaceFactory.getContainerManager().getContainerId(new DeviceId("DAPO_ENRG_l1_Volt", "test.wrapper"));
			System.out.println(containerid);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String wrapperid = "test.wrapper";
		String type = "DAPO_ENRG_l1_Volt";
		int grain = Calendar.HOUR;
		int amount = 1;
		long start = 1487026800000L;
		long stop = 1487113200000L;
//		List<String> result = impl.getValues(wrapperid, type, grain, amount, start, stop);
//		System.out.println(result.toString());
		
	}

}
