package org.fortiss.smg.actuatorclient.apros.client;

import java.io.IOException;

public class OutputStreamSocket implements EventbusSocket
{
	private String url;
	private String username;
	private String password;
	private String topic;
	private WebSocket socket;
    private MessageHandler errorHandler;
    private MessageHandler openedHandler;
	
	public OutputStreamSocket(String url, String username, String password, String topic){
		this.url = url;
		this.username = username;
		this.password = password;
		this.topic = topic;
		this.errorHandler = null;
		this.openedHandler = null;
		this.socket = makeSocket();
	}
	
	private WebSocket makeSocket(){		
		return new WebSocket("publish", this.url, this.username, this.password, this.topic, null);
	}
	
	public void close(String reason){
		try{
			this.socket.close(reason);
		}catch(Exception e){
			if (this.errorHandler != null) {this.errorHandler.handle(e.getMessage());}
		}
	}
	
	public void connect() throws IOException {
        if (this.socket.isOpen()) {this.socket.close("Reconnecting");}        
        this.socket.connect();        
	}

	public void publish(Object data) throws Exception{
		this.socket.publish(data.toString());									
	}
	
	public void setErrorHandler(MessageHandler handler) {    	
        this.errorHandler = handler;
        this.socket.setErrorHandler(this.errorHandler);
    }
    
    public MessageHandler getOpenedHandler() {    	
        return this.openedHandler;
    }
    
    public MessageHandler getErrorHandler() {    	
        return this.errorHandler;
    }
    
}
