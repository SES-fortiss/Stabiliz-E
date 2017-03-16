package org.fortiss.smg.actuatorclient.apros.impl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.fortiss.smg.actuatormaster.api.IActuatorClient;
import org.fortiss.smg.actuatormaster.api.IActuatorMaster;
import org.fortiss.smg.containermanager.api.ContainerManagerInterface;
import org.fortiss.smg.containermanager.api.ContainerManagerQueueNames;
import org.fortiss.smg.remoteframework.lib.DefaultProxy;
import org.fortiss.smg.smgschemas.commands.DoubleCommand;
import org.slf4j.LoggerFactory;

public class ActuatorClientImpl implements IActuatorClient {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ActuatorClientImpl.class);

    public DefaultProxy<ContainerManagerInterface> clientInfo;
	public ContainerManagerInterface containerManagerClient;

	private String host;
	private String wrapperTag;
	private int pollFrequency;

	private String clientId;
	private IActuatorMaster master;

//	private Sensor sensor = null;
	private Actuator act = null;
	
    public ActuatorClientImpl(String host, String port, String wrapperTag,
			int pollFreq, String username, String password, String topic) {

		
		/* Test one Connection to CM */
		
		//DefaultProxy<ContainerManagerInterface> 
		clientInfo = new DefaultProxy<ContainerManagerInterface>(
				ContainerManagerInterface.class,
				ContainerManagerQueueNames.getContainerManagerInterfaceQueryQueue(), 10000);

		logger.info("try to init CM interface");
		
		try {
			containerManagerClient = clientInfo.init();
		}
		catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} catch (TimeoutException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
		
		this.host = host;
		this.wrapperTag = wrapperTag;
		loadStaticDevs(wrapperTag);
		this.pollFrequency = pollFreq;

		/* 
		 * subscribe to APROS values using actuator class 
		 */
		act = new Actuator(host+":"+port, username, password, topic);
		
		/*
		 * publish values in the APROS model using sensor class
		 */
//		sensor = new Sensor(host+":"+port, username, password, topic);
		
		

		
	}
    
    
    private void loadStaticDevs(String wrapperTag2) {
		// TODO Auto-generated method stub
		
	}


	public String doSomething(String s){
			return "Hello smg";
		}


		
		@Override
		public boolean isComponentAlive() {
			// TODO Auto-generated method stub
			return false;
		}
		
		
		public String getClientId() {
			return this.clientId;
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
			return this.getHost();
		}
		
		public String getPort() {
			return this.getPort();
		}
		
		public void setMaster(IActuatorMaster master) {
			this.master = master;
		}

		public void setClientId(String clientId) {
			this.clientId = clientId;
		}

		public void setPollFrequency(int pollFrequency) {
			this.pollFrequency = pollFrequency;
		}

		public void setWrapperTag(String wrapperTag) {
			this.wrapperTag = wrapperTag;
		}


		public void activate() {
			// TODO Auto-generated method stub
//			if (sensor != null) {
//				sensor.start();
//			}
			if ( act!= null){
				act.connect();
			}
		}


		@Override
		public void onDoubleCommand(DoubleCommand command, String containerId) {
			// TODO Auto-generated method stub
			
		}
		
		
}
