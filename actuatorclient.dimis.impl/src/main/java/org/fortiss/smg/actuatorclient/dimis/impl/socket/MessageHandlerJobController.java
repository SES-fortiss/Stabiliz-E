/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dimis.impl.socket;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.concurrent.LinkedBlockingQueue;

import org.fortiss.smg.actuatorclient.dimis.impl.ActuatorClientImpl;
import org.fortiss.smg.actuatorclient.dimis.impl.object.DimisMessage;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;

/**
 *
 * @author hugo.pereira
 */
public class MessageHandlerJobController {
    
    private Scheduler sched;
    private LinkedBlockingQueue<DimisMessage> messageList;
    public String group;
	private ActuatorClientImpl impl;

    /**
     * Constructor
     * @param sched
     * @param messageList 
     * @param impl 
     */
    public MessageHandlerJobController(Scheduler sched, LinkedBlockingQueue<DimisMessage> messageList, ActuatorClientImpl impl) {
        this.sched = sched;
        this.messageList = messageList;
        group = "dimis.message.handler";
        this.impl = impl;
    }
    
    /**
     * Start a new Message Handler job.
     * @throws SchedulerException 
     */
    public void startMessageHandlerJob() throws SchedulerException {       
        
        JobDetail job = newJob(MessageHandlerJob.class).withIdentity("1", group).build();
            
        // start now, run forever
        SimpleTrigger trigger = newTrigger()
                .withIdentity("1", group + ".trigger")
                .startNow()
                .withSchedule(simpleSchedule().withIntervalInSeconds(1).repeatForever())
                .build();

        // pass initialization parameters into the job
        job.getJobDataMap().put(MessageHandlerJob.MESSAGE_LIST, messageList);
        job.getJobDataMap().put(MessageHandlerJob.IMPL, impl);
        
        // Add job to scheduler
        sched.scheduleJob(job, trigger);
    }
}
