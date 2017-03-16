/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dimis.impl.message.read;

import org.fortiss.smg.actuatorclient.dimis.impl.Configs;
import org.fortiss.smg.actuatorclient.dimis.impl.message.Device;
import org.fortiss.smg.actuatorclient.dimis.impl.message.Gateway;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 *
 * @author hugo.pereira
 */
public class ReadResponse {
//{
//    "opt": "R",
//    "GTW": {
//        "protV": "001",
//        "PN": "DA-0001",
//        "SN": "161001121401-0001"
//    },
//    "devs": [
//        {
//            "PN": "DA-0001",
//            "SN": "161001121401-0001",
//            "dataID": [1,2,3,4,5,6,7,8],
//            "data": [
//                228.100000,
//                228.100000,
//                228.100000,
//                228.100000,
//                228.100000,
//                228.100000,
//                228.100000,
//                228.100000
//            ]
//        }
//    ]
//}
        
    @SerializedName("opt")
    private String option;
    @SerializedName("GTW")
    private Gateway gateway;
    @SerializedName("devs")
    public ArrayList<Device> deviceList;

    /**
     * Constructor
     */
    public ReadResponse() {
        this.option = "";
        this.gateway = new Gateway("", "", "");
        this.deviceList = new ArrayList<Device>();
    }
    
    /**
     * Constructor
     * @param option
     * @param gateway
     * @param deviceList 
     */
    public ReadResponse(String option, Gateway gateway, ArrayList<Device> deviceList) {
        this.option = option;
        this.gateway = gateway;
        this.deviceList = deviceList;
    }
    
    public String toJson() {
        return Configs.gson.toJson(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ReadResponse{ opt=").append(option).append(", GTW=").append(gateway).append(", devs=[");
        for (Device dev : deviceList) {
            sb.append(dev.toString()).append(", ");
        }
        sb.append("] }");
        return sb.toString();
    }

}
