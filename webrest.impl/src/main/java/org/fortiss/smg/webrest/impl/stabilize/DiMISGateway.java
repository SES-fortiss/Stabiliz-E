package org.fortiss.smg.webrest.impl.stabilize;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.math3.util.Pair;
import org.fortiss.smg.analyzer.api.AnalyzerInterface;
import org.fortiss.smg.containermanager.api.ContainerManagerInterface;
import org.fortiss.smg.containermanager.api.devices.Container;
import org.fortiss.smg.containermanager.api.devices.EdgeType;
import org.fortiss.smg.stabilize.api.StabilizeInterface;
import org.fortiss.smg.webrest.impl.BundleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/stabilize")
public class DiMISGateway {
	
	public static AnalyzerInterface analyzer = BundleFactory.getAnalyzer();
	public static StabilizeInterface stabilize = BundleFactory.getStabilize();
	
	private static Logger logger = LoggerFactory
			.getLogger(DiMISGateway.class);

	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/doSomething")
	public String doSomething(@QueryParam("name") String s) {
		return "works";
	}

	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/getLatest")
	public String getLatestValues() {
		
		Map<String, String> result = new HashMap<String, String>();
		// take ips from config object - make config object
		Map<String, InetAddress> children = Config.getChildren();

		// Entry<ContainerId,IP address>
		for (Entry<String, InetAddress> device : children.entrySet()) {
			try {
				// rest call to all children
				
				InetAddress ipaddr = device.getValue();
				// set server ip of device and do a rest call
				Const.setServerIP(ipaddr);
				String url = AuthenticationHelper.generateValidURL("stabilize/latest", Const.getPort());
				String resp = AuthenticationHelper.testResourceAtUrl(url);
				
				result.put(device.getKey(), resp);
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// TODO return concated String
		return null;
	}
	
	
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/latest")
	public String getLatest() {
		logger.debug("latest");

		HashMap<String, Pair<Double, Long>> result;
	
		result = new HashMap<String, Pair<Double, Long>>();
				//stabilize.getLatest();

		if (!result.isEmpty()) {
			StringBuilder sb = new StringBuilder();

			for (Entry<String, Pair<Double, Long>> r : result.entrySet()) {
				
				String containerId = r.getKey();
				Double value = r.getValue().getFirst();
				long timestamp = r.getValue().getSecond();
				sb.append("[" + containerId + "," + value + "," + timestamp + "]");
				sb.append("\n");
			}

			return sb.toString();
		}
		
		// TODO
		return "Error: unable to get latest values";
	}


	/**
	 *  avg, min, max for a specific device(id) and in a grain level of either 5secs, hour, day, month, year 
	 *  
	 * @param id
	 * @param type_id
	 * @param grainName
	 * @param start
	 * @param stop
	 * @return
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{id}/{type}/{grain}/{from}/{to}")
	public String getValue(@PathParam("id") String containerId, @PathParam("type") String type_id, @PathParam("grain") String grainName, @PathParam("from") long start, @PathParam("to") long stop) {
		
		
		int grain;
		int amount = 1;
		//parse grain
		if(grainName.equals("second")) {
			grain = Calendar.SECOND;
			amount = 5;
		} else if(grainName.equals("hour")) {
			grain = Calendar.HOUR;
		} else if(grainName.equals("day")) {
			grain = Calendar.DATE;
		} else if(grainName.equals("month")) {
			grain = Calendar.MONTH;
		} else if(grainName.equals("year")) {
			grain = Calendar.YEAR;
		} else {
			return "Error: invalid grain value";
		}

		List<Map<String,Object>> data = stabilize.getValues(containerId, type_id, grain, amount, start, stop);
		
		if(!data.isEmpty()) {
		StringBuilder sb = new StringBuilder();
		for(Map<String, Object> d : data) {
			Double mean = (Double) d.get("mean");
			Double max = (Double) d.get("max");
			Double min = (Double) d.get("min");
//			Double median = d.get("median");
			long timestamp = (Long) d.get("timestamp");
			sb.append("[" + containerId + "," + type_id + "," + mean + "," + max + "," + min + "," + timestamp + "]");
			sb.append("\n");
		}
		
		return sb.toString();
		
		} else {
			// TODO return error msg
			return "Error";
		}
	}

	
	
}