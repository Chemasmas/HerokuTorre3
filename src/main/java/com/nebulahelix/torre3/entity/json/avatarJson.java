package com.nebulahelix.torre3.entity.json;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chemasmas
 */
@XmlRootElement
public class avatarJson
{

    private long id;
    private int cabeza;
    private int torso;
    private int brazos;
    private int piernas;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public int getCabeza() {
        return cabeza;
    }

    public void setCabeza(int cabeza) {
        this.cabeza = cabeza;
    }

    public int getTorso() {
        return torso;
    }

    public void setTorso(int torso) {
        this.torso = torso;
    }

    public int getBrazos() {
        return brazos;
    }

    public void setBrazos(int brazos) {
        this.brazos = brazos;
    }

    public int getPiernas() {
        return piernas;
    }

    public void setPiernas(int piernas) {
        this.piernas = piernas;
    }
}