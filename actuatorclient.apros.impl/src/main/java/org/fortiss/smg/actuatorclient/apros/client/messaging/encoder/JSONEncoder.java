package org.fortiss.smg.actuatorclient.apros.client.messaging.encoder;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class JSONEncoder 
{
	public ObjectMapper objectMapper;
	private static JSONEncoder instance;
	
	public JSONEncoder(){
		objectMapper = new ObjectMapper();
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		objectMapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public static JSONEncoder getInstance()
	{
		if (instance == null) instance = new JSONEncoder();
		return instance;
	}
	
	/**
	 * Encodes a compatible object as a JSON string using Jackson. Hides exceptions and returns null in case of errors. 
	 * @param msg
	 * @return
	 */
	public String encode(Object msg){
		String retVal = null;
    	try 
    	{    		
    		retVal = objectMapper.writeValueAsString(msg);
		} catch (JsonGenerationException e) 
		{	
			Log.e("JSONEncoder","Generation error",e);
			
		} catch (JsonMappingException e) 
		{	
			Log.e("JSONEncoder","Mapping error",e);
			
		} catch (IOException e) 
		{	
			Log.e("JSONEncoder","I/O error",e);
		}    
    	return retVal;
	}

	public <T> T decode (String msg, Class<T> type){
		T retVal = null;
		try {			
			retVal = objectMapper.readValue(msg, type);
		} catch (JsonParseException e) {
			Log.e("JSON Decode", "Parse Exception: "+e.getMessage());
			Log.d("JSON Decode",msg);
		} catch (JsonMappingException e) {
			Log.e("JSON Decode", "Mapping Exception: "+e.getMessage());
			Log.d("JSON Decode",msg);

		} catch (IOException e) {
			Log.e("JSON Decode", "IO Exception",e);
		}
		return retVal;
	}
}
