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
public class GatewayConfig extends Gateway {
    
//"GTW": {
//    "protV": "001",
//    "PN": "DA-0001",
//    "SN": "161001121401-0001",
//    "auth": "XXXXXXXXX",
//    "ipcfg": {
//            "auth": "rmtConfigIp",
//            "dhcp": "F",
//            "ip": "10.1.100.71",
//            "mask": "255.255.255.0",
//            "gtw": "10.1.100.254"
//    },
//    "server": {
//            "auth": "rmtConfigServer",
//            "ip": "10.1.100.70",
//            "port": "10001"
//    },
//    "ADE": {
//            "ConnType": "2",
//            "ConnFreq": "1",
//            "AdeVLEV": "0",
//            "AdeWTHR": "0",
//            "AdeNOLOAD": "1",
//            "Input_V_RelTranf": "1",
//            "Input_I_RelTranf": "1"
//    }
//}
    
    @SerializedName("auth")
    private String auth;
    @SerializedName("ipcfg")
    private IpConfig ipConfig;
    @SerializedName("server")
    private ServerConfig serverConfig;
    @SerializedName("ADE")
    private ADE ADE;

    /**
     * Constructor
     */
    public GatewayConfig() {
        super();
        this.auth = "";
        this.ipConfig = new IpConfig();
        this.serverConfig = new ServerConfig();
        this.ADE = new ADE();
    }
    
    /**
     * Constructor
     * @param auth
     * @param ipConfig
     * @param serverConfig
     * @param ADE
     * @param protocolVersion
     * @param partNumber
     * @param serialNumber 
     */
    public GatewayConfig(String auth, IpConfig ipConfig, ServerConfig serverConfig, ADE ADE, String protocolVersion, String partNumber, String serialNumber) {
        super(protocolVersion, partNumber, serialNumber);
        this.auth = auth;
        this.ipConfig = ipConfig;
        this.serverConfig = serverConfig;
        this.ADE = ADE;
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
        return "GatewayConfig{" + "protV=" + protocolVersion + ", PN=" + partNumber + ", SN=" + serialNumber + ", auth=" + auth + ", ipcfg=" + ipConfig.toString() + ", server=" + serverConfig.toString() + ", ADE=" + ADE.toString() + '}';
    }
}
