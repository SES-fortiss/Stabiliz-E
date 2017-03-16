package org.fortiss.smg.informationbroker.api;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.fortiss.smg.ambulance.api.HealthCheck;
import org.fortiss.smg.containermanager.api.devices.DeviceContainer;
import org.fortiss.smg.containermanager.api.devices.DeviceId;
import org.fortiss.smg.containermanager.api.devices.SIUnitType;

public interface InformationBrokerInterface extends HealthCheck, IDatabase {


	public List<DoublePoint> getDoubleValue(String containerId,
			long from,
			long to) throws TimeoutException ;

	//TODO: check if obsolete
	public List<DoublePoint>  getDoubleValueBefore(String containerId,
			long date) throws TimeoutException ;


	public boolean isSIUnitType(String unit) throws TimeoutException;

	/**
	 * Get last seen date of device from DB
	 * 
	 * @param DeviceID
	 * 	        
	 *            
	 * @return Long last value 
	 */

	public Map<Long, Double> getLastseen(String containerId) throws TimeoutException;	   

	//	    public java.util.List<WrapperDataPoint> getWrapperinfo(
	//	       String type);


}
