/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dimis.impl;

import org.fortiss.smg.actuatorclient.dimis.impl.object.DataIdentifier;
import org.fortiss.smg.actuatorclient.dimis.impl.object.DataIdentifierDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author hugo.pereira
 */
public class Configs {
    public static int socket_port = 10001;
    public static int pooler_timer = 1000;
    public static Gson gson = null;
    
    public static void buildConfigs( String[] args ) {
        Configs.socket_port = Integer.parseInt(args[0]);
        Configs.pooler_timer = Integer.parseInt(args[1]);
        
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DataIdentifier.class, new DataIdentifierDeserializer());
        gsonBuilder.registerTypeAdapter(DataIdentifier.class, new DataIdentifierDeserializer());
        Configs.gson = gsonBuilder.create();
    }
    
}
