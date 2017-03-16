/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dimis.impl.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author hugo.pereira
 */
public class Authentication {
    @Expose(serialize = false, deserialize = false)
    private final String KEY = "heloowOrdl";
    
    @SerializedName("ticks")
    protected String ticks;
    @SerializedName("user")
    protected String username;
    @SerializedName("pass")
    protected String password;

    /**
     * Constructor
     */
    public Authentication() {
        this.ticks = "";
        this.username = "";
        this.password = "";
    }

    /**
     * Constructor
     * @param ticks
     * @param username
     * @param password 
     */
    public Authentication(String ticks, String username, String password) {
        this.ticks = ticks;
        this.username = username;
        this.password = password;
    }
    
    /**
     * Validate Password
     * @return 
     */
    public boolean validate() {
        return password.equals(org.apache.commons.codec.digest.DigestUtils.sha256Hex(ticks + "_" + KEY));
    }

    @Override
    public String toString() {
        return "Authentication{" + "ticks=" + ticks + ", username=" + username + ", password=" + password + '}';
    }
    
}
