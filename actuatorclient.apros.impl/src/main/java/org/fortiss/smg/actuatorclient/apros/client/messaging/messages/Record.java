package org.fortiss.smg.actuatorclient.apros.client.messaging.messages;


/**
 * The class Record has to be extended by the SensorRecord 
 * (SensorRecord is made by sensor provider, see examples package). 
 */

public class Record extends JSONSerializable
{
	public Header header;
	public Payload payload;
    private String service;
	public Record()
	{
        header = new Header();
        payload = new Payload();

	}

	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}

	public Payload getPayload() {
	    return payload;
    }
    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public String getService() {
        return service;
    }
    public void setService(String service) {
        this.service = service;
    }
}


