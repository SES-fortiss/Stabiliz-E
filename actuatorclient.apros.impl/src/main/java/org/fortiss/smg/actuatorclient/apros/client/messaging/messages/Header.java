package org.fortiss.smg.actuatorclient.apros.client.messaging.messages;


import org.codehaus.jackson.annotate.JsonProperty;

/**
 * The class Header is instantiated by Record class.
 * Each record has to have the header which contains common properties.
 */

public class Header
{
	/**
     * type the type of record e.g. "Accelerometer"
	 * uuid the uuid of the data source
	 * timestamp the timestamp of the record in epoch millisecond format
	 * duration the duration of the event described in the record. Can be zero.
	 */
    @JsonProperty("@type")
    private String type;
    private String uuid;
    private String timestamp;
    private String duration;
    private String src;
	private String topic;
	private String genre;
    
    public Header(){
    	
    }

    public String getTimestamp()
    {
		return timestamp;
	}

	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
}


