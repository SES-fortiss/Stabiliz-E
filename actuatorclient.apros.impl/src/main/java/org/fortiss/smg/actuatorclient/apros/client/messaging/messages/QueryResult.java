package org.fortiss.smg.actuatorclient.apros.client.messaging.messages;


import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

import org.fortiss.smg.actuatorclient.apros.client.messaging.encoder.JSONEncoder;

public class QueryResult extends JSONSerializable
{
	@JsonProperty("_bus")
	protected Bus bus;


	protected ArrayList<Object> values;
	

	public ArrayList<Object> getValues() 
	{
		return values;
	}
	public void setValues(ArrayList<Object> values) 
	{
		this.values = values;
	}	
	
	public <T> ArrayList<T> getValues(Class<T> type) 
	{
		ArrayList<T> values = new ArrayList<T>();
		for (Object item : this.values)
		{
			values.add(JSONEncoder.getInstance().objectMapper.convertValue(item, type));	
		}		
		return values;
	}
	public Bus getBus() {
		return bus;
	}
	public void setBus(Bus bus) {
		this.bus = bus;
	}
}
