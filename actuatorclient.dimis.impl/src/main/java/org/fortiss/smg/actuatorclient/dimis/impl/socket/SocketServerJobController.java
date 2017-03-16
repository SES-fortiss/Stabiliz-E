/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dimis.impl.socket;

import org.fortiss.smg.actuatorclient.dimis.impl.object.Dimis;
import org.fortiss.smg.actuatorclient.dimis.impl.ActuatorClientImpl;
import org.fortiss.smg.actuatorclient.dimis.impl.Configs;
import org.fortiss.smg.actuatorclient.dimis.impl.object.DimisMessage;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.JobBuilder.newJob;

/**
 * Handles client-server communication start
 * @author hugo.pereira
 */
public class SocketServerJobController {
    
    private Scheduler sched;
    private LinkedBlockingQueue<Dimis> dimisList;
    private LinkedBlockingQueue<DimisMessage> messageList;
    public String group;
	private ActuatorClientImpl impl;

    /**
     * Constructor
     * @param sched
     * @param dimisList 
     * @param messageList 
     */
    public SocketServerJobController(Scheduler sched, LinkedBlockingQueue<Dimis> dimisList, LinkedBlockingQueue<DimisMessage> messageList, ActuatorClientImpl impl) {
        this.sched = sched;
        this.dimisList = dimisList;
        this.messageList = messageList;
        group = "dimis.processor";
        this.impl = impl;
    }
    
    /**
     * Start a new client-server comunication job
     * @param socket
     * @throws SchedulerException
     * @throws SocketException 
     */
    public void startSocketServerJob(Socket socket) throws SchedulerException, SocketException {       
          
        // TODO: Implement Handshake
        
        // forces the socket to close if the is no communication
        socket.setSoTimeout(Configs.pooler_timer * 10);
        
        // TODO: use PN as ID
        String clientIP = socket.getRemoteSocketAddress().toString().trim();
        
        JobDetail job = newJob(SocketServerJob.class).withIdentity(clientIP, group).build();
            
        // start now, run once
        SimpleTrigger trigger = newTrigger()
                .withIdentity(clientIP, group + ".trigger")
                .startNow()
                .withSchedule(simpleSchedule())
                .build();

        // pass initialization parameters into the job
        job.getJobDataMap().put(SocketServerJob.DIMIS_LIST, dimisList);
        job.getJobDataMap().put(SocketServerJob.MESSAGE_LIST, messageList);
        job.getJobDataMap().put(SocketServerJob.SOCKET, socket);
        job.getJobDataMap().put(SocketServerJob.POOLER_TIMER, Configs.pooler_timer);
        job.getJobDataMap().put(SocketServerJob.GSON, Configs.gson);
        job.getJobDataMap().put(SocketServerJob.IMPL, impl);
        
        // Add job to scheduler
        sched.scheduleJob(job, trigger);
    }
    
}
