/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dimis.impl.message.read;

import org.fortiss.smg.actuatorclient.dimis.impl.Configs;
import org.fortiss.smg.actuatorclient.dimis.impl.message.Gateway;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author hugo.pereira
 */
public class ReadConfigRequest {
//    {
//        "opt" : "R",
//	  "GTW": {
//		"protV" : "001",
//		"PN": "DA-0001",
//		"SN": "161001121401-0001"
//	  }
//    }

    @SerializedName("opt")
    private String option;
    @SerializedName("GTW")
    private Gateway gateway;

    /**
     * Constructor
     */
    public ReadConfigRequest() {
        this.option = "";
        this.gateway = new Gateway("", "", "");
    }
    
    /**
     * Constructor
     * @param option
     * @param protocolVersion
     * @param partNumber
     * @param serialNumber 
     */
    public ReadConfigRequest(String option, String protocolVersion, String partNumber, String serialNumber) {
        this.option = option;
        this.gateway = new Gateway(protocolVersion, partNumber, serialNumber);
    }

    @Override
    public String toString() {
        return "ReadRequest{" + "opt=" + option + ", GTW=" + gateway + '}';
    }
    
    public String toJson() {
        return Configs.gson.toJson(this);
    }
}
