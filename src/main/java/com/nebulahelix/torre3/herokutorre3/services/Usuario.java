package com.nebulahelix.torre3.herokutorre3.services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Chemasmas
 */
@Path("/usuario")
public class Usuario {

    /*
    https://torre3uam.herokuapp.com/usuario/login
    */
    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    public String Login(@FormParam("usr") String usr,
                        @FormParam("pwd") String pwd)
    {
        System.out.println("Alguien se Conecto con "+usr+" y "+pwd);
        return "{usr :'"+usr+"' , pwd: '"+pwd+"' }";
    }
}
