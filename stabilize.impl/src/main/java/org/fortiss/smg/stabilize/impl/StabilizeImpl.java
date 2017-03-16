package org.fortiss.smg.stabilize.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.fortiss.smg.ambulance.api.HealthCheck;
import org.fortiss.smg.analyzer.api.NoDataFoundException;
import org.fortiss.smg.containermanager.api.ContainerManagerInterface;
import org.fortiss.smg.containermanager.api.IContainerManagerListener;
import org.fortiss.smg.containermanager.api.devices.DeviceContainer;
import org.fortiss.smg.containermanager.api.devices.DeviceId;
import org.fortiss.smg.informationbroker.api.DoublePoint;
import org.fortiss.smg.informationbroker.api.InformationBrokerInterface;
import org.fortiss.smg.stabilize.api.StabilizeInterface;
import org.fortiss.smg.stabilize.api.StabilizeQueueNames;
import org.slf4j.LoggerFactory;

public class StabilizeImpl implements HealthCheck, StabilizeInterface, IContainerManagerListener {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(StabilizeImpl.class);
    
	public Map<String, String> containerMappingHRName;
	public Map<DeviceId, String> containerMappingDeviceId;

	
      
	public StabilizeImpl() throws IOException, TimeoutException {
		InterfaceFactory.activate();
		init();
		
		containerMappingHRName = new HashMap<String, String>();
		containerMappingDeviceId = new HashMap<DeviceId, String>();
		logger.debug(("containerMappingHRName " +containerMappingHRName.size()));
		logger.debug("containerMappingDeviceId " +containerMappingDeviceId);
    }
	
	
	private void init() throws TimeoutException {
			ContainerManagerInterface container = InterfaceFactory.getContainerManager();
			
//			container = containerMgr;
//			System.out.println(StabilizeQueueNames.getStabilizeInterfaceQueue());
			String queuename = StabilizeQueueNames.getStabilizeInterfaceQueue();
			try {
				boolean registered = container.registerListener("stabilize-1",queuename );
				System.out.println(registered);
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			container.updateRegisteredDevices();		
		
	}

	public String doSomething(String s) {
		return "Hello smg";
	}

	@Override
	public boolean isComponentAlive() {
		return false;
	}

	public List<Map<String,Object>> getValues(String wrapperid, String type, int grain, int amount, long start, long stop) {

		List<Pair> intervals = getIntervals(start, stop, grain, amount);
		
		ContainerManagerInterface containerManager = InterfaceFactory.getContainerManager();

		try {
			String containerId = containerManager.getContainerId(new DeviceId(type, wrapperid));
			// calculate avg, mean, min, max for all pairs in the interval
			for (Pair p : intervals) {
				p.calculate(containerId);
			}
			
			List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
			// package data
			for (Pair p : intervals) {
				Map<String, Object> resultPoint = new HashMap<String, Object>();
				resultPoint.put("wrapperid", wrapperid);
				resultPoint.put("type", type);
				resultPoint.put("mean", p.mean.get(containerId));
				resultPoint.put("max", p.max.get(containerId));
				resultPoint.put("min", p.min.get(containerId));
//				resultPoint.put("median", devId);
				resultPoint.put("timestamp", p.getStart());
				
				result.add(resultPoint);
			}

			return result;
		} catch (TimeoutException e) {
			e.printStackTrace();
		}

		// TODO what to return?
		return null;
	}
	
	public static List<Pair> getIntervals(long start, long stop, int grain, int amount) {
		List<Pair> intervals = new ArrayList<Pair>();
		
		Calendar cal_start = Calendar.getInstance();
		Calendar cal_stop = Calendar.getInstance();
		cal_start.setTimeInMillis(start);
		cal_stop.setTimeInMillis(stop);
		
		Calendar temp_start = Calendar.getInstance();
		Calendar temp_stop = Calendar.getInstance();
		temp_start.setTimeInMillis(start);
		temp_stop.setTimeInMillis(start);
		temp_stop.add(grain, amount);
		
		for(;temp_stop.compareTo(cal_stop) <= 0; cal_start.add(grain, amount), temp_stop.add(grain, amount)) {
			intervals.add(new Pair(cal_start.getTimeInMillis(), temp_stop.getTimeInMillis()));
		}
		return intervals;
		
	}

	public List<Map<String, Object>> getLatest() throws IOException, TimeoutException {
		InformationBrokerInterface infoBroker = InterfaceFactory.getInformationBroker();
		ContainerManagerInterface containerManagerInterface = InterfaceFactory.getContainerManager();

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		for (String containerId : containerMappingDeviceId.values()) {
			// check for device container
			// TODO neue methode in containermanager für timestamps
			try {
				DeviceContainer dev = containerManagerInterface.getDeviceContainer(containerId);
				long time = new Date().getTime();
				List<DoublePoint> values = infoBroker.getDoubleValue(containerId, time, time);

				if (values != null && !values.isEmpty()) {

					DoublePoint point = values.get(0);
					Double value = point.getValue();
					long timestamp = point.getTime();

					// TODO  wrapper und devid nicht nötig weil containerid
//					DeviceId devID = dev.getDeviceId();
//					String wrapper = devID.getWrapperId();
//					String devId = devID.getDevId();

					Map<String, Object> resultPoint = new HashMap<String, Object>();
					resultPoint.put("value", (Object) value);
					resultPoint.put("timestamp", timestamp);
//					resultPoint.put("wrapperid", wrapper);
//					resultPoint.put("devid", devId);

					result.add(resultPoint);
				}
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public void currentlyRegisteredContainers(Map<DeviceId, String> containerMapDeviceId, Map<String, String> containerMapHrName) throws TimeoutException {
		containerMappingDeviceId =containerMapDeviceId;
		containerMappingHRName = containerMapHrName;
		
	}

	@Override
	public void generateKPIs()  {
		Calendar cal = Calendar.getInstance();
		long stop = cal.getTimeInMillis();

		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		long start = cal.getTimeInMillis();
		int grain = Calendar.HOUR;

		// for debugging purpose only
		start = 1487026800000L;
		stop = 1487113200000L;

		List<Pair> intervals = getIntervals(start, stop, grain, 1);

		for (Pair p : intervals) {
			System.out.println(p.start + ", " + p.stop);
		}

		/*
		 * Prepare the list with type voltage
		 */
		List<String> voltageDev = new ArrayList<String>();
		List<String> frequencyDev = new ArrayList<String>();
		List<String> apprentEnergy = new ArrayList<String>();

		try {
			voltageDev.add(InterfaceFactory.getContainerManager().getContainerId(new DeviceId("DAPO_ENRG_L1_Volt", "dimis.wrapper")));
//			voltageDev.add(InterfaceFactory.getContainerManager().getContainerId(new DeviceId("DAPO_ENRG_L2_Volt", "dimis.wrapper")));
//			voltageDev.add(InterfaceFactory.getContainerManager().getContainerId(new DeviceId("DAPO_ENRG_L3_Volt", "dimis.wrapper")));
			calculateVoltageDistortion(intervals, voltageDev);

			frequencyDev.add(InterfaceFactory.getContainerManager().getContainerId(new DeviceId("DAPO_ENRG_L1_Hz", "dimis.wrapper")));
//			frequencyDev.add(InterfaceFactory.getContainerManager().getContainerId(new DeviceId("DAPO_ENRG_L2_Hz", "dimis.wrapper")));
//			frequencyDev.add(InterfaceFactory.getContainerManager().getContainerId(new DeviceId("DAPO_ENRG_L3_Hz", "dimis.wrapper")));
			calculateFrequencyDistortion(intervals, frequencyDev);
			
			
			 apprentEnergy.add(InterfaceFactory.getContainerManager().getContainerId(new DeviceId("DAPO_ENRG_l1_EnergyVA", "dimis.wrapper")));
//			 apprentEnergy.add(InterfaceFactory.getContainerManager().getContainerId(new DeviceId("DAPO_ENRG_l2_EnergyVA", "dimis.wrapper")));
//			 apprentEnergy.add(InterfaceFactory.getContainerManager().getContainerId(new DeviceId("DAPO_ENRG_l3_EnergyVA", "dimis.wrapper")));
			 getDailyPeakEnergyConsumption(intervals, apprentEnergy);
			 getDailyMinEnergyConsumption(intervals, apprentEnergy);
			
			 getdailyEnergyConsumption(apprentEnergy);
			//

		} catch (TimeoutException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Calculates the 10 % voltage distortion
	 * @param intervals, containerId
	 */
	public void calculateVoltageDistortion(List<Pair> intervals, List<String> voltageDev) {

		logger.debug("calculateVoltageDistortion");
		double nominalVolt = 230;
		double hourlyAverage = 0.0;
		double distortion;
		
		for (String containerId : voltageDev) {
			for (Pair p : intervals) {
				long from = p.start;
				long to = p.stop;

				if (InterfaceFactory.getAnalyzer() == null) {
					logger.error("Analyzer Interface not initialized");
				} else {
					logger.debug("Analyzer != null");
				}

				try {
					hourlyAverage = InterfaceFactory.getAnalyzer().getArithmeticMean(from, to, containerId);

					distortion = ((hourlyAverage / nominalVolt) - 1) * 100;
					saveKPI(containerId, "Volt_distortion", distortion, from);

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (NoDataFoundException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Calculates the 10 % frequency distortion
	 * @param intervals, containerId
	 */
	public void calculateFrequencyDistortion(List<Pair> intervals, List<String> voltageDev){
		
		logger.debug("calculateVoltageDistortion");
		double nominalHz = 50;
		double hourlyAverage = 0.0;
		double distortion;
		
		for (String containerId : voltageDev) {
			for (Pair p : intervals) {
				long from = p.start;
				long to = p.stop;

				if (InterfaceFactory.getAnalyzer() == null) {
					logger.error("Analyzer Interface not initialized");
				} else {
					logger.debug("Analyzer != null");
				}

				try {
					hourlyAverage = InterfaceFactory.getAnalyzer().getArithmeticMean(from, to, containerId);
					distortion = ((hourlyAverage / nominalHz) - 1) * 100;
					saveKPI(containerId, "Volt_distortion", distortion, from);

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (NoDataFoundException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Calculates the maximum energy consumption for last 24-hours.
	 * @param intervals, containerId
	 */
	public void getDailyPeakEnergyConsumption(List<Pair> intervals, List<String> apprentEnergy) {

		logger.debug("DailyPeakEnergyConsumption");
		List<Double> hourlyAverage = new ArrayList<Double>();
		double peakConsumption = 0;
		double maxVal = 0.0;
		long timeOcurrance = 0;

		try {
			for (String containerId : apprentEnergy) {
				for (Pair p : intervals) {

					long from = p.start;
					long to = p.stop;

					if (InterfaceFactory.getAnalyzer() == null) {
						logger.error("Analyzer Interface not initialized");
					} else {
						logger.debug("Analyzer != null");
					}
					hourlyAverage.add(InterfaceFactory.getAnalyzer().getArithmeticMean(from, to, containerId));
				
					peakConsumption = calculateMaximum(hourlyAverage);

					if (peakConsumption > maxVal) {
						maxVal = peakConsumption;
						timeOcurrance = from;
					}
				}
				logger.debug("Peak Consumption Value in last 24-hours: "+ peakConsumption);
				saveKPI(containerId, "PeakEnergyConsumption", peakConsumption,timeOcurrance);
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoDataFoundException e) {
			e.printStackTrace();
		}
	}

	private double calculateMaximum(List<Double> hourlyAverage) {

		if (hourlyAverage.size() == 0) {
			logger.debug("List is empty");
		}
		double max = hourlyAverage.get(0);
		int length = hourlyAverage.size();
		for (int i = 1; i < length; i++) {
			double value = hourlyAverage.get(i);
			max = Math.max(max, value);
		}
		return max;
	}

	/**
	 * Calculates the minimum energy consumption for last 24-hours.
	 * @param intervals, containerId
	 */
	public void getDailyMinEnergyConsumption(List<Pair> intervals, List<String> apprentEnergy) {
		
		logger.debug("DailyPeakEnergyConsumption");
		List<Double> hourlyAverage = new ArrayList<Double>();
		double minConsumption = 0.0;
		double minVal = 0.0;
		long timeOcurrance = 0;

		try {
			for (String containerId : apprentEnergy) {
				for (Pair p : intervals) {

					long from = p.start;
					long to = p.stop;

					if (InterfaceFactory.getAnalyzer() == null) {
						logger.error("Analyzer Interface not initialized");
					} else {
						logger.debug("Analyzer != null");
					}
					hourlyAverage.add(InterfaceFactory.getAnalyzer().getArithmeticMean(from, to, containerId));
				
					minConsumption = calculateMinimum(hourlyAverage);

					if (minConsumption < minVal) {
						minVal = minConsumption;
						timeOcurrance = from;
					}
				}
				logger.debug("Peak Consumption Value in last 24-hours: "+ minConsumption);
				saveKPI(containerId, "PeakEnergyConsumption", minConsumption,timeOcurrance);
			}
		} catch (TimeoutException e) {     
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoDataFoundException e) {
			e.printStackTrace();
		}
	}

	private double calculateMinimum(List<Double> hourlyAverage) {
		if (hourlyAverage.size() == 0) {
			logger.debug("List is empty");
		}
		double min = hourlyAverage.get(0);
		int length = hourlyAverage.size();
		for (int i = 1; i < length; i++) {
			double value = hourlyAverage.get(i);
			min = Math.min(min, value);
		}
		return min;
	}

	/**
	 * Calculates the daily energy consumption for last 24-hours.
	 * 
	 * @param conainterid
	 * @throws TimeoutException
	 */
	public void getdailyEnergyConsumption(List<String> apprentEnergy) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			// long midnight = cal.getTimeInMillis();
			long midnight = 1487458800000L; // for testing purposes: 19-02-2017 00:00:00:00:00

			cal.add(Calendar.DATE, -1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			// long lastnight = cal.getTimeInMillis();
			long lastnight = 1487372400000L; // for testing purposes: 18-02-2017 00:00:00:00:00

			List<Map<String, Object>> tonightConsumption = null;
			List<Map<String, Object>> previousNightConsumption = null;

			for (String containerId : apprentEnergy) {

				String sql1 = "SELECT * FROM DoubleEvents WHERE containerid = \""
						+ containerId + "\" AND timestamp < \""
						+ midnight + "\" ORDER BY timestamp DESC LIMIT 0,1 ;";

				tonightConsumption = InterfaceFactory.getInformationBroker().getSQLResults(sql1);

				double consumedEnergyAgg = Double.parseDouble(tonightConsumption.get(0).get("value").toString());

				String sql2 = "SELECT * FROM DoubleEvents WHERE containerid = \""
						+ containerId + "\" AND timestamp < "
						+ lastnight + " ORDER BY timestamp DESC LIMIT 0,1 ; ";
				
				previousNightConsumption = InterfaceFactory.getInformationBroker().getSQLResults(sql2);

				double previousConsumedEnergy = Double.parseDouble(previousNightConsumption.get(0).get("value").toString());

				double actualConsumedEnergy = consumedEnergyAgg - previousConsumedEnergy;
				logger.debug("Daily Consumption Value in last 24-hours: " + actualConsumedEnergy);
				saveKPI(containerId, "DailyEnergyConsumption", actualConsumedEnergy, midnight);
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getAllKpi(long timestamp) {
		String insertQuery = null;
		timestamp = 1487026800000L; // for testing purposes only
		
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
		
		List<Pair> intervals = getIntervals(start, stop, 1, 1);
		
		for (Pair p : intervals) {

			long from = p.start;
			long to = p.stop;

			if (InterfaceFactory.getInformationBroker() == null) {
				logger.error("Borker Interface not initialized");
			} else {
				logger.debug("InfoBroker != null");
			}
			insertQuery = " SELECT * FROM KPI WHERE timestamp >= " + from + "AND timestamp <= "+ to + "; " ;  
			logger.debug(insertQuery);
			try {
				InterfaceFactory.getInformationBroker().executeQuery(insertQuery);
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
			
		}
		return insertQuery;
	}


	@Override
	public String getKpiType(String kpitype, long timestamp) {
		String insertQuery = null;
		timestamp = 1487026800000L; // for testing purposes only
		
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
		
		List<Pair> intervals = getIntervals(start, stop, 1, 1);
		
		for (Pair p : intervals) {

			long from = p.start;
			long to = p.stop;

			if (InterfaceFactory.getInformationBroker() == null) {
				logger.error("Borker Interface not initialized");
			} else {
				logger.debug("InfoBroker != null");
			}
			insertQuery = "SELECT * FROM KPI WHERE kpitype = \"" + kpitype + "\" AND timestamp >= " + from + "AND timestamp <= "+ to + "; " ;  
			logger.debug(insertQuery);
			try {
				InterfaceFactory.getInformationBroker().executeQuery(insertQuery);
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
			
		}
		return insertQuery;
	}


	@Override
	public String getKpiDuration(long start, long stop) {
		String insertQuery = null;
		
		List<Pair> intervals = getIntervals(start , stop, 1, 1);
		
		for (Pair p : intervals) {

			long from = p.start;
			long to = p.stop;

			if (InterfaceFactory.getInformationBroker() == null) {
				logger.error("Borker Interface not initialized");
			} else {
				logger.debug("InfoBroker != null");
			}
			insertQuery = "SELECT * FROM KPI WHERE timestamp >= " + from + "AND timestamp <= "+ to + "; " ;  
			logger.debug(insertQuery);
			try {
				InterfaceFactory.getInformationBroker().executeQuery(insertQuery);
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
			
		}
		return insertQuery;
	}
	
	private void saveKPI(String containerid, String kpitype, double value, long timestamp) throws TimeoutException {
		
		String insertQuery = "INSERT INTO KPI(containerid, kpitype, value, timestamp) VALUES ( " + containerid  
				+ "," + kpitype + "," + value + "," + timestamp + ");";

		InterfaceFactory.getInformationBroker().executeQuery(insertQuery);
	}


	
}
