package org.fortiss.smg.containermanager.api;

import java.util.Map;
import java.util.concurrent.TimeoutException;
import org.fortiss.smg.containermanager.api.devices.Container;
import org.fortiss.smg.containermanager.api.devices.DeviceId;


public interface IContainerManagerListener {
	
	
	/**
	 * Provides 2 mappings for DeviceID and HRName to ContainerID
	 * @param containerMapDeviceId
	 * @param containerMapHrName
	 * @throws TimeoutException
	 */
	void currentlyRegisteredContainers(Map<DeviceId, String> containerMapDeviceId, Map<String, String> containerMapHrName) throws TimeoutException; 
	

}
