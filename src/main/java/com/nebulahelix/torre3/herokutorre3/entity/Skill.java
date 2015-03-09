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
public class Skill {

    public int getIdUsr() {
        return idUsr;
    }

    public void setIdUsr(int idUsr) {
        this.idUsr = idUsr;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    int idUsr;
    String skill;
    int tipo;
}
