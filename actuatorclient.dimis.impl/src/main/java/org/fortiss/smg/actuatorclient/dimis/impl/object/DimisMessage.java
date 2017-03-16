/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dimis.impl.object;

/**
 *
 * @author hugo.pereira
 */
public class DimisMessage {
    public Dimis dimis;
    public Object message;

    public DimisMessage(Dimis dimis, Object message) {
        this.dimis = dimis;
        this.message = message;
    }
}
