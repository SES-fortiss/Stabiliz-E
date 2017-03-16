package org.fortiss.smg.stabilize.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.fortiss.smg.ambulance.api.HealthCheck;
import org.fortiss.smg.containermanager.api.devices.DeviceContainer;

public interface StabilizeInterface extends HealthCheck {

	
	String doSomething(String arg) throws TimeoutException;

	public List<Map<String, Object>> getLatest() throws IOException, TimeoutException;
	public List<Map<String, Object>> getValues(String wrapperid, String type, int grain, int amount, long start, long stop );
	
//	public void calculateVoltageDistortion() throws TimeoutException;
//	public void calculateFrequencyDistortion() throws TimeoutException;
//	public void getdailyEnergyBranchDistribution(String type)  throws TimeoutException;
//	public void getDailyPeakEnergyConsumption(String type)  throws TimeoutException;
//	public void getDailyMinEnergyConsumption(String type)  throws TimeoutException;
	public void generateKPIs() throws TimeoutException;
	public String getAllKpi(long timestamp);
	public String getKpiType(String kpitype, long timestamp);
	public String getKpiDuration(long from, long to);
	
}
