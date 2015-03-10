package com.nebulahelix.torre3.herokutorre3.services;

import com.nebulahelix.torre3.herokutorre3.DAO.UsuarioDAO;
import com.nebulahelix.torre3.herokutorre3.entity.Usuario;
import com.nebulahelix.torre3.herokutorre3.entity.Skill;
import com.nebulahelix.torre3.herokutorre3.entity.Proyecto;
import com.nebulahelix.torre3.herokutorre3.util.Encriptado;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Chemasmas
 */
@Path("/api/usuario")
public class UsuarioService {

    /*
    * https://torre3uam.herokuapp.com/usuario/test
    */
    @POST
    @Path("/test")
    public String test(@FormParam("parametro") String param)
    {
        System.out.println("LLamando a TEST "+param);
        return "Si llega?"+param;
    }
    
    /*
    https://torre3uam.herokuapp.com/usuario/login
    */
    @POST
    @Path("/login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public String Login(@FormParam("usr") String usr,
                        @FormParam("pwd") String pwd)
    {
        System.out.println("Alguien se Conecto con "+usr+" y "+pwd);
        return "{usr :'"+usr+"' , pwd: '"+pwd+"' }";
    }
    
    /*
    https://torre3uam.herokuapp.com/usuario/register
    */
    @POST
    @Path("/register")
    @Produces(MediaType.TEXT_PLAIN)
    public String Registrar(
                        @FormParam("usr") String usuario,
                        @FormParam("pwd") String contrasena,
                        @FormParam("nombre") String nombre,
                        @FormParam("matricula") String matricula,
                        @FormParam("carrera") String carrera,
                        @FormParam("habilidades") String habilidades,
                        @FormParam("servicios") String servicios,
                        @FormParam("conocimientos") String conocimientos,
                        @FormParam("intereses") String intereses,
                        @FormParam("correo") String correo,
                        @FormParam("celular") String celular,
                        @FormParam("proyectosPrevios") String proyectosPrevios,
                        @FormParam("proyectosActuales") String proyectosActuales)
    {
        Usuario u=new Usuario();
        u.setUsername(usuario);
        u.setPassword(Encriptado.SHA256(contrasena));     //Aui falta algo :D
        
        u.setNombreCompleto(nombre);
        u.setMatricula(matricula);
        u.setTelCelular(celular);
        u.setCorreo(correo);
        u.setCarrera(carrera);
        
        //Skills
        ArrayList<Skill> skills=new ArrayList<Skill>();
        StringTokenizer sth=new StringTokenizer(habilidades,",");
        while(sth.hasMoreTokens())
        {
            Skill a=new Skill();
            a.setSkill(sth.nextToken());
            a.setTipo(1);
            skills.add(a);
        }
        
        StringTokenizer sts=new StringTokenizer(servicios,",");
        while(sts.hasMoreTokens())
        {
            Skill a=new Skill();
            a.setSkill(sts.nextToken());
            a.setTipo(2);
            skills.add(a);
        }
        
        StringTokenizer stc=new StringTokenizer(conocimientos,",");
        while(stc.hasMoreTokens())
        {
            Skill a=new Skill();
            a.setSkill(stc.nextToken());
            a.setTipo(3);
            skills.add(a);
        }
        
        StringTokenizer sti=new StringTokenizer(intereses,",");
        while(sti.hasMoreTokens())
        {
            Skill a=new Skill();
            a.setSkill(sti.nextToken());
            a.setTipo(4);
            skills.add(a);
        }
        u.setSkills(skills);
        
        //Proyectos
        ArrayList<Proyecto> proyectos=new ArrayList<Proyecto>();
        StringTokenizer stp=new StringTokenizer(proyectosPrevios,",");
        while(stp.hasMoreTokens())
        {
            Proyecto p=new Proyecto();
            p.setActivo(false);
            StringTokenizer separa=new StringTokenizer(stp.nextToken(),":");
            p.setNombre(separa.nextToken()); // nombre del proyecto
            if(separa.hasMoreTokens())
                p.setDescripcion(separa.nextToken());// si la coloco una descripcion
            
            proyectos.add(p);
        }
        
        StringTokenizer sta=new StringTokenizer(proyectosActuales,",");
        while(sta.hasMoreTokens())
        {
            Proyecto p=new Proyecto();
            p.setActivo(true);
            StringTokenizer separa=new StringTokenizer(sta.nextToken(),":");
            p.setNombre(separa.nextToken()); // nombre del proyecto
            if(separa.hasMoreTokens())
                p.setDescripcion(separa.nextToken()); // si la coloco una descripcion
            
            proyectos.add(p);
        }
        
        u.setProyectos(proyectos);
        
        UsuarioDAO.guardarUsuario(u);
        
        
        return null;
    }
}
