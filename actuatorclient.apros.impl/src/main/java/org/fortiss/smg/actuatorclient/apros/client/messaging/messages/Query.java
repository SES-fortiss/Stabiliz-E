package org.fortiss.smg.actuatorclient.apros.client.messaging.messages;


import org.codehaus.jackson.annotate.JsonProperty;

public class Query extends JSONSerializable
{
	private String type;
	private String operation;
	@JsonProperty("@insert_id")
	private double insertId;
	private Header header;
	private Payload payload;
	
	public Query()
	{
		operation = "measurement"; // "$gte"; 
		insertId = 0;		
		this.header = new Header();
		this.header.setSrc("fortissSMG");
		
		this.payload = new Payload();
		this.payload.setCommand("subscribe");
		this.payload.setTopic("apros");
		this.payload.subscriptions.add("TYPES!S!SET_POINT!SPIC10.SP_VALUE");
		
		
	}
	
	public String toString()
	{
		return "{'header': {'src':'" + this.header.getSrc() + "'}," +
				" 'payload':{'command':'" +this.payload.command  +  "', "+
					" 'topic':'" + this.payload.topic + "'," +
					 " 'subscriptions':['"+ this.payload.subscriptions.get(0) + "']}}" ;
		/*return "{"
					+ (type != null ? "\"header.@type\":\"" + type + "\", " : "")
					+ "\"_bus.insert_id\": {\"" + operation + "\": " + insertId + "}"
				+"}";
				*/		 
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getOperation() 
	{
		return operation;
	}

	public void setOperation(String operation) 
	{
		this.operation = operation;
	}

	public double getInsertId() {
		return insertId;
	}

	public void setInsertId(double insertId) {
		this.insertId = insertId;
	}
}
