package org.fortiss.smg.actuatorclient.dimis.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.fortiss.smg.actuatorclient.dimis.impl.message.Device;
import org.fortiss.smg.actuatorclient.dimis.impl.message.auth.AuthRequest;
import org.fortiss.smg.actuatorclient.dimis.impl.object.DataIdentifier;
import org.fortiss.smg.actuatorclient.dimis.impl.object.Dimis;
import org.fortiss.smg.actuatorclient.dimis.impl.object.DimisMessage;
import org.fortiss.smg.actuatorclient.dimis.impl.socket.SocketServer;
import org.fortiss.smg.actuatormaster.api.IActuatorClient;
import org.fortiss.smg.actuatormaster.api.IActuatorMaster;
import org.fortiss.smg.actuatormaster.api.events.DeviceEvent;
import org.fortiss.smg.actuatormaster.api.events.DoubleEvent;
import org.fortiss.smg.containermanager.api.ContainerManagerInterface;
import org.fortiss.smg.containermanager.api.ContainerManagerQueueNames;
import org.fortiss.smg.containermanager.api.devices.DeviceContainer;
import org.fortiss.smg.containermanager.api.devices.DeviceId;
import org.fortiss.smg.remoteframework.lib.DefaultProxy;
import org.fortiss.smg.smgschemas.commands.DoubleCommand;
import org.quartz.SchedulerException;
import org.slf4j.LoggerFactory;

public class ActuatorClientImpl implements IActuatorClient {

	private static org.slf4j.Logger logger = LoggerFactory
			.getLogger(ActuatorClientImpl.class);
	private IActuatorMaster master;
	private String clientId;
	private ScheduledExecutorService executor;
	private int pollFrequency;
	private String host;
	private String port;
	private String wrapperTag;

	private ContainerManagerInterface broker;
	private DiMISLooper looper;
	private SocketServer server = null;

	ArrayList<DeviceContainer> devices = new ArrayList<DeviceContainer>();
	Map<String, DeviceContainer> deviceMap = new HashMap<String, DeviceContainer>();
	
	
	// <DeviceId, containerID>
//	HashMap<DeviceId, String> containerIds = new HashMap<DeviceId, String>();
	private AuthRequest authRequest = null;

	public ActuatorClientImpl(String host, String port, String wrapperTag,
			int pollFreq, String username, String password) {

		this.host = host;
		this.port = port;
		this.wrapperTag = wrapperTag;
//		loadStaticDevs(wrapperTag);
		this.pollFrequency = pollFreq;
		logger.info("host: " +host+ " port: " +port + " pollFrequency: ");
		
	    Configs.buildConfigs(new String[] { "10001", "1000" });
		
		//"ipcfg": {
//      "auth": "rmtConfigIp",
//      "dhcp": "F",
//      "ip": "10.1.100.71",
//      "mask": "255.255.255.0",
//      "gtw": "10.1.100.254"
//},  
		
		// HTTP Configuration
//		String ip = "10.1.100.254";
//		int sftpPort = 1001;
//		String User = "";
//		String sftpPassword = "";
//		
	}

	private void loadStaticDevs(String wraperTag) {
		DefaultProxy<ContainerManagerInterface> clientInfo = new DefaultProxy<ContainerManagerInterface>(
				ContainerManagerInterface.class,
				ContainerManagerQueueNames
						.getContainerManagerInterfaceQueryQueue(), 10000);

		logger.info("try to init CM interface");
		ContainerManagerInterface containerManagerClient = null;
		try {
			containerManagerClient = clientInfo.init();

			
			// TODO Do the device configuration
			// Update Looper should provide device information on connect
		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			clientInfo.destroy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void sendNewDeviceEvents() {
		
		ArrayList<DeviceContainer> devicestmp = new ArrayList<DeviceContainer>();

		
		for (DeviceContainer dev : devices) {
			try {
				logger.info("Try to send " + dev.getDeviceId() + " to master");
				String containerId = master.sendDeviceEvent(new DeviceEvent(dev), this.clientId);
				dev.setContainerId(containerId);
				devicestmp.add(dev);
				deviceMap.put(containerId, dev);
				logger.info("Sent " + dev.getDeviceId() + " to master");
				logger.info("Received containerID: " + containerId + " for " + dev.getHrName()); 

			} catch (TimeoutException e) {
				logger.debug("Failed to send " + dev.getDeviceId()
						+ " to master");
			}
		}
		devices = devicestmp;
	}

	@Override
	public void onDoubleCommand(DoubleCommand command, String containerId) {
		if (deviceMap.containsKey(containerId)) {
			logger.info("Executing: Command " + command.getValue() + " was sent to ContainerID: " + containerId);

			//TODO implement command execution and inform ActuatorMaster about new status
			
			
		} else {
			logger.info("ActuatorClient " + this.clientId + " ignoring Command for ContainerID " + containerId
					+ "(Device unknown)");
		}

	}

	public String doSomething(String s) {
		return "Hello smg";
	}

	@Override
	public boolean isComponentAlive() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * ACTIVATE and DEACTIVATE
	 */
	public synchronized void activate() {
//		sendNewDeviceEvents();
		logger.debug("activate");
		/**
		 * Start Server on a dedicated Socket
		 */
		LinkedBlockingQueue<Dimis> dimisList = new LinkedBlockingQueue<Dimis>();
		LinkedBlockingQueue<DimisMessage> messageList = new LinkedBlockingQueue<DimisMessage>();
		
		try {
			server = new SocketServer(this, dimisList, messageList);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(new DiMISLooper(this), 10,
		getPollFrequency(), TimeUnit.SECONDS);
//		
		// DiMISServer server = new DiMISServer(this);
		if (server != null) {
			server.startListening();
		}

//		DiMISLooper looper ;
		
		/*
		 * TODO: Here we want to start the server and if we have successful
		 * Client connections, we will start a new looper/executer for each
		 * client
		 */
		

		// We do this in the Server ... Looper is sockethandler
		
		
	}

	public synchronized void deactivate() {
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void createEvents(List<Device> devicesList) {
		logger.debug("CreateEvents in SMG2.0");
		for(Device dev : devicesList) {
			if(devices.isEmpty()) {
				createDeviceList(devicesList);
				sendNewDeviceEvents();
			}
			ArrayList<DataIdentifier> dataIDList = dev.dataIDList;
			ArrayList<Double> valuesList = dev.valuesList;
			for(int i = 0; i < dataIDList.size()-1; i++) {
				Double value = valuesList.get(i);
				DoubleEvent ev = new DoubleEvent(value);
				String id = dataIDList.get(i).name();
				DeviceId origin = new DeviceId(id, wrapperTag);
				try {
					logger.debug("event: " + ev + ", origin dev id: " + origin.getDevId() + ", origin wrapper id: " +  origin.getWrapperId());
					DeviceContainer container = getDevContainer(origin.getDevId(), origin.getWrapperId());;
					master.sendDoubleEvent(ev, container.getContainerId(), clientId);
				} catch (TimeoutException e) {
					 logger.error("timeout sending to master", e);
				}
			}
		}
		
	}
	
	private void createDeviceList(List<Device> devicesList) {
		for(Device dev : devicesList) {
			for(DataIdentifier id : dev.dataIDList) {
				DeviceContainer newDev = new DeviceContainer(new DeviceId(id.name(), wrapperTag), wrapperTag);
				newDev.setHrName(wrapperTag + "." + id.name());
				newDev.setDeviceType(id.getType());
				devices.add(newDev);
			}
		}
	}
	
	public DeviceContainer getDevContainer(String devId, String wrapperId) {
		for(DeviceContainer devC : devices) {
			String devIdC = devC.getDevId();
			String wrapperIdC = devC.getDeviceId().getWrapperId();
			
			if(devId.equals(devIdC) && wrapperId.equals(wrapperIdC)) {
				return devC;
			}
		}
		return null;
	}

	/*
	 * GETTER and SETTER
	 */
	
	public String getClientId() {
		return this.clientId;
	}

	public List<DeviceContainer> getDeviceSpecs() {
		return this.devices;
	}

	public IActuatorMaster getMaster() {
		return this.master;
	}

	public int getPollFrequency() {
		return this.pollFrequency;
	}

	public String getWrapperTag() {
		return this.wrapperTag;
	}

	public String getHost() {
		return this.host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setPollFrequency(int pollFrequency) {
		this.pollFrequency = pollFrequency;
	}

	public void setMaster(IActuatorMaster master) {
		this.master = master;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setWrapperTag(String wrapperTag) {
		this.wrapperTag = wrapperTag;
	}

	public SocketServer getServer() {
		return server;
	}

	public void setServer(SocketServer server) {
		this.server = server;
	}

	public void setAuthRequest(AuthRequest authRequest) {
		this.authRequest = authRequest;
	}
	
	public AuthRequest getAuthRequest() {
		return this.authRequest;
	}

	public void addDevice(DeviceContainer dev) {
		devices.add(dev);
		
	}

}
