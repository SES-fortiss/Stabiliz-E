/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dimis.impl.message.auth;

import org.fortiss.smg.actuatorclient.dimis.impl.Configs;
import org.fortiss.smg.actuatorclient.dimis.impl.message.Authentication;
import org.fortiss.smg.actuatorclient.dimis.impl.message.GatewayConfig;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author hugo.pereira
 */
public class AuthRequest {
    
    @SerializedName("opt")
    private String option;
    @SerializedName("auth")
    private Authentication authentication;
    @SerializedName("GTW")
    public GatewayConfig gatewayConfig;

    /**
     * Constructor
     */
    public AuthRequest() {
        this.option = "";
        this.authentication = new Authentication();
        this.gatewayConfig = new GatewayConfig();
    }

    /**
     * Constructor
     * @param option
     * @param authentication
     * @param gatewayConfig 
     */
    public AuthRequest(String option, Authentication authentication, GatewayConfig gatewayConfig) {
        this.option = option;
        this.authentication = authentication;
        this.gatewayConfig = gatewayConfig;
    }
   
    
    /**
     * Validate Password
     * @return 
     */
    public boolean validate() {
        return authentication.validate();
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
        return "AuthRequest{" + "option=" + option + ", authentication=" + authentication.toString() + ", gatewayConfig=" + gatewayConfig.toString() + '}';
    }
    
    
}
