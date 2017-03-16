package org.fortiss.smg.actuatorclient.apros.client.messaging.messages;

import org.codehaus.jackson.annotate.JsonProperty;

public class Bus extends JSONSerializable
{
	@JsonProperty("insert_id")
	protected double insertId;	
	public double getInsertId() {
		return insertId;
	}
	public void setInsertId(double insertId) {
		this.insertId = insertId;
	}
}
