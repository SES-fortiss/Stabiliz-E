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

//	enum GrainType {
//	SECONDLY,
//	HOURLY,
//	DAILY,
//	MONTHLY,
//	YEARLY;
//	}
	
//	Map<String, InetAddress> ipaddr_mapping = null; // <containerid,ip-adresse>
//    
//	String grain_error = "{  \"status\": \"error\", "
//			+ "\"data\": null, "
//			+ "\"message\": \"invalid grain type\""
//			+ "}";
//	
//	public static AnalyzerInterface analyzer = BundleFactory.getAnalyzer();
//	public static StabilizeInterface stabilize = BundleFactory.getStabilize();
//	
//	private static Logger logger = LoggerFactory
//			.getLogger(DiMISGateway.class);
//
//	@GET
//	@Produces({ MediaType.TEXT_PLAIN })
//	@Path("/doSomething")
//	public String doSomething(@QueryParam("name") String s) {
//		return "works";
//	}
//
//	@GET
//	@Produces({ MediaType.TEXT_PLAIN })
//	@Path("/getLatest")
//	public String getLatestValues() {
//		
//		Map<String, String> result = new HashMap<String, String>();
//		// take ips from config object - make config object
//		Map<String, InetAddress> devices = Config.devices;
//		// rest call to object
//
//		// Entry<ContainerId,IP address>
//		for (Entry<String, InetAddress> device : devices.entrySet()) {
//			try {
//				InetAddress ipaddr = device.getValue();
//				// set server ip of device and do a rest call
//				Const.setServerIP(ipaddr);
//				String url = AuthenticationHelper.generateValidURL("stabilize/latest", Const.getPort());
//				String resp = AuthenticationHelper.testResourceAtUrl(url);
//				
//				result.put(device.getKey(), resp);
//				
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (UnknownHostException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//		// return concated String
//		return null;
//	}
//	
//	
//	@GET
//	@Produces({ MediaType.TEXT_PLAIN })
//	@Path("/latest")
//	public String getLatest() {
//		logger.debug("getLatest");
//		
////		if(ipaddr_mapping == null) {
////			logger.debug("load ip mapping");
////			loadMapping();
////			logger.debug("loaded ip mapping");
////		}
////		ContainerManagerInterface containerMgr = BundleFactory.getContainerManager();
////		String containerId = null;
////		int port = 8091;
////
////		// get own ip adress
////		// http://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java
////		// could be wrong ip address when there are more network interfaces
////		try {
////			InetAddress addr = InetAddress.getLocalHost();
////			logger.debug("my ip addr: " + addr);
////			containerId = getContainerId(addr);
////		} catch (UnknownHostException e1) {
////			// TODO Auto-generated catch block
////			e1.printStackTrace();
////		}
////
////		if (containerId == null) {
////			// TODO define error msg
////			return "Error: containerid not found";
////		}
////		
////		
////		// check for container type DEVICE
////
////		// are there any children?
////		try {
////			// Container container = containerMgr.getContainer(containerId);
////			List<Entry<Container, EdgeType>> children = containerMgr.getChildrenWithEdgeTypes(containerId);
////
////			if (!children.isEmpty()) {
////				logger.debug("has children");
////				for (Entry<Container, EdgeType> child : children) {
////					logger.debug("child - containerid: " + child.getKey().getContainerId());
////					Container con = child.getKey();
////					String child_containerId = con.getContainerId();
////
////					InetAddress ip = ipaddr_mapping.get(child_containerId);
////					Const.setServerIP(ip);
////					String url = AuthenticationHelper.generateValidURL("stabilize/latest", port);
////					String resp = AuthenticationHelper.testResourceAtUrl(url);
////					logger.info("WebRest API call response: " + resp);
////				}
//		// } else {
//		List<Map<String, Object>> result;
//		try {
//			result = stabilize.getLatest();
//
//			if (!result.isEmpty()) {
//				StringBuilder sb = new StringBuilder();
//
//				for (Map<String, Object> r : result) {
//					Double value = (Double) r.get("value");
//					long timestamp = (Long) r.get("timestamp");
//					String wrapperid = (String) r.get("wrapperid");
//					String devid = (String) r.get("devid");
//					sb.append("[" + wrapperid + "," + devid + "," + value + "," + timestamp + "]");
//					sb.append("\n");
//				}
//
//				return sb.toString();
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TimeoutException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		// TODO return error msg
//		return "Error calling stabilize getLatest";
//
//		// }
//
//		//		} catch (TimeoutException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (UnsupportedEncodingException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
////		return "Error: Problem with children";
//
//	}
//
//	private String getContainerId(InetAddress addr) {
//		for( Entry<String, InetAddress> pair : ipaddr_mapping.entrySet()) {
//			if(pair.getValue().equals(addr)) {
//				return pair.getKey();
//			}
//		}
//		
//		return null;
//	}
//
//	/**
//	 *  avg, min, max for a specific device(id) and in a grain level of either 5secs, hour, day, month, year 
//	 *  
//	 * @param id
//	 * @param type_id
//	 * @param grainName
//	 * @param start
//	 * @param stop
//	 * @return
//	 */
//	@GET
//	@Produces({ MediaType.APPLICATION_JSON })
//	@Path("/{id}/{type}/{grain}/{from}/{to}")
//	public String getValue(@PathParam("id") String containerId, @PathParam("type") String type_id, @PathParam("grain") String grainName, @PathParam("from") long start, @PathParam("to") long stop) {
//		
//		
//		int grain;
//		int amount = 1;
//		//parse grain
//		if(grainName.equals("second")) {
//			grain = Calendar.SECOND;
//			amount = 5;
//		} else if(grainName.equals("hour")) {
//			grain = Calendar.HOUR;
//		} else if(grainName.equals("day")) {
//			grain = Calendar.DATE;
//		} else if(grainName.equals("month")) {
//			grain = Calendar.MONTH;
//		} else if(grainName.equals("year")) {
//			grain = Calendar.YEAR;
//		} else {
//			return grain_error;
//		}
//
//		List<Map<String,Object>> data = stabilize.getValues(containerId, type_id, grain, amount, start, stop);
//		
//		if(!data.isEmpty()) {
//		StringBuilder sb = new StringBuilder();
//		for(Map<String, Object> d : data) {
//			Double mean = (Double) d.get("mean");
//			Double max = (Double) d.get("max");
//			Double min = (Double) d.get("min");
////			Double median = d.get("median");
//			long timestamp = (Long) d.get("timestamp");
//			sb.append("[" + containerId + "," + type_id + "," + mean + "," + max + "," + min + "," + timestamp + "]");
//			sb.append("\n");
//		}
//		
//		return sb.toString();
//		
//		} else {
//			// TODO return error msg
//			return "Error";
//		}
//	}
//
//	
//	public void loadMapping() {
//		// load a port?
//		
//		String path = System.getProperty("mapping.path");
//		logger.debug("mapping path: " + path);
//		if (path != null) {
//			Properties prop = new Properties();
//			try {
//				FileInputStream in = new FileInputStream(path);
//				prop.load(in);
//				in.close();
//
//				ipaddr_mapping = new HashMap<String, InetAddress>();
//				for (Entry<Object, Object> p : prop.entrySet()) {
//					String containerId = (String) p.getKey();
//					String ipaddr = (String) p.getValue();
//					logger.debug("conid: " + containerId + " ip: " + ipaddr);
//					try {
//						InetAddress addr = InetAddress.getByName(ipaddr);
//						logger.debug("ip: " + addr.getHostAddress());
//						ipaddr_mapping.put(containerId, addr);
//						
//						logger.debug("added to mapping: " + containerId + ", " + ipaddr);
//					} catch (UnknownHostException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				logger.error("Could not load properties file for ipaddr mapping");
//			}
//		} else {
//			logger.error("Config/Property file is missing for ipaddr - containerid mapping");
//		}
//	}
}