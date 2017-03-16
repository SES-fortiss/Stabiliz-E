package org.fortiss.smg.stabilize.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.fortiss.smg.analyzer.api.NoDataFoundException;
import org.fortiss.smg.containermanager.api.devices.DeviceId;
import org.fortiss.smg.informationbroker.api.InformationBrokerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KPIGenerator {
	private static Logger logger = LoggerFactory.getLogger(KPIGenerator.class);

	StabilizeImpl impl;

		public String doSomething(String arg) throws TimeoutException {
		logger.debug("Hello!! from KPIGenerator class");
		return "Hello smg";
	}
	
}
