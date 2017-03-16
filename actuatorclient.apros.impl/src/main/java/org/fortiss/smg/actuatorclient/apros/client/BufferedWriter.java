package org.fortiss.smg.actuatorclient.apros.client;

import java.io.IOException;

public class BufferedWriter extends Thread implements EventbusSocket
{
	private boolean running;
	private DataBuffer buffer;
	private EventbusSocket socket;
    private MessageHandler errorHandler;
    private MessageHandler openedHandler;
	
	public BufferedWriter(EventbusSocket socket, int bufferSize){
		this.socket = socket;
		this.buffer = new DataBuffer(bufferSize);
		this.running = false;
		this.errorHandler = null;
		this.openedHandler = null;
		start();
	}
	
	public BufferedWriter(EventbusSocket socket){
		this.socket = socket;
		this.buffer = new DataBuffer(1000);
		this.running = false;
		this.errorHandler = null;
		this.openedHandler = null;
		start();
	}
	
	public void close(String reason){
		running = false;
		buffer.close();
		socket.close(reason);		
	}
	
	public void connect() throws IOException {        
		if (!running){
			start();
		}
        this.socket.connect();        
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

	public void publish(Object data) throws Exception {
		buffer.push(data);		
	}
	
	private void send(Object data) throws Exception{
		if (data != null){
			socket.publish(data);	
		}
		
	}
	
	public void run(){
		running = true;
		Object data = null;
		while (running){
	        try{
	        	send(data);
	        	try{
	        		data = buffer.pop();	
	        	}catch(Exception ex){	        		
	        		System.out.println(ex);
	        		data = null;
	        	}	        	
	        }catch(Exception e){
	        	try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {	}
	        }	                      
		}
	}
}
