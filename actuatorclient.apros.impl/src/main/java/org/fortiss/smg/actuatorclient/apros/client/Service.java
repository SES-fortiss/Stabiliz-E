package org.fortiss.smg.actuatorclient.apros.client;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.ws.http.HTTPException;

import org.fortiss.smg.actuatorclient.apros.client.messaging.messages.Request;
import org.fortiss.smg.actuatorclient.apros.client.messaging.messages.Status;


/**
 * The Class Subscriber is a helper class to enable easy subscription policy.
 * HTTP long-polling is utilized as a subscription policy.
 *
 * @param <T> the generic type describing the subscribed POJO type.
 */

public abstract class Service<T extends Request> extends HTTPClient implements Runnable
{
	private boolean running = false;
	private String service = "service";
	private int timeout = 5;
	private Thread me;
	private String url;
	public Class<T> servedType;
	
	/**
	 * Instantiates a new subscriber.
	 *
	 * @param ip the ip address of the web-server
	 * @param port the port web-server is listening
	 * @param query the query which describes what is subscribed
	 * @param latest a parameter describing if always only the latest objects will be subscribed.
	 * 			That is, if set true, it is fetched only the objects that have newer timestamp than
	 * 			what was the timestamp of the last query. Set true if wanted to execute basic subscription policy.
	 * @param subscribedType the subscribed type
	 */
	
	public Service(String url, String username, String password, String service, Class<T> servedType)
	{
		super(username, password);	
		this.url = url + "/service/invoke";
		this.service = service;
		this.servedType = servedType;
		this.timeout = 5;
		me = new Thread(this);				
	}
	
	/**
	 * Instantiates a new subscriber.
	 *
	 * @param ip the ip address of the web-server
	 * @param port the port web-server is listening
	 * @param query the query which describes what is subscribed
	 * @param latest a parameter describing if always only the latest objects will be subscribed.
	 * 			That is, if set true, it is fetched only the objects that have newer timestamp than
	 * 			what was the timestamp of the last query. Set true if wanted to execute basic subscription policy.
	 * @param timeout the timeout (s) parameter for long-polling subscription. The longer the timeout, less unnecessary traffic.
	 * @param subscribedType the subscribed POJO type
	 */
	public Service(String url,String username, String password, String service, int timeout, Class<T> servedType)
	{
		super(username, password);
		this.url = url + "/service/invoke";
		this.timeout = timeout;
		this.service = service;
		this.servedType = servedType;
		me = new Thread(this);		
	}
	
	
	/**
	 * Start the subscription
	 */
	protected void start()
	{
		me.start();
	}
	
	protected void stop()
	{
		running = false;
	}
	
	/**
	 * Called when new data is available.
	 *
	 * @param data the data
	 */
	
	public abstract String success(T data);	
	
	/**
	 * Called when there has been an error in subscription.
	 *
	 * @param err the err
	 */
	
	public abstract void error(String err);

	
	/**
	 * Serve.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
     
    
	private void serve()
	{				
		Status status;
        String response = null;
        String token = null;
        HashMap<String, Object> fields = new HashMap<String, Object>();
        fields.put("service", service);        
        while (running)
        {        	      
        	try
        	{
	            if (token != null) fields.put("token", token);
	            else fields.remove("token");   
	            
	    		status = send(response, fields, url, timeout);	    		
	    		if (status.isStatus() && status.getValues().size() > 0)
	    		{			    		    			
	    			response = success(status.getValues(Request.class).get(0).getRequest(servedType));
	    			token = status.getValues(servedType).get(0).getToken();
	    		}
	    		if (status.isStatus() && status.getValues().size() == 0)
	    		{						
	    			token = null;
	    		}
	    		if (!status.isStatus())
	    		{
	    			error(status.getValues().get(0).toString());
	    		}
    		} catch (HTTPException e)
    		{
    			error(String.valueOf(e.getStatusCode()));
    		} 
        	catch(Exception ex)
        	{
        		error(ex.getMessage());
        	}
        }
        
					
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		running = true;	
		serve();	
	}
}
