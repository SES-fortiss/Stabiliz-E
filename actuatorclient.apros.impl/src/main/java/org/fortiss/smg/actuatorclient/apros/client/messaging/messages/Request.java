package org.fortiss.smg.actuatorclient.apros.client.messaging.messages;
import org.fortiss.smg.actuatorclient.apros.client.messaging.encoder.JSONEncoder;

public class Request extends JSONSerializable
{
	protected String token;
	protected Object request;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Object getRequest() {
		return request;
	}
	public void setRequest(Object request) {
		this.request = request;
	}
	
	public <T> T getRequest(Class<T> type) 
	{		
		return JSONEncoder.getInstance().objectMapper.convertValue(request, type);			
	}
}
