/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nebulahelix.torre3.entity.json;

/**
 *
 * @author Chemasmas
 */
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chemasmas
 */

@XmlRootElement
public class Segmento {

    public List<Pasillo> getPasillos() {
        if(pasillos==null) pasillos=new ArrayList<Pasillo>();
        return pasillos;
    }

    public void setPasillos(List<Pasillo> pasillos) {
        this.pasillos = pasillos;
    }

    List<Pasillo> pasillos;
}
