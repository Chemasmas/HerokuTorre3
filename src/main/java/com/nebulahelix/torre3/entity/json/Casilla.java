/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nebulahelix.torre3.entity.json;

//import com.nebulahelix.torre3.service.Pasillo;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chemasmas
 */

@XmlRootElement
public class Casilla {
    
    private ListaMsg mensajes=new ListaMsg();
    private List<Long> usuarios=new ArrayList<Long>();
    
    public List<Long> getUsuarios() {
        if(usuarios==null)
            setUsuarios(new ArrayList<Long>());
        return usuarios;
    }

    public void setUsuarios(List<Long> usuarios) {
        this.usuarios = usuarios;
    }

    public ListaMsg getMensajes() {
        if(mensajes==null)
            setMensajes(new ListaMsg());
        return mensajes;
    }

    public void setMensajes(ListaMsg mensajes) {
        this.mensajes = mensajes;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        
        sb.append("{ usuarios:[");
        for (Long usuario : usuarios) {
            sb.append(usuario);
            sb.append(",");
        }
        sb.append("], mensajes : [");
        
        for (Mensaje mensaje : mensajes.mensajes) {
            sb.append(mensaje);
            sb.append(",");
        }
        sb.append("]}");
        
        return sb.toString();
    }
}
