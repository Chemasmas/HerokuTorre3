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
public class Token
{
    private String token;
    private Long id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
