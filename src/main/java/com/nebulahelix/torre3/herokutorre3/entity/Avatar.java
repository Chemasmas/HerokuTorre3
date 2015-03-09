/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nebulahelix.torre3.herokutorre3.entity;

/**
 *
 * @author Chemasmas
 */
public class Avatar {

    public int getIdUsr() {
        return idUsr;
    }

    public void setIdUsr(int idUsr) {
        this.idUsr = idUsr;
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
    int idUsr;
    int cabeza;
    int torso;
    int brazos;
    int piernas;
}
