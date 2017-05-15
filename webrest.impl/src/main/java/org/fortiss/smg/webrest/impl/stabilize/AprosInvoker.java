package org.fortiss.smg.webrest.impl.stabilize;

import java.util.concurrent.TimeoutException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.fortiss.smg.containermanager.api.devices.DeviceId;
import org.fortiss.smg.smgschemas.commands.DoubleCommand;
import org.fortiss.smg.webrest.impl.BundleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/apros")
public class AprosInvoker {
	
	private static Logger logger = LoggerFactory
			.getLogger(AprosInvoker.class);

	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/doSomething")
	public String doSomething(@QueryParam("name") String s) {
		return "works";
	}
	
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/{switchId}/{status}")
	public String changeSwitch(@PathParam("switchId") String switchId, @PathParam("status") int status) {

		
		if (status != 1 && status != 0) {
			return "error: invalid status value - use 0 or 1";
		}
		
		try {
			// get containerId
			DeviceId id = new DeviceId(switchId, "apros.wrapper");
			String containerId = BundleFactory.getContainerManager().getContainerId(id);

			if (containerId != null) {
				DoubleCommand command = new DoubleCommand(status);
				logger.debug("sending DoubleCommand " + status + " to containerId: " + containerId + ", devId: " + id.getDevId());
				BundleFactory.getIActuatorMaster().sendDoubleCommand(command, containerId);
			} else {
				return "error: could not find switch";
			}
			
		} catch (TimeoutException e) {
			return "error";
		}

		return "success";

	}
}
