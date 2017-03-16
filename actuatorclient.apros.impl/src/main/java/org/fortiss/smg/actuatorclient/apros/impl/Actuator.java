package org.fortiss.smg.actuatorclient.apros.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fortiss.smg.actuatorclient.apros.client.InputSocket;
import org.fortiss.smg.actuatorclient.apros.client.MessageHandler;
import org.fortiss.smg.actuatorclient.apros.client.messaging.messages.Record;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class Actuator.
 */

public class Actuator extends InputSocket<Record>
{
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(Actuator.class);
	
//	private String src = "fortissSMG";
//	private String type = "actuator";
//	private String command = "subscribe";
//	private String topic = "apros";
//	private String service = "stabilize";
//	private ArrayList<Object> sub_list = new ArrayList<Object>(Arrays.asList("TYPES!S!SET_POINT!LMPHV_A_2.SP_VALUE"));
	
	public Actuator(String url, String username, String password, String topic)
	{	
		super(url, username, password, topic, Record.class);
		
	}

	/* (non-Javadoc)
	 * @see hiconcept.client.Subscriber#success(java.util.List)
	 */
	
	public void success(List<Record> data)	
	{	
		//System.out.println(data);
		
		System.out.println("Actuator received " + data.size() + " items");		
		for (Record item : data)
		{
			System.out.println("	" + item);	
		}
		
	}
	
	/* (non-Javadoc)
	 * @see hiconcept.client.Subscriber#error(java.lang.String)
	 */
	public void error(String err)
	{
		System.out.println("Actuator encountered an error: " + err);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Act.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void connect()
	{
		logger.debug("inside connect method");
		super.connect();		
	}
	
	public void close(String reason)
	{
		super.close(reason);
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException 
	{	
		String url = "130.188.225.233:10000";
		//String url = "localhost:10000";
		//String url = "127.0.0.1:10000";
		String username = "stabilize";
		String password = "stabilize";
		String topic = "stabilize";
		
		Actuator actuator = new Actuator(url, username, password, topic);	
		logger.info("inside Actuator class");
		actuator.connect();
		logger.info("Actuator connected");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Press enter to exit");        
        try {
            reader.readLine();
        } catch (IOException e) 
        {
            System.out.println(e);
        } 
        actuator.close("End of execution");
	}

	@Override
	public void setErrorHandler(MessageHandler handler) {
		// TODO Auto-generated method stub
		
	}
}
