package org.fortiss.smg.actuatorclient.apros.client;

import java.io.IOException;
import java.util.Calendar;

public class SocketRecovery implements Runnable, EventbusSocket{
	private long socketTryTimestamp = 0;
	private long socketRetryTimeout = 1000;
    private EventbusSocket socket;
    private MessageHandler errorHandler;
    private boolean running;
    private Thread forceConnectThread;
    
    public SocketRecovery(EventbusSocket socket, boolean autoConnect){
    	this.socket = socket;
    	running = true;
    	socket.setErrorHandler(new ErrorHandler());
    	if (autoConnect){    		    		
    		forceConnectThread = new Thread(this);
    		forceConnectThread.start();
    	}    
    }
    
    public void connect() throws IOException{    	
    	socket.connect();
    	running = true;    	
    }
    
    public void close(String reason){
    	running = false;
    	socket.close(reason);    	
    }
    
    public void refreshSocket(){    	    	
    	while(running){    		
    		if (Calendar.getInstance().getTimeInMillis() - socketTryTimestamp > socketRetryTimeout){
    			socketTryTimestamp = Calendar.getInstance().getTimeInMillis();
    			try{
    				connect();
    				break;
    			}catch(Exception ex){    				
    				if (errorHandler != null){    					
    					errorHandler.handle(ex.getMessage());
    				}
    			}
    		}
    		else{
    			try {
    			    Thread.sleep(socketRetryTimeout);
    			} catch(InterruptedException ex) {
    			    Thread.currentThread().interrupt();
    			}
    		}
    	}
    }
    
    private class ErrorHandler implements MessageHandler{
    	public ErrorHandler(){}
    	public void handle(String message){
    		refreshSocket();
    	}
    }

	public void run() {
		refreshSocket();		
	}
	
	public void setErrorHandler(MessageHandler handler) {
		socket.setErrorHandler(handler);		
	}

	public void publish(Object data) throws Exception {
		this.socket.publish(data);		
	}
}
