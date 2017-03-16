package org.fortiss.smg.stabilize.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.fortiss.smg.analyzer.api.AnalyzerInterface;
import org.fortiss.smg.analyzer.api.NoDataFoundException;
import org.fortiss.smg.containermanager.api.devices.DeviceId;
import org.slf4j.LoggerFactory;

public class Pair {


	private static org.slf4j.Logger logger = LoggerFactory.getLogger(StabilizeImpl.class);
	
	long start;
	long stop;
	
	public HashMap<String, Double> mean = new HashMap<String, Double>();
	public HashMap<String, Double> min = new HashMap<String, Double>();
	public HashMap<String, Double> max = new HashMap<String, Double>();
	public HashMap<String, Double> median = new HashMap<String, Double>();
	
//	Double mean;
//	Double min;
//	Double max;
	
	public Pair(long start, long stop) {
		this.start = start;
		this.stop = stop;
	}

	public void calculate(String containerId) {
		logger.debug("calculate-0");
		if(containerId == null) {
			logger.debug("containerid == null");
			return;
		}
		// calculate mean, min, max, median
		logger.debug("calculate");
		try {
			logger.info("calculated mean for " + containerId + ": " + InterfaceFactory.getAnalyzer().getArithmeticMean(start, stop, containerId));
			mean.put(containerId, InterfaceFactory.getAnalyzer().getArithmeticMean(start, stop, containerId));
			min.put(containerId, InterfaceFactory.getAnalyzer().getMin(start, stop, containerId));
			max.put(containerId, InterfaceFactory.getAnalyzer().getMax(start, stop, containerId));
			//median.get(devid).add(x)
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoDataFoundException e) {
			e.printStackTrace();;
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// TODO what happens for a timeout? return which kind or
			// error???
		}
	}
	
	public String toString() {
		String string = "[";

		string += mean + "," + min + "," + max + "," + start + "," + stop;
		
		string += "]";
		
		return string;
	}

	public long getStart() {
		return start;
	}

	public long getStop() {
		return stop;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public void setStop(long stop) {
		this.stop = stop;
	}

}
