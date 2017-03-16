/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dimis.impl.message.write;

import org.fortiss.smg.actuatorclient.dimis.impl.Configs;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author hugo.pereira
 */
public class WriteResponse {
    
    @SerializedName("opt")
    private String option;
    @SerializedName("status")
    private String status;

    /**
     * Constructor
     */
    public WriteResponse() {
        this.option = "";
        this.status = "";
    }
    
    
    /**
     * Constructor
     * @param option
     * @param status 
     */
    public WriteResponse(String option, String status) {
        this.option = option;
        this.status = status;
    }

    @Override
    public String toString() {
        return "WriteResponse{" + "opt=" + option + ", status=" + status + '}';
    }
    
    /**
     * 
     * @return 
     */
    public String toJson() {
        return Configs.gson.toJson(this);
    }
}
