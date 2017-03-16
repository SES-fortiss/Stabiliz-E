package org.fortiss.smg.actuatorclient.apros.client.messaging.messages;
import org.fortiss.smg.actuatorclient.apros.client.messaging.encoder.JSONEncoder;

public class JSONSerializable
{
	public String toString() 
	{
		return JSONEncoder.getInstance().encode(this);		
	}
	
	public Object decode(String json)
	{		
		return JSONEncoder.getInstance().decode(json, this.getClass());
	}
}