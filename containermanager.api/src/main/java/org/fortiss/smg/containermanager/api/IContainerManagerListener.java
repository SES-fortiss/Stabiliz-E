package org.fortiss.smg.containermanager.api;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.commons.math3.util.Pair;
import org.fortiss.smg.containermanager.api.devices.Container;
import org.fortiss.smg.containermanager.api.devices.DeviceId;


public interface IContainerManagerListener {
	
	
	/**
	 * Provides 2 mappings for DeviceID and HRName to ContainerID
	 * @param containerMappingDeviceIdWithStrings
	 * @param containerMapHrName
	 * @throws TimeoutException
	 */
//	void currentlyRegisteredContainers(Map<DeviceId, String> containerMapDeviceId, Map<String, String> containerMapHrName) throws TimeoutException; 
	
	void currentlyRegisteredContainers(HashMap<org.apache.commons.lang3.tuple.Pair<String, String>, String> containerMappingDeviceIdWithStrings, Map<String, String> containerMapHrName) throws TimeoutException; 
	
	
	String doSomething(String text);

}
