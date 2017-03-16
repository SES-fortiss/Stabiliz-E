package org.fortiss.smg.actuatorclient.apros.client;

import org.fortiss.smg.actuatorclient.apros.client.messaging.messages.Record;

import java.io.IOException;
import java.util.HashMap;

public class Invoker {
	
	private String url;
	private String service;
    private HTTPClient socket;
	

	public Invoker(String url, String service, String username, String password) {
		this.url = "https://" + url + "/service/invoke";
		this.service = service;
        this.socket = new HTTPClient(username, password);
	}

	private void invoke(String data, int timeout ) {
		HashMap<String, Object> fields = new HashMap<String, Object>();
        fields.put("service", service);
        try {
            this.socket.send(data, fields, this.url, timeout, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException 
	{
		String url = "130.188.225.233:10000";
		String username = "fortiss";
		String password = "Bz1xr6";
		String service = "stabilize";
        Record data = new Record();
        data.header.setSrc("Raspberry");
        data.payload.setCommand("write");
        data.payload.setSetpoint("TYPES!S!SET_POINT!SPIC03.SP_VALUE");
        data.payload.setValue(1);
		data.payload.setTopic("apros");
		Invoker invoker = new Invoker(url, service, username, password);
        // System.out.println(data);
		// {"header":{"src":"Raspberry"},"payload":{"command":"write","setpoint":"TYPES!S!SET_POINT!SPIC01.SP_VALUE","topic":"apros","value":0.0}}
		invoker.invoke(data.toString(), 5000);
	}
	
}

