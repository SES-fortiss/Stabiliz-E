package org.fortiss.smg.actuatorclient.apros.client;

import java.util.HashMap;

import javax.xml.ws.http.HTTPException;

import org.fortiss.smg.actuatorclient.apros.client.messaging.messages.Status;

public class OutputSocket implements EventbusSocket
{
	private String url;
	private String username;
	private String password;
	private String topic;
	private HTTPClient socket;
	
	public OutputSocket(String url, String username, String password, String topic){
		this.url = "https://" + url;
		this.username = username;
		this.password = password;
		this.topic = topic;
		this.socket = makeSocket();
	}
	
	private HTTPClient makeSocket(){
		return new HTTPClient(this.username, this.password);
	}
	
	public void close(String reason){
		return;
	}
	
	public void connect(){
		return;
	}

	public void publish(Object data) throws Exception{
		Status status;
		HashMap<String, Object> fields = new HashMap<String, Object>();
		fields.put("topic", this.topic);			
		try{
			status = this.socket.send(data.toString(), fields, url + "/pubsub/input");
			if (!status.isStatus()) throw new Exception(status.getValues().get(0).toString());
		}catch(HTTPException ex){
			throw new Exception(new Integer(ex.getStatusCode()).toString());
		}		
	}

	@Override
	public void setErrorHandler(MessageHandler handler) {
		// TODO Auto-generated method stub
		
	}
}
