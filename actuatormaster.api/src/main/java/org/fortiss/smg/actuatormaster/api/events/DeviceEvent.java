package org.fortiss.smg.actuatormaster.api.events;

import org.fortiss.smg.containermanager.api.devices.DeviceContainer;
import org.apache.commons.math3.stat.descriptive.moment.SecondMoment;


public class DeviceEvent extends AbstractDeviceEvent<DeviceContainer> {

	protected DeviceEvent() {
		super();
	}
	
	public DeviceEvent(DeviceContainer value) {
		super(value);
	}
	
	
}
