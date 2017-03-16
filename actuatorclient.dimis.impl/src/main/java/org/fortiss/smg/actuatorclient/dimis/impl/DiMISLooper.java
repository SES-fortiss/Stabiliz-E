package org.fortiss.smg.actuatorclient.dimis.impl;

import java.net.Socket;
import java.util.concurrent.ScheduledExecutorService;

import org.fortiss.smg.actuatorclient.dimis.impl.message.auth.AuthRequest;
import org.fortiss.smg.actuatorclient.dimis.impl.message.read.ReadRequest;
import org.fortiss.smg.actuatorclient.dimis.impl.message.read.ReadResponse;
import org.fortiss.smg.actuatorclient.dimis.impl.object.Dimis;
import org.fortiss.smg.actuatorclient.dimis.impl.object.DimisMessage;
import org.fortiss.smg.actuatorclient.dimis.impl.socket.SocketServer;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonParser;


public class DiMISLooper implements Runnable {
	
	private static org.slf4j.Logger logger = LoggerFactory
			.getLogger(DiMISLooper.class);

	private ActuatorClientImpl impl;
	private JsonParser parser;
	private ScheduledExecutorService executor;
	private int pollFrequency;
	
	
	DiMISLooper(ActuatorClientImpl impl){
		this.impl = impl;
		this.parser = new JsonParser();
	}
	
	public DiMISLooper(DiMISLooper diMISLooper) {
		this.pollFrequency = 5000;
	}

	@Override
	public void run() {
		logger.debug("DiMIS Looper poll Meter");
		SocketServer server = impl.getServer();
		
	
		
		AuthRequest authRequest = impl.getAuthRequest();
		if (authRequest != null) {
			Dimis dimis = server.dimisList.element();
			if(dimis != null) {
				// configure and send read request message
				//Dimis dimis = new Dimis(authRequest.gatewayConfig.serialNumber);
				logger.debug("Adding readRequest to dimis request list");
				ReadRequest readRequest = new ReadRequest(
	                "R", 
	                authRequest.gatewayConfig.protocolVersion, 
	                authRequest.gatewayConfig.partNumber, 
	                authRequest.gatewayConfig.serialNumber);
				logger.debug("readRequest"+ readRequest.toString());
				dimis.requestList.add(readRequest);
				logger.debug("Added");	
	     
		} else {
			logger.debug("dimis == null");
		}
		} else {
			logger.debug("authrequest == null");
		}
		// test - debug
//		String s = "{\"opt\":\"R\",\"GTW\":{\"protV\":\"001\",\"PN\":\"DA-0001\",\"SN\":\"161001121401-0001\"},\"devs\":[{\"PN\":\"DA-0001\",\"SN\":\"161001121401-0001\",\"dataID\":[1,2,3,4,5,6,7,8],\"data\":[228.100000,228.100000,228.100000,228.100000,228.100000,228.100000,228.100000,228.100000]}]}";
//		Configs.buildConfigs(new String[] { "1", "2", "3" });
//		ReadResponse data = Configs.gson.fromJson(s, ReadResponse.class);
//		Dimis dimis1 = new Dimis("161001121401-0001");
//		server.messageList.add(new DimisMessage(dimis1, data));
		
	}
	public int getPollFrequency() {
		return this.pollFrequency;
	}
	
}
