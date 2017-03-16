package org.fortiss.smg.webrest.impl.stabilize;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fortiss.smg.stabilize.api.StabilizeInterface;
import org.fortiss.smg.webrest.impl.BundleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/stabilize")
public class KPIGateway {
	
	private static Logger logger = LoggerFactory
			.getLogger(KPIGateway.class);
		
	
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/simple/{name}")
	public String simple(@PathParam("name") String s) {
		logger.debug("in the simple method");
		String name = s;
		return s +" is in KPIGateway!";
	}
	
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/getAll/{timestamp}")
	public String getAllKPIs(@PathParam("timestamp") long timestamp) {
		/* 
		 * @param: timestamp 1485253521931
		 * url:http://localhost:8091/api/stabilize/getAll/1485253521931
		 */
		StabilizeInterface stab = BundleFactory.getStabilize();
		String result = stab.getAllKpi(timestamp);
		return result;
	}

	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/getKPItype/{kpitype}/{timestamp}")
	public String getType(@PathParam("kpitype") String type, @PathParam("timestamp") long timestamp) {
		/* 
		 * @param: timestamp 1485253521931
		 * url:http://localhost:8091/api/stabilize/getKPItype/Volt_distortion/1485253521931
		 */
		StabilizeInterface stab = BundleFactory.getStabilize();
		String result= stab.getKpiType(type, timestamp);
		return result;
	}
	
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/getKPIduration/{from}/{to}")
	public String getKPIduration(@PathParam("from") long from, @PathParam("to") long to) {
		/* 
		 * @param: from, to
		 * url:http://localhost:8091/api/stabilize/getKPIduration/1485253521931/1485253521931
		 */
		StabilizeInterface stab = BundleFactory.getStabilize();
		String result = stab.getKpiDuration(from, to);
		return result;
	}
}
