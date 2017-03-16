/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dimis.impl.message.config;

import org.fortiss.smg.actuatorclient.dimis.impl.Configs;
import org.fortiss.smg.actuatorclient.dimis.impl.message.GatewayConfig;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author hugo.pereira
 */
public class ConfigRequest {
//{
//    "opt": "C",
//    "GTW": {
//        "protV": "001",
//        "PN": "DA-0001",
//        "SN": "161001121401-0001",
//        "auth": "XXXXXXXXX",
//        "ipcfg": {
//                "auth": "rmtConfigIp",
//                "dhcp": "F",
//                "ip": "10.1.100.71",
//                "mask": "255.255.255.0",
//                "gtw": "10.1.100.254"
//        },
//        "server": {
//                "auth": "rmtConfigServer",
//                "ip": "10.1.100.70",
//                "port": "10001"
//        },
//        "ADE": {
//                "ConnType": "2",
//                "ConnFreq": "1",
//                "AdeVLEV": "0",
//                "AdeWTHR": "0",
//                "AdeNOLOAD": "1",
//                "Input_V_RelTranf": "1",
//                "Input_I_RelTranf": "1"
//        }
//    }
//}
 
    @SerializedName("opt")
    private String option;
    @SerializedName("GTW")
    private GatewayConfig gatewayConfig;

    /**
     * Constructor
     */
    public ConfigRequest() {
        this.option = "";
        this.gatewayConfig = new GatewayConfig();
    }
    
    /**
     * Constructor
     * @param option
     * @param gatewayConfig
     */
    public ConfigRequest(String option, GatewayConfig gatewayConfig) {
        this.option = option;
        this.gatewayConfig = gatewayConfig;
    }
    
    public String toJson() {
        return Configs.gson.toJson(this);
    }

    @Override
    public String toString() {
        return "ConfigRequest{" + "opt=" + option + ", GTW=" + gatewayConfig.toString() + '}';
    }

    
    
    
}
