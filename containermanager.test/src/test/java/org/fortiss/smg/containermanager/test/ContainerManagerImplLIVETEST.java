package org.fortiss.smg.containermanager.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

import junit.framework.Assert;

import org.apache.commons.math3.util.Pair;
import org.fortiss.smg.actuatormaster.api.ActuatorMasterQueueNames;
import org.fortiss.smg.actuatormaster.api.IActuatorMaster;
import org.fortiss.smg.actuatormaster.api.events.DeviceEvent;
import org.fortiss.smg.actuatormaster.api.events.DoubleEvent;
import org.fortiss.smg.containermanager.api.ContainerManagerInterface;
import org.fortiss.smg.containermanager.api.ContainerManagerQueueNames;
import org.fortiss.smg.containermanager.api.devices.Container;
import org.fortiss.smg.containermanager.api.devices.ContainerEdge;
import org.fortiss.smg.containermanager.api.devices.ContainerFunction;
import org.fortiss.smg.containermanager.api.devices.ContainerType;
import org.fortiss.smg.containermanager.api.devices.DeviceContainer;
import org.fortiss.smg.containermanager.api.devices.DeviceContainerSpec;
import org.fortiss.smg.containermanager.api.devices.DeviceId;
import org.fortiss.smg.containermanager.api.devices.EdgeType;
import org.fortiss.smg.containermanager.api.devices.SIDeviceType;
import org.fortiss.smg.containermanager.impl.ContainerManagerImpl;
import org.fortiss.smg.informationbroker.api.InformationBrokerInterface;
import org.fortiss.smg.informationbroker.api.InformationBrokerQueueNames;
import org.fortiss.smg.remoteframework.lib.DefaultProxy;
import org.fortiss.smg.smgschemas.commands.DoubleCommand;
import org.fortiss.smg.sqltools.lib.serialize.SimpleSerializer;
import org.fortiss.smg.sqltools.lib.utils.TestingDBUtil;
import org.fortiss.smg.testing.MockOtherBundles;
import org.fortiss.smg.containermanager.test.DummySensorCodes;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContainerManagerImplLIVETEST {

	private static org.fortiss.smg.testing.MockOtherBundles mocker;

	@BeforeClass
	public static void setUpDataBase() throws SQLException,
			ClassNotFoundException {
		ArrayList<String> bundles = new ArrayList<String>();

		bundles.add("Ambulance");
		bundles.add("InformationBroker");
		bundles.add("ActuatorMaster");
		
		
		mocker = new org.fortiss.smg.testing.MockOtherBundles(bundles);
	}

	private ContainerManagerImpl impl;
	private IActuatorMaster broker;
	//private TestingDBUtil db;
	@Before
	public void setUp() throws IOException, TimeoutException, ClassNotFoundException {
		
		DefaultProxy<IActuatorMaster> clientInfo = new DefaultProxy<IActuatorMaster>(
				IActuatorMaster.class,
				ActuatorMasterQueueNames.getActuatorMasterInterfaceQueue(), 5000);

		broker = clientInfo.init();
		System.out.println("found informationbroker"); 
		impl = new ContainerManagerImpl();//broker);
		System.out.println("init containermgr");
		
		//broker.executeQuery("INSERT INTO Room (id, roomRoleId,user_id) VALUES (1,1,1)");
		
		/*HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", 1);
		map.put("roomRoleId", 1);
		map.put("user_id", 2);
		SimpleSerializer.saveToDB(map, broker, "Room");*/
	}



	@Test
	public void testSendCommand() {
	
		DeviceContainerSpec dSpec = new DeviceContainerSpec();
		dSpec.setDeviceType(SIDeviceType.Powerplug);
		dSpec.setCommandMinRange(0);
		dSpec.setCommandRangeStep(1.0);
		dSpec.setCommandMaxRange(1);
		dSpec.setAcceptsCommands(true);
		dSpec.setValue(0);
		DeviceContainer devCon = new DeviceContainer(new DeviceId("dev", "dummy"), "foo",
				dSpec);
		
		
		devCon.setContainerId("255"); 
		
		impl.addDevContainer(devCon);
		
		//TODO
		DeviceId tmp = new DeviceId(
				"Steckdosenleiste.EnOcean.Fortiss.Steckdosenleiste",
				"enoceanUSB.wrapper");
		
		try {
			broker.sendDoubleCommand(new DoubleCommand(1.0), "255");
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(1.0, ((DeviceContainer) impl.getContainer("255")).getValue(), 0.0);
		

	}
	
	
	
}