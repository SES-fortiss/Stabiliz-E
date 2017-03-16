/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dimis.impl.object;

import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author hugo.pereira
 */
public class Dimis {
    public String id;
    public LinkedBlockingQueue<Object> requestList;

    public Dimis(String id) {
        this.id = id;
        this.requestList = new LinkedBlockingQueue<Object>();
    }

    @Override
    public String toString() {
        return "Dimis{" + "id=" + id + " }";
    }
    
}
