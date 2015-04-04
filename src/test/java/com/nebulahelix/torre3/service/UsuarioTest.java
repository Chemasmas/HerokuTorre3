/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nebulahelix.torre3.service;

import com.nebulahelix.torre3.entity.json.Notificacion;
import com.nebulahelix.torre3.entity.json.Tipo;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Chemasmas
 */
public class UsuarioTest {
    
   @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void loginVacio()
    {
        System.out.println("Login Vacio");
        String usr = "";
        String pwd = "";
        String uuid = "";
        Usuario instance = new Usuario();
        Response result = instance.login(usr, pwd, uuid);
        
        Notificacion esperado=new Notificacion();
        esperado.setTipo(Tipo.Fallo);
        esperado.setNotificacion("Esa pareja Usuario/Password NO esta registrada");
        
        assertEquals(esperado, (Notificacion)result.getEntity());
    }
    
    @Test
    public void registerVacio()
    {
        Usuario instance = new Usuario();
        String usuario="";
        String contrasena="";
        String nombre="";
        String matricula="";
        String carrera="";
        String habilidades="";
        String servicios="";
        String conocimientos="";
        String intereses="";
        String celular="";
        String proyectosPrevios="";
        String proyectosActuales="";
        int piernas=1;
        int brazos=1;
        int cabeza=1;
        int torso=1;
        
        Response result = instance.registrar(usuario,contrasena,nombre,matricula
                ,carrera,habilidades,servicios,conocimientos,intereses,celular
                ,proyectosPrevios,proyectosActuales,piernas,brazos,cabeza,torso);
        
        Notificacion n=new Notificacion();
        n.setTipo(Tipo.Fallo);
        n.setNotificacion("La peticion fallo, faltan campos");
        
        System.out.println(n.getNotificacion());
        System.out.println(((Notificacion)result.getEntity()).getNotificacion());
        
        assertEquals(n,(Notificacion)result.getEntity());
                
    }

    /*
    @org.junit.Test
    public void testLogin_3args() {
        System.out.println("login");
        String usr = "";
        String pwd = "";
        String uuid = "";
        Usuario instance = new Usuario();
        Response expResult = null;
        Response result = instance.login(usr, pwd, uuid);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @org.junit.Test
    public void testLogin_String_String() {
        System.out.println("login");
        String usr = "";
        String token = "";
        Usuario instance = new Usuario();
        Response expResult = null;
        Response result = instance.login(usr, token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @org.junit.Test
    public void testRegistrar() {
        System.out.println("Registrar");
        String usuario = "";
        String contrasena = "";
        String nombre = "";
        String matricula = "";
        String carrera = "";
        String habilidades = "";
        String servicios = "";
        String conocimientos = "";
        String intereses = "";
        String celular = "";
        String proyectosPrevios = "";
        String proyectosActuales = "";
        int piernas = 0;
        int brazos = 0;
        int cabeza = 0;
        int torso = 0;
        Usuario instance = new Usuario();
        Response expResult = null;
        Response result = instance.Registrar(usuario, contrasena, nombre, matricula, carrera, habilidades, servicios, conocimientos, intereses, celular, proyectosPrevios, proyectosActuales, piernas, brazos, cabeza, torso);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @org.junit.Test
    public void testGetUsuario() {
        System.out.println("getUsuario");
        long id = 0L;
        Usuario instance = new Usuario();
        Response expResult = null;
        Response result = instance.getUsuario(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @org.junit.Test
    public void testEditarUsuario() {
        System.out.println("editarUsuario");
        long id = 0L;
        String nombre = "";
        String matricula = "";
        String carrera = "";
        String celular = "";
        String usr = "";
        String token = "";
        Usuario instance = new Usuario();
        Response expResult = null;
        Response result = instance.editarUsuario(id, nombre, matricula, carrera, celular, usr, token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @org.junit.Test
    public void testGetAvatar() {
        System.out.println("getAvatar");
        long id = 0L;
        Usuario instance = new Usuario();
        Response expResult = null;
        Response result = instance.getAvatar(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */
}
