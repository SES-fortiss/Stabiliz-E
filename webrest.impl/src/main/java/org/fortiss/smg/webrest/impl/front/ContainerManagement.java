package org.fortiss.smg.webrest.impl.front;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.fortiss.smg.containermanager.api.ContainerManagerInterface;
import org.fortiss.smg.containermanager.api.ContainerManagerQueueNames;
import org.fortiss.smg.containermanager.api.devices.Container;
import org.fortiss.smg.containermanager.api.devices.ContainerType;
import org.fortiss.smg.containermanager.api.devices.SIDeviceType;
import org.fortiss.smg.remoteframework.lib.DefaultProxy;
import org.fortiss.smg.smgschemas.commands.DoubleCommand;
import org.fortiss.smg.webrest.impl.BundleFactory;
import org.fortiss.smg.webrest.impl.ParametersNotValid;

/**
 * 
 * @author Sajjad Taheri
 * 
 */
@Path("/container")
public class ContainerManagement {

	
	private ContainerManagerInterface containerManager = BundleFactory.getContainerManager();
	
	
	@Context
	HttpHeaders headers;

	@Context
	UriInfo uriInfo;

	@Context
	Request request;

	public static final int TIMEOUTLONG = 5000;

	// ContainerManagerInterface container;
//
//	@GET
//	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
//	@Path("/container/detailedvalues/{containerId}")
//	public HashMap<SIDeviceType, Pair<Double, Long>> getDetailedValues(@PathParam("containerId") String containerId)
//			throws TimeoutException {
//		return containerManager.getDetailedValues(containerId);
//	}
	
	
	@GET
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
	@Path("/container/meanbytype/{containerId}/type/{type}")
	public double getMean(@PathParam("containerId") String containerId, @PathParam("type") SIDeviceType type)
			throws TimeoutException {
		return containerManager.getMeanByType(containerId, type);
	}

	@GET
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
	@Path("/container/minbytype/{containerId}/type/{type}")
	public double getMin(@PathParam("containerId") String containerId, @PathParam("type") SIDeviceType type)
			throws TimeoutException {
		return containerManager.getMinByType(containerId, type);
	}

	@GET
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
	@Path("/container/sumbytype/{containerId}/type/{type}")
	public double getSum(@PathParam("containerId") String containerId, @PathParam("type") SIDeviceType type)
			throws TimeoutException {
		return containerManager.getSumByType(containerId, type);
	}

	@GET
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
	@Path("/container/maxbytype/{containerId}/type/{type}")
	public double getMax(@PathParam("containerId") String containerId, @PathParam("type") SIDeviceType type)
			throws TimeoutException {
		return containerManager.getMaxByType(containerId, type);
	}

	@GET
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
	@Path("/container/valuebytype/{containerId}/type/{type}")
	public double getCurrentValueByType(@PathParam("containerId") String containerId,
			@PathParam("type") SIDeviceType type) throws TimeoutException {
		return containerManager.getCurrentValueByType(containerId, type);
	}


	/**
	 * ContainerManager - ContainerAdministration using GUI
	 */

	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/containeradd/{containerType}/{parentID}")
	public String addContainer(@PathParam("containerType") ContainerType containerType,
			@PathParam("parentID") String parentID) throws TimeoutException {

		// TODO: need updating ContainerID unique Long oldConatinerID ->
		// DeviceID in ContainerTable

		String containerID = BundleFactory.getContainerManager().addContainer(containerType);
		containerManager.addRealContainerEdge(parentID, containerID);

		return containerID;
	}

	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/containermove/{containerID}/{oldParentID}/{newParentID}")
	public String moveContainer(@PathParam("containerID") String containerID,
			@PathParam("oldParentID") String oldParentID, @PathParam("newParentID") String newParentID)
			throws TimeoutException {

		if (containerManager.updateRealContainerEdgeFixedChild(newParentID, containerID)) {
			return "SUCCESS";
		}
		return "FAILED";
	}

	@POST
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/containerrename/{containerID}/{newHRName}")
	public String renameContainer(@PathParam("containerID") String containerID,
			@PathParam("newHRName") String newHRName) throws TimeoutException {

		Container con = containerManager.getContainer(containerID);
		con.setHrName(newHRName);

		if (containerManager.updateContainer(containerID, con)) {
			return "SUCCESS";
		}
		return "FAILED";
	}

	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/containerremove/{containerID}")
	public String removeContainer(@PathParam("containerID") String containerID) throws TimeoutException {

		if (containerManager.removeContainer(containerID)) {
			return "SUCCESS";
		}
		return "FAILED";
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	@Path("/command/{containerid}/{command}")
	public String sendCommand(@PathParam("containerid") String containerID,
			@PathParam("command") double command) {

		DoubleCommand smgCommand = new DoubleCommand(command);
		
		
		try {
			containerManager.sendCommand(smgCommand, containerID);
		} catch (TimeoutException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JSONObject result = new JSONObject();

		try {
			result.put("Command", command);

			result.put("ContainerID", containerID);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.toString();

	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	@Path("/command/{containerid}/{command}/{type}")
	public String sendCommand(@PathParam("containerid") String containerID,
			@PathParam("command") double command, @PathParam("type") SIDeviceType type) {

		DoubleCommand smgCommand = new DoubleCommand(command);
		
		
		try {
			containerManager.sendCommandToContainer(smgCommand, containerID, type);
		} catch (TimeoutException e1) {
			e1.printStackTrace();
		}

		JSONObject result = new JSONObject();

		try {
			result.put("Command", command);

			result.put("ContainerID", containerID);
			
			result.put("SIDeviceType", type);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.toString();

	}
	

}
