/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dimis.impl.socket;

import java.util.concurrent.LinkedBlockingQueue;

import org.fortiss.smg.actuatorclient.dimis.impl.ActuatorClientImpl;
import org.fortiss.smg.actuatorclient.dimis.impl.Methods;
import org.fortiss.smg.actuatorclient.dimis.impl.message.config.ConfigResponse;
import org.fortiss.smg.actuatorclient.dimis.impl.message.read.ReadConfigResponse;
import org.fortiss.smg.actuatorclient.dimis.impl.message.read.ReadResponse;
import org.fortiss.smg.actuatorclient.dimis.impl.message.write.WriteResponse;
import org.fortiss.smg.actuatorclient.dimis.impl.object.DimisMessage;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;

/**
 * Massage Handler
 * @author hugo.pereira
 */
@DisallowConcurrentExecution
public class MessageHandlerJob implements InterruptableJob {
    public static final String MESSAGE_LIST = "MESSAGE_LIST";

	public static final String IMPL = "IMPL";

    LinkedBlockingQueue<DimisMessage> messageList = null;
    ActuatorClientImpl impl = null;
    
    boolean canRun;
    JobKey jobKey;

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
        messageList = (LinkedBlockingQueue<DimisMessage>) jobData.get(MESSAGE_LIST);
        impl = (ActuatorClientImpl) jobData.get(IMPL);
        
        canRun = true;
        while (canRun) {
            try {
                // wait for a new message
                DimisMessage dimisMessage = messageList.take();
                if (dimisMessage.message instanceof ReadResponse) {
                    // handle read response message
                    ReadResponse response = ((ReadResponse) dimisMessage.message);
//                    Methods.PrintLine("Read Response: " + readResponse.toJson());
                    Methods.PrintLine("Read Response: " + response.toJson());
                    impl.createEvents(response.deviceList);
//                    Methods.PrintLine("Read Response: " + response.deviceList.get(0).printData());
                } else if (dimisMessage.message instanceof WriteResponse) {
                    // handle write response message
                    WriteResponse response = ((WriteResponse) dimisMessage.message);
                    Methods.PrintLine("Write Response: " + response.toJson());
                } else if (dimisMessage.message instanceof ConfigResponse) {
                    // handle config response message
                    ConfigResponse response = ((ConfigResponse) dimisMessage.message);
                    Methods.PrintLine("Config Response: " + response.toJson());
                } else if (dimisMessage.message instanceof ReadConfigResponse) {
                    // handle read config response message
                    ReadConfigResponse response = ((ReadConfigResponse) dimisMessage.message);
                    Methods.PrintLine("Read config Response: " + response.toJson());
                }
            } catch (Exception ex) {
                canRun = false;
                Methods.PrintLine("Error in message parser: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        
        Methods.PrintLine("Stopping job: " + jobKey.toString());

    }

//	public void parseResponse(ReadResponse response) {
//		
//		List<Device> devices = new ArrayList<Device>();
//		String jsonString = response.toJson();
//		JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
//		JsonArray devs = jsonObject.get("devs").getAsJsonArray();
//		for (JsonElement dev : devs) {
//			if (dev.isJsonObject()) {
//				JsonObject root = devs.getAsJsonObject();
//				String pn = root.get("PN").getAsString();
//				String sn = root.get("SN").getAsString();
//				JsonArray dataID = root.get("dataID").getAsJsonArray();
//				JsonArray dataJson = root.get("data").getAsJsonArray();
//				// parse dataID
//				ArrayList<DataIdentifier> dataIDList = new ArrayList<DataIdentifier>();
//				for (JsonElement e : dataID) {
//					DataIdentifier dataIdentifier = new DataIdentifierDeserializer().deserialize(e, null, null);
//					dataIDList.add(dataIdentifier);
//				}
//				// parse data
//				ArrayList<Double> data = new ArrayList<Double>();
//				for (JsonElement e : dataJson) {
//					Double dataPoint = e.getAsDouble();
//					data.add(dataPoint);
//				}
//				// create new device and add to device list
//				devices.add(new Device(pn, sn, dataIDList, data));
//			}
//		}
//		
//		impl.createEvents(devices);
//	}
}
