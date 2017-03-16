/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dimis.impl.object;

import java.lang.reflect.Type;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 *
 * @author hugo.pereira
 * https://kylewbanks.com/blog/Int-Enum-Mapping-with-GSON
 * http://hashtagfail.com/post/44606137082/mobile-services-android-serialization-gson
 */
public class DataIdentifierDeserializer implements JsonDeserializer<DataIdentifier>, JsonSerializer<DataIdentifier> {

    @Override
    public DataIdentifier deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        int key = element.getAsInt();
        return DataIdentifier.fromKey(key);
    }
    
    @Override
    public JsonElement serialize(DataIdentifier src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getKey());
    }
    
}
