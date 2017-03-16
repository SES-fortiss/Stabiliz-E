/**
 * 
 */
package org.fortiss.smg.webrest.impl.stabilize;

import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.fortiss.smg.containermanager.api.devices.SIDeviceType;
import org.fortiss.smg.gamification.api.QuizQuestion;
import org.fortiss.smg.gamification.api.SingleGamificationUser;
import org.fortiss.smg.gamification.api.GamificationGroup;
import org.fortiss.smg.webrest.impl.BundleFactory;
import org.fortiss.smg.webrest.impl.ParametersNotValid;
import org.fortiss.smg.webrest.impl.front.ContainerManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Pragya Gupta
 *
 */
@Path("/stabelize")
public class StabelizESimulation {

	private static Logger logger = LoggerFactory
			.getLogger(StabelizESimulation.class);


	@POST
	@Path("/store/{src}/{timestamp}/{genre}/{node}/{type}/{value}")
	public boolean storeValueFromSimulation (
			@PathParam("src") String src, 
			@PathParam("timestamp") String timestamp,  
			@PathParam("genre") String genre,
			@PathParam("node") String node,
			@PathParam("type") String type,
			@PathParam("value") String value)
					throws TimeoutException  {

		boolean isSuccessful = false;
		
		/**
		 * TODO: Parse Values before insert according to types
		 * value = double ?
		 * ...
		 */
			
		if (BundleFactory.getInformationBroker().executeQuery("INSERT INTO SimulationEvents VALUES ("
					+node+","+type+","+genre+","+Double.parseDouble(value)+","+src+","+Integer.parseInt(timestamp)+");")) {
			isSuccessful = true;
		};
			return isSuccessful;
	}

	@POST
	@Path("/storeJSON/{json}")
	public boolean storeValueFromSimulation (
			@PathParam("json") String json)
					throws TimeoutException  {

		boolean isSuccessful = false;
		
		/**
		 * TODO: Parse the json String and extract values, we are interested in:
		 * src = parsed_json.src
		 * ...
		 * also catch exceptions
		 */
		String values = "";
		
		if (BundleFactory.getInformationBroker().executeQuery("INSERT INTO SimulationEvents ("+ values +");")) {
			isSuccessful = true;
		};
		
			
		return isSuccessful;
	}
	
	
	
	/**
	 * Retrieves the current state of a device
	 * 
	 * @param deviceContainerID
	 *            : the the device container ID (wrapperID.deviceID)
	 * @return
	 */
	@GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	@Path("/items/{itemname}")
	public static String getAllStates(@PathParam("itemname") String ContainerID) {
		ContainerManagement containerManager = new ContainerManagement();
		logger.debug("openHAB is getting a state and a container ID with: " + ContainerID);

		String[] types = { "ConsumptionPowermeter", "ProductionPowermeter", "Battery"};
		
		JSONObject result = new JSONObject();
			
		try {
			
			for (String type : types) {
				SIDeviceType t = SIDeviceType.fromString(type);
				
				double value = containerManager.getMean(ContainerID, t);
				if (Double.isNaN(value)) {
					result.put(type.toLowerCase(), 0.0);
				}
				else {
					result.put(type.toLowerCase(), value);
				}
			}
		
		} catch (TimeoutException e) {
			logger.info("No connection?", e.fillInStackTrace());
			throw new ParametersNotValid("Unable to connect to ContainerManager");
		} catch (JSONException e	) {		
			e.printStackTrace();
		}
		return result.toString();
		//return "Hello world";
		
	}
	
	
	
}
