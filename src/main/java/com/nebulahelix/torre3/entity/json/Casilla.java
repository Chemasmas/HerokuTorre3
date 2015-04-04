/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nebulahelix.torre3.entity.json;

//import com.nebulahelix.torre3.service.Pasillo;
import com.nebulahelix.torre3.entity.Anuncios;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chemasmas
 */

@XmlRootElement
public class Casilla {

    public List<avatarJson> getUsuarios() {
        if(usuarios==null)
        {
            usuarios=new ArrayList<avatarJson>();
            //usuarios.add(new avatarJson());
        }
        return usuarios;
    }

    public void setUsuarios(List<avatarJson> usuarios) {
        this.usuarios = usuarios;
    }
    
    private List<avatarJson> usuarios;
    private List<Mensaje> mensajes;

    public List<Mensaje> getMensajes() {
        if(mensajes==null) mensajes=new ArrayList<Mensaje>();
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        
        this.mensajes = mensajes;
    }

    public void addUser(avatarJson aj) 
    {
        getUsuarios().add(aj);
    }

    public void addAnuncio(Mensaje anuncio) {
        getMensajes().add(anuncio);
    }
}
