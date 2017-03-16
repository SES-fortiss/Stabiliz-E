package org.fortiss.smg.actuatorclient.apros.client;

import java.io.IOException;
import java.util.UUID;

import org.fortiss.smg.actuatorclient.apros.client.messaging.encoder.JSONEncoder;

//public class InputStreamSocket<T> implements MessageHandler, InputEventbusSocket
public class InputStreamSocket implements MessageHandler, EventbusSocket
{
	private String url;
	private String username;
	private String password;
	private String topic;
	private Class<?> type;
	private String uuid;
	private WebSocket socket;
	private InputStreamMessageHandler messageHandler;
    private MessageHandler errorHandler;
    private MessageHandler openedHandler;
	
	public InputStreamSocket(String url, String username, String password, String topic, Class<?> type){
		this.url = url;
		this.username = username;
		this.password = password;
		this.topic = topic;
		this.type = type;
		this.socket = makeSocket();
		this.messageHandler = null;
		this.errorHandler = null;
		this.openedHandler = null;
		this.uuid = UUID.randomUUID().toString();
	}
	
	private WebSocket makeSocket(){
		WebSocket socket = new WebSocket("subscribe", this.url, this.username, this.password, this.topic, uuid);
		socket.setMessageHandler(this);
		return socket;
	}
	
	public void setMessageHandler(InputStreamMessageHandler messageHandler) {
		this.messageHandler = messageHandler;
		this.socket.setMessageHandler(this);
	}
    
    public void setOpenedHandler(MessageHandler handler) {    	
        this.openedHandler = handler;
        this.socket.setOpenedHandler(this.openedHandler);
    }
    
    public void setErrorHandler(MessageHandler handler) {    	
        this.errorHandler = handler;
        this.socket.setErrorHandler(this.errorHandler);
    }
    
    public InputStreamMessageHandler getMessageHandler() {
    	return this.messageHandler;
    }
    
    public MessageHandler getOpenedHandler() {    	
        return this.openedHandler;
    }
    
    public MessageHandler getErrorHandler() {    	
        return this.errorHandler;
    }
    

	public void connect() throws IOException{
        if (this.socket.isOpen()) {this.socket.close("Reconnecting");}        
        this.socket.connect();              
	}
	
	public void close(String reason){
		try{
			this.socket.close(reason);	
		}catch(Exception e){
			if (this.errorHandler != null) {this.errorHandler.handle(e.getMessage());}
		}				
	}

	        
	public void handle(String message) {		
		if (messageHandler != null){			
			messageHandler.handleMessage(JSONEncoder.getInstance().decode(message, type));												
		}		
	}
	
	public static class InputStreamMessageHandler{	
		private Class<?> type;
		public InputStreamMessageHandler(Class<?> type){
			this.type = type;
		}
		public Class<?> getType(){return type;}
		public void handleMessage(Object message){}		
	}

	@Override
	public void publish(Object data) throws Exception {		
	}
}
