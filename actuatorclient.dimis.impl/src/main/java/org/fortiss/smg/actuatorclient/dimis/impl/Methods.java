package org.fortiss.smg.actuatorclient.dimis.impl;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hugo.pereira
 */
public class Methods {
    
    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    
    /**
     * 
     * @param string 
     */
    public static void PrintLine(String string) {
        System.out.println("[" + dateFormat.format(new Date()) + "] => " + string);
    }
}
