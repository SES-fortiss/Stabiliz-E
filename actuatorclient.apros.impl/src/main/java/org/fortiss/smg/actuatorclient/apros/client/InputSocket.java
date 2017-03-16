package org.fortiss.smg.actuatorclient.apros.client;


import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.xml.ws.http.HTTPException;

import org.fortiss.smg.actuatorclient.apros.client.HTTPClient;
import org.fortiss.smg.actuatorclient.apros.client.messaging.messages.Bus;
import org.fortiss.smg.actuatorclient.apros.client.messaging.messages.Query;
import org.fortiss.smg.actuatorclient.apros.client.messaging.messages.QueryResult;
import org.fortiss.smg.actuatorclient.apros.client.messaging.messages.Status;
import org.fortiss.smg.actuatorclient.apros.impl.Actuator;
import org.slf4j.LoggerFactory;


/**
 * The Class Subscriber is a helper class to enable easy subscription policy.
 * HTTP long-polling is utilized as a subscription policy.
 *
 * @param <T> the generic type describing the subscribed POJO type.
 */

public abstract class InputSocket<T> implements Runnable, EventbusSocket
{
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(InputSocket.class);
	protected boolean running = false;
	private String url;
	private String topic = "input";
	private Query query;
	private int timeout = 5000;
	private int limit = 1000;
	private Thread me;
	private HTTPClient socket = null;
	public Class<T> subscribedType;
	
	/**
	 * Instantiates a new subscriber.
	 *
	 * @param ip the ip address of the web-server
	 * @param port the port web-server is listening
	 * @param subscribedType the subscribed type
	 */

	public InputSocket(String url, String username, String password, String topic, Class<T> subscribedType)	
	{
		this.socket = new HTTPClient(username, password);
		this.url = url;		
		this.topic = topic;
		this.timeout = 5000;
		this.limit = 1000;
		this.subscribedType = subscribedType;
		this.me = new Thread(this);	
	}
	
	public InputSocket(String url, String username, String password, String topic, int timeout, int limit, Class<T> subscribedType)	
	{
		this.socket = new HTTPClient(username, password);
		this.url = url;		
		this.topic = topic;
		this.timeout = timeout;
		this.limit = limit;
		this.subscribedType = subscribedType;
		this.me = new Thread(this);		
	}
	
	
	/**
	 * Start the subscription
	 */
	public void connect()
	{
		me.start();
	}
	
	public void close(String reason)
	{
		this.running = false;
	}
	
	/**
	 * Called when new data is available.
	 *
	 * @param data the data
	 */
	
	protected abstract void success(List<T> data);		
	
	/**
	 * Called when there has been an error in subscription.
	 *
	 * @param err the err
	 */
	
	protected abstract void error(String err);
	
	private QueryResult query(Query query, Class<T> type) throws Exception, IOException, HTTPException	
	{
		return query(query, this.topic, this.timeout, true, type);						
	}
	
	private QueryResult query(Query query, String topic, int timeout, boolean compress, Class<T> type) throws Exception, IOException, HTTPException	
	{		
		QueryResult queryResult;
		HashMap<String, Object> fields = new HashMap<String, Object>();		
		//fields.put("limit", this.limit);
		fields.put("'service'", "'stabilize'");
		//fields.put("'topic'", "'apros'");
		logger.info("Sending query: "+ query.toString());
		logger.info("Sending field" + fields);
		logger.info("Sending URL" + "https://" + url + "/service/invoke");
		
		Status result = this.socket.send(query.toString(), fields, "https://" + url + "/service/invoke", timeout, compress);
		
		logger.info("Result of Query:" + result);
		if (result.isStatus()){
			queryResult = new QueryResult();
			queryResult.setValues(result.getValues());
			Bus bus = new Bus();
			bus.setInsertId(result.getBus().getInsertId());
			logger.info("InsertID: "+result.getBus().getInsertId());
			queryResult.setBus(bus);
			return queryResult;
		}
		else{
			throw new Exception(result.getValues().get(0).toString());
		}	
	}
	
	/**
	 * Unsubscribe.
	 */
	
	public void unsubscribe()
	{
		running = false;
		me.interrupt();
		try 
		{
			me.join();
		} catch (InterruptedException e) {}
	}

	/**
	 * Subscribe.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	
	private void subscribe()
	{				
		this.running = true;	
		this.query = new Query();
		this.query.setOperation("measurement");
		this.query.setInsertId(Calendar.getInstance().getTimeInMillis() * 1000);		
		QueryResult queryResult;
		while(this.running)
		{			
			try
			{
				queryResult = this.query(this.query, this.subscribedType);
				if (queryResult.getValues().size() > 0)
				{
					success(queryResult.getValues(subscribedType));
					query.setOperation("$gt");
					query.setInsertId(queryResult.getBus().getInsertId());									
				}
			} catch (HTTPException e)
			{				
				error(String.valueOf(e.getStatusCode()));
			} catch (Exception e) 
			{		
				error(e.getMessage());
			}	
		}	
				
	}
	
	public void publish(Object data){
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{		
		subscribe();
	}
}
