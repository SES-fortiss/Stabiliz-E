package org.fortiss.smg.actuatorclient.apros.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;
import java.util.zip.InflaterOutputStream;
import java.util.zip.ZipException;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.xml.bind.DatatypeConverter;

import org.glassfish.tyrus.core.Base64Utils;

//import com.sun.xml.internal.messaging.saaj.util.Base64;


public class WebSocket extends Endpoint{	
	private String socketType;
	private String url;
	private String username;
	private String password;
	private String topic;
	private String sessionId;
	private Session socket = null;
	private ClientEndpointConfig.Configurator configurator;
	private ClientEndpointConfig clientConfig;
	private WebSocketContainer container;
	
	private MessageHandler messageHandler;
    private MessageHandler errorHandler;
    private MessageHandler openedHandler;

	
	public WebSocket(String socketType, String url, String username, String password, String topic, String sessionId){
		this.socketType = socketType;
		this.url = url;
		this.username = username;
		this.password = password;
		this.topic = topic;
		this.sessionId = sessionId;
		this.messageHandler = null;
		this.errorHandler = null;
		this.openedHandler = null;
		prepareSocketContainer();		
	}
	
	private void prepareSocketContainer(){
		configurator = new ClientEndpointConfig.Configurator() {
    	    public void beforeRequest(Map<String, List<String>> headers) {
    	    	List<String> list = new ArrayList<String>();
    	    	list.add("Basic " + DatatypeConverter.printBase64Binary((username + ":" + password).getBytes()));
    	        headers.put("Authorization", list);
    	    }
    	};	        		        	
    	clientConfig = ClientEndpointConfig.Builder.create()
    	        .configurator(configurator)	        	       
    	        .build();    	        	
    	container = ContainerProvider.getWebSocketContainer();
	}
	
	private String formUrl(){
		String prefix;		
		String formedURL;
		prefix = "wss://";		
		formedURL = prefix + this.url + "/wspubsub/";				
        if (sessionId != null){            
        	formedURL += socketType + "?topic=" + topic + "&session_id=" + sessionId + "&compress=";
        	formedURL += "True";                        	
        }
        else{
        	formedURL += socketType + "?topic=" + topic + "&compress=";
        	formedURL += "True";            
        }
        return formedURL;        
	}
	
	private byte[] compress(String data) throws IOException
	{		
		ByteArrayOutputStream obj = new ByteArrayOutputStream();
		DeflaterOutputStream deflater = new DeflaterOutputStream(obj);
		deflater.write(data.getBytes("utf-8"));		
		deflater.close();
        return obj.toByteArray();
	}
	
	private boolean isGZIPed(byte[] data){
		int head = ((int) data[0] & 0xff) | ((data[1] << 8) & 0xff00);
	    return (GZIPInputStream.GZIP_MAGIC == head);
	}
	
	private String decompress(byte[] data) throws IOException
	{		
    	BufferedReader messageReader; 
    	String inputLine;
    	StringBuffer messageBuffer;   
    	if (isGZIPed(data)){messageReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new ByteArrayInputStream(data))));}
    	else{messageReader = new BufferedReader(new InputStreamReader(new InflaterInputStream(new ByteArrayInputStream(data))));}		
		messageBuffer = new StringBuffer();
		while ((inputLine = messageReader.readLine()) != null) 
		{
			messageBuffer.append(inputLine);
		}						
		messageReader.close();
		return messageBuffer.toString();    	
	}
	
	private String encode(byte[] data) throws UnsupportedEncodingException
	{		
		return DatatypeConverter.printBase64Binary(data);
	}
	
	private byte[] decode(String data)
	{			
		return Base64Utils.decode(data);
	}
	

    public void setMessageHandler(MessageHandler msgHandler) {    	
        this.messageHandler = msgHandler;
    }
    
    public void setOpenedHandler(MessageHandler handler) {    	
        this.openedHandler = handler;
    }
    
    public void setErrorHandler(MessageHandler handler) {    	
        this.errorHandler = handler;
    }    
    
    public MessageHandler getMessageHandler() {
    	return this.messageHandler;
    }
    
    public MessageHandler getOpenedHandler() {    	
        return this.openedHandler;
    }
    
    public MessageHandler getErrorHandler() {    	
        return this.errorHandler;
    }

    public void publish(Object data) throws Exception {    	
    	if (isOpen() == false){throw new IOException("Connection not open");}    
    	if (data.toString().length() == 0){throw new Exception("Nothing to send");}    	
    	String message = "{\"data\" : \"" + encode(compress(data.toString())) + "\"}";
		this.socket.getBasicRemote().sendText(message);	
    }
    
	public void close(String reason) throws IOException{
		this.socket.close();		
	}
	
	public boolean isOpen(){
		return this.socket != null && this.socket.isOpen();
		
	}
	
	public void connect(){		
		if (isOpen() == false){
	        try {	        		        	
	        	container.connectToServer(this, clientConfig, new URI(formUrl()));	        		        	
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
		}
	}	
	public void onOpen(Session socket, EndpointConfig config) {    	
        this.socket = socket;
        this.socket.addMessageHandler(new javax.websocket.MessageHandler.Whole<String>() {
            public void onMessage(String message) {      
            	if (messageHandler != null && message.length() > 0) {            	                			
        			try {        			              				
        				message = message.substring(1, message.length()-1);
        				message = decompress(decode(message));       
        				try{        		 
        					messageHandler.handle(message);	
        				}catch(Exception e){        					
        					System.out.println(e);
        				}        	    		
        			} catch (IOException e) {
        				System.out.println(e);
        				System.out.println("Failure to decompress the incoming message "+ message + " due to " + e);
        			}  
            	}
            }
        });
        if (this.openedHandler != null) {this.openedHandler.handle("");};
    }
    
    public void onClose(Session socket, CloseReason reason) {    
        if (this.errorHandler != null) {this.errorHandler.handle(reason.toString());};
    }
	

    public void onError(Session socket, Throwable t) {
		try {
			close("");
			if (this.errorHandler != null) {this.errorHandler.handle(t.getMessage());};
		} catch (IOException e) {
			if (this.errorHandler != null) {this.errorHandler.handle(e.getMessage());};
		}        
    }

}
