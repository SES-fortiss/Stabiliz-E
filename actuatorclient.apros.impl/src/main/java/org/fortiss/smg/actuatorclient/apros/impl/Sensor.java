package org.fortiss.smg.actuatorclient.apros.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.UUID;

import org.fortiss.smg.actuatorclient.apros.client.OutputSocket;
import org.fortiss.smg.actuatorclient.apros.client.messaging.messages.Record;

/**
 * The Class Sensor.
 */
public class Sensor implements Runnable
{
	private OutputSocket socket;
	private boolean running = false;
	private Thread me;
	private String uuid = UUID.randomUUID().toString();
	
	/**
	 * Instantiates a new sensor.
	 *
	 * @param ip the ip
	 * @param port the port
	 */
	
	public Sensor(String url, String username, String password, String topic)
	{
		this.socket = new OutputSocket(url, username, password, topic);		
		me = new Thread(this);
	}
	
	/**
	 * Sense.
	 *
	 * @throws Exception the exception
	 */
	public void sense() throws Exception
	{
		Record record = new Record();
		//record.header.uuid = this.uuid;
		//record.header.type = "sensor";
		//record.header.timestamp = Calendar.getInstance().getTimeInMillis();
		//record.header.duration = 0;
		//record.header.setSrc("Raspberry");

		try
		{
			this.socket.publish(record);
		}catch(Exception ex)
		{
			System.out.println("Exception in publishing message: " +ex.getMessage());
		}
	}
	
	/**
	 * Start.
	 */
	public void start()
	{
		me.start();
	}
	
	/**
	 * Stop.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void stop() throws InterruptedException
	{
		running = false;
		me.interrupt();
		me.join();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() 
	{
		running = true;		
		while (running)
		{			
			try 
			{				
				sense();
			} catch (Exception e1) 
			{			
				System.out.println("Exception: " + e1);
			}			
			try {				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			
		}
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	public static void main(String[] args) throws IOException, InterruptedException 
	{		
		//String url = "localhost:10000";
		String url = "130.188.225.233:10000";
		Sensor sensor = new Sensor(url, "stabilize", "stabilize", "stabilize");
		sensor.start();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Press enter to exit");        
        try {
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        sensor.stop();
        
	}
}
