/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nebulahelix.torre3.test;

import com.nebulahelix.torre3.service.Usuario;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.inmemory.InMemoryTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import static org.junit.Assert.assertEquals;
import org.junit.Test;



/**
 *
 * @author Chemasmas
 */
public class UsuarioTest extends JerseyTest{
     
    
    @Override
    protected Application configure() {
        return new ResourceConfig(Usuario.class);
    }
    
    @Override
    public TestContainerFactory getTestContainerFactory() {
        return new InMemoryTestContainerFactory();
    }
    
    @Test
    public void test() {
        final String hello = target("usuario/test").request().accept(MediaType.APPLICATION_JSON).get(String.class);
        assertEquals("{\"token\":\"Test\"}", hello);
    }
    
    @Test
    public void login()
    {
        assertEquals(0,0);
    }
    
}

@XmlRootElement
class UsuarioLogin{
    
    String usr;
    String pwd;
    String uuid;
}