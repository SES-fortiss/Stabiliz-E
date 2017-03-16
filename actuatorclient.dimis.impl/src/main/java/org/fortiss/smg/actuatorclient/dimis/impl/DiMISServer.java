package org.fortiss.smg.actuatorclient.dimis.impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.LoggerFactory;

public class DiMISServer {

	private static org.slf4j.Logger logger = LoggerFactory
			.getLogger(DiMISServer.class);

	private static int dimisPort = 8081;

	private static ActuatorClientImpl impl;

	public DiMISServer(ActuatorClientImpl actuatorClientImpl) {
		this.setImpl(actuatorClientImpl);

	}

	public static void init() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(dimisPort);
			
		} catch (IOException e) {
			logger.error("Error: bind to port: " + dimisPort + " failed "
					+ e.getMessage());

		}

		// loop for incomming connections
		while (true) {
			try {
				logger.debug("DiMISServerSocket - accepting");
				Socket clientSocket = serverSocket.accept();

				// Start Thread when connection is accepted
				logger.debug("DiMISServerSocket - accept done");
//				Thread threadHandler = new Thread(new DiMISLooper(getImpl(),
//						clientSocket));
//				threadHandler.start();

				logger.info("DiMISServerSocket - DiMISLooper initiated - waiting for more clients");
			} catch (IOException e) {
				logger.error("'accept' on Port " + dimisPort + " failed");

			}

		}
	}

	public static ActuatorClientImpl getImpl() {
		return impl;
	}

	public void setImpl(ActuatorClientImpl impl) {
		DiMISServer.impl = impl;
	}

}
