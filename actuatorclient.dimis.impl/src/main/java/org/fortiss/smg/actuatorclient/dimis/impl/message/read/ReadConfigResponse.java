/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dimis.impl.message.read;

import org.fortiss.smg.actuatorclient.dimis.impl.Configs;
import org.fortiss.smg.actuatorclient.dimis.impl.message.GatewayConfig;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author hugo.pereira
 */
public class ReadConfigResponse {
    
    @SerializedName("opt")
    private String option;
    @SerializedName("GTW")
    private GatewayConfig gatewayConfig;

    /**
     * Constructor
     */
    public ReadConfigResponse() {
        this.option = "";
        this.gatewayConfig = new GatewayConfig();
    }
    
    /**
     * Constructor
     * @param option
     * @param gatewayConfig
     */
    public ReadConfigResponse(String option, GatewayConfig gatewayConfig) {
        this.option = option;
        this.gatewayConfig = gatewayConfig;
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
        return "ReadConfigResponse{" + "opt=" + option + ", GTW=" + gatewayConfig.toString() + '}';
    }
}
