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
public class ADE {
//"ADE": {
//        "ConnType": "2",
//        "ConnFreq": "1",
//        "AdeVLEV": "0",
//        "AdeWTHR": "0",
//        "AdeNOLOAD": "1",
//        "Input_V_RelTranf": "1",
//        "Input_I_RelTranf": "1"
//}  
    
    @SerializedName("ConnType")
    private String ConnType;
    @SerializedName("ConnFreq")
    private String ConnFreq;
    @SerializedName("AdeVLEV")
    private String AdeVLEV;
    @SerializedName("AdeWTHR")
    private String AdeWTHR;
    @SerializedName("AdeNOLOAD")
    private String AdeNOLOAD;
    @SerializedName("Input_V_RelTranf")
    private String Input_V_RelTranf;
    @SerializedName("Input_I_RelTranf")
    private String Input_I_RelTranf;

    /**
     * Constructor
     */
    public ADE() {
        this.ConnType = "";
        this.ConnFreq = "";
        this.AdeVLEV = "";
        this.AdeWTHR = "";
        this.AdeNOLOAD = "";
        this.Input_V_RelTranf = "";
        this.Input_I_RelTranf = "";
    }
        
    /**
     * Constructor
     * @param ConnType
     * @param ConnFreq
     * @param AdeVLEV
     * @param AdeWTHR
     * @param AdeNOLOAD
     * @param Input_V_RelTranf
     * @param Input_I_RelTranf 
     */
    public ADE(String ConnType, String ConnFreq, String AdeVLEV, String AdeWTHR, String AdeNOLOAD, String Input_V_RelTranf, String Input_I_RelTranf) {
        this.ConnType = ConnType;
        this.ConnFreq = ConnFreq;
        this.AdeVLEV = AdeVLEV;
        this.AdeWTHR = AdeWTHR;
        this.AdeNOLOAD = AdeNOLOAD;
        this.Input_V_RelTranf = Input_V_RelTranf;
        this.Input_I_RelTranf = Input_I_RelTranf;
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
        return "ADE{" + "ConnType=" + ConnType + ", ConnFreq=" + ConnFreq + ", AdeVLEV=" + AdeVLEV + ", AdeWTHR=" + AdeWTHR + ", AdeNOLOAD=" + AdeNOLOAD + ", Input_V_RelTranf=" + Input_V_RelTranf + ", Input_I_RelTranf=" + Input_I_RelTranf + '}';
    }
    
}
