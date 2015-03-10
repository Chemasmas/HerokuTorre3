/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nebulahelix.torre3.herokutorre3.DAO;

import java.net.URISyntaxException;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chemasmas
 */
public class BD {
    
    static Connection con;

    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_PROTOCOL = "jdbc:postgresql://";
    private static final String DB_SCHEMA="deuf7oh9dj1p7n";
    private static final String DB_USER = "funsnjhopnxlzg";
    private static final String DB_PASSWORD = "0trJ4wny6ePKYulICuT8f5U5MX";
    private static final String HOST="localhost";
        
    
    
    public static Connection getConnection(){
        if(con==null)
        {
            try {
                URI dbUri;
                dbUri= new URI(System.getenv("DATABASE_URL"));
                
                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
                
                con=DriverManager.getConnection(dbUrl, username, password);
            } catch (URISyntaxException ex) {
                System.err.println("URI no encontrada");
            } catch (SQLException ex) {
                System.err.println("FAllo de SQL");
            }
        }
        
        return con;
    }
}
