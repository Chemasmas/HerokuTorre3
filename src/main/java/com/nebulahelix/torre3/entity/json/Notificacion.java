/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nebulahelix.torre3.entity.json;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chemasmas
 */
@XmlRootElement
public class Notificacion implements MessageEntity
{
    private String Notificacion;
    private Tipo tipo;
    
    public String getNotificacion() {
        return Notificacion;
    }

    public void setNotificacion(String Notificacion) {
        this.Notificacion = Notificacion;
    }
    
    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}