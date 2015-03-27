/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nebulahelix.torre3.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chemasmas
 */
public class Encriptado {

    public static String SHA256(String original)
    {
        try {
            MessageDigest digest=MessageDigest.getInstance("SHA-256");
            digest.update(original.getBytes());
            
            byte[] data=digest.digest();
            
            StringBuilder res=new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                String val=Integer.toHexString(0xff & data[i]);
                if(val.length()==1) res.append('0');
                res.append(val);
            }
            return res.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encriptado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
