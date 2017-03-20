package org.fortiss.smg.webrest.impl.stabilize;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {

	private static Logger logger = LoggerFactory
			.getLogger(Config.class);
	
	private static Map<String, InetAddress> children;
	
	
	private static void loadConfig() {
		
		String path = System.getProperty("mapping.path");
		logger.debug("mapping path: " + path);

		if (path != null) {
			Properties prop = new Properties();
			try {
				FileInputStream in = new FileInputStream(path);
				prop.load(in);
				in.close();

				children = new HashMap<String, InetAddress>();
				for (Entry<Object, Object> p : prop.entrySet()) {
					String containerId = (String) p.getKey();
					String ipaddr = (String) p.getValue();
					logger.debug("conid: " + containerId + " ip: " + ipaddr);
					try {
						InetAddress addr = InetAddress.getByName(ipaddr);
						logger.debug("ip: " + addr.getHostAddress());
						children.put(containerId, addr);

						logger.debug("added to mapping: " + containerId + ", " + ipaddr);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Could not load properties file for ipaddr mapping");
			}
		} else {
			logger.error("Config/Property file is missing for ipaddr - containerid mapping");
		}
	}


	public static Map<String, InetAddress> getChildren() {
		if(children == null)
			loadConfig();
		
		return children;
		
	}
	
}
