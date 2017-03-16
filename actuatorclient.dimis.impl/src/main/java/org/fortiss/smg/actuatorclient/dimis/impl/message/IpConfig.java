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
public class IpConfig {
//"ipcfg": {
//        "auth": "rmtConfigIp",
//        "dhcp": "F",
//        "ip": "10.1.100.71",
//        "mask": "255.255.255.0",
//        "gtw": "10.1.100.254"
//},  
    @SerializedName("auth")
    private String auth;
    @SerializedName("dhcp")
    private String dhcp;
    @SerializedName("ip")
    private String ip;
    @SerializedName("mask")
    private String ipMask;
    @SerializedName("gtw")
    private String ipGateway;

    /**
     * Constructor
     */
    public IpConfig() {
        this.auth = "";
        this.dhcp = "";
        this.ip = "";
        this.ipMask = "";
        this.ipGateway = "";
    }
    
    /**
     * Constructor
     * @param auth
     * @param dhcp
     * @param ip
     * @param ipMask
     * @param ipGateway 
     */
    public IpConfig(String auth, String dhcp, String ip, String ipMask, String ipGateway) {
        this.auth = auth;
        this.dhcp = dhcp;
        this.ip = ip;
        this.ipMask = ipMask;
        this.ipGateway = ipGateway;
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
        return "IpConfig{" + "auth=" + auth + ", dhcp=" + dhcp + ", ip=" + ip + ", mask=" + ipMask + ", gtw=" + ipGateway + '}';
    }
}
