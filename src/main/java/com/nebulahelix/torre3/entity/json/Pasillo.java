/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nebulahelix.torre3.entity.json;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chemasmas
 */

@XmlRootElement
public class Pasillo {
    private List<Casilla> casillas;
    
    public Pasillo()
    {
        casillas=new ArrayList<Casilla>();
        //Pasillo Vacio
        casillas.add(new Casilla());
        casillas.add(new Casilla());
        casillas.add(new Casilla());
        casillas.add(new Casilla());
        casillas.add(new Casilla());
        casillas.add(new Casilla());
        casillas.add(new Casilla());
        casillas.add(new Casilla());
        casillas.add(new Casilla());
        casillas.add(new Casilla());
    }
    

    public List<Casilla> getCasillas() {
        if(casillas==null) casillas=new ArrayList<Casilla>(10);
        return casillas;
    }

    public void setCasillas(List<Casilla> casillas) {
        this.casillas = casillas;
    }
}
