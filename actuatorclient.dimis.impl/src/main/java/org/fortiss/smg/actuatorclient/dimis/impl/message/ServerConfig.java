/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dimis.impl.message;

import org.fortiss.smg.actuatorclient.dimis.impl.Configs;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author hugo.pereira
 */
public class ServerConfig {
   
//"server": {
//        "auth": "rmtConfigServer",
//        "ip": "10.1.100.70",
//        "port": "10001"
//}
   
    @SerializedName("auth")
    private String auth;
    @SerializedName("ip")
    private String ip;
    @SerializedName("port")
    private String port;

    /**
     * Constructor 
     */
    public ServerConfig() {
        this.auth = "";
        this.ip = "";
        this.port = "";
    }
    
    /**
     * Constructor
     * @param auth
     * @param ip
     * @param port 
     */
    public ServerConfig(String auth, String ip, String port) {
        this.auth = auth;
        this.ip = ip;
        this.port = port;
    }
    
    /**
     * 
     * @return 
     */
    public String toJson() {
        return Configs.gson.toJson(this);
    }

    @Override
    public String toString() {
        return "ServerConfig{" + "auth=" + auth + ", ip=" + ip + ", port=" + port + '}';
    }
    
    
    
}
