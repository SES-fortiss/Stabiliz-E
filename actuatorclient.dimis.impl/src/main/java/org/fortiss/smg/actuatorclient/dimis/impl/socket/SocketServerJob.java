/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dimis.impl.socket;

import org.fortiss.smg.actuatorclient.dimis.impl.object.Dimis;
import org.apache.log4j.Logger;
import org.fortiss.smg.actuatorclient.dimis.impl.ActuatorClientActivator;
import org.fortiss.smg.actuatorclient.dimis.impl.ActuatorClientImpl;
import org.fortiss.smg.actuatorclient.dimis.impl.Methods;
import org.fortiss.smg.actuatorclient.dimis.impl.message.ADE;
import org.fortiss.smg.actuatorclient.dimis.impl.message.GatewayConfig;
import org.fortiss.smg.actuatorclient.dimis.impl.message.IpConfig;
import org.fortiss.smg.actuatorclient.dimis.impl.message.ServerConfig;
import org.fortiss.smg.actuatorclient.dimis.impl.message.auth.AuthRequest;
import org.fortiss.smg.actuatorclient.dimis.impl.message.config.ConfigRequest;
import org.fortiss.smg.actuatorclient.dimis.impl.message.config.ConfigResponse;
import org.fortiss.smg.actuatorclient.dimis.impl.message.read.ReadConfigRequest;
import org.fortiss.smg.actuatorclient.dimis.impl.message.read.ReadConfigResponse;
import org.fortiss.smg.actuatorclient.dimis.impl.message.read.ReadRequest;
import org.fortiss.smg.actuatorclient.dimis.impl.message.read.ReadResponse;
import org.fortiss.smg.actuatorclient.dimis.impl.message.write.WriteRequest;
import org.fortiss.smg.actuatorclient.dimis.impl.message.write.WriteResponse;
import org.fortiss.smg.actuatorclient.dimis.impl.object.DimisMessage;





import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hugo.pereira
 */

@DisallowConcurrentExecution
public class SocketServerJob implements InterruptableJob {
	
	private static org.slf4j.Logger logger = LoggerFactory
			.getLogger(SocketServerJob.class);
	
	
	public static final String DIMIS_LIST = "DIMIS_LIST";
    public static final String MESSAGE_LIST = "MESSAGE_LIST";
    public static final String SOCKET = "SOCKET";
    public static final String POOLER_TIMER = "POOLER_TIMER";
    public static final String GSON = "GSON";
	public static final String IMPL = "IMPL";

    LinkedBlockingQueue<Dimis> dimisList = null;
    LinkedBlockingQueue<DimisMessage> messageList = null;
    Socket socket = null;
    int pooler_timer = 1000;
    
    ActuatorClientImpl impl;
    
    long last_time;
    Gson gson;
    BufferedReader is;
    PrintWriter os;
    
    ReadRequest requestMessage;

    boolean canRun;
    JobKey jobKey;
    Dimis dimis;

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        Methods.PrintLine("Interrupting job: " + jobKey.toString());
        canRun = false;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        // This job simply prints out its job name and the
        // date and time that it is running
        jobKey = context.getJobDetail().getKey();
        Methods.PrintLine("Starting job: " + jobKey.toString());

        // Grab and print passed parameters
        JobDataMap jobData = context.getJobDetail().getJobDataMap();

        // init job variables
        dimisList = (LinkedBlockingQueue<Dimis>) jobData.get(DIMIS_LIST);
        messageList = (LinkedBlockingQueue<DimisMessage>) jobData.get(MESSAGE_LIST);
        socket = (Socket) jobData.get(SOCKET);
        logger.debug("socket: " + socket.getPort() + " " + socket.isConnected());
        pooler_timer = (Integer) jobData.get(POOLER_TIMER);
        gson = (Gson) jobData.get(GSON);
        impl = (ActuatorClientImpl) jobData.get(IMPL);
        
        last_time = System.currentTimeMillis();
        
        // Temporary ID
        dimis = new Dimis(socket.getRemoteSocketAddress().toString().trim());
        dimisList.add(dimis);
        
        try {
            // get socket streams
            is = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            os = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));             
            
            // get authentication message
            String line = is.readLine();
            logger.debug("Auth message " + line);
//            System.out.println(line);
            // parse auth message
            //AuthRequest authRequest = gson.fromJson(line, AuthRequest.class);
            
           // logger.debug("Auth message (Object content:" + authRequest.toString() + ")");
            
            // validate auth message
            //TODO: workaround for auth
            if (true) {
            //if (authRequest.validate()) {
                Methods.PrintLine("Device " + dimis.id + " Authenticated");
                logger.debug("Device " + dimis.id + " Authenticated");
                canRun = true;
                             
                // save authRequest at impl to do read request
                
                //TODO: workaround
                AuthRequest authRequest = new AuthRequest(null, null, new GatewayConfig("", null, null, null, "001", "DA-0001", "161001121401-0001"));
               
                
                impl.setAuthRequest(authRequest);
//                dimisList.forEach((device) {
//                    if (device.id.equals(authRequest.gatewayConfig.serialNumber)) {
//                        canRun = false;
//                        Methods.PrintLine("Device " + device.id + " already registered");
//                    }
//                });
                // if no device is registered with this SN, register it
                
                //TODO this code needs to be added to the Looper for frequent requests
                
                //we do not need it here:
                /*if (canRun) {
                    dimis.id = authRequest.gatewayConfig.serialNumber;
                    // configure read message
                    requestMessage = new ReadRequest(
                            "R", 
                            authRequest.gatewayConfig.protocolVersion, 
                            authRequest.gatewayConfig.partNumber, 
                            authRequest.gatewayConfig.serialNumber);
                }
                */
                
            } else {
                canRun = false;
                Methods.PrintLine("Error during authentication for device " + dimis.id);
                logger.debug("Error during authentication for device " + dimis.id);
            }
            
        } catch (IOException ex) {
            canRun = false;
            Methods.PrintLine("Error at stream initialization: " + ex.getMessage());
            logger.debug("Error at stream initialization: " + ex.getMessage());
        }
        
        while (canRun) {
            try {
                // wait for high priority messages
                Object message = dimis.requestList.poll(pooler_timer, TimeUnit.MILLISECONDS);
                if (message != null) {
                    if (message instanceof WriteRequest) {
                        // send write request message
                        sendWriteRequest(((WriteRequest) message).toJson());
                    } else if (message instanceof ConfigRequest) {
                        // send config request message
                        sendConfigurationRequest(((ConfigRequest) message).toJson());
                    } else if (message instanceof ReadConfigRequest) {
                        // send read config request message
                        sendReadConfigurationRequest(((ReadConfigRequest) message).toJson());
                    } else if (message instanceof ReadRequest) {
                    	logger.debug("Sending Read Request");
                    	sendReadRequest(((ReadRequest) message).toJson());
                    }
                    
                }
                //sendReadRequest();
                
            } catch (Exception ex) {
                canRun = false;
                Methods.PrintLine("Error with " + dimis.id + ": " + ex.getMessage());
                logger.debug("Error with " + dimis.id + ": " + ex.getMessage());
            }
        }

        dimisList.remove(dimis);
        Methods.PrintLine("Stopping job: " + jobKey.toString());
        logger.debug("Stopping job: " + jobKey.toString());
    }
    
    /** 
     * Sends a read request and handles the response 
     * @throws java.lang.InterruptedException 
     * @throws java.io.IOException 
     */
    public void sendReadRequest() throws InterruptedException, IOException {
        // send message
        os.print(requestMessage);
        os.flush(); 

        // request response  
        String data = is.readLine();  

        // print respose 
        if(data != null) {
            logger.debug(dimis.id + "-" + data.length() + ":" + (System.currentTimeMillis() - last_time));
//            System.out.println(data);
            last_time = System.currentTimeMillis();
            messageList.add(new DimisMessage(dimis, gson.fromJson(data, ReadResponse.class)));
        } else {
            canRun = false; 
            Methods.PrintLine("Error " + SocketServer.error++ + ": Read Response message is empty or null");
            logger.debug("Error " + SocketServer.error++ + ": Read Response message is empty or null");
        }
    }
    
    
    public void sendReadRequest(String message) throws InterruptedException, IOException {
        // send message
        os.print(message);
        os.flush(); 

        // request response  
        String data = is.readLine();  

        // print respose 
        if(data != null) {
            logger.debug(dimis.id + "-" + data.length() + ":" + (System.currentTimeMillis() - last_time));
//            System.out.println(data);
            last_time = System.currentTimeMillis();
            logger.debug("Adding readResponse to messageList");
            messageList.add(new DimisMessage(dimis, gson.fromJson(data, ReadResponse.class)));
            logger.debug("Added to messageList");
        } else {
            canRun = false; 
            Methods.PrintLine("Error " + SocketServer.error++ + ": Read Response message is empty or null");
            logger.debug("Error " + SocketServer.error++ + ": Read Response message is empty or null");
        }
    }
    
    
    
    /** 
     * Sends a write request and handles the response
     * @param message
     * @throws java.lang.InterruptedException 
     * @throws java.io.IOException 
     */
    public void sendWriteRequest(String message) throws InterruptedException, IOException {

        // send message
        os.print(message);
        os.flush(); 

        // request response  
        String data = is.readLine();  

        // print respose 
        if(data != null) {
           logger.debug(dimis.id + "-" + data.length() + ":" + (System.currentTimeMillis() - last_time));
//            System.out.println(data);
            last_time = System.currentTimeMillis();
            messageList.add(new DimisMessage(dimis, gson.fromJson(data, WriteResponse.class)));
        } else {
            canRun = false; 
            Methods.PrintLine("Error " + SocketServer.error++ + ": Write Response message is empty or null");
            logger.debug("Error " + SocketServer.error++ + ": Write Response message is empty or null");
        }
    }

    /**
     * Sends a configuration request and handles the response
     * @param message
     * @throws java.lang.InterruptedException 
     * @throws java.io.IOException 
     */
    public void sendConfigurationRequest(String message) throws InterruptedException, IOException {
        
        // send message
        os.print(message);
        os.flush(); 

        // request response  
        String data = is.readLine();  

        // print respose 
        if(data != null) {
            logger.debug(dimis.id + "-" + data.length() + ":" + (System.currentTimeMillis() - last_time));
//            System.out.println(data);
            last_time = System.currentTimeMillis();
            messageList.add(new DimisMessage(dimis, gson.fromJson(data, ConfigResponse.class)));
        } else {
            canRun = false; 
            Methods.PrintLine("Error " + SocketServer.error++ + ": Config Response message is empty or null");
            logger.debug("Error " + SocketServer.error++ + ": Config Response message is empty or null");
        }
    }
    
    
    /** 
     * Sends a read config requesnt and handles the response 
     * @throws java.lang.InterruptedException 
     * @throws java.io.IOException 
     */
    public void sendReadConfigurationRequest(String message) throws InterruptedException, IOException {
        // send message
        os.print(message);
        os.flush(); 

        // request response  
        String data = is.readLine();  

        // print respose 
        if(data != null) {
            logger.debug(dimis.id + "-" + data.length() + ":" + (System.currentTimeMillis() - last_time));
//            System.out.println(data);
            last_time = System.currentTimeMillis();
            messageList.add(new DimisMessage(dimis, gson.fromJson(data, ReadConfigResponse.class)));
            //TODO: simulate client behaviour read response
        } else {
            canRun = false; 
            Methods.PrintLine("Error " + SocketServer.error++ + ": Read Response message is empty or null");
            logger.debug("Error " + SocketServer.error++ + ": Read Response message is empty or null");
        }
    }
}
