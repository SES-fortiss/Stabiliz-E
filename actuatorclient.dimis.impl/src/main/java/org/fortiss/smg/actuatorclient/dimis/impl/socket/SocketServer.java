/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dimis.impl.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.fortiss.smg.actuatorclient.dimis.impl.ActuatorClientImpl;
import org.fortiss.smg.actuatorclient.dimis.impl.Methods;
import org.fortiss.smg.actuatorclient.dimis.impl.object.Dimis;
import org.fortiss.smg.actuatorclient.dimis.impl.object.DimisMessage;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.LoggerFactory;

/**
 * Socket Server Handler
 * @author hugo.pereira
 */
public class SocketServer {
    public static int error = 1;
    private static org.slf4j.Logger logger = LoggerFactory
			.getLogger(SocketServer.class);
    private Scheduler sched;
    private SocketServerJobController controller;
    public LinkedBlockingQueue<Dimis> dimisList;
    public LinkedBlockingQueue<DimisMessage> messageList;
    private ActuatorClientImpl impl;
    

    /**
     * Constructor
     * @param dimisList
     * @param messageList
     * @throws SchedulerException 
     */
    public SocketServer(ActuatorClientImpl actuatorClientImpl, LinkedBlockingQueue<Dimis> dimisList, LinkedBlockingQueue<DimisMessage> messageList) throws SchedulerException {
        
    	this.impl = actuatorClientImpl;
    	
        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        sched = sf.getScheduler(); 
        
        // All of the jobs have been added to the scheduler, but none of the jobs
        // will run until the scheduler has been started
        sched.start();
        
        this.dimisList = dimisList;
        this.messageList = messageList;
        this.controller = new SocketServerJobController(sched, dimisList, messageList, impl);
        
        new MessageHandlerJobController(sched, messageList, impl).startMessageHandlerJob();
    }

    /**
     * Starts the socket server
     */
    public void startListening() {
        while (true) {   
        	logger.debug("listening");
            ServerSocket ssock = null;
            try {
                // configure socket server
		int portNumber = Integer.parseInt(impl.getPort());
            	//TODO check if int
                ssock = new ServerSocket(portNumber);
                ssock.setSoTimeout(60000);
                logger.debug("Listening");
                
                while (true) {
                    // start listening
                    final Socket sock = ssock.accept();
                    
                    // launch a new thread for each connection
                    //TODO Change to single connection only !
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                logger.debug("Connected");
                                // starts the client-server communication
                                controller.startSocketServerJob(sock);
                                logger.debug("Connected? " + sock.isConnected());
                            } catch (Exception ex) {
                                Methods.PrintLine("Error " + SocketServer.error++ + ": " + ex.getMessage());
                                logger.debug("Error " + SocketServer.error++ + ": " + ex.getMessage());
                            }
                        }
                    }).start();
                }
            } catch (Exception ex) {
                // restart socket server
                Methods.PrintLine("Error " + SocketServer.error++ + ": " + ex.getMessage());
                logger.debug("Error " + SocketServer.error++ + ": " + ex.getMessage());
                if (ssock != null) {
                    try {
                        ssock.close();
                    } catch (IOException ex1) {
                        Methods.PrintLine("Error: " + ex1.getMessage());
                        logger.debug("Error: " + ex1.getMessage());
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex1) {
                    Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } 
        }
    }
    
    public void sendMessage(Dimis dimis) {
    	
    	dimisList.add(dimis);
    }
    
    
}
