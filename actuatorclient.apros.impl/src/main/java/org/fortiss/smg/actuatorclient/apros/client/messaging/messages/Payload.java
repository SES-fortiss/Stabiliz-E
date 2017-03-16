package org.fortiss.smg.actuatorclient.apros.client.messaging.messages;

import java.util.ArrayList;



/**
 * The class Payload is instantiated by Record class.
 * Each record has to have the header which contains common properties.
 */

public class Payload
{
	/**
	 * type the type of record e.g. "Accelerometer"
	 * uuid the uuid of the data source
	 * timestamp the timestamp of the record in epoch millisecond format
	 * duration the duration of the event described in the record. Can be zero.
	 */
	String command;
	private String setpoint;
	String topic;
	private String node;
	private String status_code;
	private String type;
	private double value;
	public ArrayList<Object> subscriptions;

    public Payload(){
    	this.subscriptions = new ArrayList<Object>();
    	
    }

	public ArrayList<Object> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(ArrayList<Object> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getSetpoint() {
		return setpoint;
	}

	public void setSetpoint(String setpoint) {
		this.setpoint = setpoint;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getNode() {
		return node;
	}

	public String getStatus_code() {
		return status_code;
	}

	public String getType() {
		return type;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}

	public void setType(String type) {
		this.type = type;
	}
}


