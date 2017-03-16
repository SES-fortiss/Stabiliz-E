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
public class Gateway {
//"GTW": {
//    "protV" : "001",
//    "PN": "DA-0001",
//    "SN": "161001121401-0001"
//}
    
    @SerializedName("protV")
    public String protocolVersion;
    @SerializedName("PN")
    public String partNumber;
    @SerializedName("SN")
    public String serialNumber;

    /**
     * Constructor
     */
    public Gateway() {
        this.protocolVersion = "";
        this.partNumber = "";
        this.serialNumber = "";
    }  

    /**
     * Constructor
     * @param protocolVersion
     * @param partNumber
     * @param serialNumber
     */
    public Gateway(String protocolVersion, String partNumber, String serialNumber) {
        this.protocolVersion = protocolVersion;
        this.partNumber = partNumber;
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "Gateway{" + "protV=" + protocolVersion + ", PN=" + partNumber + ", SN=" + serialNumber + '}';
    }
       
    public String toJson() {
        return Configs.gson.toJson(this);
    }
}
