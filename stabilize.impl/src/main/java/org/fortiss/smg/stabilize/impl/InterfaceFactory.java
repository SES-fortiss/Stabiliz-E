package org.fortiss.smg.stabilize.impl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.fortiss.smg.actuatormaster.api.ActuatorMasterQueueNames;
import org.fortiss.smg.actuatormaster.api.IActuatorMaster;
import org.fortiss.smg.analyzer.api.AnalyzerInterface;
import org.fortiss.smg.analyzer.api.AnalyzerQueueNames;
import org.fortiss.smg.containermanager.api.ContainerManagerInterface;
import org.fortiss.smg.containermanager.api.ContainerManagerQueueNames;
import org.fortiss.smg.informationbroker.api.InformationBrokerInterface;
import org.fortiss.smg.informationbroker.api.InformationBrokerQueueNames;
import org.fortiss.smg.remoteframework.lib.DefaultProxy;
import org.fortiss.smg.remoteframework.lib.RabbitRPCProxy;
import org.fortiss.smg.stabilize.api.StabilizeInterface;
import org.fortiss.smg.usermanager.api.KeyManagerInterface;
import org.fortiss.smg.usermanager.api.UserManagerInterface;
import org.fortiss.smg.usermanager.api.UserManagerQueueNames;

public class InterfaceFactory {
	
	private static InformationBrokerInterface broker;
	private static KeyManagerInterface keyManager;
	private static UserManagerInterface userManager; 
	private static ContainerManagerInterface containerManager;
	private static AnalyzerInterface analyzer;
	private static IActuatorMaster master;
	private static StabilizeInterface stabilize;
	/**
	 * @author Pragya Kirti Gupta
	 */
	public static  void activate() throws IOException, TimeoutException {

		DefaultProxy<InformationBrokerInterface> clientInfo = new DefaultProxy<InformationBrokerInterface>(
				InformationBrokerInterface.class,
				InformationBrokerQueueNames.getQueryQueue(), 5000);

		broker = clientInfo.init();

		DefaultProxy<ContainerManagerInterface> containerManagerInfo = new DefaultProxy<ContainerManagerInterface>(
				ContainerManagerInterface.class,
				ContainerManagerQueueNames
						.getContainerManagerInterfaceQueryQueue(), 10000);

		containerManager = containerManagerInfo.init();
		
		DefaultProxy<AnalyzerInterface> analyzerInfo = new DefaultProxy<AnalyzerInterface>(
				AnalyzerInterface.class,
				AnalyzerQueueNames
						.getAnalyzerInterfaceQueue(), 5000);

		analyzer = analyzerInfo.init();
		
		System.out.println("InformationBroker, ContainerManager, Analyzer activated successfully!");
		
	}
	public static void setInformationBrokerHandler(
			InformationBrokerInterface persistenceHandler) {
		InterfaceFactory.broker = persistenceHandler;
	}


	public static void setContainerManager(
			ContainerManagerInterface containerManager) {
		InterfaceFactory.containerManager = containerManager;
	}
	
	public static void setAnalyzer(
			AnalyzerInterface analyzer) {
		InterfaceFactory.analyzer = analyzer;
	}
	

	public static ContainerManagerInterface getContainerManager() {
		return InterfaceFactory.containerManager;
	}
	
	public static InformationBrokerInterface getInformationBroker() {
		return InterfaceFactory.broker;
	}
	
	public static AnalyzerInterface getAnalyzer() {
		return InterfaceFactory.analyzer;
	}

}
